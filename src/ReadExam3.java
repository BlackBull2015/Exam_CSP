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


public class ReadExam3 extends HttpServlet {

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

    int i= 0;

    String GetString(ResultSet resultSet) throws SQLException {



        StringBuffer results = new StringBuffer();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int numberOfColumns = metaData.getColumnCount();

        results.append("<table>");
        results.append("<tr>");
        for ( int i = 1; i <= numberOfColumns; i++ ) {
            results.append("<td>"+ metaData.getColumnName( i )
                    + "</td>" );
        }

        results.append( "</tr>" );

        while ( resultSet.next() ) {
            results.append( "<tr>" );
            for ( int i = 1; i <= numberOfColumns; i++ ) {
                results.append("<td>"+ resultSet.getObject( i )
                        + "</td>" );
            }
            results.append( "</tr>" );
            results.append( "\n" );
        }
        results.append("</table>");

        return results.toString();


    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException{
        try {
            //Connects to database
            JavaBook1();
            System.out.println("Connected to database");

            Statement statement = connection.createStatement();

            ResultSet res = statement.executeQuery("SELECT * FROM form");


            String str = GetString(res);


            System.out.println("Read from database");
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
                    " <li><b> Read it: </b>: "
                    + str +"\n" +
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