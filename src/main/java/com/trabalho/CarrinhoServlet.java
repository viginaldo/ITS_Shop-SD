package com.trabalho;

import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/carrinho")
public class CarrinhoServlet extends HttpServlet {
    private Map<String, Equipamento> estoque;

    @Override
    public void init() throws ServletException {
        // Compartilha o mesmo estoque do ListaITSServlet
        ServletContext ctx = getServletContext();
        if (ctx.getAttribute("estoque") == null) {
            ListaITSServlet lista = new ListaITSServlet();
            lista.init();
            ctx.setAttribute("estoque", lista.estoque);
        }
        this.estoque = (Map<String, Equipamento>) ctx.getAttribute("estoque");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        String contextPath = req.getContextPath();
        String id = req.getParameter("id");

        out.println("<!DOCTYPE html><html lang='pt'><head>");
        out.println("<meta charset='UTF-8'><title>Carrinho - ITS Shop</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<style>.card-img-top{max-height:100px;object-fit:contain;}</style>");
        out.println("</head><body class='bg-light'>");

        out.println("<div class='container py-5'>");
        out.println("<h2 class='mb-4 text-success'><i class='fas fa-shopping-cart'></i> Carrinho de Compras</h2>");

        out.println("<form action='confirmacao' method='post' id='formCarrinho'>");
        out.println("<div class='row'>");

        // === LISTA DE ITENS ===
        out.println("<div class='col-lg-8'>");
        out.println("<div id='itensCarrinho'></div>");
        out.println("</div>");

        // === RESUMO ===
        out.println("<div class='col-lg-4'>");
        out.println("<div class='card shadow-sm'>");
        out.println("<div class='card-body'>");
        out.println("<h5>Resumo da Compra</h5>");
        out.println("<p>Itens: <strong id='totalItens'>0</strong></p>");
        out.println("<p>Subtotal: <strong id='subtotal'>0.00</strong> MT</p>");
        out.println("<hr>");
        out.println("<button type='submit' class='btn btn-success w-100'>Finalizar Compra</button>");
        out.println("</div></div></div>");

        out.println("</div></form></div>");

        // === DADOS DO ESTOQUE (PARA JS) ===
        out.println("<script>");
        out.println("const estoque = [");
        for (Map.Entry<String, Equipamento> e : estoque.entrySet()) {
            out.println("  {id:'" + e.getKey() + "', nome:'" + e.getValue().getNome().replace("'", "\\'") + "', preco:" + e.getValue().getPreco() + ", img:'" + contextPath + "/imagens/" + e.getKey() + ".jpg'},");
        }
        out.println("];");

        // === CARRINHO + ITEM ÚNICO ===
        out.println("const urlParams = new URLSearchParams(window.location.search);");
        out.println("const itemId = urlParams.get('id');");
        out.println("let cart = JSON.parse(sessionStorage.getItem('cart')) || {};");
        out.println("if (itemId) cart[itemId] = (cart[itemId] || 0) + 1;");
        out.println("sessionStorage.setItem('cart', JSON.stringify(cart));");

        // === RENDERIZAÇÃO ===
        out.println("function renderCarrinho() {");
        out.println("  const container = document.getElementById('itensCarrinho');");
        out.println("  container.innerHTML = '';");
        out.println("  let totalItens = 0, subtotal = 0;");
        out.println("  Object.keys(cart).forEach(id => {");
        out.println("    const item = estoque.find(e => e.id === id);");
        out.println("    if (!item) return;");
        out.println("    const qtd = cart[id];");
        out.println("    const total = item.preco * qtd;");
        out.println("    totalItens += qtd; subtotal += total;");
        out.println("    container.innerHTML += `");
        out.println("      <div class='card mb-3'>");
        out.println("        <div class='row g-0 align-items-center'>");
        out.println("          <div class='col-2'><img src='${item.img}' class='img-fluid rounded-start card-img-top'></div>");
        out.println("          <div class='col-6'><div class='card-body py-2'>");
        out.println("            <h6 class='mb-1'>${item.nome}</h6>");
        out.println("            <p class='text-success mb-0'>${item.preco} MT</p>");
        out.println("          </div></div>");
        out.println("          <div class='col-2'>");
        out.println("            <input type='number' class='form-control form-control-sm' value='${qtd}' min='1' ");
        out.println("                   onchange='updateQtd(\"${id}\", this.value)'>");
        out.println("          </div>");
        out.println("          <div class='col-2 text-end'>");
        out.println("            <p class='fw-bold'>${total.toFixed(2)} MT</p>");
        out.println("          </div>");
        out.println("        </div></div>`;");
        out.println("  });");
        out.println("  document.getElementById('totalItens').textContent = totalItens;");
        out.println("  document.getElementById('subtotal').textContent = subtotal.toFixed(2);");
        out.println("}");
        out.println("function updateQtd(id, qtd) {");
        out.println("  qtd = parseInt(qtd) || 1;");
        out.println("  if (qtd < 1) qtd = 1;");
        out.println("  cart[id] = qtd;");
        out.println("  sessionStorage.setItem('cart', JSON.stringify(cart));");
        out.println("  renderCarrinho();");
        out.println("  updateBadge();");
        out.println("}");
        out.println("window.onload = renderCarrinho;");
        out.println("</script>");

        out.println("</body></html>");
    }
}