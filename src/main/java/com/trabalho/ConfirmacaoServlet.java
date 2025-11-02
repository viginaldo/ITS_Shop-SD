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

        // PEGA ESTOQUE COMPARTILHADO
        @SuppressWarnings("unchecked")
		Map<String, Equipamento> estoque = (Map<String, Equipamento>) 
            req.getServletContext().getAttribute("estoque");
        if (estoque == null) {
            out.println("<h1>Erro: Catálogo não carregado.</h1>");
            return;
        }

        String totalStr = req.getParameter("total");
        String nomeCliente = req.getParameter("nomeCliente");
        String endereco = req.getParameter("endereco");
        String numeroCartao = req.getParameter("numeroCartao");

        List<ItemCompra> itens = new ArrayList<>();
        Enumeration<String> params = req.getParameterNames();
        double total = 0;

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

        if (totalStr != null && !totalStr.isEmpty()) {
            total = Double.parseDouble(totalStr);
        }

        String cartaoMascarado = (numeroCartao != null && numeroCartao.matches("\\d{16}"))
            ? "**** **** **** " + numeroCartao.substring(12)
            : "**** **** **** XXXX";

        // DATA E HORA
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter fmtData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter fmtHora = DateTimeFormatter.ofPattern("HH:mm");
        String data = fmtData.format(agora);
        String hora = fmtHora.format(agora);

        // NÚMERO DO DOCUMENTO
        String docNum = "VD_" + agora.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        out.println("<!DOCTYPE html><html lang='pt'><head>");
        out.println("<meta charset='UTF-8'><title>Recibo ITS Shop</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css' rel='stylesheet'>");
        out.println("<style>");
        out.println("body{background:#f8f9fa;font-family:Arial,sans-serif;}");
        out.println(".card-img-top{height:180px;object-fit:contain;background:#fff;padding:10px;}");
        out.println(".back-to-top{position:fixed;bottom:20px;right:20px;z-index:1000;display:none;}");
        out.println(".qty-input{width:60px;text-align:center;}");
        out.println(".form-step { display: none; }");
        out.println(".form-step.active { display: block; }");
        out.println("</style></head><body>");

        out.println("<div class='recibo bg-white p-4 shadow-sm'>");

        // CABEÇALHO
        out.println("<div class='text-center'>");
        out.println("<h5 class='fw-bold'>ITS SHOP & TECH S.A.</h5>");
        out.println("<small>Av. 24 de Julho, Maputo<br>");
        out.println("QUELIMANE - ZAMBEZIA<br>");
        out.println("N.U.I.T. 40190268</small>");
        out.println("</div><hr>");

        // DATA E NÚMERO
        out.println("<div class='row'>");
        out.println("<div class='col-7'><small>Data: " + data + " " + hora + "</small></div>");
        out.println("<div class='col-5 text-right'><small>Caixa: 2</small></div>");
        out.println("</div>");
        out.println("<div><small>Número do documento: " + docNum + "</small></div>");
        out.println("<div><small>Nome: CLIENTE GERAL</small></div><hr>");

        // TABELA DE ITENS
        out.println("<table class='table table-sm table-borderless'>");
        out.println("<thead><tr><th>QTD</th><th>DESCRIÇÃO</th><th class='text-right'>PREÇO</th><th class='text-right'>TOTAL</th></tr></thead>");
        out.println("<tbody>");

        for (ItemCompra item : itens) {
            out.println("<tr>");
            out.println("<td>" + item.qtd + " UNI</td>");
            out.println("<td>" + truncate(item.nome, 25) + "</td>");
            out.println("<td class='text-right'>" + String.format("%.2f", item.preco) + "</td>");
            out.println("<td class='text-right'>" + String.format("%.2f", item.subtotal) + "</td>");
            out.println("</tr>");
        }

        out.println("</tbody></table><hr>");

        // TOTAIS
        out.println("<div class='row'>");
        out.println("<div class='col-7'><strong>Total Venda:</strong></div>");
        out.println("<div class='col-5 text-right'><strong>" + String.format("%.2f", total) + " MT</strong></div>");
        out.println("</div>");

        out.println("<div class='row mt-2'>");
        out.println("<div class='col-7'><strong>TOTAL OFERTADO</strong></div>");
        out.println("<div class='col-5 text-right'><strong>" + String.format("%.2f", total) + " MT</strong></div>");
        out.println("</div>");

        out.println("<div class='row mt-2'>");
        out.println("<div class='col-7'><strong>CARTÃO</strong></div>");
        out.println("<div class='col-5 text-right'><strong>" + String.format("%.2f", total) + " MT</strong></div>");
        out.println("</div><hr>");

        // DADOS DO CLIENTE
        out.println("<small><strong>Cliente:</strong> " + (nomeCliente != null ? nomeCliente : "Anônimo") + "</small><br>");
        out.println("<small><strong>Entrega:</strong> " + (endereco != null ? endereco : "Não informado") + "</small><br>");
        out.println("<small><strong>Cartão:</strong> " + cartaoMascarado + "</small><hr>");

        // RODAPÉ
        out.println("<div class='text-center'>");
        out.println("<small>Obrigado pela preferência!<br>");
        out.println("Processado por: ITS Shop System</small>");
        out.println("</div>");

        // BOTÃO DE DOWNLOAD (CANTO INFERIOR DIREITO)
        out.println("<div class='position-fixed' style='bottom:20px; right:20px;'>");
        out.println("<button onclick='window.print()' class='btn btn-success btn-sm rounded-circle shadow no-print' title='Baixar Recibo'>");
        out.println("<i class='fas fa-download'></i></button>");
        out.println("</div>");

        out.println("</div>"); // fim recibo

        out.println("<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js'></script>");
        out.println("</body></html>");
    }

    // CLASSE AUXILIAR
    private static class ItemCompra {
        String nome;
        double preco;
        int qtd;
        double subtotal;
        ItemCompra(String n, double p, int q, double s) { nome=n; preco=p; qtd=q; subtotal=s; }
    }

    // TRUNCAR NOME LONGO
    private String truncate(String s, int max) {
        return s.length() > max ? s.substring(0, max-3) + "..." : s;
    }
}