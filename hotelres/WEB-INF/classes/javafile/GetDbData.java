import javax.security.auth.login.LoginContext;
import javax.servlet.*;
import java.io.*;
import javax.servlet.http.*;
import java.sql.*;
import org.json.simple.JSONObject;

public class GetDbData extends HttpServlet{
	public static Connection con = null;
	public static String id = "0";

	public GetDbData(){
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
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		response.addHeader("Access-Control-Allow-Origin","*"); 
		response.setContentType("text/json");
		PrintWriter out = response.getWriter();
		LoginContext logincontext = null;
		int aflagg = TokenExchange.aflag;
		if(aflagg ==0){
			JSONObject ejobj = new JSONObject();
			ejobj.put("id",id);
			ejobj.put("error","You Are UnAuthorized");
			System.out.println(ejobj);
			out.println(ejobj);
		}else{
		System.out.println("inside getdb");
		
		Statement stmt;
		ResultSet rs = null;
		String troom = null;
		String tclient = null;
		String tres = null;
		try{
			GetDbData gdb = new GetDbData();
			troom = gdb.getCount("room");
			tclient = gdb.getCount("client");
			tres = gdb.getCount("reservation");
			System.out.println("room -> " + troom + " client -> " + tclient + "reservation -> " + tres);
		}catch(Exception e){
			System.out.println(e);
		}
		JSONObject jobj = new JSONObject();
		jobj.put("id",id);
		jobj.put("troom",troom);
		jobj.put("tclient",tclient);
		jobj.put("tres",tres);
		System.out.println(jobj);
		out.println(jobj);
	}
	
	}
	public String getCount(String table){
		String count = null;
		ResultSet rs = null;
		Statement stmt;
		try{
			String query = String.format("select count(*) from %s;",table);
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			count = rs.getString(1);
			
		}catch(Exception e){
			System.out.println(e);
		}
		return count;
	} 
}