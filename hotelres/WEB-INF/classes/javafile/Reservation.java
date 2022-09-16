import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import javax.security.auth.login.LoginContext;
import org.json.simple.JSONObject;
import java.util.*;
import java.text.*;

public class Reservation extends HttpServlet{
	public Connection con = null;
	public LoginContext logincontext;
	public Reservation(){
	
		try{
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
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
		String accesstoken = TokenExchange.map.get("accesstoken");
		System.out.println("\n\naccesstoken -> "+ accesstoken + "\n\n");
		response.addHeader("Access-Control-Allow-Origin","*"); 
		response.setContentType("text/json");
		PrintWriter  out = response.getWriter();
		URL obj = new URL("http://localhost:8080/lorduoauth/Reservation");
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("accesstoken",accesstoken);
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
				
//}
}
}
