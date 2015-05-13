package co.yellowpay;

/**
 *
 * @author Mahmoud
 */
public class Config {
    
    public static final String API_KEY = System.getenv("YELLOW_API_KEY");
    public static final String API_SECRET = System.getenv("YELLOW_API_SECRET");
    
    public static final String IPN_URL = System.getenv("YELLOW_IPN_URL");
    
}
