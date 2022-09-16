import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpResponse;
import java.sql.*;
import javax.security.auth.login.LoginContext;
import org.json.simple.JSONObject;
import java.util.*;
import java.text.*;

public class Admin extends HttpServlet{
	public Connection con = null;
	LoginContext logincontext = null;
	
 public Admin(){
	 try{
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotelreserve1_0", "postgres", "pwd");
			System.out.println("connection estabished");

		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Connection failed");
		}
 }
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		String accesstoken = TokenExchange.map.get("accesstoken");
		System.out.println("\n\naccesstoken -> " + accesstoken + "\n\n");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json");
		PrintWriter out = response.getWriter();
		URL obj = new URL("http://localhost:8080/lorduoauth/Reservation");
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("accesstoken", accesstoken);
		int responseCode = conn.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String inputLine;
			StringBuffer res = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				res.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(res.toString());
			out.println(res);
		} else {
			System.out.println("GET request not worked");
		}
	}



}