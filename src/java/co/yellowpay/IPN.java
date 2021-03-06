package co.yellowpay;

import co.yellowpay.sdk.YellowException;
import co.yellowpay.sdk.YellowSDK;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.servlet.ServletException;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String apiKey = Config.API_KEY;
        String apiSecret = Config.API_SECRET;
        YellowSDK yellowSDK = new YellowSDK(apiKey, apiSecret);
        
        String body = getBody(request);
        String url = Config.IPN_URL;
        String signature = request.getHeader("api-sign");
        String nonce = request.getHeader("api-nonce");
        
        boolean isValidIPN = false;
        try {
            isValidIPN = yellowSDK.verifyIPN(url, signature, nonce, body);
        } catch (YellowException ex) {
            Logger.getLogger(IPN.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if( isValidIPN ){
            logToFile("ipn", "is valid IPN call\n");
        }else{
            logToFile("ipn", "is invalid IPN call\n");
            /// invalid/ fake IPN , no need to do anything
            response.sendError(403, "Unauthorized");
        }
    }
    
    // get body as string
    public String getBody(HttpServletRequest request) throws IOException{
        String body;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        InputStream inputStream = request.getInputStream();
        if (inputStream != null) {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            char[] charBuffer = new char[128];
            int bytesRead;
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }
        } else {
            stringBuilder.append("");
        }
        if (bufferedReader != null) {
            bufferedReader.close();
        }

        body = stringBuilder.toString();
        return body;
    }
    
    // Logging to a file
    private void logToFile(String loggerName, String textToLog) throws IOException {
        // Logging to a file
        Logger logger = Logger.getLogger(loggerName);  
        FileHandler fh;  

        // This block configure the logger with handler and formatter  
        fh = new FileHandler(loggerName+".log");  
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter);  

        // the following statement is used to log any messages  
        logger.info(textToLog);  
    }

}
