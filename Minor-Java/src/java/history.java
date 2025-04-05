import java.io.*;
import java.time.*;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;

public class history extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//            String id=request.getParameter("10");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel='stylesheet' href='main.css' />");   
            out.println("<link rel='stylesheet' href='navbar.css' />");   
//            out.println("<link rel='stylesheet' href='footer.css' />");   
            out.println("<link rel='stylesheet' href='home.css' />");   

            out.println("</head>");
            out.println("<!DOCTYPE html><html><head><style>table{background-color:white;margin-top:-54%;cellpadding:20px;} th,td{padding:25px;}.get-started-section {min-height: 100vh;background: linear-gradient(135deg, #259755 0%, #27ae60 100%);color: #ffffff;display: flex;align-items: center;justify-content: center;padding: 80px 20px;}.get-started-content {max-width: 1200px;width: 100%;text-align: center;margin-bottom: 1%;}.get-started-section h2 {font-size: 3rem;margin-bottom: 1rem;}.subtitle {font-size: 1.2rem;margin-bottom: 4rem;opacity: 0.9;}.features-grid {display: grid;grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));margin-bottom: 4rem;margin-left: 15.5%;}.feature-card {height: 300px;width: 330px;background-color: rgba(255, 255, 255, 0.1);padding: 2rem;border-radius: 15px;backdrop-filter: blur(10px);transition: transform 0.3s ease;}.feature-card:hover {transform: translateY(-5px);}.feature-icon {font-size: 2.5rem;margin-bottom: 1rem;}.feature-card h3 {font-size: 1.5rem;margin-bottom: 1rem;}.feature-card p {opacity: 0.9;line-height: 1.6;}.cta-section {margin-top: 3rem;}.cta-section .primary-btn {background-color: #ffffff;color: #259755;font-size: 1.2rem;padding: 15px 40px;margin-bottom: 1rem;}.cta-section .primary-btn:hover {background-color: rgba(255, 255, 255, 0.9);}.cta-note {font-size: 0.9rem;opacity: 0.8;}@media (max-width: 768px) {.get-started-section {padding: 60px 20px;}.get-started-section h2 {font-size: 2rem;}.features-grid {grid-template-columns: 1fr;}}a {text-decoration: none;color: white;}#foot {margin-top: -5%;}</style></head><body><nav class=\"navbar\"><div class=\"logo\"><img src=\"CropPred.png\" alt=\"CropPred\" class=\"logo-img\"><span>CropPred</span></div><div class=\"nav-links\"><a href=\"dashboard.html\" class=\"active\">Home</a><a href=\"profile.html\">Profile</a><a href=\"#foot\">Contact</a><a href=\"Logout\">Logout</a></div><button class=\"mobile-menu-btn\"><span></span><span></span><span></span></button></nav><section id=\"try-now\" class=\"get-started-section\"><div class=\"get-started-content\"><div class=\"features-grid\"></div></div></section><footer class=\"footer\" id=\"foot\"><div class=\"footer-content\"><div class=\"footer-section\"><h4>Contact</h4><p>Email: support@croppred.com</p><p>Phone: (555) 123-4567</p></div><div class=\"footer-section\"><h4>Legal</h4><a href=\"#\">Privacy Policy</a><a href=\"#\">Terms of Service</a></div><div class=\"footer-section\"><h4>Support</h4><a href=\"#\">Report Bugs</a><a href=\"#\">FAQ</a></div></div><div class=\"footer-bottom\"><p>&copy; 2024 CropPred. All rights reserved.</p></div></footer><script src=\"main.js\"></script>");
    out.println("<style>body, html { height: 100%; margin: 0; display: flex; flex-direction: column; } .content { flex: 1; } .footer { background-color: #2c3e50; color: #ffffff; padding: 4rem 2rem 2rem;} .footer-content { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 2rem; max-width: 1200px; margin: 0 auto; } .footer-section h4 { color: #259755; margin-bottom: 1.5rem; } .footer-section a { color: #ffffff; text-decoration: none; display: block; margin-bottom: 0.5rem; transition: color 0.3s ease; } .footer-section a:hover { color: #259755; } .footer-bottom { text-align: center; margin-top: 3rem; padding-top: 2rem; border-top: 1px solid rgba(255, 255, 255, 0.1); }</style>");

      try {
            // Retrieve session and check if farmerId exists
            HttpSession session = request.getSession(false); // Retrieve existing session
            if (session != null) {
                Integer farmerId = (Integer) session.getAttribute("farmerId");
                if (farmerId != null) {
                    // Use farmerId to fetch data
                    String id = farmerId.toString();
//                     id=(String)session.getAttribute("farmerId");;

                    // Set up database connection
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con1 = DriverManager.getConnection("jdbc:mysql:///crop?useSSL=false", "root", "root");
                    Statement st = con1.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM output WHERE Farmer_ID = '" + id + "'");

                    // Display data in HTML table
                    out.println("<center>");
                    out.println("<table border='2px'>");
                    out.println("<th>Farmer_ID</th><th>Date</th><th>Time</th><th>N-soil</th><th>P-soil</th><th>K-soil</th>");
                    out.println("<th>Temperature</th><th>Humidity</th><th>ph</th><th>Rainfall</th><th>State</th><th>Predicted Crop</th>");

                    while (rs.next()) {
                        out.println("<tr>");
                        out.println("<td>" + rs.getString(1) + "</td>");
                        out.println("<td>" + rs.getString(2) + "</td>");
                        out.println("<td>" + rs.getString(3) + "</td>");
                        out.println("<td>" + rs.getString(4) + "</td>");
                        out.println("<td>" + rs.getString(5) + "</td>");
                        out.println("<td>" + rs.getString(6) + "</td>");
                        out.println("<td>" + rs.getString(7) + "</td>");
                        out.println("<td>" + rs.getString(8) + "</td>");
                        out.println("<td>" + rs.getString(9) + "</td>");
                        out.println("<td>" + rs.getString(10) + "</td>");
                        out.println("<td>" + rs.getString(11) + "</td>");
                        out.println("<td>" + rs.getString(12) + "</td>");
                        out.println("</tr>");
                    }

                    out.println("</table>");
                    out.println("</center>");
                } else {
                    // Redirect to sign-in page if farmerId is not found in the session
                    response.sendRedirect("sign-in.html");
                }
            } else {
                // Redirect to sign-in page if session does not exist
                response.sendRedirect("sign-in.html");
            }
        } catch (Exception e) {
            // Handle exception and display error message
            out.println("<p>Error: " + e.getMessage() + "</p>");
            e.printStackTrace(); // Log the exception for debugging purposes
        }
            
//            out.println("<script>function showPopup(){const popup=document.getElementById('result');popup.classList.add('show');setTimeout(()=>{popup.classList.remove('show');},5000);}   window.onload = showPopup;</script>"); 
            out.println("</body></html>");
        }
    }


