package co.yellowpay;

import co.yellowpay.sdk.YellowException;
import co.yellowpay.sdk.YellowSDK;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        String apiKey = Config.API_KEY;
        String apiSecret = Config.API_SECRET;
        YellowSDK yellowSDK = new YellowSDK(apiKey, apiSecret);
        
        HashMap<String, String> result = new HashMap<String, String>();
        try {
            result = yellowSDK.checkInvoiceStatus(id);
        } catch (YellowException ex) {
            Logger.getLogger(Status.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if( result.containsKey("status") ){
            request.setAttribute("status", result.get("status"));
            request.getRequestDispatcher("status.jsp").forward(request, response);
        }else{
            request.setAttribute("status", "error: problem in inputs or internet connection");
            request.getRequestDispatcher("status.jsp").forward(request, response);
        }
    }
    

}
