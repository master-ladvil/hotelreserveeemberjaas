import javax.security.auth.login.LoginContext;
import javax.servlet.*;
import java.io.*;
import javax.servlet.http.*;

public class Logout extends HttpServlet{
public void service(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
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
}