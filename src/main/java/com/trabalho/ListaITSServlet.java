package com.trabalho;

import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class ListaITSServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        Map<String, Equipamento> estoque = new HashMap<>();

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

        getServletContext().setAttribute("estoque", estoque);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        if (getServletContext().getAttribute("estoque") == null) {
            init();
        }

        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        String cp = req.getContextPath();

        @SuppressWarnings("unchecked")
        Map<String, Equipamento> estoque = (Map<String, Equipamento>) getServletContext().getAttribute("estoque");

        out.println("<!DOCTYPE html><html lang='pt'><head>");
        out.println("  <meta charset='UTF-8'>");
        out.println("  <meta name='viewport' content='width=device-width, initial-scale=1'>");
        out.println("  <title>ITS Shop | Catálogo</title>");
        out.println("  <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("  <link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css' rel='stylesheet'>");
        out.println("  <style>");
        out.println("    body{background:#f8f9fa;font-family:Arial,sans-serif;}");
        out.println("    .card-img-top{height:180px;object-fit:contain;background:#fff;padding:10px;}");
        out.println("    .back-to-top{position:fixed;bottom:20px;right:20px;z-index:1000;display:none;}");
        out.println("    .qty-input{width:60px;text-align:center;}");
        out.println("    .form-step { display: none; }");
        out.println("    .form-step.active { display: block; }");
        out.println("  </style>");
        out.println("</head><body>");

        /* NAV */
        out.println("  <nav class='navbar navbar-expand-lg navbar-dark bg-dark fixed-top'>");
        out.println("    <div class='container-fluid'>");
        out.println("      <a class='navbar-brand' href='#'>ITS Shop</a>");
        out.println("      <button class='navbar-toggler' type='button' data-bs-toggle='collapse' data-bs-target='#navbarNav'>");
        out.println("        <span class='navbar-toggler-icon'></span>");
        out.println("      </button>");
        out.println("      <div class='collapse navbar-collapse' id='navbarNav'>");
        out.println("        <ul class='navbar-nav ms-auto'>");
        out.println("          <li class='nav-item'><a class='nav-link' href='" + cp + "/index.html'>Sair</a></li>");
        out.println("          <li class='nav-item'><a class='nav-link' href='#about'>About Us</a></li>");
        out.println("        </ul>");
        out.println("      </div>");
        out.println("    </div>");
        out.println("  </nav>");
        out.println("  <div style='height:70px;'></div>");

        /* CATALOGO */
        out.println("  <div class='container py-4'>");
        out.println("    <h2 class='text-center mb-4'>Catálogo de Equipamentos</h2>");
        out.println("    <div class='row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4'>");

        for (Equipamento e : estoque.values()) {
            out.println("      <div class='col'>");
            out.println("        <div class='card h-100 shadow-sm'>");
            out.println("          <div class='position-relative'>");
            out.println("            <button class='btn btn-sm position-absolute top-0 end-0 m-2 bg-white rounded-circle' onclick=\"toggleCart('" + e.getId() + "')\">");
            out.println("              <i class='fas fa-cart-plus text-success' id='icon-" + e.getId() + "'></i>");
            out.println("            </button>");
            out.println("            <img src='" + cp + "/imagens/" + e.getId() + ".jpg' class='card-img-top' alt='" + e.getNome() + "'>");
            out.println("          </div>");
            out.println("          <div class='card-body d-flex flex-column'>");
            out.println("            <h5 class='card-title'>" + e.getNome() + "</h5>");
            out.println("            <p class='card-text text-success fw-bold mt-auto'>" + e.getPreco() + " MT</p>");
            out.println("            <button class='btn btn-success btn-sm mt-2' data-bs-toggle='offcanvas' data-bs-target='#offcanvasCompra' onclick=\"abrirOffcanvas('" + e.getId() + "')\">Comprar</button>");
            out.println("          </div>");
            out.println("        </div>");
            out.println("      </div>");
        }

        out.println("    </div>");
        out.println("  </div>");

        /* FOOTER */
        out.println("  <footer id='about' class='bg-dark text-white py-5 mt-5'>");
        out.println("    <div class='container text-center'>");
        out.println("      <h3>Grupo 4</h3>");
        out.println("      <div class='row row-cols-5 g-3 justify-content-center'>");
        out.println("        <div class='col text-center'><img src='" + cp + "/imagens/devs/langa.jpg' width='70' class='rounded-circle'><br>Patrick Langa<br>20231003</div>");
        out.println("        <div class='col text-center'><img src='" + cp + "/imagens/devs/ryry.jpg' width='70' class='rounded-circle'><br>Ryazy Hassane<br>20230304</div>");
        out.println("        <div class='col text-center'><img src='" + cp + "/imagens/devs/tig.jpg' width='70' class='rounded-circle'><br>Tiago Correia<br>20200018</div>");
        out.println("        <div class='col text-center'><img src='" + cp + "/imagens/devs/vigi.jpg' width='70' class='rounded-circle'><br>Viginaldo Joaquim<br>20210982</div>");
        out.println("        <div class='col text-center'><img src='" + cp + "/imagens/devs/yuyu.jpg' width='70' class='rounded-circle'><br>Yunus Suelmia<br>20210382</div>");
        out.println("      </div>");
        out.println("    </div>");
        out.println("  </footer>");

        /* CART BUTTON + BACK TO TOP */
        out.println("  <button class='btn btn-primary rounded-circle position-fixed' style='bottom:90px;right:20px;width:60px;height:60px;z-index:1000;' data-bs-toggle='modal' data-bs-target='#modalCarrinho'>");
        out.println("    <i class='fas fa-shopping-cart fs-4'></i>");
        out.println("    <span class='badge bg-danger position-absolute top-0 start-100 translate-middle' id='cart-count'>0</span>");
        out.println("  </button>");

        out.println("  <button onclick='topFunction()' id='backToTop' class='btn btn-dark rounded-circle back-to-top'>");
        out.println("    <i class='fas fa-arrow-up'></i>");
        out.println("  </button>");

        /* OFFCANVAS (single item checkout) */
        out.println("  <div class='offcanvas offcanvas-end' tabindex='-1' id='offcanvasCompra'>");
        out.println("    <div class='offcanvas-header'>");
        out.println("      <h5>Finalizar Compra</h5>");
        out.println("      <button type='button' class='btn-close' data-bs-dismiss='offcanvas'></button>");
        out.println("    </div>");
        out.println("    <div class='offcanvas-body'>");
        out.println("      <div class='text-center mb-3'>");
        out.println("        <img id='off-img' class='img-fluid' style='max-height:120px;'>");
        out.println("        <h6 id='off-nome' class='mt-2'></h6>");
        out.println("        <p class='text-success fw-bold' id='off-preco'></p>");
        out.println("      </div>");
        out.println("      <div class='d-flex align-items-center justify-content-center mb-3'>");
        out.println("        <button class='btn btn-outline-secondary' onclick='updateQtyOffcanvas(-1)'>−</button>");
        out.println("        <input type='number' id='off-qty' class='form-control qty-input mx-2' value='1' min='1'>");
        out.println("        <button class='btn btn-outline-secondary' onclick='updateQtyOffcanvas(1)'>+</button>");
        out.println("      </div>");
        out.println("      <p class='text-end fw-bold text-success' id='off-total'>Total: 0.00 MT</p>");
        out.println("      <button class='btn btn-primary w-100 mb-3' onclick='mostrarFormOffcanvas()'>Continuar</button>");
        out.println("      <form id='formOffcanvas' class='form-step' method='post' action='confirmacao'>");
        out.println("        <input type='hidden' name='idItem' id='hidden-id'>");
        out.println("        <input type='hidden' name='quantidade' id='hidden-qty' value='1'>");
        out.println("        <input type='hidden' name='total' id='hidden-total' value='0'>");
        out.println("        <div class='mb-3'><label class='form-label'>Nome Completo</label><input type='text' class='form-control' name='nomeCliente' required></div>");
        out.println("        <div class='mb-3'><label class='form-label'>Endereço Completo</label><input type='text' class='form-control' name='endereco' required></div>");
        out.println("        <div class='row mb-3'>");
        out.println("          <div class='col-md-8'>");
        out.println("            <label class='form-label'>Número do Cartão</label>");
        out.println("            <input type='text' class='form-control numero-cartao' name='numeroCartao' maxlength='19' placeholder='1234 5678 9012 3456' required>");
        out.println("          </div>");
        out.println("          <div class='col-md-4'>");
        out.println("            <label class='form-label'>CVV</label>");
        out.println("            <input type='text' class='form-control' name='cvv' pattern='\\\\d{3,4}' maxlength='4' placeholder='123' required>");
        out.println("          </div>");
        out.println("        </div>");
        out.println("        <button type='submit' class='btn btn-success w-100'>Confirmar Compra</button>");
        out.println("      </form>");
        out.println("    </div>");
        out.println("  </div>");

        /* MODAL CARRINHO */
        out.println("  <div class='modal fade' id='modalCarrinho' tabindex='-1'>");
        out.println("    <div class='modal-dialog modal-lg modal-dialog-centered modal-dialog-scrollable'>");
        out.println("      <div class='modal-content'>");
        out.println("        <div class='modal-header d-flex justify-content-between align-items-center'>");
        out.println("          <h5>Carrinho (<span id='total-itens'>0</span>)</h5>");
        out.println("          <div class='d-flex gap-2'>");
        out.println("            <div class='form-check'><input class='form-check-input' type='checkbox' id='selecionar-todos' onchange='toggleAll()'> <label class='form-check-label'>Todos</label></div>");
        out.println("            <button class='btn btn-sm btn-outline-danger' onclick='removerSelecionados()'>Remover</button>");
        out.println("          </div>");
        out.println("          <button type='button' class='btn-close' data-bs-dismiss='modal'></button>");
        out.println("        </div>");
        out.println("        <div class='modal-body' id='carrinho-itens'></div>");
        out.println("        <div class='modal-footer justify-content-between'>");
        out.println("          <span>Total: <strong id='total-preco'>0.00</strong> MT</span>");
        out.println("          <button class='btn btn-primary' onclick='mostrarCheckoutMultiplo()'>Continuar</button>");
        out.println("        </div>");
        out.println("      </div>");
        out.println("    </div>");
        out.println("  </div>");

        /* MODAL CHECKOUT MULTIPLO (vai ser populado dinamicamente) */
        out.println("  <div class='modal fade' id='modalCheckout' tabindex='-1'>");
        out.println("    <div class='modal-dialog modal-dialog-centered'>");
        out.println("      <div class='modal-content'>");
        out.println("        <div class='modal-header'><h5>Finalizar Compra</h5><button type='button' class='btn-close' data-bs-dismiss='modal'></button></div>");
        out.println("        <div class='modal-body'>");
        out.println("          <form id='formCheckoutMultiplo' method='post' action='confirmacao'></form>");
        out.println("        </div>");
        out.println("      </div>");
        out.println("    </div>");
        out.println("  </div>");

        /* --- Dados do estoque para o JS --- */
        out.println("<script>");
        out.println("  const estoque = [");
        boolean first = true;
        for (Equipamento e : estoque.values()) {
            if (!first) out.print(",");
            // escape single quotes in nome
            String nomeEsc = e.getNome().replace("'", "\\'");
            out.print("{ id: '" + e.getId() + "', nome: '" + nomeEsc + "', preco: " + e.getPreco() + " }");
            first = false;
        }
        out.println("];");
        out.println("</script>");

        /* --- JavaScript principal (centralizado) --- */
        out.println("<script>");
        out.println("  // Cart stored in sessionStorage as object {id: qty}");
        out.println("  let cart = JSON.parse(sessionStorage.getItem('cart')) || {};");
        out.println("  let offItemId = null; let offQty = 1; let offPreco = 0;");
        out.println("");
        out.println("  function toggleCart(id) {");
        out.println("    if (cart[id]) {");
        out.println("      delete cart[id];");
        out.println("      const ic = document.getElementById('icon-'+id); if(ic) ic.className = 'fas fa-cart-plus text-success';");
        out.println("    } else {");
        out.println("      cart[id] = 1;");
        out.println("      const ic = document.getElementById('icon-'+id); if(ic) ic.className = 'fas fa-check text-primary';");
        out.println("    }");
        out.println("    sessionStorage.setItem('cart', JSON.stringify(cart)); updateCart();");
        out.println("  }");
        out.println("");
        out.println("  function abrirOffcanvas(id) {");
        out.println("    const item = estoque.find(e => e.id === id);");
        out.println("    if(!item) return;");
        out.println("    document.getElementById('off-img').src = '" + cp + "/imagens/' + id + '.jpg';");
        out.println("    document.getElementById('off-nome').textContent = item.nome;");
        out.println("    document.getElementById('off-preco').textContent = item.preco + ' MT';");
        out.println("    document.getElementById('hidden-id').value = id;");
        out.println("    offItemId = id;");
        out.println("    offQty = cart[id] || 1;");
        out.println("    offPreco = item.preco;");
        out.println("    document.getElementById('off-qty').value = offQty;");
        out.println("    document.getElementById('hidden-qty').value = offQty;");
        out.println("    updateOffcanvasTotal();");
        out.println("    document.querySelectorAll('.form-step').forEach(f => f.classList.remove('active'));");
        out.println("  }");
        out.println("");
        out.println("  function updateQtyOffcanvas(delta) {");
        out.println("    offQty = Math.max(1, offQty + delta);");
        out.println("    document.getElementById('off-qty').value = offQty;");
        out.println("    document.getElementById('hidden-qty').value = offQty;");
        out.println("    updateOffcanvasTotal();");
        out.println("  }");
        out.println("");
        out.println("  function updateOffcanvasTotal() {");
        out.println("    const total = (offPreco || 0) * offQty;");
        out.println("    document.getElementById('off-total').textContent = 'Total: ' + total.toFixed(2) + ' MT';");
        out.println("    document.getElementById('hidden-total').value = total.toFixed(2);");
        out.println("  }");
        out.println("");
        out.println("  function mostrarFormOffcanvas() {");
        out.println("    document.getElementById('formOffcanvas').classList.add('active');");
        out.println("  }");
        out.println("");
        out.println("  // quando submeter o form do offcanvas, atualiza o carrinho");
        out.println("  document.addEventListener('submit', function(e){");
        out.println("    if(e.target && e.target.id === 'formOffcanvas') {");
        out.println("      e.preventDefault();");
        out.println("      cart[offItemId] = parseInt(document.getElementById('hidden-qty').value) || 1;");
        out.println("      sessionStorage.setItem('cart', JSON.stringify(cart)); updateCart();");
        out.println("      // Fecha offcanvas programaticamente");
        out.println("      const off = bootstrap.Offcanvas.getInstance(document.getElementById('offcanvasCompra'));");
        out.println("      if(off) off.hide();");
        out.println("    }");
        out.println("  });");
        out.println("");
        out.println("  function updateCart() {");
        out.println("    const count = Object.values(cart).reduce((a,b) => a + b, 0);");
        out.println("    document.getElementById('cart-count').textContent = count;");
        out.println("    document.getElementById('total-itens').textContent = count;");
        out.println("    renderCarrinho();");
        out.println("  }");
        out.println("");
        out.println("  function renderCarrinho() {");
        out.println("    const container = document.getElementById('carrinho-itens');");
        out.println("    container.innerHTML = '';");
        out.println("    let total = 0;");
        out.println("    Object.keys(cart).forEach(id => {");
        out.println("      const item = estoque.find(e => e.id === id);");
        out.println("      if(!item) return;");
        out.println("      const qtd = cart[id];");
        out.println("      const subtotal = item.preco * qtd;");
        out.println("      total += subtotal;");
        out.println("");
        out.println("      // construir elemento via DOM (evita problemas com template literals dentro de out.println)");
        out.println("      const wrapper = document.createElement('div');");
        out.println("      wrapper.className = 'border rounded p-3 mb-2';");
        out.println("");
        out.println("      const chkDiv = document.createElement('div');");
        out.println("      chkDiv.className = 'form-check d-flex align-items-center';");
        out.println("");
        out.println("      const inputChk = document.createElement('input');");
        out.println("      inputChk.className = 'form-check-input item-check';");
        out.println("      inputChk.type = 'checkbox';");
        out.println("      inputChk.dataset.id = id;");
        out.println("      inputChk.checked = true;");
        out.println("");
        out.println("      const label = document.createElement('label');");
        out.println("      label.className = 'form-check-label d-flex align-items-center w-100';");
        out.println("");
        out.println("      const img = document.createElement('img');");
        out.println("      img.src = '" + cp + "/imagens/' + id + '.jpg';");
        out.println("      img.style.width = '50px';");
        out.println("      img.className = 'me-3';");
        out.println("");
        out.println("      const info = document.createElement('div');");
        out.println("      info.className = 'flex-grow-1';");
        out.println("      info.innerHTML = '<strong>' + item.nome + '</strong><br><small>' + item.preco + ' MT</small>'; ");
        out.println("");
        out.println("      const qtyDiv = document.createElement('div');");
        out.println("      qtyDiv.className = 'd-flex align-items-center';");
        out.println("");
        out.println("      const btnMinus = document.createElement('button');");
        out.println("      btnMinus.className = 'btn btn-sm btn-outline-secondary';");
        out.println("      btnMinus.type = 'button';");
        out.println("      btnMinus.textContent = '−';");
        out.println("      btnMinus.onclick = function(){ updateQty(id, -1); };");
        out.println("");
        out.println("      const inputQty = document.createElement('input');");
        out.println("      inputQty.type = 'number';");
        out.println("      inputQty.className = 'form-control form-control-sm qty-input mx-1';");
        out.println("      inputQty.value = qtd;");
        out.println("      inputQty.onchange = function(){ updateQty(id, parseInt(this.value) - qtd); };");
        out.println("");
        out.println("      const btnPlus = document.createElement('button');");
        out.println("      btnPlus.className = 'btn btn-sm btn-outline-secondary';");
        out.println("      btnPlus.type = 'button';");
        out.println("      btnPlus.textContent = '+';");
        out.println("      btnPlus.onclick = function(){ updateQty(id, 1); };");
        out.println("");
        out.println("      const subtotalEl = document.createElement('strong');");
        out.println("      subtotalEl.className = 'ms-3';");
        out.println("      subtotalEl.textContent = subtotal.toFixed(2) + ' MT';");
        out.println("");
        out.println("      const removeBtn = document.createElement('button');");
        out.println("      removeBtn.className = 'btn btn-sm btn-danger ms-3';");
        out.println("      removeBtn.type = 'button'; removeBtn.textContent = '×';");
        out.println("      removeBtn.onclick = function(){ removerDoCarrinho(id); };");
        out.println("");
        out.println("      qtyDiv.appendChild(btnMinus); qtyDiv.appendChild(inputQty); qtyDiv.appendChild(btnPlus);");
        out.println("");
        out.println("      label.appendChild(img);");
        out.println("      label.appendChild(info);");
        out.println("      label.appendChild(qtyDiv);");
        out.println("      label.appendChild(subtotalEl);");
        out.println("      label.appendChild(removeBtn);");
        out.println("");
        out.println("      chkDiv.appendChild(inputChk);");
        out.println("      chkDiv.appendChild(label);");
        out.println("      wrapper.appendChild(chkDiv);");
        out.println("      container.appendChild(wrapper);");
        out.println("    });");
        out.println("    document.getElementById('total-preco').textContent = total.toFixed(2);");
        out.println("  }");
        out.println("");
        out.println("  function updateQty(id, delta) {");
        out.println("    const novo = Math.max(1, (cart[id] || 0) + delta);");
        out.println("    cart[id] = novo;");
        out.println("    sessionStorage.setItem('cart', JSON.stringify(cart)); updateCart();");
        out.println("  }");
        out.println("");
        out.println("  function removerDoCarrinho(id) {");
        out.println("    delete cart[id];");
        out.println("    sessionStorage.setItem('cart', JSON.stringify(cart));");
        out.println("    updateCart();");
        out.println("    const ic = document.getElementById('icon-'+id); if(ic) ic.className = 'fas fa-cart-plus text-success';");
        out.println("  }");
        out.println("");
        out.println("  function toggleAll() {");
        out.println("    const checked = document.getElementById('selecionar-todos').checked;");
        out.println("    document.querySelectorAll('.item-check').forEach(c => c.checked = checked);");
        out.println("  }");
        out.println("");
        out.println("  function removerSelecionados() {");
        out.println("    document.querySelectorAll('.item-check:checked').forEach(c => {");
        out.println("      const id = c.dataset.id;");
        out.println("      delete cart[id];");
        out.println("      const ic = document.getElementById('icon-'+id); if(ic) ic.className = 'fas fa-cart-plus text-success';");
        out.println("    });");
        out.println("    sessionStorage.setItem('cart', JSON.stringify(cart)); updateCart();");
        out.println("  }");
        out.println("");
        out.println("  function mostrarCheckoutMultiplo() {");
        out.println("    const form = document.getElementById('formCheckoutMultiplo');");
        out.println("    form.innerHTML = '';"); // limpa antes de montar");
        out.println("");
        out.println("    // campos comuns");
        out.println("    const nomeDiv = document.createElement('div');");
        out.println("    nomeDiv.className = 'mb-3';");
        out.println("    nomeDiv.innerHTML = \"<label class='form-label'>Nome Completo</label><input type='text' class='form-control' name='nomeCliente' required>\";");
        out.println("    form.appendChild(nomeDiv);");
        out.println("");
        out.println("    const endDiv = document.createElement('div');");
        out.println("    endDiv.className = 'mb-3';");
        out.println("    endDiv.innerHTML = \"<label class='form-label'>Endereço Completo</label><input type='text' class='form-control' name='endereco' required>\";");
        out.println("    form.appendChild(endDiv);");
        out.println("");
        out.println("    // cartão row");
        out.println("    const row = document.createElement('div'); row.className = 'row mb-3';");
        out.println("    const col8 = document.createElement('div'); col8.className = 'col-md-8';");
        out.println("    col8.innerHTML = \"<label class='form-label'>Número do Cartão</label>\";");
        out.println("    const cartInput = document.createElement('input');");
        out.println("    cartInput.type = 'text'; cartInput.name = 'numeroCartao'; cartInput.className = 'form-control numero-cartao';");
        out.println("    cartInput.maxLength = 19; cartInput.placeholder = '1234 5678 9012 3456'; cartInput.required = true;");
        out.println("    col8.appendChild(cartInput);");
        out.println("");
        out.println("    const col4 = document.createElement('div'); col4.className = 'col-md-4';");
        out.println("    col4.innerHTML = \"<label class='form-label'>CVV</label>\";");
        out.println("    const cvvInput = document.createElement('input');");
        out.println("    cvvInput.type = 'text'; cvvInput.name = 'cvv'; cvvInput.className = 'form-control';");
        out.println("    cvvInput.pattern = '\\\\d{3,4}'; cvvInput.maxLength = 4; cvvInput.placeholder = '123'; cvvInput.required = true;");
        out.println("    col4.appendChild(cvvInput);");
        out.println("");
        out.println("    row.appendChild(col8); row.appendChild(col4); form.appendChild(row);");
        out.println("");
        out.println("    // adiciona hidden inputs com quantidades dos itens selecionados e calcula total");
        out.println("    let total = 0;");
        out.println("    document.querySelectorAll('.item-check:checked').forEach(cb => {");
        out.println("      const id = cb.dataset.id;");
        out.println("      const qtd = cart[id] || 0;");
        out.println("      const item = estoque.find(e => e.id === id);");
        out.println("      if(!item) return;");
        out.println("      total += item.preco * qtd;");
        out.println("      const hidden = document.createElement('input');");
        out.println("      hidden.type = 'hidden'; hidden.name = 'qtd_' + id; hidden.value = qtd;");
        out.println("      form.appendChild(hidden);");
        out.println("    });");
        out.println("");
        out.println("    const totalHidden = document.createElement('input'); totalHidden.type = 'hidden'; totalHidden.name = 'total'; totalHidden.value = total.toFixed(2); form.appendChild(totalHidden);");
        out.println("");
        out.println("    const btn = document.createElement('button'); btn.type = 'submit'; btn.className = 'btn btn-success w-100'; btn.textContent = 'Confirmar'; form.appendChild(btn);");
        out.println("");
        out.println("    // fecha modal carrinho e abre modal checkout");
        out.println("    const modalCarrinhoInst = bootstrap.Modal.getInstance(document.getElementById('modalCarrinho'));");
        out.println("    if(modalCarrinhoInst) modalCarrinhoInst.hide();");
        out.println("    new bootstrap.Modal(document.getElementById('modalCheckout')).show();");
        out.println("  }");
        out.println("");
        out.println("  // formCheckoutMultiplo será submetido normalmente ao action='confirmacao'");
        out.println("");
        out.println("  // back to top");
        out.println("  window.onscroll = function() {");
        out.println("    document.getElementById('backToTop').style.display = (document.body.scrollTop > 100 || document.documentElement.scrollTop > 100) ? 'block' : 'none';");
        out.println("  };");
        out.println("  function topFunction(){ document.body.scrollTop = 0; document.documentElement.scrollTop = 0; }");
        out.println("");
        out.println("  // formatação do número do cartão — aplicável a todos os inputs com classe 'numero-cartao'");
        out.println("  function formatarCartaoInput(el) {");
        out.println("    let value = el.value.replace(/\\D/g, '');");
        out.println("    value = value.substring(0,16);");
        out.println("    el.value = value.replace(/(.{4})/g, '$1 ').trim();");
        out.println("  }");
        out.println("  // delegação: captura qualquer input com classe 'numero-cartao'");
        out.println("  document.addEventListener('input', function(e){");
        out.println("    if(e.target && e.target.classList && e.target.classList.contains('numero-cartao')) {");
        out.println("      formatarCartaoInput(e.target);");
        out.println("    }");
        out.println("  });");
        out.println("");
        out.println("  // inicialização");
        out.println("  window.addEventListener('load', function(){ updateCart(); });");
        out.println("</script>");

        /* BOOTSTRAP BUNDLE */
        out.println("<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js'></script>");
        out.println("</body></html>");
    }
}