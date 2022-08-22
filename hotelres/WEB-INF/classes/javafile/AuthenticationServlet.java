
import java.io.IOException;
import java.io.PrintWriter;
import javax.security.auth.login.Configuration;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.LoginContext;
import javax.sql.rowset.serial.SerialException;

public  class AuthenticationServlet extends HttpServlet{
    public static LoginContext loginContext = null;
    protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        String name = null;    
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        //out.println("<html><body>");
        String user = request.getParameter("uname");
        String mobile = request.getParameter("mobile");
        System.out.println("user -> "+user + "mobile "+ mobile);
        if((user != null)&&(mobile != null)){
            LorduCallback lordcallback = new LorduCallback(user, mobile);
            boolean authFlag = true;
            try{
            loginContext = new LoginContext("lordhotel",lordcallback);
            System.out.println("Sending login....");
            loginContext.login();
            }catch(Exception e){
                authFlag = false;
                System.out.println(e);
            }
            if(authFlag){
                String userrole = loginContext.getSubject().getPrincipals().iterator().next().getName();
                System.out.println("Userrole -> " +userrole);
                if(userrole.equals("admin")){
                    out.println(2022);
                }
                else{
                    out.println(1);
                }
            }
            else out.println(0);//request.getRequestDispatcher("/error.jsp").include(request, response);

        }else{
            out.println(0);
            //request.getRequestDispatcher("/error.jsp").include(request, response);
        }
        //out.println("</body></html>");
    }
    /*public LoginContext returnlogincontext(){
        return AuthenticationServlet.loginContext;
    }*/
    
}