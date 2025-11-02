package com.trabalho;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class ConfirmacaoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();

        // === PEGA ESTOQUE COMPARTILHADO ===
        @SuppressWarnings("unchecked")
        Map<String, Equipamento> estoque = (Map<String, Equipamento>) 
            req.getServletContext().getAttribute("estoque");

        boolean catalogoCarregado = estoque != null && !estoque.isEmpty();

        // === DADOS DO CLIENTE (sempre capturados) ===
        String nomeCliente = req.getParameter("nomeCliente");
        String endereco = req.getParameter("endereco");
        String numeroCartao = req.getParameter("numeroCartao");
        String totalStr = req.getParameter("total");

        String cartaoMascarado = (numeroCartao != null && numeroCartao.matches("\\d{16}"))
            ? "**** **** **** " + numeroCartao.substring(12)
            : "**** **** **** XXXX";

        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter fmtData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter fmtHora = DateTimeFormatter.ofPattern("HH:mm");
        String data = fmtData.format(agora);
        String hora = fmtHora.format(agora);
        String docNum = "VD_" + agora.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        // === INÍCIO DO HTML ===
        out.println("<!DOCTYPE html><html lang='pt'><head>");
        out.println("<meta charset='UTF-8'><title>Recibo</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css' rel='stylesheet'>");
        out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js'></script>");
        out.println("<style>");
        out.println("@media print { .no-print { display: none; } }");
        out.println(".recibo { max-width: 380px; margin: 20px auto; font-family: 'Courier New', monospace; font-size: 13px; line-height: 1.3; }");
        out.println("th, td { font-size: 12px; padding: 2px 0; }");
        out.println(".text-right { text-align: right; }");
        out.println("hr { border: 0; border-top: 1px dashed #000; margin: 8px 0; }");
        out.println("</style></head><body>");

        // === BOTÃO DE DOWNLOAD SEMPRE VISÍVEL ===
        out.println("<div class='position-fixed' style='bottom:20px; right:20px; z-index:1000;'>");
        out.println("<button onclick='gerarPDF()' class='btn btn-success btn-sm rounded-circle shadow' title='Baixar Recibo'>");
        out.println("<i class='fas fa-download'></i></button>");
        out.println("</div>");

        out.println("<div class='recibo bg-white p-3 border'>");

        // === CABEÇALHO ===
        out.println("<div class='text-center'>");
        out.println("<h6 class='fw-bold mb-0'>ITS SHOP & TECH S.A.</h6>");
        out.println("<small>Av. 24 de Julho, NAMPULA<br>NAMPULA - CIDADE<br>N.U.I.T. 40190268</small>");
        out.println("</div><hr>");

        out.println("<div class='d-flex justify-content-between'>");
        out.println("<small>Data: " + data + " " + hora + "</small>");
        out.println("<small>Caixa: 2</small>");
        out.println("</div>");
        out.println("<small>Número do documento: " + docNum + "</small>");
        out.println("<small>Nome: CLIENTE GERAL</small><hr>");

        // === SE CATÁLOGO NÃO CARREGADO ===
        if (!catalogoCarregado) {
            out.println("<div class='text-center text-danger'>");
            out.println("<p><strong>Erro: Catálogo não carregado.</strong></p>");
            out.println("<p><a href='" + req.getContextPath() + "/lista'>Tente novamente</a></p>");
            out.println("</div>");
        } else {
            // === ITENS COMPRADOS ===
            List<ItemCompra> itens = new ArrayList<>();
            double total = 0.0;
            Enumeration<String> params = req.getParameterNames();

            while (params.hasMoreElements()) {
                String p = params.nextElement();
                if (p.startsWith("qtd_")) {
                    String id = p.substring(4);
                    int qtd = Integer.parseInt(req.getParameter(p));
                    Equipamento eq = estoque.get(id);
                    if (eq != null) {
                        double subtotal = eq.getPreco() * qtd;
                        total += subtotal;
                        itens.add(new ItemCompra(eq.getNome(), eq.getPreco(), qtd, subtotal));
                    }
                } else if (p.equals("idItem")) {
                    String id = req.getParameter("idItem");
                    int qtd = Integer.parseInt(req.getParameter("quantidade"));
                    double subt = Double.parseDouble(req.getParameter("total"));
                    Equipamento eq = estoque.get(id);
                    if (eq != null) {
                        itens.add(new ItemCompra(eq.getNome(), eq.getPreco(), qtd, subt));
                        total = subt;
                    }
                }
            }
            if (totalStr != null && !totalStr.isEmpty()) total = Double.parseDouble(totalStr);

            // === TABELA DE ITENS ===
            out.println("<table class='w-100'><thead><tr>");
            out.println("<th>QTD</th><th>DESCRIÇÃO</th><th class='text-right'>PREÇO</th><th class='text-right'>TOTAL</th>");
            out.println("</tr></thead><tbody>");

            for (ItemCompra item : itens) {
                out.println("<tr>");
                out.println("<td>" + item.qtd + " UNI</td>");
                out.println("<td>" + truncate(item.nome, 22) + "</td>");
                out.println("<td class='text-right'>" + String.format("%.2f", item.preco) + "</td>");
                out.println("<td class='text-right'>" + String.format("%.2f", item.subtotal) + "</td>");
                out.println("</tr>");
            }
            out.println("</tbody></table><hr>");

            // === TOTAIS ===
            out.println("<div class='d-flex justify-content-between'><strong>Total Venda:</strong> <strong>" + String.format("%.2f", total) + " MT</strong></div>");
            out.println("<div class='d-flex justify-content-between mt-1'><strong>TOTAL OFERTADO</strong> <strong>" + String.format("%.2f", total) + " MT</strong></div>");
            out.println("<div class='d-flex justify-content-between mt-1'><strong>CARTÃO</strong> <strong>" + String.format("%.2f", total) + " MT</strong></div><hr>");
        }

        // === DADOS DO CLIENTE ===
        out.println("<small><strong>Cliente:</strong> " + (nomeCliente != null ? nomeCliente : "Anônimo") + "</small><br>");
        out.println("<small><strong>Entrega:</strong> " + (endereco != null ? endereco : "Não informado") + "</small><br>");
        out.println("<small><strong>Cartão:</strong> " + cartaoMascarado + "</small><hr>");

        // === RODAPÉ ===
        out.println("<div class='text-center'>");
        out.println("<small>Obrigado pela preferência!<br>Processado por: ITS Shop System</small>");
        out.println("</div>");

        out.println("</div>"); // fim recibo

        // === SCRIPT PARA GERAR PDF ===
        out.println("<script>");
        out.println("function gerarPDF() {");
        out.println("  const { jsPDF } = window.jspdf;");
        out.println("  const doc = new jsPDF({ orientation: 'portrait', unit: 'mm', format: [80, 200] });");
        out.println("  const recibo = document.querySelector('.recibo').innerText;");
        out.println("  const linhas = recibo.split('\\n');");
        out.println("  let y = 10;");
        out.println("  linhas.forEach(linha => {");
        out.println("    if (y > 190) { doc.addPage(); y = 10; }");
        out.println("    doc.text(linha.trim(), 5, y);");
        out.println("    y += 4;");
        out.println("  });");
        out.println("  doc.save('recibo_" + docNum + ".pdf');");
        out.println("}");
        out.println("</script>");

        out.println("</body></html>");
    }

    private static class ItemCompra {
        String nome; double preco; int qtd; double subtotal;
        ItemCompra(String n, double p, int q, double s) { nome=n; preco=p; qtd=q; subtotal=s; }
    }

    private String truncate(String s, int max) {
        return s.length() > max ? s.substring(0, max-3) + "..." : s;
    }
}