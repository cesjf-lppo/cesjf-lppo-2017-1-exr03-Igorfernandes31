/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lppo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aluno
 */
@WebServlet(name = "EditaRegistro", urlPatterns = {"/edita.html"})
public class editarRegistro extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        Visitante visitante = new Visitante();
        try {
            //Pegar os dados do banco
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conexao = DriverManager.getConnection("jdbc:derby://localhost:1527/lppo-2017-1", "usuario", "senha");
            Statement operacao = conexao.createStatement();
            ResultSet resultado = operacao.executeQuery("SELECT * FROM contato WHERE id=" + id);
            if (resultado.next()) {
                // contato = new Contato();

                

                visitante.setId(Long.parseLong(request.getParameter("id")));
                visitante.setNome(request.getParameter("nome"));
                visitante.setIdade(Integer.parseInt(request.getParameter("idade")));
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ListarRegistrosServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ListarRegistrosServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.setAttribute("pessoa", visitante);
        request.getRequestDispatcher("WEB-INF/edita-registro.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Visitante visitante = new Visitante();
        visitante.setId(Long.parseLong(request.getParameter("id")));
        visitante.setNome(request.getParameter("nome"));

        try {
            //Pegar os dados do banco
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conexao = DriverManager.getConnection("jdbc:derby://localhost:1527/lppo-2017-1", "usuario", "senha");
            Statement operacao = conexao.createStatement();
            operacao.executeUpdate("UPDATE visitante SET nome='"
                    + visitante.getNome() + "', idade='"
                    + visitante.getIdade() + "', id='"
                    + visitante.getId());

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ListarRegistrosServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ListarRegistrosServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.sendRedirect("lista.html");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
