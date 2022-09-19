
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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
	// public LoginContext logincontext;
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
		// logincontext = AuthenticationServlet.loginContext;
		// System.out.println(logincontext.getSubject().getPrincipals().iterator().next().getName());
		// if(logincontext == null ||
		// logincontext.getSubject().getPrincipals().iterator().next().getName().equals("admin")){
		// out.println(5);
		// }else{

		String roomno = request.getParameter("roomno");
		String sdate = request.getParameter("sdate");
		String edate = request.getParameter("edate");
		System.out.println("room no -> " + roomno + "sdate --> " + sdate + "edate -> " + edate);
		int flag = reserveroom(roomno, sdate, edate);
		System.out.println("Flag -> " + flag);
		response.addHeader("Access-Control-Allow-Origin", "*");
		out.println(flag);

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("[+]\n\n okay fuckers inside the get of my ass... \n\n");
		
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		String accesstoken = TokenExchange.map.get("accesstoken");
		System.out.println("\n\naccesstoken -> " + accesstoken + "\n\n");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json");

		URL obj = new URL("http://localhost:8080/lorduoauth/AvailableRoomsResources");
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("accesstoken", accesstoken);
		int responseCode = conn.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String inputLine;
			StringBuffer res = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				res.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(res.toString());
			//response.addHeader("Access-Control-Allow-Origin", "*");
			out.println(res);
		} else {
			System.out.println("GET request not worked");
		}
	}

	public int reserveroom(String roomno, String sdate, String edate) {
		String accesstoken = TokenExchange.map.get("accesstoken");
		System.out.println("\n\naccesstoken -> " + accesstoken + "\n\n");
		try {
			String url = String.format(
					"http://localhost:8080/lorduoauth/AvailableRoomsResources?rid=%s&sdate=%s&edate=%s", roomno, sdate,
					edate);
			System.out.println("\n\n" + url + "\n\n");
			URL obj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("accesstoken", accesstoken);
			int responseCode = conn.getResponseCode();
			System.out.println("GET Response Code :: " + responseCode);

			return 1;

		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}

	}

}
