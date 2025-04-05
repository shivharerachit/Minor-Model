import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.Random;

public class signup extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Getting input values
        String s1 = request.getParameter("phone_no");  // Phone number
        String s2 = request.getParameter("password");  // Password
        String s3 = request.getParameter("aadhar");    // Aadhar number
        String s4 = request.getParameter("username");  // Username
        String s5 = request.getParameter("email");     // Email
        String s6 = request.getParameter("fullname");  // Full name
        String s7 = request.getParameter("dob");       // Date of birth
        String s8 = request.getParameter("gender");    // Gender
        String s9 = request.getParameter("state");     // State
        String s10 = request.getParameter("city");     // City

        PrintWriter out = response.getWriter();

        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql:///crop?useSSL=false", "root", "root");

            // Check if the user already exists based on Phone_NO or Email
            Statement st = con.createStatement();
            String checkUserQuery = "SELECT * FROM farmer_registration WHERE Phone_NO = '" + s1 + "' OR Aadhar_NO = '" + s3 + "'";
            ResultSet rs = st.executeQuery(checkUserQuery);

            if (rs.next()) {
                // User already exists
                    response.sendRedirect("sign-up1.html");
//                out.println("<h1>User already exists. Please login.</h1>");
            } else {
//                // Generate a random Farmer_ID
//                Random random = new Random();
//                int farmerId = random.nextInt(100000); // Generates a number between 0 and 99999

                // Insert new user details into the database
                String insertQuery = "INSERT INTO farmer_registration (Name, Phone_NO, Password, Mail_ID, Aadhar_NO, UserName, DOB, Gender, State, City) "
                        + "VALUES ('" + s6 + "','" + s1 + "','" + s2 + "','" + s5 + "','" + s3 + "','" + s4 + "','" + s7 + "','" + s8 + "','" + s9 + "','" + s10 + "')";
                
                int result = st.executeUpdate(insertQuery);

                if (result > 0) {
                    out.println("<h1>Registration Successful!</h1>");
                    response.sendRedirect("sign-in.html"); // Redirect to the sign-in page
                } else {
//                    out.println("<h1>Registration Failed. Please try again.</h1>");
                    response.sendRedirect("sign-up1.html");
                }
            }

            // Close resources
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            out.println("<h1>Error:</h1>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }
}
