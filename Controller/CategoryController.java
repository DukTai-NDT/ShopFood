/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import entity.Product;
import entity.Category;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Vector;
import model.DAOCategory;

/**
 *
 * @author Admin
 */
@WebServlet(name="CategoryController", urlPatterns={"/CategoryURL"})
public class CategoryController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        DAOCategory dao = new DAOCategory();
        try (PrintWriter out = response.getWriter()) {
           String service = request.getParameter("service");
            if(service == null){
                service = "listAllCategory";
            }
            if(service.equals("listAllCategory")){
                 String sql = "select * from Categories";
                String submit = request.getParameter("submit");
                if (submit != null) {

                    int cid = Integer.parseInt(request.getParameter("cid"));
                    sql = "SELECT * FROM [dbo].[Categories] where CategoryID = " + cid ;
                }
                Vector<Category> vectorCategory = dao.getCategory(sql);
                RequestDispatcher dispath = request.getRequestDispatcher("shop-grid.jsp");
                request.setAttribute("dataCategory", vectorCategory);
                dispath.forward(request, response);
                
//                for (Category category : vectorCategory) {
//                    category.getCategoryID();
//                }
            }
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
