/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import entity.Category;
import entity.Product;
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
import jakarta.servlet.RequestDispatcher;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ShopController", urlPatterns = {"/ShopURL"})
public class ShopController extends HttpServlet {

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
        DAOProduct daoProduct = new DAOProduct();
        DAOCategory daoCategory = new DAOCategory();
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            String pidParam = request.getParameter("pid");
            String cidParam = request.getParameter("cid");
            String service = null;
            if (service == null) {
                // Kiểm tra nếu không có pid và cid thì lấy toàn bộ dữ liệu
                if (pidParam == null && cidParam == null) {
                    // Lấy toàn bộ sản phẩm và danh mục
                    String sqlProduct = "SELECT * FROM Products";
                    String sqlCategory = "SELECT * FROM Categories";

                    Vector<Product> vectorProduct = daoProduct.getProduct(sqlProduct);
                    Vector<Category> vectorCategory = daoCategory.getCategory(sqlCategory);

                    // Đặt dữ liệu vào request
                    request.setAttribute("dataProduct", vectorProduct);
                    request.setAttribute("dataCategory", vectorCategory);

                    // Forward tới trang JSP
                    request.getRequestDispatcher("shop-grid.jsp").forward(request, response);
                    return;
                }

                // Trường hợp chỉ có pid (lấy thông tin sản phẩm theo pid)
                if (pidParam != null) {
                    try {
                        int pid = Integer.parseInt(pidParam);
                        String sqlProduct = "SELECT * FROM Products WHERE ProductID = " + pid;
                        Vector<Product> vectorProduct = daoProduct.getProduct(sqlProduct);

                        // Đặt sản phẩm vào request
                        request.setAttribute("dataProduct", vectorProduct);
                        request.getRequestDispatcher("shop-grid.jsp").forward(request, response);
                        return;
                    } catch (NumberFormatException e) {
                        out.println("Invalid Product ID");
                        return;
                    }
                }

                // Trường hợp chỉ có cid (lấy thông tin danh mục theo cid)
                if (cidParam != null) {
                    try {
                        int cid = Integer.parseInt(cidParam);
                        String sqlCategory = "SELECT * FROM Categories WHERE CategoryID = " + cid;
                        Vector<Category> vectorCategory = daoCategory.getCategory(sqlCategory);

                        // Đặt danh mục vào request
                        request.setAttribute("dataCategory", vectorCategory);
                        request.getRequestDispatcher("shop-grid.jsp").forward(request, response);
                        return;
                    } catch (NumberFormatException e) {
                        out.println("Invalid Category ID");
                        return;
                    }
                }
            }
            if (service.equals("listAllProductWithCID")) {

                
                        int cid = Integer.parseInt(cidParam);
                        String sqlProduct = "SELECT * FROM Products WHERE CategoryID = " + cid;

                        
                        Vector<Product> vectorProduct = daoProduct.getProduct(sqlProduct);

                        
                            request.setAttribute("dataProduct", vectorProduct);
                       
                        request.getRequestDispatcher("shop-grid.jsp").forward(request, response);
                       out.print("ok");
                        return;
                        
                    
                }
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
