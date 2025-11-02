package com.trabalho;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/confirmacao")
public class ConfirmacaoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();

        String idItem = req.getParameter("idItem");
        String quantidade = req.getParameter("quantidade");
        String total = req.getParameter("total");
        String nomeCliente = req.getParameter("nomeCliente");
        String endereco = req.getParameter("endereco");
        String numeroCartao = req.getParameter("numeroCartao");

        String cartaoMascarado = (numeroCartao != null && numeroCartao.matches("\\d{16}"))
            ? "************" + numeroCartao.substring(12)
            : "************XXX";

        out.println("<!DOCTYPE html><html lang='pt'><head>");
        out.println("<meta charset='UTF-8'><title>Recibo</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<style>body{background:linear-gradient(135deg, #56ab2f, #a8e6cf); min-height:100vh;}</style>");
        out.println("</head><body class='d-flex align-items-center justify-content-center'>");

        out.println("<div class='card shadow-lg mx-auto' style='max-width:600px;'>");
        out.println("<div class='card-header bg-success text-white text-center'>");
        out.println("<h3>Compra Confirmada!</h3></div>");
        out.println("<div class='card-body'>");
        out.println("<p><strong>Produto ID:</strong> " + idItem + "</p>");
        out.println("<p><strong>Quantidade:</strong> " + quantidade + "</p>");
        out.println("<p class='text-primary fw-bold fs-5'>Total: " + total + " MT</p>");
        out.println("<hr><p><strong>Cliente:</strong> " + nomeCliente + "</p>");
        out.println("<p><strong>Entrega:</strong> " + endereco + "</p>");
        out.println("<hr><p><strong>Cartão:</strong> " + cartaoMascarado + "</p>");
        out.println("<div class='text-center mt-4'>");
        out.println("<a href='" + req.getContextPath() + "/index.html' class='btn btn-outline-primary btn-lg'>Voltar à Loja</a>");
        out.println("</div></div></div></body></html>");
    }
}