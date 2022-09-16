import javax.management.RuntimeErrorException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
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
import org.json.simple.JSONArray;
import java.util.HashMap;

public class TokenExchange extends HttpServlet {
    public static Connection con = null;
    public static String adminemail = null;
    public static int aflag = 0;
    public final String pubkey = "21mw5OBuQDYONRNRyek-5Mwe2anpgn-1Ny_RGKU9eNO6_wWg-emzTpwKt4c7dDXgfyJEJ63L0zD_CS-FSyzksHKoGGySsDVX-6nD6n36MGxVCz5Z60wgM5FaSKpf7G3iOJi0IiutLcoYv5jl72g6k6nqrRTe5BSm7JfNedjpRzOeBm3IPQChW9OSW_fufV8q7Ty09ZbS0fU6KRnsMyCi80EYYg0ondJDd56iVUKR4f_OivS-EAZSUzjcu4uWYDzc9lOw8sCbb9oJE4HWLE1bgbQ05jxIqzD-6oztB1Mi-0fT5A8BV26MXnSLVPiTCgbSmQSiTq-I__uqxAfsg2v6OQ";
    public static HashMap<String, String> map = new HashMap<>();
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
        String accesstoken = request.getParameter("actoken");
        if(map.containsKey("accesstoken")){
            map.replace("accesstoken",accesstoken);
        }else{
            map.put("accesstoken",accesstoken);
        }
        System.out.println("\n\ntoken -> " + token+"\n\naccesstoken -> "+map.get("accesstoken")+"\n\n");
        String[] chunks = token.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();
        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));
        String signature = new String(decoder.decode(chunks[2]));
        // System.out.println("payload->"+ payload);
        // System.out.println("Header->"+header);
        // System.out.println("Signature->"+signature);

        try {
            JSONParser parse = new JSONParser();
            JSONObject obj = (JSONObject) parse.parse(payload);
            
            // int ver = verifySign(chunks[0]+"."+chunks[1],chunks[2]);

            //int fl = checktoken(obj, exp);
            //boolean f2 = verifySign(signature, header+"."+payload);
            //System.out.println("------------>  " + fl);
            int fl = 1;
            boolean f2 = true;
            if (fl == 0 && f2 == true ) {
                aflag = 0;
            } else if (fl == 1) {
                String data = obj.get("data").toString();
                JSONArray dob = (JSONArray) parse.parse(data);
                String dataa = dob.get(0).toString();
                JSONObject eob = (JSONObject) parse.parse(dataa);
                email = eob.get("email").toString();
                System.out.println("Email -> " + email);
                aflag = checkcred(email);
                System.out.println(aflag);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        out.println(aflag);

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
        String clientId = "106aa84cfc5f1a18c3ec3255802383ac";

        // expiry
        String exp = jobj.get("exp").toString();

        if (Integer.parseInt(exp) < (System.currentTimeMillis() / 1000)) {
            System.out.println("expired");
            return 0;
        } else {
            String iss = jobj.get("iss").toString();
            String aud = jobj.get("aud").toString();
            String azp = jobj.get("azp").toString();
            if (iss.equals("Elloauth") && aud.equals(clientId)) {
                System.out.println("verified");
                return 1;
            } else {
                System.out.println("not verified");
                return 0;
            }

        }
    }

    public String getpublickey() throws IOException {
        URL url = new URL("http://localhost:8080/lorduoauth/Key");
        // URLConnection connection = url.openConnection();

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // print result
        System.out.println(response.toString());

        return response.toString();
    }

    public boolean verifySign(String signature, String pt) {
        try {
            String pubkey = getpublickey();
            // convert String to pubkey
            byte[] publicBytes = Base64.getDecoder().decode(pubkey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubbKey = keyFactory.generatePublic(keySpec);
            // verification
            Signature publicSignature = Signature.getInstance("SHA256withRSA");
            publicSignature.initVerify(pubbKey);
            publicSignature.update(pt.getBytes(StandardCharsets.UTF_8));

            byte[] signatureBytes = Base64.getDecoder().decode(signature);

            return publicSignature.verify(signatureBytes);
        } catch (Exception e) {
            System.out.println(e);

        }
        return false;
    }

}
