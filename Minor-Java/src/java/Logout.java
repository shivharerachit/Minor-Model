import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class Logout extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the session and invalidate it
        HttpSession session = request.getSession(false);  // Don't create a new session if one doesn't exist
        if (session != null) {
            session.invalidate();  // Invalidate the session to log the user out
        }

        // Optionally, delete cookies (if you are using cookies to remember the user)
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);  // Set the cookie expiration time to 0 to delete it
                response.addCookie(cookie);  // Send the expired cookie to the client
            }
        }

        // Redirect to the login page after logout
        response.sendRedirect("index.html");  // Or any other page you want to redirect to after logout
    }
}
