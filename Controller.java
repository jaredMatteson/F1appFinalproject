package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import java.sql.*;

import javax.swing.*;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class Controller {

    @FXML
    private ImageView app_logo;
    String filepath = "..\\..\\images.png";

    @FXML
    private Button savSctRpt;

    @FXML
    private TextField driverUrlTXT;

    @FXML
    private ComboBox<String> dropDownDrivers;

    @FXML
    private TextField LnameTXT;

    @FXML
    private TextField driverDobTXT;

    @FXML
    private TextField driverCodeTXT;

    @FXML
    private TextField FnameTXT;

    @FXML
    private TextField driverIdTXT;

    @FXML
    private TextField scoutingNotes;

    @FXML
    private TextField nationalityTXT;

    @FXML
    private Button retSctRpt;

    @FXML
    private TextField driverNumberTXT;

    private ArrayList<Driver> drivers;


    private static final String username = "jaredmatte";
    private static final String password = "g0r1lLa5";
    private static final String myUrl = "jdbc:mysql://db4free.net:3306/driversdata?verifyServerCertificate=false&useSSL=false";

    public void initialize (){
        drivers = ErgastAPI.getDriverInfo();

        for( int i = 0; i < drivers.size(); i++){
            dropDownDrivers.getItems().add(drivers.get(i).firstName + " " + drivers.get(i).lastName);
        }
    }

    @FXML
      // setting text to each text field
    void driverSelected(ActionEvent event) {
       String name = dropDownDrivers.getValue();



        Driver temp = getFromName(name);
        if(temp == null)
        {
            return;
        }

        driverIdTXT.setText(temp.driverId);
        driverCodeTXT.setText(temp.driverCode);
        driverDobTXT.setText(temp.dateOfBirth);
        driverNumberTXT.setText(temp.permanentNumber);
        driverUrlTXT.setText(temp.driverUrl);
        nationalityTXT.setText(temp.nationality);
        FnameTXT.setText(temp.firstName);
        LnameTXT.setText(temp.lastName);

    }

    @FXML
    //attempting database create record on button press
    void saveNotes(ActionEvent event) {
        databaseCreate();


    }

    @FXML
    //attempting data retrieval on button press
    void retrieveNotes(ActionEvent event) {
        databaseReatrieve();


    }

    Driver getFromName(String fullName){
        int index = fullName.indexOf(' ');
        String fName = fullName.substring(0,index);
        String lName = fullName.substring(index + 1);

        for(int i = 0; i < drivers.size(); i++) {
            if (drivers.get(i).firstName.equals(fName) && drivers.get(i).lastName.equals((lName))) {
                return drivers.get(i);
            }
        }
        return null;
    }


    void databaseCreate(){

        try {
            Connection conn = DriverManager.getConnection(myUrl, username, password);
            System.out.println("Connection Made!!");

            // mysql insert statement
            String query = "INSERT INTO racerInfo(driverId, permanentNumber, code, url, givenName," +
                    "familyName, dateOfBirth, nationality, ScoutingNotes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // create the mysql prepared statement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, driverIdTXT.getText());
            preparedStmt.setString(2, driverNumberTXT.getText());
            preparedStmt.setString(3, driverCodeTXT.getText());
            preparedStmt.setString(4, driverUrlTXT.getText());
            preparedStmt.setString(5, FnameTXT.getText());
            preparedStmt.setString(6, LnameTXT.getText());
            preparedStmt.setString(7, driverDobTXT.getText());
            preparedStmt.setString(8, nationalityTXT.getText());
            preparedStmt.setString(9, scoutingNotes.getText());


            // execute the preparedstatement
            preparedStmt.execute();
            System.out.println("Record Created");



            //Closing connection
            conn.close();
            System.out.println("Connection closed.");
        }catch (Exception e){
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());

        }

    }

    void databaseReatrieve () {

        try{

            Connection conn1 = DriverManager.getConnection(myUrl, username, password);
            System.out.println("Connection Made!!");


            //mySql retreival statement

            String retrieve = "SELECT  ScoutingNotes FROM racerInfo WHERE driverId = driverId";

            Statement driverInfo_stmt = null;

            driverInfo_stmt = conn1.createStatement();

            driverInfo_stmt.executeQuery(retrieve);


             conn1.close();
            System.out.println("Connection Closed.");
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

    }


    private void fillTable(Connection conn) {
    }
}
