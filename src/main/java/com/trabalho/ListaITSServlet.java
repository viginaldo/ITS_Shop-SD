package com.trabalho;

import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/lista")
public class ListaITSServlet extends HttpServlet {
    private Map<String, Equipamento> estoque = new HashMap<>();

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
        estoque.put("13", new Equipamento("13", "iPhone 13 (128GB)", 32000.00));
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

        // === HTML HEADER ===
        out.println("<!DOCTYPE html><html lang='pt'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Catálogo - ITS Shop</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css' rel='stylesheet'>");
        out.println("<link rel='icon' href='" + contextPath + "/favicons/favicon.ico' type='image/x-icon'>");
        // OG TAGS (WHATSAPP PREVIEW)
        out.println("<meta property='og:title' content='ITS Shop - Tecnologia em Nampula'>");
        out.println("<meta property='og:description' content='Loja de TI com 20 produtos, entrega rápida em Moçambique!'>");
        out.println("<meta property='og:image' content='https://itsshop-sd-production.up.railway.app/imagens/preview.png'>");
        out.println("<meta property='og:image:type' content='image/png'>");
        out.println("<meta property='og:image:width' content='1200'>");
        out.println("<meta property='og:image:height' content='630'>");
        out.println("<meta property='og:url' content='https://itsshop-sd-production.up.railway.app'>");
        out.println("</head><body>");

        // === NAVBAR COM CONTADOR ===
        out.println("<nav class='navbar navbar-expand-lg navbar-dark bg-dark fixed-top'>");
        out.println("<div class='container-fluid'>");
        out.println("<a class='navbar-brand' href='" + contextPath + "/index.html'>ITS Shop</a>");
        out.println("<button class='navbar-toggler' type='button' data-bs-toggle='collapse' data-bs-target='#navbarNav'>");
        out.println("<span class='navbar-toggler-icon'></span></button>");
        out.println("<div class='collapse navbar-collapse' id='navbarNav'>");
        out.println("<ul class='navbar-nav ms-auto'>");
        out.println("<li class='nav-item'><a class='nav-link' href='/lista'>Catálogo</a></li>");
        out.println("<li class='nav-item'>");
        out.println("<a class='nav-link position-relative' href='/carrinho'>");
        out.println("<i class='bi bi-cart'></i>");
        out.println("<span class='position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger' id='cart-count'>0</span>");
        out.println("</a></li></ul></div></div></nav>");

        out.println("<div class='container py-5 mt-5'>");
        out.println("<h2 class='text-center mb-5'>Catálogo de Produtos</h2>");
        out.println("<div class='row row-cols-1 row-cols-md-3 g-4'>");

        // === PRODUTOS COM ÍCONE CARRINHO ===
        for (Equipamento e : estoque.values()) {
            out.println("<div class='col'>");
            out.println("<div class='card h-100 position-relative'>");
            out.println("<button class='btn btn-sm btn-outline-primary position-absolute top-0 end-0 m-2 z-3' ");
            out.println("onclick=\"toggleCart('" + e.getId() + "')\" title='Adicionar ao carrinho'>");
            out.println("<i class='bi bi-cart-plus' id='cart-icon-" + e.getId() + "'></i></button>");
            out.println("<img src='" + contextPath + "/imagens/" + e.getId() + ".jpg' class='card-img-top' alt='" + e.getNome() + "'>");
            out.println("<div class='card-body d-flex flex-column'>");
            out.println("<h5 class='card-title'>" + e.getNome() + "</h5>");
            out.println("<p class='card-text text-success fw-bold'>" + e.getPreco() + " MT</p>");
            out.println("</div></div></div>");
        }

        out.println("</div></div>");

        // === FOOTER COM EQUIPE ===
        out.println("<footer class='bg-dark text-white py-5 mt-5'>");
        out.println("<div class='container'>");
        out.println("<div class='row text-center'>");
        out.println("<div class='col'><img src='" + contextPath + "/imagens/devs/ana.jpg' width='80' class='rounded-circle'><br>Ana Silva<br>2023001</div>");
        out.println("<div class='col'><img src='" + contextPath + "/imagens/devs/joao.jpg' width='80' class='rounded-circle'><br>João Mendes<br>2023002</div>");
        out.println("<div class='col'><img src='" + contextPath + "/imagens/devs/maria.jpg' width='80' class='rounded-circle'><br>Maria Lopes<br>2023003</div>");
        out.println("<div class='col'><img src='" + contextPath + "/imagens/devs/antonio.jpg' width='80' class='rounded-circle'><br>António Muianga<br>2023004</div>");
        out.println("<div class='col'><img src='" + contextPath + "/imagens/devs/yuyu.jpg' width='80' class='rounded-circle'><br>Yunus Suelmia<br>20210382</div>");
        out.println("</div></div></footer>");

        // === BOTÃO VOLTAR AO TOPO ===
        out.println("<button onclick='scrollToTop()' id='btnTop' class='btn btn-success rounded-circle shadow-lg' ");
        out.println("style='position:fixed; bottom:30px; right:30px; width:50px; height:50px; display:none; z-index:1000;'>");
        out.println("<i class='bi bi-arrow-up'></i></button>");

        // === JAVASCRIPT DO CARRINHO ===
        out.println("<script>");
        out.println("let cart = JSON.parse(sessionStorage.getItem('cart')) || {};");
        out.println("function updateCount() {");
        out.println("  const count = Object.values(cart).reduce((a,b)=>a+b,0);");
        out.println("  document.getElementById('cart-count').textContent = count;");
        out.println("  for (let id in cart) {");
        out.println("    const icon = document.getElementById('cart-icon-'+id);");
        out.println("    if (icon) icon.classList.replace('bi-cart-plus', 'bi-cart-check');");
        out.println("  }");
        out.println("}");
        out.println("function toggleCart(id) {");
        out.println("  if (cart[id]) { delete cart[id]; document.getElementById('cart-icon-'+id).classList.replace('bi-cart-check','bi-cart-plus'); }");
        out.println("  else { cart[id] = 1; document.getElementById('cart-icon-'+id).classList.replace('bi-cart-plus','bi-cart-check'); }");
        out.println("  sessionStorage.setItem('cart', JSON.stringify(cart)); updateCount();");
        out.println("}");
        out.println("window.onload = updateCount;");
        out.println("window.onscroll = () => { document.getElementById('btnTop').style.display = (document.body.scrollTop > 300 || document.documentElement.scrollTop > 300) ? 'block' : 'none'; };");
        out.println("function scrollToTop() { window.scrollTo({top:0, behavior:'smooth'}); }");
        out.println("</script>");

        out.println("</body></html>");
    }
}