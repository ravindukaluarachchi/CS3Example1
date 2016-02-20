/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rav.servlets;


import com.rav.bean.Transaction;
import com.rav.controller.RJSONHandler;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author ravklk
 */
@WebServlet(name = "A", urlPatterns = {"/A"})
public class A extends HttpServlet {

    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            Transaction t1 = new Transaction(1,"u1", "apple", 1, 100F);
            
            /*List<Item> items = new ArrayList<Item>();
            items.add(item);
            ObjectMapper mapper = new ObjectMapper();*/
            File f = new File( getServletContext().getRealPath("/WEB-INF/data"),"transactions.json");
            RJSONHandler<Transaction> jh = new RJSONHandler<Transaction>("com.rav.bean.Transaction",f);
            jh.writeToList(t1);
            List<Transaction> readList = jh.readList();
            System.out.println();
          //  mapper.writeValue(f, items);
            //Item i2 = mapper.readValue(f, Item.class);
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet A</title>");            
            out.println("</head>");
            out.println("<body>");
            
            for (Transaction i : readList) {
                out.println(i.getId() + "," + i.getDate() + "," + i.getTime() + "," + i.getUser() + "," + i.getItem() + "," + i.getQuantity() + "," + i.getPrice() + "<br/>");
            }
            
            out.println("</body>");
            out.println("</html>");
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();
        } finally {
            out.close();
        }
    }

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
        processRequest(request, response);
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
        processRequest(request, response);
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
