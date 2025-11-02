package com.trabalho;

import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/carrinho")
public class CarrinhoServlet extends HttpServlet {
    private Map<String, Equipamento> estoque = new HashMap<>();

    @Override
    public void init() throws ServletException {
        // Mesmo estoque do ListaITSServlet
        new ListaITSServlet().init();
        this.estoque = ((ListaITSServlet) getServletContext().getAttribute("estoque") != null)
            ? (Map<String, Equipamento>) getServletContext().getAttribute("estoque")
            : new HashMap<>();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        String contextPath = req.getContextPath();

        out.println("<!DOCTYPE html><html lang='pt'><head>");
        out.println("<meta charset='UTF-8'><title>Carrinho - ITS Shop</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css' rel='stylesheet'>");
        out.println("</head><body class='bg-light'>");

        out.println("<div class='container py-5'>");
        out.println("<h2 class='mb-4'>Carrinho (<span id='item-count'>0</span>)</h2>");
        out.println("<div class='row'>");

        // Lista de itens
        out.println("<div class='col-lg-8'><div id='cart-items' class='bg-white p-4 rounded shadow-sm'></div></div>");

        // Resumo lateral
        out.println("<div class='col-lg-4'>");
        out.println("<div class='card'>");
        out.println("<div class='card-body'>");
        out.println("<h5>Resumo do Pedido</h5><hr>");
        out.println("<p>Subtotal: <span id='subtotal'>0.00</span> MT</p>");
        out.println("<p>Itens: <span id='total-items'>0</span></p>");
        out.println("<p class='text-success'>Envio: Grátis</p><hr>");
        out.println("<h5>Total estimado: <span id='total'>0.00</span> MT</h5>");
        out.println("<a href='/confirmacao' class='btn btn-primary w-100'>Continuar (<span id='continue-count'>0</span>)</a>");
        out.println("</div></div></div>");

        out.println("</div></div>");

        // JS do carrinho
        out.println("<script>");
        out.println("const produtos = {");
        estoque.forEach((k,v) -> out.println("'" + k + "': {nome: '" + v.getNome() + "', preco: " + v.getPreco() + ", img: '" + contextPath + "/imagens/" + k + ".jpg'},"));
        out.println("};");
        out.println("const cart = JSON.parse(sessionStorage.getItem('cart')) || {};");
        out.println("function render() {");
        out.println("  let html = '', subtotal = 0, count = 0;");
        out.println("  for (let id in cart) {");
        out.println("    const p = produtos[id]; const total = p.preco * cart[id];");
        out.println("    html += `<div class='d-flex align-items-center mb-3 border-bottom pb-3'>`;");
        out.println("    html += `<div class='form-check'><input class='form-check-input' type='checkbox' checked></div>`;");
        out.println("    html += `<img src='${p.img}' width='60' class='me-3'>`;");
        out.println("    html += `<div class='flex-grow-1'><h6>${p.nome}</h6><small>${cart[id]} em estoque</small></div>`;");
        out.println("    html += `<div><strong>${total.toFixed(2)} MT</strong> <a href='#' onclick='removeItem(${id})' class='text-danger ms-2'>Remover</a></div>`;");
        out.println("    html += `</div>`;");
        out.println("    subtotal += total; count += cart[id];");
        out.println("  }");
        out.println("  document.getElementById('cart-items').innerHTML = html || '<p class=\"text-muted\">Carrinho vazio</p>';");
        out.println("  ['subtotal','total'].forEach(id => document.getElementById(id).textContent = subtotal.toFixed(2));");
        out.println("  ['item-count','total-items','continue-count'].forEach(id => document.getElementById(id).textContent = count);");
        out.println("}");
        out.println("function removeItem(id) { delete cart[id]; sessionStorage.setItem('cart', JSON.stringify(cart)); render(); }");
        out.println("render();");
        out.println("</script>");

        out.println("</body></html>");
    }
}