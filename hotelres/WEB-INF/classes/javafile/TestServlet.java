
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

public class TestServlet extends HttpServlet{
    public void service(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        System.out.println("access in get");
        String name = request.getParameter("uname");
        System.out.println(name);
        response.setContentType("text/html");
		PrintWriter out = response.getWriter();
        out.println("hi");
    }

}