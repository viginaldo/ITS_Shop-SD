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
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();

        out.println("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Confirmação</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("</head><body class='bg-success text-white text-center py-5'>");
        out.println("<div class='container'>");
        out.println("<h1>Compra Confirmada!</h1>");
        out.println("<p>Obrigado pela preferência. Seu pedido foi enviado.</p>");
        out.println("<a href='/lista' class='btn btn-light'>Voltar ao Catálogo</a>");
        out.println("</div></body></html>");
    }
}