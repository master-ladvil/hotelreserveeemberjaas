import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import javax.security.auth.login.LoginContext;

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
		logincontext = AuthenticationServlet.loginContext;
		if(logincontext == null || !logincontext.getSubject().getPrincipals().iterator().next().getName().equals("admin"))
		{
			response.sendRedirect("error.jsp");
		}
		try{
			Statement stmt = null;
			ResultSet rs = null;
			String query = String.format("select reservation.id,fullname,sdate,edate,rid from reservation join client on reservation.clid = client.id;");
			stmt = con.createStatement();
			rs  = stmt.executeQuery(query);
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html><body><table border =1><tr>");
			out.println("<td><b>id</b></td>");
			out.println("<td><b>fullname</b></td>");
			out.println("<td><b>sdate</b></td>");
			out.println("<td><b>edate</b></td>");
			out.println("<td><b>RoomNo</b></td>");
			while(rs.next()){
				out.println("<tr>");
				out.println("<td>"+rs.getString("id")+"</td>");
				out.println("<td>"+rs.getString("fullname")+"</td>");
				out.println("<td>"+rs.getString("sdate")+"</td>");
				out.println("<td>"+rs.getString("edate")+"</td>");
				out.println("<td>"+rs.getString("rid")+"</td></tr>");
			}
			out.println("</table>");
			out.println("<form><input type = 'submit' value = 'choose action' formaction = \"adminindex.jsp\" ><input type = 'submit' value ='logout' formaction = \"Logout\"> </form>");
		}catch(Exception e){System.out.println(e);}
				
}
}
