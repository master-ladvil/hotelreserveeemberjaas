import javax.management.RuntimeErrorException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.sql.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class TokenExchange extends HttpServlet {
    public static Connection con = null;
    public static String adminemail = null;
    public static int aflag = 0;
    

    public TokenExchange() {
        try {
            System.out.println("[+]inside my constructor..");
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotelreserve1_0", "postgres", "pwd");
            if (con != null) {
                System.out.println("connection estabished");
            } else {
                System.out.println("Connection failed");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        aflag = 0;
        System.out.println(aflag);
        out.println(1);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = null;
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        String token = request.getParameter("token");

        String[] chunks = token.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();
        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));


        try {
            JSONParser parse = new JSONParser();
            JSONObject obj = (JSONObject) parse.parse(payload);
            int ver = checktoken(obj);
            if (ver == 0) {
                aflag = 0;
            } else if (ver == 1) {
                email = obj.get("email").toString();
                System.out.println("Email -> " + email);
                aflag = checkcred(email);
                System.out.println(aflag);
                out.println(aflag);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        ;

        // String host = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=";
        // String target = host+token;
        // System.out.println("url -> " + target);

        /*
         * URL url = new URL(target);
         * HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         * conn.setRequestMethod("GET");
         * conn.connect();
         * 
         * int responsecode = conn.getResponseCode();
         * System.out.println(responsecode);
         * if(responsecode != 200){
         * throw new RuntimeException("HttpResponseCode: "+ responsecode);
         * }
         * else{
         * String inLine = "";
         * Scanner scanner = new Scanner(url.openStream());
         * 
         * while (scanner.hasNext()){
         * inLine += scanner.nextLine();
         * }
         * scanner.close();
         * // System.out.println(inLine);
         * //parsing
         * 
         * try{
         * JSONParser parse = new JSONParser();
         * JSONObject obj = (JSONObject) parse.parse(inLine);
         * email = obj.get("email").toString();
         * System.out.println("Email -> " + email);
         * }catch(Exception e){
         * System.out.println(e);
         * }
         */

        // response.sendRedirect("http://localhost:4200");

    }

    // }
    public int checkcred(String email) {

        Statement stmt;
        ResultSet rs = null;
        try {
            String query = "select email from admin where id = 1";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            rs.next();
            String dbmail = rs.getString(1);
            if (email.equals(dbmail)) {
                adminemail = email;
                return 1;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public int checktoken(JSONObject jobj) {
        String clientId = "632357853468-c9i98eg398g759brmg8nlbg9cu2h0b4i.apps.googleusercontent.com";
        // expiry
        String exp = jobj.get("exp").toString();
        if (Integer.parseInt(exp) < (System.currentTimeMillis() / 1000)) {
            System.out.println("expired");
            return 0;
        } else {
            String iss = jobj.get("iss").toString();
            String aud = jobj.get("aud").toString();
            String azp = jobj.get("azp").toString();
            if (iss.equals("https://accounts.google.com") && aud.equals(azp) && aud.equals(clientId)) {
                System.out.println("verified");
                return 1;
            } else {
                System.out.println("not verified");
                return 0;
            }

        }
    }

}
