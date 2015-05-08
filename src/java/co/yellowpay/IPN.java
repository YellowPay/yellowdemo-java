package co.yellowpay;

import co.yellowpay.sdk.YellowSDK;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mahmoud
 */
@WebServlet(name = "IPN", urlPatterns = {"/IPN"})
public class IPN extends HttpServlet {
    
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
        
        String apiKey = Keys.API_KEY;
        String apiSecret = Keys.API_SECRET;
        YellowSDK yellowSDK = new YellowSDK(apiKey, apiSecret);
        
        String body = getStringBodyFromRequest(request.getInputStream());
        String url = request.getRequestURL().toString();
        String signature = request.getHeader("HTTP_API_SIGN");
        String nonce = request.getHeader("HTTP_API_NONCE");
        
        boolean isValidIPN = yellowSDK.verifyIPN(url, signature, nonce, body);
        
        if( isValidIPN ){
            logToFIle("ipn", "is valid IPN call\n");
            response.setHeader(url, nonce);
        }else{
            logToFIle("ipn", "is invalid IPN call\n");
            /// invalid/ fake IPN , no need to do anything
            response.sendError(403, "Unauthorized");
        }
    }
    
    // Converts a file to a string
    private String getStringBodyFromRequest(ServletInputStream input) throws IOException {
        int value;
        StringBuilder body = new StringBuilder();
        while ((value=input.read()) != -1) {
          body.append(value);
        }
        
        return body.toString();
    }
    
    // Logging to a file
    private void logToFIle(String loggerName, String textToLog) {
        // Logging to a file
        Logger logger = Logger.getLogger(loggerName);  
        FileHandler fh;  

        try {  
            // This block configure the logger with handler and formatter  
            fh = new FileHandler(loggerName+".log");  
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  

            // the following statement is used to log any messages  
            logger.info(textToLog);  
        } catch (SecurityException | IOException e) {  
        }  
    }

}
