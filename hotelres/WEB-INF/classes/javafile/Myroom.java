
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

public class Myroom extends HttpServlet{
	public Connection con = null;
	public LoginContext logincontext;

	public Myroom(){
	
		try{
			System.out.println("[+]inside myroom constructor..");
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
        String id = "0";
        String name = null;
        String clid = null;
		response.setContentType("text/json");
		PrintWriter out = response.getWriter();
		logincontext = AuthenticationServlet.loginContext;
		if(logincontext == null || logincontext.getSubject().getPrincipals().iterator().next().getName().equals("admin")){
			JSONObject ejobj = new JSONObject();
			ejobj.put("id",id);
			ejobj.put("error","You Are UnAuthorized");
			System.out.println(ejobj);
			out.println(ejobj);
		}else{
		try{
			System.out.println("inside MyRoom...");
            name = logincontext.getSubject().getPrincipals().iterator().next().getName();
            System.out.println("name ->  "+name);
			Statement stmt = null;
			ResultSet rs = null;
            ResultSet crs = null;
            String nquery = String.format("select id from client where fullname = '%s';",name);
            stmt = con.createStatement();
			crs = stmt.executeQuery(nquery);
            crs.next();
            clid = crs.getString("id");
            String query = String.format("select id,sdate,edate,rid from reservation where clid = '%s';",clid);
			rs  = stmt.executeQuery(query);
			List<JSONObject> resJsonList = Tojasonrs.getResultSet(rs);
			for(int i =0;i<resJsonList.size();i++){
				System.out.println(resJsonList.get(i));
			}
			out.println(resJsonList);
		}catch(Exception e){System.out.println(e);}
				
}
}
}
