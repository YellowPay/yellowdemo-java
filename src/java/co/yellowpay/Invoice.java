package co.yellowpay;

import co.yellowpay.sdk.YellowException;
import co.yellowpay.sdk.YellowSDK;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
@WebServlet(name = "Invoice", urlPatterns = {"/Index"})
public class Invoice extends HttpServlet {

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
        request.getRequestDispatcher("index.jsp").forward(request, response);
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
        String curreny = request.getParameter("currency");
        String amount = request.getParameter("amount");
        String type = request.getParameter("type");
        
        String apiKey = Config.API_KEY;
        String apiSecret = Config.API_SECRET;
        YellowSDK yellowSDK = new YellowSDK(apiKey, apiSecret);
        
        //create invoice
        Map<String, String> payload = new HashMap<String, String>();
        payload.put("base_price", amount);
        payload.put("base_ccy", curreny);
        payload.put("callback", "https://yellow-demo-java.herokuapp.com/IPN");
        payload.put("type", type);
        
        HashMap<String, String> result = new HashMap<String, String>();
        try {
            result = yellowSDK.createInvoice(payload);
        } catch (YellowException ex) {
            Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if( result.containsKey("id") ){
            request.setAttribute("invoice_id", result.get("id"));
            request.setAttribute("invoice_url", result.get("url"));
            request.getRequestDispatcher("invoice.jsp").forward(request, response);
        }else if( result.containsKey("detail") ){
            request.setAttribute("error", result.get("detail"));
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }else{
            request.setAttribute("error", "problem in inputs or internet connection");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

}
