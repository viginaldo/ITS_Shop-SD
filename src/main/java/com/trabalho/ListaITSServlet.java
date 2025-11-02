package com.trabalho;

import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class ListaITSServlet extends HttpServlet {
    private Map<String, Equipamento> estoque = new HashMap<>();

    @Override
    public void init() throws ServletException {
    	estoque.put("1", new Equipamento("1", "PC Gamer i7 (Intel Core i7, 16GB RAM, RTX 3060)", 45000.00));  // Baseado em Pichau/Jumia MZ
        estoque.put("2", new Equipamento("2", "Monitor 27\" 144Hz IPS (LG ou Samsung)", 8500.00));  // Lap Tec/Procomputers
        estoque.put("3", new Equipamento("3", "Teclado Mecânico RGB (Logitech ou Redragon)", 1800.00));  // Jumia MZ
        estoque.put("4", new Equipamento("4", "Mouse Wireless Gamer 16000 DPI (Logitech Pebble 2)", 1200.00));  // Lap Tec
        estoque.put("5", new Equipamento("5", "Laptop Dell Latitude 5430 i7 (16GB RAM, 512GB SSD)", 55000.00));  // Jumia MZ 2024 + inflação
        estoque.put("6", new Equipamento("6", "Smartphone Samsung Galaxy A54 (128GB, 8GB RAM)", 14500.00));  // Perfect Price MZ
        estoque.put("7", new Equipamento("7", "Impressora HP DeskJet 2822e Multifunções", 3200.00));  // Switch Technology
        estoque.put("8", new Equipamento("8", "Webcam HD Logitech C920 (1080p)", 2500.00));  // ITCORE MZ
        estoque.put("9", new Equipamento("9", "Laptop Lenovo ThinkPad L15 i5 (8GB RAM, 512GB SSD)", 28000.00));  // MaisVendas MZ
        estoque.put("10", new Equipamento("10", "Headset Gamer RGB (HyperX ou Razer)", 2200.00));  // Mozcomputers
        estoque.put("11", new Equipamento("11", "SSD 1TB NVMe (Samsung 970 EVO)", 4500.00));  // Procomputers
        estoque.put("12", new Equipamento("12", "RAM 16GB DDR4 3200MHz (Kingston)", 2800.00));  // ITCORE
        estoque.put("13", new Equipamento("13", "Smartphone iPhone 13 (128GB)", 32000.00));  // Jumia MZ ajustado
        estoque.put("14", new Equipamento("14", "Impressora Laser HP LaserJet MFP M28w", 4800.00));  // MediaMarkt PT (similar MZ)
        estoque.put("15", new Equipamento("15", "Tablet Samsung Galaxy Tab A8 (32GB)", 8500.00));  // Perfect Price MZ
        estoque.put("16", new Equipamento("16", "Desktop Lenovo ThinkCentre i5 (8GB RAM, 256GB SSD)", 22000.00));  // MaisVendas
        estoque.put("17", new Equipamento("17", "Câmera de Segurança WiFi (TP-Link Tapo)", 1500.00));  // ITCORE
        estoque.put("18", new Equipamento("18", "Pen Drive 128GB USB 3.0 (SanDisk)", 800.00));  // Procomputers
        estoque.put("19", new Equipamento("19", "Power Bank 10000mAh (Anker ou Xiaomi)", 1200.00));  // Jumia MZ
        estoque.put("20", new Equipamento("20", "Hub USB 3.0 4 Portas (Anker)", 900.00));  // Mozcomputers
        System.out.println("ESTOQUE INICIALIZADO: " + estoque.size() + " itens");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();

        // FORÇA INICIALIZAÇÃO SE VAZIO
        if (estoque.isEmpty()) {
            init();
        }

        // CONTEXT PATH PARA JS
        String contextPath = req.getContextPath();

        out.println("<!DOCTYPE html><html lang='pt'><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>ITS Shop | Catálogo</title>");
        
     // === FAVICON + OPEN GRAPH (PREVIEW AO PARTILHAR) ===
        out.println("<link rel='icon' href='" + contextPath + "/imagens/favicons/favicon.ico' type='image/x-icon'>");
        out.println("<link rel='apple-touch-icon' href='" + contextPath + "/imagens/favicons/favicon.png'>");

        // META TAGS PARA SEO E PARTILHA
     // === OPEN GRAPH CORRIGIDO PARA WHATSAPP (URL ABSOLUTA + PNG OTIMIZADO) ===
        out.println("<meta property='og:title' content='ITS Shop - Tecnologia em Nampula'>");
        out.println("<meta property='og:description' content='Loja de TI com 20 produtos, entrega rápida em Moçambique!'>");
        out.println("<meta property='og:image' content='https://itsshop-sd-production.up.railway.app/imagens/preview.png'>");
        out.println("<meta property='og:image:type' content='image/png'>");
        out.println("<meta property='og:image:width' content='1200'>");
        out.println("<meta property='og:image:height' content='630'>");
        out.println("<meta property='og:url' content='https://itsshop-sd-production.up.railway.app'>");
        out.println("<meta property='og:type' content='website'>");
        out.println("<meta property='og:locale' content='pt_MZ'>");

        // === TWITTER CARD (AJUDA WHATSAPP) ===
        out.println("<meta name='twitter:card' content='summary_large_image'>");
        out.println("<meta name='twitter:title' content='ITS Shop - Tecnologia em Nampula'>");
        out.println("<meta name='twitter:description' content='Compre laptops, smartphones e acessórios com entrega rápida!'>");
        out.println("<meta name='twitter:image' content='https://itsshop-sd-production.up.railway.app/imagens/preview.png'>");
        
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css' rel='stylesheet'>");
        out.println("<style>body{background:#f8f9fa;} .card-img-top{height:200px;object-fit:contain;}</style>");
        out.println("</head><body>");

        // Navbar
        out.println("<nav class='navbar navbar-dark bg-dark fixed-top shadow-sm'>");
        out.println("<div class='container-fluid d-flex justify-content-between align-items-center'>");

        // BOTÃO SAIR (ESQUERDA)
        out.println("<a class='btn btn-outline-light' href='" + contextPath + "/index.html'>");
        out.println("<i class='fas fa-sign-out-alt'></i> Sair</a>");

        // LOGO CENTRALIZADA
        out.println("<a class='navbar-brand position-absolute start-50 translate-middle-x' href='#about' style='font-size:1.4rem;'>");
        out.println("<img src='" + contextPath + "/imagens/logo.png' alt='ITS Shop Logo' height='40' class='me-2'></a>");
        
        // BOTÃO ABOUT US (DIREITA)
        out.println("<a class='btn btn-outline-light' href='#about'>");
        out.println("<i class='fas fa-users'></i> About Us</a>");

        out.println("</div></nav>");

        // ESPAÇO PARA A NAVBAR FIXA
        out.println("<div style='height: 80px;'></div>");

        out.println("<div class='container py-5'>");
        out.println("<h2 class='text-center mb-5 text-success'><i class='fas fa-laptop'></i> Catálogo de Equipamentos</h2>");
        out.println("<div class='row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4'>");

        for (Equipamento e : estoque.values()) {
            String imagem = switch (e.getId()) {
            case "1"  -> "imagens/pc.jpg";
            case "2"  -> "imagens/monitor.jpg";
            case "3"  -> "imagens/teclado.jpg";
            case "4"  -> "imagens/mouse.jpg";
            case "5"  -> "imagens/dell.jpg";
            case "6"  -> "imagens/A54.jpg";
            case "7"  -> "imagens/Impressora.jpg";
            case "8"  -> "imagens/Webcam.jpg";
            case "9"  -> "imagens/lenovo.jpg";
            case "10" -> "imagens/Headset.jpg";
            case "11" -> "imagens/SSD.jpg";
            case "12" -> "imagens/RAM.jpg";
            case "13" -> "imagens/iPhone13.jpg";
            case "14" -> "imagens/ImpressoraL.jpg";
            case "15" -> "imagens/Tablet.jpg";
            case "16" -> "imagens/Desktop.jpg";
            case "17" -> "imagens/camera.jpg";
            case "18" -> "imagens/PenD.jpg";
            case "19" -> "imagens/Power.jpg";
            case "20" -> "imagens/Hub.jpg";
                default -> "https://via.placeholder.com/300x200";
            };

            out.println("<div class='col'>");
            out.println("<div class='card h-100 shadow-sm'>");
            out.println("<img src='" + contextPath + "/" + imagem + "' class='card-img-top' alt='" + e.getNome() + "'>");
            out.println("<div class='card-body d-flex flex-column'>");
            out.println("<h5 class='card-title'>" + e.getNome() + "</h5>");
            out.println("<p class='card-text text-success fw-bold'>Preço: " + e.getPreco() + " MT</p>");

            out.println("<button type='button' class='btn btn-success w-100 mt-auto' ");
            out.println("onclick=\"abrirCarrinho('" + e.getId() + "','" + e.getNome() + "','" + e.getPreco() + "','" + imagem + "')\">");
            out.println("<i class='fas fa-shopping-cart'></i> Comprar</button>");

            out.println("</div></div></div>");
        }

        out.println("</div></div>");

        // OFFCANVAS (MENU LATERAL)
        out.println("<div class='offcanvas offcanvas-end' tabindex='-1' id='carrinho' aria-labelledby='carrinhoLabel'>");
        out.println("<div class='offcanvas-header'>");
        out.println("<h5 class='offcanvas-title' id='carrinhoLabel'>Carrinho de Compras</h5>");
        out.println("<button type='button' class='btn-close' data-bs-dismiss='offcanvas'></button>");
        out.println("</div>");
        out.println("<div class='offcanvas-body' id='carrinhoConteudo'>");
        out.println("<p class='text-center text-muted'>Selecione um produto para começar.</p>");
        out.println("</div>");
        out.println("</div>");

        // JavaScript CORRIGIDO
        out.println("<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js'></script>");
        out.println("<script>");
        out.println("let produtoAtual = null;");
        out.println("let qtdAtual = 1;");
        out.println("const contextPath = '" + contextPath + "';");

        out.println("function abrirCarrinho(id, nome, preco, imagem) {");
        out.println("  produtoAtual = {id, nome, preco: parseFloat(preco), imagem};");
        out.println("  qtdAtual = 1;");
        out.println("  const conteudo = `");
        out.println("    <div class='text-center p-3'>");
        out.println("      <img src='${contextPath}/` + imagem + `' class='img-fluid rounded mb-3' style='max-height:150px;'>");
        out.println("      <h6 class='fw-bold'>` + nome + `</h6>");
        out.println("      <p class='text-success fw-bold'>Preço: <span id='precoUnit'>` + preco + `</span> MT</p>");
        out.println("      <div class='d-flex justify-content-center align-items-center mb-3'>");
        out.println("        <button class='btn btn-outline-secondary btn-sm' onclick='mudarQtd(-1)'>−</button>");
        out.println("        <span class='mx-3 fw-bold fs-5' id='quantidade'>1</span>");
        out.println("        <button class='btn btn-outline-secondary btn-sm' onclick='mudarQtd(1)'>+</button>");
        out.println("      </div>");
        out.println("      <p class='fs-4 text-primary fw-bold'>Total: <span id='total'>` + preco + `</span> MT</p>");
        out.println("      <button class='btn btn-success w-100' onclick='irParaDadosCliente()'>Continuar</button>");
        out.println("    </div>`;");
        out.println("  document.getElementById('carrinhoConteudo').innerHTML = conteudo;");
        out.println("  atualizarTotal();");
        out.println("  new bootstrap.Offcanvas(document.getElementById('carrinho')).show();");
        out.println("}");

        out.println("function mudarQtd(delta) {");
        out.println("  qtdAtual = Math.max(1, qtdAtual + delta);");
        out.println("  document.getElementById('quantidade').textContent = qtdAtual;");
        out.println("  atualizarTotal();");
        out.println("}");

        out.println("function atualizarTotal() {");
        out.println("  if (produtoAtual) {");
        out.println("    const total = (produtoAtual.preco * qtdAtual).toFixed(2);");
        out.println("    document.getElementById('total').textContent = total;");
        out.println("  }");
        out.println("}");

        out.println("function irParaDadosCliente() {");
        out.println("  const total = (produtoAtual.preco * qtdAtual).toFixed(2);");
        out.println("  const conteudo = `");
        out.println("    <div class='p-3'>");
        out.println("      <h6 class='text-center mb-4 fw-bold'>Finalizar Compra</h6>");
        out.println("      <form action='confirmacao' method='post'>");
        out.println("        <input type='hidden' name='idItem' value='` + produtoAtual.id + `'>");
        out.println("        <input type='hidden' name='quantidade' value='` + qtdAtual + `'>");
        out.println("        <input type='hidden' name='total' value='` + total + `'>");
        out.println("        <div class='mb-3'>");
        out.println("          <input type='text' name='nomeCliente' class='form-control' placeholder='Nome Completo' required>");
        out.println("        </div>");
        out.println("        <div class='mb-3'>");
        out.println("          <input type='text' name='endereco' class='form-control' placeholder='Endereço de Entrega' required>");
        out.println("        </div>");
        out.println("        <div class='mb-3'>");
        out.println("          <input type='text' name='numeroCartao' class='form-control' placeholder='Cartão (16 dígitos)' pattern='[0-9]{16}' maxlength='16' required>");
        out.println("        </div>");
        out.println("        <button type='submit' class='btn btn-success w-100'>Finalizar Compra</button>");
        out.println("      </form>");
        out.println("    </div>`;");
        out.println("  document.getElementById('carrinhoConteudo').innerHTML = conteudo;");
        out.println("}");
        out.println("</script>");
        
     // === FOOTER COM EQUIPE ===
        out.println("<footer id='about' class='bg-dark text-white py-5 mt-5'>");
        out.println("<div class='container'>");
        out.println("<h3 class='text-center mb-5'>Grupo 4</h3>");
        out.println("<div class='row row-cols-1 row-cols-md-5 g-4'>");

        // MEMBRO 1
        out.println("<div class='col text-center'>");
        out.println("<img src='" + contextPath + "/imagens/devs/langa.jpg' class='rounded-circle mb-3' width='120' height='120' alt='Membro 1'>");
        out.println("<h6 class='fw-bold'>Patrick Langa</h6>");
        out.println("<p class='small'>20231003</p>");
        out.println("<p class='small'>Desenvolvedor Mobile</p>");
        out.println("</div>");

        // MEMBRO 2
        out.println("<div class='col text-center'>");
        out.println("<img src='" + contextPath + "/imagens/devs/ryry.jpg' class='rounded-circle mb-3' width='120' height='120' alt='Membro 2'>");
        out.println("<h6 class='fw-bold'>Ryazy Hassane</h6>");
        out.println("<p class='small'>20230304</p>");
        out.println("<p class='small'>Desenvolvedor Back-end</p>");
        out.println("</div>");

        // MEMBRO 3
        out.println("<div class='col text-center'>");
        out.println("<img src='" + contextPath + "/imagens/devs/tig.jpg' class='rounded-circle mb-3' width='120' height='120' alt='Membro 3'>");
        out.println("<h6 class='fw-bold'>Tiago Correia</h6>");
        out.println("<p class='small'>20200018</p>");
        out.println("<p class='small'>Designer UI/UX</p>");
        out.println("</div>");

        // MEMBRO 4
        out.println("<div class='col text-center'>");
        out.println("<img src='" + contextPath + "/imagens/devs/vigi.jpg' class='rounded-circle mb-3' width='120' height='120' alt='Membro 4'>");
        out.println("<h6 class='fw-bold'>Viginaldo Joaquim</h6>");
        out.println("<p class='small'>20210982</p>");
        out.println("<p class='small'>Desenvolvedor Full-Stack</p>");
        out.println("</div>");

        // MEMBRO 5
        out.println("<div class='col text-center'>");
        out.println("<img src='" + contextPath + "/imagens/devs/yuyu.jpg' class='rounded-circle mb-3' width='120' height='120' alt='Membro 5'>");
        out.println("<h6 class='fw-bold'>Yunus Suelmia</h6>");
        out.println("<p class='small'>20210382</p>");
        out.println("<p class='small'>Tester & QA</p>");
        out.println("</div>");

        out.println("</div>");
        out.println("<hr class='my-4'>");
        out.println("<p class='text-center small'>&copy; 2025 ITS Shop - Trabalho Prático de SD. Todos os direitos reservados.</p>");
        out.println("</div></footer>");
        
     // === BOTÃO VOLTAR AO TOPO (FLUTUANTE) ===
        out.println("<button onclick='scrollToTop()' id='btnTop' class='btn btn-success rounded-circle shadow-lg' ");
        out.println("style='position:fixed; bottom:30px; right:30px; width:50px; height:50px; display:none; z-index:1000;' ");
        out.println("title='Voltar ao topo'>");
        out.println("<i class='fas fa-arrow-up'></i>");
        out.println("</button>");

        // === JavaScript para mostrar/esconder + scroll suave ===
        out.println("<script>");
        out.println("window.onscroll = function() {");
        out.println("  const btn = document.getElementById('btnTop');");
        out.println("  if (document.body.scrollTop > 300 || document.documentElement.scrollTop > 300) {");
        out.println("    btn.style.display = 'block';");
        out.println("  } else {");
        out.println("    btn.style.display = 'none';");
        out.println("  }");
        out.println("};");

        out.println("function scrollToTop() {");
        out.println("  window.scrollTo({top: 0, behavior: 'smooth'});");
        out.println("}");
        out.println("</script>");

        out.println("</body></html>");
    }
}