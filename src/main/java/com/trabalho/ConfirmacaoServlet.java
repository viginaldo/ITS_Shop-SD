package com.trabalho;

import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class ConfirmacaoServlet extends HttpServlet {

    // ESTOQUE (cópia do ListaITSServlet para buscar nome pelo ID)
    private static final Map<String, Equipamento> ESTOQUE = new HashMap<>();
    static {
        ESTOQUE.put("1", new Equipamento("1", "PC Gamer i7 (Intel Core i7, 16GB RAM, RTX 3060)", 45000.00));
        ESTOQUE.put("2", new Equipamento("2", "Monitor 27\" 144Hz IPS (LG ou Samsung)", 8500.00));
        ESTOQUE.put("3", new Equipamento("3", "Teclado Mecânico RGB (Logitech ou Redragon)", 1800.00));
        ESTOQUE.put("4", new Equipamento("4", "Mouse Wireless Gamer 16000 DPI (Logitech Pebble 2)", 1200.00));
        ESTOQUE.put("5", new Equipamento("5", "Laptop Dell Latitude 5430 i7 (16GB RAM, 512GB SSD)", 55000.00));
        ESTOQUE.put("6", new Equipamento("6", "Smartphone Samsung Galaxy A54 (128GB, 8GB RAM)", 14500.00));
        ESTOQUE.put("7", new Equipamento("7", "Impressora HP DeskJet 2822e Multifunções", 3200.00));
        ESTOQUE.put("8", new Equipamento("8", "Webcam HD Logitech C920 (1080p)", 2500.00));
        ESTOQUE.put("9", new Equipamento("9", "Laptop Lenovo ThinkPad L15 i5 (8GB RAM, 512GB SSD)", 28000.00));
        ESTOQUE.put("10", new Equipamento("10", "Headset Gamer RGB (HyperX ou Razer)", 2200.00));
        ESTOQUE.put("11", new Equipamento("11", "SSD 1TB NVMe (Samsung 970 EVO)", 4500.00));
        ESTOQUE.put("12", new Equipamento("12", "RAM 16GB DDR4 3200MHz (Kingston)", 2800.00));
        ESTOQUE.put("13", new Equipamento("13", "Smartphone iPhone 13 (128GB)", 32000.00));
        ESTOQUE.put("14", new Equipamento("14", "Impressora Laser HP LaserJet MFP M28w", 4800.00));
        ESTOQUE.put("15", new Equipamento("15", "Tablet Samsung Galaxy Tab A8 (32GB)", 8500.00));
        ESTOQUE.put("16", new Equipamento("16", "Desktop Lenovo ThinkCentre i5 (8GB RAM, 256GB SSD)", 22000.00));
        ESTOQUE.put("17", new Equipamento("17", "Câmera de Segurança WiFi (TP-Link Tapo)", 1500.00));
        ESTOQUE.put("18", new Equipamento("18", "Pen Drive 128GB USB 3.0 (SanDisk)", 800.00));
        ESTOQUE.put("19", new Equipamento("19", "Power Bank 10000mAh (Anker ou Xiaomi)", 1200.00));
        ESTOQUE.put("20", new Equipamento("20", "Hub USB 3.0 4 Portas (Anker)", 900.00));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();

        String total = req.getParameter("total");
        String nomeCliente = req.getParameter("nomeCliente");
        String endereco = req.getParameter("endereco");
        String numeroCartao = req.getParameter("numeroCartao");

        List<String> itens = new ArrayList<>();
        Enumeration<String> params = req.getParameterNames();

        while (params.hasMoreElements()) {
            String p = params.nextElement();
            if (p.startsWith("qtd_")) {
                String id = p.substring(4);
                String qtd = req.getParameter(p);
                Equipamento eq = ESTOQUE.get(id);
                String nome = eq != null ? eq.getNome() : "Produto Desconhecido";
                itens.add(nome + " (x" + qtd + ")");
            } else if (p.equals("idItem")) {
                String id = req.getParameter("idItem");
                String qtd = req.getParameter("quantidade");
                Equipamento eq = ESTOQUE.get(id);
                String nome = eq != null ? eq.getNome() : "Produto Desconhecido";
                itens.add(nome + " (x" + qtd + ")");
                total = req.getParameter("total");
            }
        }

        String cartaoMascarado = (numeroCartao != null && numeroCartao.matches("\\d{16}"))
            ? "**** **** **** " + numeroCartao.substring(12)
            : "**** **** **** XXXX";

        out.println("<!DOCTYPE html><html lang='pt'><head>");
        out.println("<meta charset='UTF-8'><title>Recibo</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<style>body{background:linear-gradient(135deg, #56ab2f, #a8e6cf); min-height:100vh;}</style>");
        out.println("</head><body class='d-flex align-items-center justify-content-center'>");

        out.println("<div class='container'>");
        out.println("<div class='card shadow-lg mx-auto' style='max-width:600px;'>");
        out.println("<div class='card-header bg-success text-white text-center'>");
        out.println("<h3>Compra Confirmada!</h3></div>");
        out.println("<div class='card-body'>");

        out.println("<p><strong>Itens:</strong> " + (itens.isEmpty() ? "Nenhum" : String.join(", ", itens)) + "</p>");
        out.println("<p class='text-primary fw-bold fs-5'>Total: " + total + " MT</p>");

        out.println("<hr>");
        out.println("<p><strong>Cliente:</strong> " + (nomeCliente != null ? nomeCliente : "Anônimo") + "</p>");
        out.println("<p><strong>Entrega:</strong> " + (endereco != null ? endereco : "Não informado") + "</p>");

        out.println("<hr>");
        out.println("<p><strong>Cartão:</strong> " + cartaoMascarado + "</p>");

        out.println("<div class='text-center mt-4'>");
        out.println("<a href='" + req.getContextPath() + "/lista' class='btn btn-outline-primary btn-lg px-5'>Voltar ao Catálogo</a>");
        out.println("</div></div></div></div>");

        out.println("</body></html>");
    }
}