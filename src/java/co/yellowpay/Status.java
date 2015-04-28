package co.yellowpay;

import co.yellowpay.sdk.YellowSDK;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mahmoud
 */
@WebServlet(name = "Status", urlPatterns = {"/Status"})
public class Status extends HttpServlet {
    
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
        String id = request.getParameter("id");

        String apiKey = Keys.API_KEY;
        String apiSecret = Keys.API_SECRET;
        YellowSDK yellowSDK = new YellowSDK(apiKey, apiSecret);
        
        HashMap<String, String> result = yellowSDK.checkInvoiceStatus(id);
        if( result.containsKey("status") ){
            request.setAttribute("status", result.get("status"));
            request.getRequestDispatcher("status.jsp").forward(request, response);
        }else{
            request.setAttribute("status", "error: problem in inputs or internet connection");
            request.getRequestDispatcher("status.jsp").forward(request, response);
        }
    }
    

}
