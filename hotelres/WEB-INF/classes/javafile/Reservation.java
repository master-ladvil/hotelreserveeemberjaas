import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
		response.addHeader("Access-Control-Allow-Origin","*"); 
		response.setContentType("text/json");
			PrintWriter out = response.getWriter();
		logincontext = AuthenticationServlet.loginContext;
		/*if(logincontext == null || !logincontext.getSubject().getPrincipals().iterator().next().getName().equals("admin"))
		{
			out.println(0);
			System.out.println("unauth");
		}else{*/
		try{
			System.out.println("inside Reservation...");
			Statement stmt = null;
			ResultSet rs = null;
			String query = String.format("select reservation.id,fullname,sdate,edate,rid from reservation join client on reservation.clid = client.id;");
			stmt = con.createStatement();
			rs  = stmt.executeQuery(query);
			List<JSONObject> resJsonList = Tojasonrs.getResultSet(rs);
			for(int i =0;i<resJsonList.size();i++){
				System.out.println(resJsonList.get(i));
			}
			out.println(resJsonList);
		}catch(Exception e){System.out.println(e);}
				
//}
}
}
