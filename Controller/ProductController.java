/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import entity.Category;
import entity.Product;
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
import model.DAOProduct;

/**
 *
 * @author Admin
 */
@WebServlet(name="ProductController", urlPatterns={"/ProductURL"})
public class ProductController extends HttpServlet {
   
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
        DAOProduct dao = new DAOProduct();
        DAOCategory daocat = new DAOCategory();
        try (PrintWriter out = response.getWriter()) {
            String service = request.getParameter("service");
            if(service == null){
                service = "listAllProduct";
            }
            if(service.equals("listAllProductWithCID")){
                int cid = Integer.parseInt(request.getParameter("cid"));
                String sqlProduct = "Select * from Products where CategoryID = " +cid;
                String sqlCategory = "Select * from Categories";
                Vector<Product> vectorProduct = dao.getProduct(sqlProduct);
                Vector<Category> vectorCategory = daocat.getCategory(sqlCategory);
                request.setAttribute("dataProduct", vectorProduct);
                request.setAttribute("dataCategory", vectorCategory);
               request.getRequestDispatcher("shop-grid.jsp").forward(request, response);
            
                
            }
            if(service.equals("listAllProduct")){
                 String sql = "select * from Products";
                String submit = request.getParameter("submit");
                if (submit != null) {

                    String pname = request.getParameter("pname");
                    sql = "select *\n"
                            + "from Products\n"
                            + "where ProductName like '%" + pname + "%'";
                }
                Vector<Product> vector = dao.getProduct(sql);
                //select view
                RequestDispatcher dispath = request.getRequestDispatcher("shop-grid.jsp");
                //set data
                request.setAttribute("dataProduct", vector);
                
                // Run - view
                dispath.forward(request, response);
//                Vector<Product> vector = Vector<Product> request.getAttribute("data");
//                for (Product product : vector) {
//                    
//                }
//    Product product = vector.get(0);
//    product.getUnitPrice()
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
