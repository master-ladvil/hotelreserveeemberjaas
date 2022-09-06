import javax.security.auth.login.LoginContext;
import javax.servlet.*;
import java.io.*;
import javax.servlet.http.*;

public class Logout extends HttpServlet{
public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
	response.addHeader("Access-Control-Allow-Origin","*"); 
	response.setContentType("text/plain");
	PrintWriter out = response.getWriter();
	try{
	LoginContext logincontext =  AuthenticationServlet.loginContext;
	System.out.println("[+]calling logout..");
	logincontext.logout();
	out.println(1);
	}catch(Exception e){
		System.out.println(e);
		out.println(0);
	}
}
public void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
	response.setContentType("text/plain");	
	PrintWriter out = response.getWriter();
	TokenExchange.aflag = 0;
	TokenExchange.adminemail=null;
	out.println("Logouted");
}
}