import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
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
		    //logincontext = AuthenticationServlet.loginContext;
			
			int aflagg = TokenExchange.aflag;
			System.out.println("AFLAG->>>>>>>>>>>>"+aflagg);
			response.setContentType("text/plain");
			response.addHeader("Access-Control-Allow-Origin", "*");
			PrintWriter out = response.getWriter();
			if(aflagg == 0){
				System.out.println("nologinerror");
				out.println(5);
			}else{showRooms(response);}
	}

	public void showRooms(HttpServletResponse response){
		Statement stmt;
		ResultSet rs = null;
		try {
			String query = String.format(
					"SELECT room.id,capacity,rtype,price,isavailablle FROM room JOIN capacity ON room.cid=capacity.id JOIN rtype ON room.tid = rtype.id order by room.id asc;");
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);

			response.setContentType("text/json");
			//response.addHeader("Access-Control-Allow-Origin","http://localhost:4200");
			PrintWriter out = response.getWriter();

			List<JSONObject> resJsonList = Tojasonrs.getResultSet(rs);
			for(int i =0;i<resJsonList.size();i++){
				System.out.println(resJsonList.get(i));
			}
			out.println(resJsonList);

	}catch(Exception e){
		System.out.println(e);
	}
}


}