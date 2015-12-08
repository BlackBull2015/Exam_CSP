import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Created by Eric on 11/27/2015.
 */


public class Exam3 extends HttpServlet {

    //Global connection
    Connection connection;

    //Function to connect to databae
    public void JavaBook1(){
        String driver = "com.mysql.jdbc.Driver";
        try {
            Class.forName(driver);
            System.out.println("Driver selected");

            String url ="jdbc:mysql://localhost:3306/exam3"; //Database name here
            connection = DriverManager.getConnection(url, "root", "root");  //URL, user and password
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException{
        try {
            //Connects to database
            JavaBook1();
            System.out.println("Connected to database");
            //Creates prepared statement for database
            PreparedStatement send_data = connection.prepareStatement("INSERT INTO form (name, surname, idnum,email,telephone,comments) VALUES (?,?,?,?,?,?);") ;

            System.out.println("Created prepared statemetn");

            //    Can be used to create Statement directly
            //    Statement statement = connection.createStatement();

            System.out.println("Prepering statement");


            if(request.getParameter("first_name").length()>0)
                send_data.setString(1, request.getParameter("first_name"));
            else
                send_data.setString(1, "DefName");

            if(request.getParameter("last_name").length()>0)
                send_data.setString(2, request.getParameter("last_name"));
            else
                send_data.setString(2, "DefSurname");

            if(request.getParameter("idnum").length()>0)
                send_data.setString(3, request.getParameter("idnum"));
            else
                send_data.setString(3, "0");

            if(request.getParameter("email").length()>0)
                send_data.setString(4, request.getParameter("email"));
            else
                send_data.setString(4, "None");

            if(request.getParameter("telephone").length()>0)
                send_data.setString(5, request.getParameter("telephone"));
            else
                send_data.setString(5, "0");

            if(request.getParameter("comments").length()>0)
                send_data.setString(6, request.getParameter("comments"));
            else
                send_data.setString(6, "None");



            System.out.println("Statement prepared");

            System.out.println(request.getParameter("first_name"));
            System.out.println(request.getParameter("last_name"));
            System.out.println(request.getParameter("idnum"));
            System.out.println(request.getParameter("email"));
            System.out.println(request.getParameter("telephone"));
            System.out.println(request.getParameter("comments"));



            //Updates database
            System.out.println("Statement prepared");
            System.out.println(send_data.toString());
            int x = send_data.executeUpdate();
            System.out.println("Thrown to database");

            System.out.println("All Sent to database");
            //Sends back gotten informations
            response.setContentType("text/html");
            PrintWriter out= response.getWriter();
            String title ="Using GET Method to Read Form Data";
            String docType =
                    "<!doctype html public \"-//w3c//dtd html 4.0 "+
                            "transitional//en\">\n";
            out.println(docType +
                    "<html>\n" +
                    "<head><title>" + title + "</title></head>\n" +
                    "<body bgcolor=\"#f0f0f0\">\n" +
                    "<h1 align=\"center\">" + title + "</h1>\n" +
                    "<ul>\n" +
                    " <li><b>First Name</b>: "
                    + request.getParameter("first_name") + "\n" +
                    " <li><b>Last Name</b>: "
                    + request.getParameter("last_name") + "\n" +
                    " <li><b>Id Number </b>: "
                    + request.getParameter("idnum") + "\n" +
                    " <li><b>Email Address</b>: "
                    + request.getParameter("email") + "\n" +
                    " <li><b>Telephone Number</b>: "
                    + request.getParameter("telephone") + "\n" +
                    " <li><b>Comments Number</b>: "
                    + request.getParameter("comments") + "\n" +
                    "</ul>\n" +
                    "</body></html>")
            ;

        }catch(Exception e){
            PrintWriter out= response.getWriter();
            String str = "";
            str = "Something went wrong, Servlet died";
            out.println(str);
        }
    }

    public void doPost (HttpServletRequest request,
                        HttpServletResponse response)
            throws ServletException,IOException
    {
        doGet(request,response);

    }

}