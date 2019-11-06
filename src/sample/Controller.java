package sample;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.*;
import java.util.Collections;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField addUrNum;

    @FXML
    private TextField addPrmAdmNum;

    @FXML
    private Button updateBtn;

    @FXML
    private TextField updActive;

    @FXML
    private TextField updOrdID;

    @FXML
    private TextField addSecAdmNum;

    @FXML
    private Button addBtn;

    @FXML
    private TextField updAdmDat;

    @FXML
    private TextField addOrdId;

    @FXML
    private TextField delURNum;

    @FXML
    private TextField addMsgCtrID;

    @FXML
    private TextField updSecAdmNum;

    @FXML
    private TextField updBedReqID;

    @FXML
    private TextField addActive;

    @FXML
    private TextField updPrmAdmNum;

    @FXML
    private Button viewBtn;

    @FXML
    private TextField addAdmDate;

    @FXML
    private Button deleteBtn;

    @FXML
    private TableView<ObservableList> viewTable;

    @FXML
    private TextField updDisDat;

    @FXML
    private TextField addBedReqID;

    @FXML
    private TextField addDisDate;

    @FXML
    private TextField updMsgCtrID;

    @FXML
    private TextField updURNum;

    private ObservableList<ObservableList> data;

    private  Connection connection;

    @Override
    public void initialize(URL Location, ResourceBundle resource) {
        refreshViewTable();
    }

    private String setConnection() {
        //hardcoded values
        String serverName = "DESKTOP-H2AK16H";
        String portNumber = "1433";
        String databaseName = "SingleEncounter";
        String userName = "julius";
        String password = "ZAQ!2wsxcde3";

        //connect to DB
        return  "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + databaseName + ";user=" + userName + ";password=" + password;
    }

    @FXML
    public void viewTable(javafx.event.ActionEvent actionEvent) {
        this.refreshViewTable();
        //clear table
        /*viewTable.getColumns().clear();

        data = FXCollections.observableArrayList();

        if (this.connection != null) {
            try (Statement stmt = this.connection.createStatement();) {
                String SQL = "SELECT * FROM tblSingleEncounterLookup";
                ResultSet rs = stmt.executeQuery(SQL);

                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    //We are using non property style for making dynamic table
                    final int j = i;
                    TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {

                        public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                            return new SimpleStringProperty(param.getValue().get(j) == null ? "" : param.getValue().get(j).toString());
                        }
                    });
                    //System.out.println("test" + updPrmAdmNum.getText());
                    viewTable.getColumns().addAll(col);
                }

                // Iterate through the data in the result set and display it.
                while (rs.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        //Iterate Column
                        row.add(rs.getString(i));
                    }
                    System.out.println("Row [1] added " + row);
                    data.add(row);

                }

                //FINALLY ADDED TO TableView
                viewTable.setItems(data);

                //System.out.println(rs.getString("UR") + " " + rs.getString("PriAdmNo") + " " + rs.getString("SecAdmNo") + " " + rs.getString("OrderID") + " " +
                //        rs.getString("AdmDate") + " " + rs.getString("DisDate") + " " + rs.getString("Active") + " " + rs.getString("BedRequest"));
            }

            // Handle any errors that may have occurred.
            catch (Exception e) {
                e.printStackTrace();
            }
        } */
    }

    @FXML
    public void addEntry (javafx.event.ActionEvent e){
        //validation
        if (addUrNum.getText() == null || addUrNum.getText().trim().equalsIgnoreCase("") ||
                addPrmAdmNum.getText() == null || addUrNum.getText().trim().equalsIgnoreCase("") ||
                addAdmDate.getText() == null || addAdmDate.getText().trim().equalsIgnoreCase("") ||
                addActive.getText() == null || addActive.getText().equalsIgnoreCase("")  ){
            System.out.println("Data cannot be NULL");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Data Validation");
            alert.setHeaderText("UR Number, Primary Admission Number, Admission Date, and Active Status cannot be blank.");
            alert.setContentText("Please Try Again.");

            alert.showAndWait();
            return;
        }

        //clear table
        viewTable.getColumns().clear();

        data = FXCollections.observableArrayList();

        try (Connection con = DriverManager.getConnection(setConnection()); Statement stmt = con.createStatement();) {
            //call add stored procedure
            String SQL = "EXECUTE dbo.spSingleEncounter_insertRecord '" + addUrNum.getText() + "', '" + addPrmAdmNum.getText() + "', '" + addSecAdmNum.getText() + "', '" + addOrdId.getText() + "', '" +
                    addAdmDate.getText() + "', '" +  addDisDate.getText() + "', '" + addActive.getText() + "', '" + addBedReqID.getText() + "', 'Insert', 'MANUAL'";
            System.out.println(SQL);
            stmt.executeQuery(SQL);
            refreshViewTable();
            System.out.println("Data insert...");
        }
        catch (Exception t){
            t.printStackTrace();
        }
    }

    @FXML
    public void updEntry (javafx.event.ActionEvent e){

        //clear table
        viewTable.getColumns().clear();

        data = FXCollections.observableArrayList();

        try (Connection con = DriverManager.getConnection(setConnection()); Statement stmt = con.createStatement();) {
            //call add stored procedure
            String SQL = "EXECUTE dbo.spSingleEncounter_updateRecord '" + updURNum.getText() + "', '" + updPrmAdmNum.getText() + "', '" + updSecAdmNum.getText() + "', '" + updOrdID.getText() + "', '" +
                    updAdmDat.getText() + "', '" +  updDisDat.getText() + "', '" + updActive.getText() + "', '" + updBedReqID.getText() + "', 'Update', 'MANUAL'";
            System.out.println(SQL);
            stmt.executeQuery(SQL);
            refreshViewTable();
            System.out.println("Data Update...");
        }
        catch (Exception t){
            t.printStackTrace();
        }
    }

    @FXML
    public void delEntry (javafx.event.ActionEvent e){

        //clear table
        viewTable.getColumns().clear();

        data = FXCollections.observableArrayList();

        try (Connection con = DriverManager.getConnection(setConnection()); Statement stmt = con.createStatement();) {
            //call add stored procedure
            String SQL = "EXECUTE dbo.spSingleEncounter_deleteRecord '" + delURNum.getText() + "', 'Update', 'MANUAL'";
            System.out.println(SQL);
            stmt.executeQuery(SQL);
            refreshViewTable();
            System.out.println("Data Delete...");
        }
        catch (Exception t){
            t.printStackTrace();
        }
    }

    public  void  refreshViewTable (){
        //clear table
        System.out.println("Start");
        this.viewTable.getColumns().clear();

        this.data = FXCollections.observableArrayList();
            try ( Connection con = DriverManager.getConnection(setConnection()); Statement stmt = con.createStatement()/*this.connection.createStatement();*/) {
                String SQL = "SELECT * FROM tblSingleEncounterLookup";
                ResultSet rs = stmt.executeQuery(SQL);

                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    //We are using non property style for making dynamic table
                    final int j = i;
                    TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {

                        public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                            System.out.println(param.getValue().get(j));
                            return new SimpleStringProperty(param.getValue().get(j) == null ? "" : param.getValue().get(j).toString());
                        }
                    });
                    //System.out.println("test" + updPrmAdmNum.getText());
                    this.viewTable.getColumns().addAll(col);
                }

                // Iterate through the data in the result set and display it.
                while (rs.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        //Iterate Column
                        row.add(rs.getString(i));
                    }
                    System.out.println("Row [1] added " + row);
                    this.data.add(row);

                }

                //FINALLY ADDED TO TableView
                this.viewTable.setItems(data);

                //System.out.println(rs.getString("UR") + " " + rs.getString("PriAdmNo") + " " + rs.getString("SecAdmNo") + " " + rs.getString("OrderID") + " " +
                //        rs.getString("AdmDate") + " " + rs.getString("DisDate") + " " + rs.getString("Active") + " " + rs.getString("BedRequest"));
            }

            // Handle any errors that may have occurred.
            catch (Exception e) {
                e.printStackTrace();
            }
//        }
        System.out.println("End");
    }

}
