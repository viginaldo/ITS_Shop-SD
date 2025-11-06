package com.trabalho;

import java.io.*;
import java.time.*;
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

        @SuppressWarnings("unchecked")
        Map<String, Equipamento> estoque = (Map<String, Equipamento>) req.getServletContext().getAttribute("estoque");

        if (estoque == null || estoque.isEmpty()) {
            out.println("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Erro</title></head><body>");
            out.println("<div class='text-center text-danger fw-bold'>ERRO: Catálogo não carregado.<br><a href='" + req.getContextPath() + "/lista'>Tente novamente</a></div>");
            out.println("</body></html>");
            return;
        }

        String nomeCliente = req.getParameter("nomeCliente");
        String endereco = req.getParameter("endereco");
        String numeroCartao = req.getParameter("numeroCartao");
        String totalStr = req.getParameter("total");

        String numeroCartaoLimpo = numeroCartao != null ? numeroCartao.replaceAll("\\D", "") : "";
        String cartaoMascarado = (numeroCartaoLimpo.length() == 16)
            ? "**** **** **** " + numeroCartaoLimpo.substring(12)
            : "**** **** **** XXXX";

        ZonedDateTime agora = ZonedDateTime.now(ZoneId.of("Africa/Maputo"));
        DateTimeFormatter fmtData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter fmtHora = DateTimeFormatter.ofPattern("HH:mm");
        String data = fmtData.format(agora);
        String hora = fmtHora.format(agora);
        String docNum = "VD_" + agora.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        out.println("<!DOCTYPE html><html lang='pt'><head>");
        out.println("<meta charset='UTF-8'><title>Recibo ITS</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css' rel='stylesheet'>");
        out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js'></script>");
        out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js'></script>");
        out.println("<style>");
        out.println(".recibo { max-width: 380px; margin: 20px auto; font-family: 'Courier New', monospace; font-size: 13px; line-height: 1.3; padding: 15px; border: 1px solid #000; background: #fff; }");
        out.println("th, td { font-size: 12px; padding: 1px 0; }");
        out.println(".text-right { text-align: right; }");
        out.println("hr { border-top: 1px dashed #000; margin: 6px 0; }");
        out.println("</style></head><body>");

        out.println("<div class='position-fixed' style='bottom:20px; right:20px; z-index:1000;'>");
        out.println("<button onclick='gerarPDF()' class='btn btn-success btn-sm rounded-circle shadow' title='Baixar Recibo'>");
        out.println("<i class='fas fa-download'></i></button>");
        out.println("</div>");

        out.println("<div class='recibo'>");
        out.println("<div class='text-center'><h6 class='fw-bold mb-0'>ITS SHOP & TECH S.A.</h6>");
        out.println("<small>Av. 24 de Julho, NAMPULA<br>NAMPULA - CIDADE<br>N.U.I.T. 40190268</small></div><hr>");
        out.println("<div class='d-flex justify-content-between'><small>Data: " + data + " " + hora + "</small><small>Caixa: 2</small></div>");
        out.println("<small>Nº doc: " + docNum + "</small><br><small>Nome: CLIENTE GERAL</small><hr>");
        
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
                    double sub = eq.getPreco() * qtd;
                    total += sub;
                    itens.add(new ItemCompra(eq.getNome(), eq.getPreco(), qtd, sub));
                }
            }
        }

        if (itens.isEmpty() && req.getParameter("idItem") != null) {
            String id = req.getParameter("idItem");
            int qtd = Integer.parseInt(req.getParameter("quantidade"));
            double sub = Double.parseDouble(req.getParameter("total"));
            Equipamento eq = estoque.get(id);
            if (eq != null) {
                itens.add(new ItemCompra(eq.getNome(), eq.getPreco(), qtd, sub));
                total = sub;
            }
        }

        if (totalStr != null && !totalStr.isEmpty()) total = Double.parseDouble(totalStr);

        out.println("<table class='w-100'><thead><tr><th>QTD</th><th>DESCRIÇÃO</th><th class='text-right'>PREÇO</th><th class='text-right'>TOTAL</th></tr></thead><tbody>");
        for (ItemCompra i : itens) {
            out.println("<tr><td>" + i.qtd + " UNI</td><td>" + truncate(i.nome, 22) + "</td><td class='text-right'>" + String.format("%.2f", i.preco) + "</td><td class='text-right'>" + String.format("%.2f", i.subtotal) + "</td></tr>");
        }
        out.println("</tbody></table><hr>");
        out.println("<div class='d-flex justify-content-between'><strong>Total Venda:</strong> <strong>" + String.format("%.2f", total) + " MT</strong></div>");
        out.println("<div class='d-flex justify-content-between'><strong>TOTAL OFERTADO</strong> <strong>" + String.format("%.2f", total-total) + " MT</strong></div>");
        out.println("<div class='d-flex justify-content-between'><strong>CARTÃO</strong> <strong>" + String.format("%.2f", total) + " MT</strong></div><hr>");

        out.println("<small><strong>Cliente:</strong> " + (nomeCliente != null ? nomeCliente : "Anônimo") + "</small><br>");
        out.println("<small><strong>Entrega:</strong> " + (endereco != null ? endereco : "Não informado") + "</small><br>");
        out.println("<small><strong>Cartão:</strong> " + cartaoMascarado + "</small><hr>");
        out.println("<div class='text-center'><small>Obrigado!<br>ITS Shop System</small></div>");
        out.println("</div>");

        out.println("<script>");
        out.println("function gerarPDF() {");
        out.println("  const recibo = document.querySelector('.recibo');");
        out.println("  html2canvas(recibo, {scale: 3, useCORS: true}).then(canvas => {");
        out.println("    const img = canvas.toDataURL('image/png');");
        out.println("    const { jsPDF } = window.jspdf;");
        out.println("    const pdf = new jsPDF('p', 'mm', [80, 300]);");
        out.println("    const width = pdf.internal.pageSize.getWidth();");
        out.println("    const height = (canvas.height * width) / canvas.width;");
        out.println("    pdf.addImage(img, 'PNG', 0, 0, width, height);");
        out.println("    pdf.save('recibo_ITS.pdf');");
        out.println("  });");
        out.println("}");
        out.println("</script>");
        out.println("<div class='d-flex gap-2 justify-content-center'>");
        out.println("<a href='" + req.getContextPath() + "/' class='btn btn-primary'><i class='fas fa-home'></i> Voltar</a> </div>");
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