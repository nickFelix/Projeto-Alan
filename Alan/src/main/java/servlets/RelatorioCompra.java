/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import DAO.CompraDAO;
import DAO.FilialDAO;
import DAO.ProdutoDAO;
import classes.CompraListagem;
import classes.ProdutoListagem;
import classes.entidades.Filial;
import classes.entidades.Produto;
import classes.entidades.Usuario;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.Formatacoes;

/**
 *
 * @author Arthur
 */
@WebServlet(name = "RelatorioCompra", urlPatterns = {"/RelatorioCompra"})
public class RelatorioCompra extends BaseServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario)session.getAttribute("usuarioLogado");
        List<CompraListagem> lista = new ArrayList<>();
        List<ProdutoListagem> produtos = new ArrayList<>();
        ArrayList<Filial> filiais = new ArrayList<>();
        
        try {
            lista = CompraDAO.listar(usuario);
            produtos = ProdutoDAO.listar();
            filiais = FilialDAO.listarRelatorio(usuario);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(RelatorioCompra.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        
        double quantidadeTotal=0;
        double valorTotal=0;
        
        for(CompraListagem cl : lista){
            quantidadeTotal+=cl.getQuantidade();
            valorTotal+=cl.getValor();
            
            
        }
        
        request.setAttribute("quantidadeTotal", Double.toString(quantidadeTotal));
        request.setAttribute("valorTotal", Formatacoes.formatarMoeda(valorTotal));
        
        request.setAttribute("filiais", filiais);
        request.setAttribute("Compras", lista);
        request.setAttribute("produtos", produtos);

        processRequest(request, response, "/WEB-INF/jsp/relatorioCompras.jspx");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario)session.getAttribute("usuarioLogado");
        ArrayList<Filial> filiais = new ArrayList<>();
        
        String dataInicial = request.getParameter("dataInicial");
        String dataFinal = request.getParameter("dataFinal");
        int codProduto = Integer.parseInt(request.getParameter("produtoId"));
        int codFilial = Integer.parseInt(request.getParameter("filialId"));
        
        
        
        List<CompraListagem> lista = new ArrayList<>();
        List<ProdutoListagem> produtos = new ArrayList<>();
        
        try {
            if(codProduto==0){
                lista = CompraDAO.listar(dataInicial, dataFinal, usuario, codFilial);
            }
            else{
                lista = CompraDAO.listar(dataInicial, dataFinal, codProduto, usuario, codFilial);
            }
            produtos = ProdutoDAO.listar();
            filiais = FilialDAO.listarRelatorio(usuario);
        } catch (SQLException ex) {
            Logger.getLogger(RelatorioCompra.class.getName()).log(Level.SEVERE, null, ex);
                String message = ex.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RelatorioCompra.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        double quantidadeTotal=0;
        double valorTotal=0;
        
        for(CompraListagem cl : lista){
            quantidadeTotal+=cl.getQuantidade();
            valorTotal+=cl.getValor();
        }
        
        request.setAttribute("quantidadeTotal", quantidadeTotal);
        request.setAttribute("valorTotal", valorTotal);
        request.setAttribute("filiais", filiais);
        request.setAttribute("Compras", lista);
        request.setAttribute("produtos", produtos);

        processRequest(request, response, "/WEB-INF/jsp/relatorioCompras.jspx");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

