/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import entity.Cart;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Vector;
import model.DAOCart;


/**
 *
 * @author Admin
 */
@WebServlet(name="CartController", urlPatterns={"/CartURL"})
public class CartController extends HttpServlet {
   
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
        HttpSession session = request.getSession(true);
        DAOCart daoCart = new DAOCart();
        
        try (PrintWriter out = response.getWriter()) {
            String service = request.getParameter("service");
            if(service == null){
                service = "showCart";
            }
            if(service.equals("showCart")){
                Vector<Cart> vectorCart = new Vector<>();
                 Enumeration<String> enu = session.getAttributeNames();//lay lai cot key
                 while (enu.hasMoreElements()) {
                    String key = (String) enu.nextElement();
                    Object obj = session.getAttribute(key);

                    // Kiểm tra xem obj có phải là kiểu Cart không
                    if (obj instanceof Cart) {
                        Cart cart = (Cart) obj;
                        vectorCart.add(cart);
                    }
                }
                 request.setAttribute("dataCart", vectorCart);
                 request.getRequestDispatcher("shoping-cart.jsp").forward(request, response);
                 
            }
            if(service.equals("add2Cart")){
                int pid = Integer.parseInt(request.getParameter("pid"));
                Cart cart = daoCart.getCart(pid);
                
                if(session.getAttribute(pid+"") == null){
                    cart.setQuantity(1);
                    session.setAttribute(pid+"", cart);
                }else{
                    Cart existCart = (Cart)session.getAttribute(pid+"");
                    existCart.setQuantity(existCart.getQuantity() + 1);
                    session.setAttribute(pid+"", existCart);
                }
                response.sendRedirect("CartURL");
            }
            if(service.equals("remove2Cart")){
                int pid = Integer.parseInt(request.getParameter("pid"));
                Cart oldCart = (Cart)session.getAttribute(pid+"");
                if(oldCart.getQuantity() > 1){
                    oldCart.setQuantity(oldCart.getQuantity() - 1);
                    session.setAttribute(pid+"", oldCart);
                }else{
                    session.removeAttribute(pid+"");
                }
                response.sendRedirect("CartURL"); 
            }
            if(service.equals("delete2Cart")){
                session.invalidate();
                response.sendRedirect("ShopURL");
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
