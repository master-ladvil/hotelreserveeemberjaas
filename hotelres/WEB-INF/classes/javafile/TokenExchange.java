import javax.management.RuntimeErrorException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
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

    public TokenExchange(){
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
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.addHeader("Access-Control-Allow-Origin","*"); 
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
        aflag = 0;
        System.out.println(aflag);
        out.println(1);

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
        String email = null;
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        String token = request.getParameter("token");
        String host = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=";
        String target = host+token;
        System.out.println("url -> " + target);
        URL url = new URL(target);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responsecode = conn.getResponseCode();
        System.out.println(responsecode);
        if(responsecode != 200){
            throw new RuntimeException("HttpResponseCode: "+ responsecode);
        }
        else{
            String inLine = "";
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()){
                inLine += scanner.nextLine();
            }
            scanner.close();
           // System.out.println(inLine);
            //parsing

           try{
            JSONParser parse = new JSONParser();
            JSONObject obj = (JSONObject) parse.parse(inLine);
            email = obj.get("email").toString();
            System.out.println("Email -> " + email);
           }catch(Exception e){
               System.out.println(e);
           }
           aflag = checkcred(email);
           System.out.println(aflag);
           out.println(aflag);
           
	        //response.sendRedirect("http://localhost:4200");

        }


    }
    public int checkcred(String email){
        Statement stmt;
        ResultSet rs = null;
        try{
           String query = "select email from admin where id = 1";
           stmt = con.createStatement();
           rs = stmt.executeQuery(query);
           rs.next();
           String dbmail =  rs.getString(1);
           if(email.equals(dbmail)){
                adminemail = email;
                return 1;              
           }
        }catch(Exception e){
            System.out.println(e);
        }
        return 0;
    }
    
}
