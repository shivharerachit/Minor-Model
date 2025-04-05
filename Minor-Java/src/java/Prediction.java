import java.io.*;
import java.time.*;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;

public class Prediction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get user inputs from the form
        double nSoil = Double.parseDouble(request.getParameter("n_soil"));
        double pSoil = Double.parseDouble(request.getParameter("p_soil"));
        double kSoil = Double.parseDouble(request.getParameter("k_soil"));
        double temperature = Double.parseDouble(request.getParameter("temperature"));
        double humidity = Double.parseDouble(request.getParameter("humidity"));
        double ph = Double.parseDouble(request.getParameter("ph"));
        double rainfall = Double.parseDouble(request.getParameter("rainfall"));
        String state = request.getParameter("state");
        String city = request.getParameter("city");

        // Construct the JSON request body dynamically
//        String requestBody = String.format("{\"N_SOIL\": %.1f, \"P_SOIL\": %.1f, \"K_SOIL\": %.1f, \"TEMPERATURE\": %.1f, \"HUMIDITY\": %.1f, \"ph\": %.1f, \"RAINFALL\": %.1f, \"STATE\": \"%s\"}",
//                nSoil, pSoil, kSoil, temperature, humidity, ph, rainfall, state);
        String requestBody = String.format("{\"N_SOIL\": %.1f, \"P_SOIL\": %.1f, \"K_SOIL\": %.1f, \"TEMPERATURE\": %.1f, \"HUMIDITY\": %.1f, \"ph\": %.1f, \"RAINFALL\": %.1f, \"STATE\": \"%s\"}",
                nSoil, pSoil, kSoil, temperature, humidity, ph, rainfall, state);

        // API URL
        String url = "http://cropprediction.azurewebsites.net/predict";

        // Create the URL object
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Set request method to POST
        con.setRequestMethod("POST");

        // Set request headers (optional but recommended)
        con.setRequestProperty("Content-Type", "application/json");

        // Enable input/output streams
        con.setDoOutput(true);

        // Send the JSON request body
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Get the response code
        int responseCode = con.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        // Read the response body
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            String inputLine;
            StringBuilder responseBody = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                responseBody.append(inputLine);
            }

            response.setContentType("text/html");
            String s=responseBody.toString();
             // Get the current date and time
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        // Format date and time
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String Date = currentDate.format(dateFormatter);
        String Time = currentTime.format(timeFormatter);
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel='stylesheet' href='main.css' />");   
            out.println("<link rel='stylesheet' href='navbar.css' />");   
            out.println("<link rel='stylesheet' href='footer.css' />");   
            out.println("<link rel='stylesheet' href='home.css' />");   
            out.println("<style>*{font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;} #result{color:black; margin-left:29.5%;  margin-top: -50.5%;} #result span{border:1px solid black; border-radius:8px; background-color:white; color:#259755; font-weight:bold; font-size:24px;padding:183px 150px;} form{z-index:-1;} .form-group label {font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;} .get-started-section {min-height: 100vh; background: linear-gradient(135deg, #259755 0%, #27ae60 100%); color: #ffffff; display: flex; align-items: center; justify-content: center; padding: 80px 20px;} .container {max-width: 600px; margin: 50px auto; padding: 40px; background-color: #fff; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); border-radius: 10px;} h2 {text-align: center; color: #259755; margin-bottom: 6%; text-decoration: underline;} .form-row {display: flex; justify-content: space-between; margin-bottom: 20px;} .form-row .form-group {flex: 1; margin-right: 10px;} .form-row .form-group:last-child {margin-right: 0;} .form-group label {font-size: 14px; font-weight: bold; color: black; display: block; margin-bottom: 8px;} .form-group input {width: 100%; padding: 10px; font-size: 14px; font-weight: bold; border: 1.6px solid black; border-radius: 5px; box-sizing: border-box;} .button-group {text-align: center;} .button-group input[type='submit'], .button-group input[type='reset'] {margin: 0 10px; padding: 10px 20px; font-size: 16px; border: none; border-radius: 5px; cursor: pointer;} .button-group input[type='submit'] {background-color: #4CAF50; color: white; font-weight: bold;} .button-group input[type='submit']:hover {background-color: #45a049;} .button-group input[type='reset'] {background-color: #f44336; color: white; font-weight: bold;} .button-group input[type='reset']:hover {background-color: #da190b;}</style>");

            out.println("</head>");
//            out.println("<h2>Prediction Result:</h2>");
           out.println("<body><!-- NavBar --><nav class=\"navbar\"><div class=\"logo\"><img src=\"CropPred.png\" alt=\"CropPred\" class=\"logo-img\"><span>CropPred</span></div><div class=\"nav-links\"><a href=\"dashboard.html\" class=\"active\">Home</a><a href=\"profile.html\">Profile</a><a href=\"#foot\">Contact</a><a href=\"Logout\">Logout</a></div><button class=\"mobile-menu-btn\"><span></span><span></span><span></span></button></nav><!-- Get Started Section --><section id=\"try-now\" class=\"get-started-section\"><div class=\"container\"><h2>Enter Details</h2><form action=\"Prediction\" method=\"POST\"><div class=\"form-row\"><div class=\"form-group\"><label for=\"n_soil\">N_SOIL (Nitrogen in soil):</label><input type=\"number\" id=\"n_soil\" name=\"n_soil\" step=\"0.1\" required></div><div class=\"form-group\"><label for=\"p_soil\">P_SOIL (Phosphorus in soil):</label><input type=\"number\" id=\"p_soil\" name=\"p_soil\" step=\"0.1\" required></div><div class=\"form-group\"><label for=\"k_soil\">K_SOIL (Potassium in soil):</label><input type=\"number\" id=\"k_soil\" name=\"k_soil\" step=\"0.1\" required></div></div><div class=\"form-row\"><div class=\"form-group\"><label for=\"temperature\">TEMPERATURE (°C):</label><input type=\"number\" id=\"temperature\" name=\"temperature\" step=\"0.1\" required></div><div class=\"form-group\"><label for=\"humidity\">HUMIDITY (%):</label><input type=\"number\" id=\"humidity\" name=\"humidity\" step=\"0.1\" required></div><div class=\"form-group\"><label for=\"ph\">pH (Soil pH):</label><input type=\"number\" id=\"ph\" name=\"ph\" step=\"0.1\" required></div></div><div class=\"form-row\"><div class=\"form-group\"><label for=\"rainfall\">RAINFALL (mm):</label><input type=\"number\" id=\"rainfall\" name=\"rainfall\" step=\"0.1\" required></div><div class=\"form-group\"><label for=\"state\">STATE:</label><input type=\"text\" id=\"state\" name=\"state\" required></div></div><div class=\"button-group\"><input type=\"submit\" value=\"Predict\"><input type=\"reset\" value=\"Reset\"></div></form></div></section><!-- Footer --><footer class=\"footer\" id=\"foot\"><div class=\"footer-content\"><div class=\"footer-section\"><h4>Contact</h4><p>Email: support@croppred.com</p><p>Phone: (555) 123-4567</p></div><div class=\"footer-section\"><h4>Legal</h4><a href=\"#\">Privacy Policy</a><a href=\"#\">Terms of Service</a></div><div class=\"footer-section\"><h4>Support</h4><a href=\"#\">Report Bugs</a><a href=\"#\">FAQ</a></div></div><div class=\"footer-bottom\"><p>&copy; 2024 CropPred. All rights reserved.</p></div></footer><script src=\"main.js\"></script>");  
//      
           try
            {
                // Database connection
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con1=DriverManager.getConnection("jdbc:mysql:///crop?useSSL=false","root","root");
                Statement st=con1.createStatement();
                
//       // Retrieve session and check if farmerId exists
            HttpSession session = request.getSession(false); // Retrieve existing session
            if (session != null) {
                Integer farmerId = (Integer) session.getAttribute("farmerId");
                if (farmerId != null) {
                    // Use farmerId to fetch data
                    String id = farmerId.toString();
                 out.println("<script>console.log('ID=' + '" + id + "');</script>");

String insertPredictionQuery = String.format(
    "INSERT INTO output (Farmer_ID, Date, Time, N_SOIL, P_SOIL, K_SOIL, TEMPERATURE, HUMIDITY, PH, RAINFALL, STATE, CITY, Predicted_CROP) " +
    "VALUES (%s, '%s', '%s', %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, '%s', '%s', '%s')",
    id, Date, Time, nSoil, pSoil, kSoil, temperature, humidity, ph, rainfall, state, city, s
);

st.executeUpdate(insertPredictionQuery);
                out.println("<script>function showResult(){const result=document.getElementById('result');result.style.display='block';setTimeout(()=>{result.style.display='none';},5000);}window.onload=showResult;</script>");
                out.println("<div id='result'><span>"+s+"</span></div>");
                }  
            
             else {
                    // Redirect to sign-in page if farmerId is not found in the session
                    response.sendRedirect("sign-in.html");
                   }
            }
             else {
                // Redirect to sign-in page if session does not exist
                response.sendRedirect("sign-in.html");
            }
        }
             
            catch(Exception e)
            {
                out.println(e);
            }
            out.println("</body></html>");
        }
       }
}
    

