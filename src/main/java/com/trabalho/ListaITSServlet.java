package com.trabalho;

import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/lista")
public class ListaITSServlet extends HttpServlet {
    Map<String, Equipamento> estoque = new HashMap<>();

    @Override
    public void init() throws ServletException {
        estoque.put("1", new Equipamento("1", "PC Gamer i7 (Intel Core i7, 16GB RAM, RTX 3060)", 45000.00));
        estoque.put("2", new Equipamento("2", "Monitor 27\" 144Hz IPS (LG ou Samsung)", 8500.00));
        estoque.put("3", new Equipamento("3", "Teclado Mecânico RGB (Logitech ou Redragon)", 1800.00));
        estoque.put("4", new Equipamento("4", "Mouse Wireless Gamer 16000 DPI (Logitech Pebble 2)", 1200.00));
        estoque.put("5", new Equipamento("5", "Laptop Dell Latitude 5430 i7 (16GB RAM, 512GB SSD)", 55000.00));
        estoque.put("6", new Equipamento("6", "Smartphone Samsung Galaxy A54 (128GB, 8GB RAM)", 14500.00));
        estoque.put("7", new Equipamento("7", "Impressora HP DeskJet 2822e Multifunções", 3200.00));
        estoque.put("8", new Equipamento("8", "Webcam HD Logitech C920 (1080p)", 2500.00));
        estoque.put("9", new Equipamento("9", "Laptop Lenovo ThinkPad L15 i5 (8GB RAM, 512GB SSD)", 28000.00));
        estoque.put("10", new Equipamento("10", "Headset Gamer RGB (HyperX ou Razer)", 2200.00));
        estoque.put("11", new Equipamento("11", "SSD 1TB NVMe (Samsung 970 EVO)", 4500.00));
        estoque.put("12", new Equipamento("12", "RAM 16GB DDR4 3200MHz (Kingston)", 2800.00));
        estoque.put("13", new Equipamento("13", "Smartphone iPhone 13 (128GB)", 32000.00));
        estoque.put("14", new Equipamento("14", "Impressora Laser HP LaserJet MFP M28w", 4800.00));
        estoque.put("15", new Equipamento("15", "Tablet Samsung Galaxy Tab A8 (32GB)", 8500.00));
        estoque.put("16", new Equipamento("16", "Desktop Lenovo ThinkCentre i5 (8GB RAM, 256GB SSD)", 22000.00));
        estoque.put("17", new Equipamento("17", "Câmera de Segurança WiFi (TP-Link Tapo)", 1500.00));
        estoque.put("18", new Equipamento("18", "Pen Drive 128GB USB 3.0 (SanDisk)", 800.00));
        estoque.put("19", new Equipamento("19", "Power Bank 10000mAh (Anker ou Xiaomi)", 1200.00));
        estoque.put("20", new Equipamento("20", "Hub USB 3.0 4 Portas (Anker)", 900.00));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        String contextPath = req.getContextPath();

        if (estoque.isEmpty()) init();

        out.println("<!DOCTYPE html><html lang='pt'><head>");
        out.println("<meta charset='UTF-8'><title>ITS Shop | Catálogo</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css' rel='stylesheet'>");
        out.println("<style>.card-img-top{height:200px;object-fit:contain;}</style>");
        out.println("</head><body>");

        // NAVBAR
        out.println("<nav class='navbar navbar-dark bg-dark fixed-top'>");
        out.println("<div class='container-fluid d-flex justify-content-between'>");
        out.println("<a class='btn btn-outline-light' href='" + contextPath + "/index.html'>Sair</a>");
        out.println("<a class='navbar-brand mx-auto' href='#'>ITS Shop</a>");
        out.println("<a class='btn btn-outline-light' href='#about'>About Us</a>");
        out.println("</div></nav>");
        out.println("<div style='height:70px;'></div>");

        out.println("<div class='container py-5'>");
        out.println("<h2 class='text-center mb-5'>Catálogo de Equipamentos</h2>");
        out.println("<div class='row row-cols-1 row-cols-md-3 g-4'>");

        for (Equipamento e : estoque.values()) {
            String img = "imagens/" + e.getId() + ".jpg";
            out.println("<div class='col'>");
            out.println("<div class='card h-100 position-relative'>");
            out.println("<button class='btn btn-sm position-absolute top-0 end-0 m-2' style='background:white;' onclick=\"addToCart('" + e.getId() + "')\">");
            out.println("<i class='fas fa-cart-plus text-success' id='icon-" + e.getId() + "'></i></button>");
            out.println("<img src='" + contextPath + "/" + img + "' class='card-img-top' alt='" + e.getNome() + "'>");
            out.println("<div class='card-body d-flex flex-column'>");
            out.println("<h5 class='card-title'>" + e.getNome() + "</h5>");
            out.println("<p class='card-text text-success fw-bold'>" + e.getPreco() + " MT</p>");
            out.println("<a href='/carrinho?id=" + e.getId() + "' class='btn btn-success mt-auto'>Comprar</a>");
            out.println("</div></div></div>");
        }

        out.println("</div></div>");

        // CARRINHO FLUTUANTE
        out.println("<a href='/carrinho' class='btn btn-primary rounded-circle position-fixed' style='bottom:30px;right:30px;width:60px;height:60px;z-index:1000;'>");
        out.println("<i class='fas fa-shopping-cart fs-4'></i>");
        out.println("<span class='badge bg-danger position-absolute top-0 start-100 translate-middle' id='cart-count'>0</span>");
        out.println("</a>");

        // FOOTER
        out.println("<footer id='about' class='bg-dark text-white py-5 mt-5'>");
        out.println("<div class='container text-center'>");
        out.println("<h3>Grupo 4</h3>");
        out.println("<div class='row row-cols-5 g-3'>");
        out.println("<div class='col'><img src='" + contextPath + "/imagens/devs/langa.jpg' width='80' class='rounded-circle'><br>Patrick Langa<br>20231003</div>");
        out.println("<div class='col'><img src='" + contextPath + "/imagens/devs/ryry.jpg' width='80' class='rounded-circle'><br>Ryazy Hassane<br>20230304</div>");
        out.println("<div class='col'><img src='" + contextPath + "/imagens/devs/tig.jpg' width='80' class='rounded-circle'><br>Tiago Correia<br>20200018</div>");
        out.println("<div class='col'><img src='" + contextPath + "/imagens/devs/vigi.jpg' width='80' class='rounded-circle'><br>Viginaldo Joaquim<br>20210982</div>");
        out.println("<div class='col'><img src='" + contextPath + "/imagens/devs/yuyu.jpg' width='80' class='rounded-circle'><br>Yunus Suelmia<br>20210382</div>");
        out.println("</div></div></footer>");

        // JS
        out.println("<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js'></script>");
        out.println("<script>");
        out.println("let cart = JSON.parse(sessionStorage.getItem('cart')) || {};");
        out.println("function addToCart(id) {");
        out.println("  cart[id] = (cart[id] || 0) + 1;");
        out.println("  sessionStorage.setItem('cart', JSON.stringify(cart));");
        out.println("  updateCount();");
        out.println("  document.getElementById('icon-'+id).className = 'fas fa-check text-primary';");
        out.println("}");
        out.println("function updateCount() {");
        out.println("  const count = Object.values(cart).reduce((a,b)=>a+b,0);");
        out.println("  document.getElementById('cart-count').textContent = count;");
        out.println("}");
        out.println("window.onload = updateCount;");
        out.println("</script>");

        out.println("</body></html>");
    }
}