package com.trabalho;

import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

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
        String cp = req.getContextPath();

        if (estoque.isEmpty()) init();

        out.println("<!DOCTYPE html><html lang='pt'><head>");
        out.println("<meta charset='UTF-8'><title>ITS Shop | Catálogo</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css' rel='stylesheet'>");
        out.println("<style>");
        out.println("body{background:#f8f9fa;font-family:Arial,sans-serif;}");
        out.println(".card-img-top{height:180px;object-fit:contain;background:#fff;padding:10px;}");
        out.println(".offcanvas-body img{max-height:80px;object-fit:contain;}");
        out.println(".back-to-top{position:fixed;bottom:20px;right:20px;z-index:1000;display:none;}");
        out.println("</style></head><body>");

        // NAVBAR
        out.println("<nav class='navbar navbar-expand-lg navbar-dark bg-dark fixed-top'>");
        out.println("<div class='container-fluid'>");
        out.println("<a class='navbar-brand' href='#'>ITS Shop</a>");
        out.println("<button class='navbar-toggler' type='button' data-bs-toggle='collapse' data-bs-target='#navbarNav'>");
        out.println("<span class='navbar-toggler-icon'></span></button>");
        out.println("<div class='collapse navbar-collapse' id='navbarNav'>");
        out.println("<ul class='navbar-nav ms-auto'>");
        out.println("<li class='nav-item'><a class='nav-link' href='" + cp + "/index.html'>Sair</a></li>");
        out.println("<li class='nav-item'><a class='nav-link' href='#about'>About Us</a></li>");
        out.println("</ul></div></div></nav>");
        out.println("<div style='height:70px;'></div>");

        // CATÁLOGO
        out.println("<div class='container py-4'>");
        out.println("<h2 class='text-center mb-4'>Catálogo de Equipamentos</h2>");
        out.println("<div class='row row-cols-1 row-cols-md-3 g-4'>");

        for (Equipamento e : estoque.values()) {
            out.println("<div class='col'>");
            out.println("<div class='card h-100 shadow-sm'>");
            out.println("<div class='position-relative'>");
            out.println("<button class='btn btn-sm position-absolute top-0 end-0 m-2 bg-white rounded-circle' onclick=\"addToCart('" + e.getId() + "')\">");
            out.println("<i class='fas fa-cart-plus text-success' id='icon-" + e.getId() + "'></i></button>");
            out.println("<img src='" + cp + "/imagens/" + e.getId() + ".jpg' class='card-img-top' alt='" + e.getNome() + "'>");
            out.println("</div>");
            out.println("<div class='card-body d-flex flex-column'>");
            out.println("<h5 class='card-title'>" + e.getNome() + "</h5>");
            out.println("<p class='card-text text-success fw-bold mt-auto'>" + e.getPreco() + " MT</p>");
            out.println("<button class='btn btn-success btn-sm mt-2' data-bs-toggle='offcanvas' data-bs-target='#offcanvasCompra' onclick=\"abrirOffcanvas('" + e.getId() + "')\">Comprar</button>");
            out.println("</div></div></div>");
        }

        out.println("</div></div>");

        // FOOTER
        out.println("<footer id='about' class='bg-dark text-white py-5 mt-5'>");
        out.println("<div class='container text-center'>");
        out.println("<h3>Grupo 4</h3>");
        out.println("<div class='row row-cols-5 g-3 justify-content-center'>");
        out.println("<div class='col text-center'>");
        out.println("<img src='" + cp + "/imagens/devs/langa.jpg' width='70' class='rounded-circle'><br>Patrick Langa<br>20231003");
        out.println("</div>");
        out.println("<div class='col text-center'>");
        out.println("<img src='" + cp + "/imagens/devs/ryry.jpg' width='70' class='rounded-circle'><br>Ryazy Hassane<br>20230304");
        out.println("</div>");
        out.println("<div class='col text-center'>");
        out.println("<img src='" + cp + "/imagens/devs/tig.jpg' width='70' class='rounded-circle'><br>Tiago Correia<br>20200018");
        out.println("</div>");
        out.println("<div class='col text-center'>");
        out.println("<img src='" + cp + "/imagens/devs/vigi.jpg' width='70' class='rounded-circle'><br>Viginaldo Joaquim<br>20210982");
        out.println("</div>");
        out.println("<div class='col text-center'>");
        out.println("<img src='" + cp + "/imagens/devs/yuyu.jpg' width='70' class='rounded-circle'><br>Yunus Suelmia<br>20210382");
        out.println("</div>");
        out.println("</div></div></footer>");

        // BOTÃO CARRINHO
        out.println("<button class='btn btn-primary rounded-circle position-fixed' style='bottom:90px;right:20px;width:60px;height:60px;z-index:1000;' data-bs-toggle='modal' data-bs-target='#modalCarrinho'>");
        out.println("<i class='fas fa-shopping-cart fs-4'></i>");
        out.println("<span class='badge bg-danger position-absolute top-0 start-100 translate-middle' id='cart-count'>0</span>");
        out.println("</button>");

        // BOTÃO VOLTAR AO TOPO
        out.println("<button onclick='topFunction()' id='backToTop' class='btn btn-dark rounded-circle back-to-top'>");
        out.println("<i class='fas fa-arrow-up'></i></button>");

        // OFFCANVAS COMPRA (LATERAL)
        out.println("<div class='offcanvas offcanvas-end' tabindex='-1' id='offcanvasCompra'>");
        out.println("<div class='offcanvas-header'>");
        out.println("<h5>Adicionar ao Carrinho</h5>");
        out.println("<button type='button' class='btn-close' data-bs-dismiss='offcanvas'></button>");
        out.println("</div>");
        out.println("<div class='offcanvas-body text-center'>");
        out.println("<img id='off-img' class='img-fluid mb-3' style='max-height:150px;'>");
        out.println("<h6 id='off-nome'></h6>");
        out.println("<p class='text-success fw-bold' id='off-preco'></p>");
        out.println("<button class='btn btn-success w-100' onclick='addToCartFromOffcanvas()'>Adicionar</button>");
        out.println("</div></div>");

        // MODAL CARRINHO
        out.println("<div class='modal fade' id='modalCarrinho' tabindex='-1'>");
        out.println("<div class='modal-dialog modal-lg modal-dialog-centered modal-dialog-scrollable'>");
        out.println("<div class='modal-content'>");
        out.println("<div class='modal-header'>");
        out.println("<h5>Carrinho (<span id='total-itens'>0</span> itens)</h5>");
        out.println("<button type='button' class='btn-close' data-bs-dismiss='modal'></button>");
        out.println("</div>");
        out.println("<div class='modal-body' id='carrinho-itens'></div>");
        out.println("<div class='modal-footer justify-content-between'>");
        out.println("<span>Total: <strong id='total-preco'>0.00</strong> MT</span>");
        out.println("<button class='btn btn-success' onclick='abrirCheckout()'>Finalizar Compra</button>");
        out.println("</div></div></div></div>");

        // ESTOQUE EM JS
        out.println("<script>");
        out.println("const estoque = [");
        boolean first = true;
        for (Equipamento e : estoque.values()) {
            if (!first) out.println(",");
            out.print("  {id:'" + e.getId() + "', nome:'" + e.getNome().replace("'", "\\'") + "', preco:" + e.getPreco() + "}");
            first = false;
        }
        out.println("\n];");

        out.println("let cart = JSON.parse(sessionStorage.getItem('cart')) || {};");
        out.println("let offItemId;");

        out.println("function abrirOffcanvas(id) {");
        out.println("  const item = estoque.find(e => e.id === id);");
        out.println("  document.getElementById('off-img').src = '" + cp + "/imagens/' + id + '.jpg';");
        out.println("  document.getElementById('off-nome').textContent = item.nome;");
        out.println("  document.getElementById('off-preco').textContent = item.preco + ' MT';");
        out.println("  offItemId = id;");
        out.println("}");

        out.println("function addToCartFromOffcanvas() {");
        out.println("  addToCart(offItemId);");
        out.println("  bootstrap.Offcanvas.getInstance(document.getElementById('offcanvasCompra')).hide();");
        out.println("}");

        out.println("function addToCart(id) {");
        out.println("  cart[id] = (cart[id] || 0) + 1;");
        out.println("  sessionStorage.setItem('cart', JSON.stringify(cart));");
        out.println("  updateCart();");
        out.println("  document.getElementById('icon-'+id).className = 'fas fa-check text-primary';");
        out.println("}");

        out.println("function updateCart() {");
        out.println("  const count = Object.values(cart).reduce((a,b)=>a+b,0);");
        out.println("  document.getElementById('cart-count').textContent = count;");
        out.println("  document.getElementById('total-itens').textContent = count;");
        out.println("  renderCarrinho();");
        out.println("}");

        out.println("function renderCarrinho() {");
        out.println("  const container = document.getElementById('carrinho-itens');");
        out.println("  container.innerHTML = '';");
        out.println("  let total = 0;");
        out.println("  Object.keys(cart).forEach(id => {");
        out.println("    const item = estoque.find(e => e.id === id);");
        out.println("    const qtd = cart[id];");
        out.println("    const subtotal = item.preco * qtd;");
        out.println("    total += subtotal;");
        out.println("    container.innerHTML += `");
        out.println("      <div class='d-flex align-items-center border-bottom py-2'>");
        out.println("        <img src='" + cp + "/imagens/${id}.jpg' class='me-3' style='width:50px;'>");
        out.println("        <div class='flex-grow-1'>");
        out.println("          <strong>${item.nome}</strong><br>");
        out.println("          <small>${item.preco} MT × ${qtd}</small>");
        out.println("        </div>");
        out.println("        <strong>${subtotal.toFixed(2)} MT</strong>");
        out.println("        <button class='btn btn-sm btn-danger ms-2' onclick='removerDoCarrinho(\"${id}\")'>×</button>");
        out.println("      </div>`;");
        out.println("  });");
        out.println("  document.getElementById('total-preco').textContent = total.toFixed(2);");
        out.println("}");

        out.println("function removerDoCarrinho(id) {");
        out.println("  delete cart[id];");
        out.println("  sessionStorage.setItem('cart', JSON.stringify(cart));");
        out.println("  updateCart();");
        out.println("  document.getElementById('icon-'+id).className = 'fas fa-cart-plus text-success';");
        out.println("}");

        out.println("function abrirCheckout() {");
        out.println("  const form = document.createElement('form');");
        out.println("  form.method = 'POST'; form.action = 'confirmacao';");
        out.println("  let total = 0;");
        out.println("  Object.keys(cart).forEach(id => {");
        out.println("    const qtd = cart[id];");
        out.println("    const input = document.createElement('input');");
        out.println("    input.type = 'hidden'; input.name = 'qtd_' + id; input.value = qtd;");
        out.println("    form.appendChild(input);");
        out.println("    total += estoque.find(e => e.id === id).preco * qtd;");
        out.println("  });");
        out.println("  const inputTotal = document.createElement('input');");
        out.println("  inputTotal.type = 'hidden'; inputTotal.name = 'total'; inputTotal.value = total.toFixed(2);");
        out.println("  form.appendChild(inputTotal);");
        out.println("  document.body.appendChild(form); form.submit();");
        out.println("}");

        // VOLTAR AO TOPO
        out.println("window.onscroll = function() {");
        out.println("  const btn = document.getElementById('backToTop');");
        out.println("  btn.style.display = (document.body.scrollTop > 100 || document.documentElement.scrollTop > 100) ? 'block' : 'none';");
        out.println("};");
        out.println("function topFunction() { document.body.scrollTop = 0; document.documentElement.scrollTop = 0; }");

        out.println("window.onload = updateCart;");
        out.println("</script>");
        out.println("<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js'></script>");
        out.println("</body></html>");
    }
}