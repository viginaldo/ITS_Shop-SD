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
        String contextPath = req.getContextPath();

        if (estoque.isEmpty()) init();

        out.println("<!DOCTYPE html><html lang='pt'><head>");
        out.println("<meta charset='UTF-8'><title>ITS Shop | Catálogo</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css' rel='stylesheet'>");
        out.println("<style>");
        out.println("body{background:#f8f9fa;} .card-img-top{height:200px;object-fit:contain;}");
        out.println(".modal-img{max-height:80px;object-fit:contain;}");
        out.println("</style></head><body>");

        // NAVBAR
        out.println("<nav class='navbar navbar-dark bg-dark fixed-top'>");
        out.println("<div class='container-fluid d-flex justify-content-between align-items-center'>");
        out.println("<a class='btn btn-outline-light' href='" + contextPath + "/index.html'>Sair</a>");
        out.println("<a class='navbar-brand mx-auto' href='#'>ITS Shop</a>");
        out.println("<a class='btn btn-outline-light' href='#about'>About Us</a>");
        out.println("</div></nav>");
        out.println("<div style='height:70px;'></div>");

        out.println("<div class='container py-5'>");
        out.println("<h2 class='text-center mb-5'>Catálogo de Equipamentos</h2>");
        out.println("<div class='row row-cols-1 row-cols-md-3 g-4'>");

        // PRODUTOS
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
            out.println("<button class='btn btn-success mt-auto' data-bs-toggle='modal' data-bs-target='#modalCompra' onclick=\"abrirModalCompra('" + e.getId() + "')\">Comprar</button>");
            out.println("</div></div></div>");
        }

        out.println("</div></div>");

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

        // BOTÃO CARRINHO FLUTUANTE
        out.println("<button class='btn btn-primary rounded-circle position-fixed' style='bottom:30px;right:30px;width:60px;height:60px;z-index:1000;' data-bs-toggle='modal' data-bs-target='#modalCarrinho'>");
        out.println("<i class='fas fa-shopping-cart fs-4'></i>");
        out.println("<span class='badge bg-danger position-absolute top-0 start-100 translate-middle' id='cart-count'>0</span>");
        out.println("</button>");

        // MODAL COMPRA (1 ITEM)
        out.println("<div class='modal fade' id='modalCompra' tabindex='-1'>");
        out.println("<div class='modal-dialog modal-dialog-centered'>");
        out.println("<div class='modal-content'>");
        out.println("<div class='modal-header'><h5>Confirmar Compra</h5><button type='button' class='btn-close' data-bs-dismiss='modal'></button></div>");
        out.println("<div class='modal-body text-center'>");
        out.println("<img id='modal-img' class='modal-img mb-3'>");
        out.println("<h6 id='modal-nome'></h6>");
        out.println("<p class='text-success fw-bold' id='modal-preco'></p>");
        out.println("<button class='btn btn-success' onclick='addToCartFromModal()'>Adicionar ao Carrinho</button>");
        out.println("</div></div></div></div>");

        // MODAL CARRINHO
        out.println("<div class='modal fade' id='modalCarrinho' tabindex='-1'>");
        out.println("<div class='modal-dialog modal-lg modal-dialog-centered modal-dialog-scrollable'>");
        out.println("<div class='modal-content'>");
        out.println("<div class='modal-header'>");
        out.println("<h5>Carrinho (<span id='total-itens'>0</span>)</h5>");
        out.println("<div>");
        out.println("<div class='form-check'><input class='form-check-input' type='checkbox' id='selecionar-todos' onchange='toggleAll()'> <label class='form-check-label'>Selecionar todos</label></div>");
        out.println("<button class='btn btn-sm btn-outline-danger' onclick='limparCarrinho()'>Limpar carrinho</button>");
        out.println("<button class='btn btn-sm btn-outline-danger' onclick='removerSelecionados()'>Remover selecionados</button>");
        out.println("</div></div>");
        out.println("<div class='modal-body' id='carrinho-itens'></div>");
        out.println("<div class='modal-footer justify-content-between'>");
        out.println("<span>Total: <strong id='total-preco'>0.00</strong> MT</span>");
        out.println("<button class='btn btn-success' data-bs-toggle='modal' data-bs-target='#modalCheckout' onclick='prepararCheckout()'>Ir para Checkout</button>");
        out.println("</div></div></div></div>");

        // MODAL CHECKOUT
        out.println("<div class='modal fade' id='modalCheckout' tabindex='-1'>");
        out.println("<div class='modal-dialog modal-dialog-centered'>");
        out.println("<div class='modal-content'>");
        out.println("<form id='formCheckout' method='post' action='confirmacao'>");
        out.println("<div class='modal-header'><h5>Finalizar Compra</h5><button type='button' class='btn-close' data-bs-dismiss='modal'></button></div>");
        out.println("<div class='modal-body'>");
        out.println("<div class='mb-3'><label class='form-label'>Nome Completo</label><input type='text' class='form-control' name='nomeCliente' required></div>");
        out.println("<div class='mb-3'><label class='form-label'>Endereço de Entrega</label><input type='text' class='form-control' name='endereco' required></div>");
        out.println("<div class='mb-3'><label class='form-label'>Número do Cartão (16 dígitos)</label><input type='text' class='form-control' name='numeroCartao' pattern='\\d{16}' maxlength='16' placeholder='1234567890123456' required></div>");
        out.println("<hr><p><strong>Itens:</strong></p><div id='itensCheckout'></div>");
        out.println("<p class='text-end fw-bold'>Total: <span id='totalCheckout'>0.00</span> MT</p>");
        out.println("</div>");
        out.println("<div class='modal-footer'><button type='submit' class='btn btn-success w-100'>Confirmar e Pagar</button></div>");
        out.println("</form></div></div></div>");

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
        out.println("let modalItemId;");

        out.println("function addToCart(id) {");
        out.println("  cart[id] = (cart[id] || 0) + 1;");
        out.println("  sessionStorage.setItem('cart', JSON.stringify(cart));");
        out.println("  updateCart();");
        out.println("  document.getElementById('icon-'+id).className = 'fas fa-check text-primary';");
        out.println("}");

        out.println("function abrirModalCompra(id) {");
        out.println("  const item = estoque.find(e => e.id === id);");
        out.println("  document.getElementById('modal-img').src = '" + contextPath + "/imagens/' + id + '.jpg';");
        out.println("  document.getElementById('modal-nome').textContent = item.nome;");
        out.println("  document.getElementById('modal-preco').textContent = item.preco + ' MT';");
        out.println("  modalItemId = id;");
        out.println("}");

        out.println("function addToCartFromModal() {");
        out.println("  addToCart(modalItemId);");
        out.println("  bootstrap.Modal.getInstance(document.getElementById('modalCompra')).hide();");
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
        out.println("      <div class='border rounded p-3 mb-2 d-flex align-items-center'>");
        out.println("        <input type='checkbox' class='me-3 item-check' data-id='${id}'>");
        out.println("        <img src='" + contextPath + "/imagens/${id}.jpg' class='me-3' style='width:60px;'>");
        out.println("        <div class='flex-grow-1'>");
        out.println("          <strong>${item.nome}</strong><br>");
        out.println("          <small class='text-success'>${item.preco} MT</small>");
        out.println("        </div>");
        out.println("        <div class='d-flex align-items-center'>");
        out.println("          <button class='btn btn-sm btn-outline-secondary' onclick='updateQtd(\"${id}\", -1)'>-</button>");
        out.println("          <input type='number' class='form-control form-control-sm mx-1 text-center' style='width:50px;' value='${qtd}' onchange='updateQtd(\"${id}\", this.value - ${qtd})'>");
        out.println("          <button class='btn btn-sm btn-outline-secondary' onclick='updateQtd(\"${id}\", 1)'>+</button>");
        out.println("        </div>");
        out.println("        <strong class='ms-3'>${subtotal.toFixed(2)} MT</strong>");
        out.println("        <button class='btn btn-sm btn-danger ms-3' onclick='removerItem(\"${id}\")'>Remover</button>");
        out.println("      </div>`;");
        out.println("  });");
        out.println("  document.getElementById('total-preco').textContent = total.toFixed(2);");
        out.println("}");

        out.println("function updateQtd(id, delta) {");
        out.println("  const novo = (cart[id] || 0) + delta;");
        out.println("  if (novo <= 0) { delete cart[id]; } else { cart[id] = novo; }");
        out.println("  sessionStorage.setItem('cart', JSON.stringify(cart));");
        out.println("  updateCart();");
        out.println("}");

        out.println("function removerItem(id) { delete cart[id]; sessionStorage.setItem('cart', JSON.stringify(cart)); updateCart(); }");
        out.println("function toggleAll() { const checked = document.getElementById('selecionar-todos').checked; document.querySelectorAll('.item-check').forEach(c => c.checked = checked); }");
        out.println("function limparCarrinho() { cart = {}; sessionStorage.setItem('cart', '{}'); updateCart(); }");
        out.println("function removerSelecionados() { document.querySelectorAll('.item-check:checked').forEach(c => delete cart[c.dataset.id]); sessionStorage.setItem('cart', JSON.stringify(cart)); updateCart(); }");

        out.println("function prepararCheckout() {");
        out.println("  document.querySelectorAll('#formCheckout input[type=hidden]').forEach(i => i.remove());");
        out.println("  const itensDiv = document.getElementById('itensCheckout');");
        out.println("  itensDiv.innerHTML = '';");
        out.println("  let total = 0;");
        out.println("  let primeiroId = null;");
        out.println("  Object.keys(cart).forEach(id => {");
        out.println("    const item = estoque.find(e => e.id === id);");
        out.println("    const qtd = cart[id];");
        out.println("    const subtotal = item.preco * qtd;");
        out.println("    total += subtotal;");
        out.println("    if (!primeiroId) primeiroId = id;");
        out.println("    const input = document.createElement('input');");
        out.println("    input.type = 'hidden'; input.name = 'qtd_' + id; input.value = qtd;");
        out.println("    document.getElementById('formCheckout').appendChild(input);");
        out.println("    itensDiv.innerHTML += `<small>${item.nome} × ${qtd} = ${subtotal.toFixed(2)} MT</small><br>`;");
        out.println("  });");
        out.println("  document.getElementById('totalCheckout').textContent = total.toFixed(2);");
        out.println("  const inputId = document.createElement('input'); inputId.type = 'hidden'; inputId.name = 'idItem'; inputId.value = primeiroId || ''; document.getElementById('formCheckout').appendChild(inputId);");
        out.println("  const inputTotal = document.createElement('input'); inputTotal.type = 'hidden'; inputTotal.name = 'total'; inputTotal.value = total.toFixed(2); document.getElementById('formCheckout').appendChild(inputTotal);");
        out.println("}");

        out.println("window.onload = updateCart;");
        out.println("</script>");
        out.println("<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js'></script>");
        out.println("</body></html>");
    }
}