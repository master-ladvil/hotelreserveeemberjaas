
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;
import javax.security.auth.login.LoginContext;
import org.json.simple.JSONObject;



public class My extends HttpServlet {
	public static Connection con = null;
	public static String uname = null;
	public static String mobile = null;
	public LoginContext logincontext;
	public boolean globalflag = true;
	public My() {
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


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("[+] inside post of my.......");
		
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		logincontext = AuthenticationServlet.loginContext;
		System.out.println(logincontext.getSubject().getPrincipals().iterator().next().getName());
		if(logincontext == null || logincontext.getSubject().getPrincipals().iterator().next().getName().equals("admin")){
			out.println(5);
		}else{
		
		String roomno = request.getParameter("roomno");
		String sdate = request.getParameter("sdate");
		String edate = request.getParameter("edate");
		System.out.println("room no -> " + roomno + "sdate --> "+ sdate + "edate -> "+ edate);
		int flag = reserveroom(roomno,sdate,edate);
		System.out.println("Flag -> "+flag);
		response.addHeader("Access-Control-Allow-Origin","*"); 
		out.println(flag);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				response.addHeader("Access-Control-Allow-Origin","*"); 
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			System.out.println("[+]inside get method..");		
			logincontext = AuthenticationServlet.loginContext;
			//System.out.println(logincontext.getSubject().getPrincipals().iterator().next().getName());
			if(logincontext == null ){
				out.println(0);
			}else if(logincontext.getSubject().getPrincipals().iterator().next().getName().equals("admin")){
				out.println(0);
			}
			else{
				System.out.println("[+]got context obj with name ->  "+logincontext.getSubject().getPrincipals().iterator().next().getName());
				My.uname =logincontext.getSubject().getPrincipals().iterator().next().getName();
				System.out.println("uname-> " + uname);
					showrooms(uname, response);
				}
			}
	
	public List<JSONObject> getJsonObject(ResultSet rs){
		List<JSONObject> resJsonList = Tojasonrs.getResultSet(rs);
		return resJsonList;
	}

	public void showrooms(String name, HttpServletResponse response) {
		Statement stmt;
		ResultSet rs = null;
		try {
			System.out.println("[+]inside showrooms..");
			String query = String.format(
					"SELECT room.id,capacity,rtype,price FROM room JOIN capacity ON room.cid=capacity.id JOIN rtype ON room.tid = rtype.id WHERE isavailablle = true;");
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			//showing json
			List<JSONObject> jobjlist = getJsonObject(rs);

			for(int i =0;i<jobjlist.size();i++){
				System.out.println(jobjlist.get(i));
			}
			response.addHeader("Access-Control-Allow-Origin","*");
			response.setContentType("text/json");
			PrintWriter out = response.getWriter();
			out.println(jobjlist);
			

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public int reserveroom(String roomno,String sdate, String edate) {
		Statement stmt;
        ResultSet rs = null;
        try{
		    System.out.println("[+] inside reserveroom uname  -> " + uname);
            String query = String.format("select id from client where fullname = '%s';",uname);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            rs.next();
            int clid = rs.getInt("id");
            String rquery = String.format("insert into reservation(rid,clid,sdate,edate) values('%s','%s','%s','%s');",roomno,clid,sdate,edate);
            stmt.executeUpdate(rquery);
            System.out.println("reserved room "+roomno);
            String upquery = String.format("update room set isavailablle = false where id = '%s';",roomno);
            stmt.executeUpdate(upquery);
            return 1; 

        }catch(Exception e){
            System.out.println(e);
			return 0;
        }
    
	}
  
	public void addsession(HttpServletRequest request)throws ServletException{
		HttpSession session = request.getSession();
		session.setAttribute("username",uname);
	}
	public int checkcontext()throws ServletException{
		System.out.println("[+]checking context....");
		if(logincontext == null){
			return 0;
		}
		String currentuser = logincontext.getSubject().getPrincipals().iterator().next().getName();
        if(currentuser != uname){
            return 0;
        }
        else if(currentuser.equals(uname)){
            return 1;
        }
        return 0;		
	}

}
