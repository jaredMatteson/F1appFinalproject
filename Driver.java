package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Driver {
    public String driverId;
    public String permanentNumber;
    public String driverCode;
    public String driverUrl;
    public String firstName;
    public String lastName;
    public String dateOfBirth;
    public String nationality;

    public Driver(){}

    public Driver(String driverId, String permanentNumber, String driverCode, String driverUrl,
                  String firstName, String lastName, String dateOfBirth, String nationality){
        this.dateOfBirth = dateOfBirth;
        this.driverCode = driverCode;
        this.driverId = driverId;
        this.driverUrl = driverUrl;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.permanentNumber = permanentNumber;




    }
}

