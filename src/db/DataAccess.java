package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import javafx.collections.ObservableList;

public class DataAccess{

    public void DataAccess(String serverName, String portNumber, String databaseName, String userName, String password){

        //hardcoded values
        serverName = "DESKTOP-H2AK16H";
        portNumber = "1433";
        databaseName = "AHRhapsodySingleEncounterTEST";
        userName = "servicer-singleenc";
        password = "SingleEncounterP@$$";

        //connect to DB
        String connectionUrl = "jdbc:sqlserver://"+ serverName+":"+portNumber+";databaseName="+databaseName+";user="+userName+";password="+password;

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "SELECT * FROM tblSingleEncounterLookup";
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                System.out.println(rs.getString("UR") + " " + rs.getString("PriAdmNo") + " " + rs.getString("SecAdmNo") + " " + rs.getString("OrderID") + " " +
                        rs.getString("AdmDate") + " " + rs.getString("DisDate") + " " + rs.getString("Active") + " " + rs.getString("BedRequest"));
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }


    }
}


