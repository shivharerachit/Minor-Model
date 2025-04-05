import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;

public class login extends HttpServlet
{
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
	{
		String s1=request.getParameter("phoneNO");
		String s2=request.getParameter("password");
		
		PrintWriter out=response.getWriter();
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql:///crop?useSSL=false","root","root");
			Statement st=con.createStatement();
                        
//                        st.execute("select Farmer_ID from farmer-registration where Phone_NO='"+s1+"' AND Password='"+s2+"'");
                        ResultSet rs=st.executeQuery("select Farmer_ID from farmer_registration where Phone_NO='"+s1+"' AND Password='"+s2+"'");
                        
                if (rs.next()) {
                             
                 // Retrieve Farmer_ID from result set
                int farmerId = rs.getInt("Farmer_ID");
                out.println("<p>"+farmerId+"</p>");
                // User found, create session
                HttpSession session = request.getSession();
                session.setAttribute("phoneNo", s1); // Store username in session
                 session.setAttribute("farmerId", farmerId); // Store Farmer_ID in session

//                // Optionally, create cookies to remember user login
//                Cookie userCookie = new Cookie("phoneNo",s1);
//                userCookie.setMaxAge(60 * 60 * 24); // 1 day
//                response.addCookie(userCookie);

                // Redirect to a different page after login
//                out.println("<h1>Logged in</h1>");
                response.sendRedirect("dashboard.html");
//                response.sendRedirect("welcome.html"); // Replace with your welcome page
            } else {
                    response.sendRedirect("sign-in1.html");
            }
                            
			
		}
		catch(Exception e)
		{
			out.println(e);
		}
		
	}
}
