package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class ErgastAPI {
    public static ArrayList<Driver> getDriverInfo() {

        JSONParser parser = new JSONParser();
        JSONArray drivers = new JSONArray();

        try {
            URL url = new URL("http://ergast.com/api/f1/2018/circuits/albert_park/drivers.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error Code : "
                        + conn.getResponseCode());
            }
            Object obj = new JSONParser().parse(new InputStreamReader(conn.getInputStream()));
            JSONObject jo = (JSONObject) obj;
            drivers = (JSONArray)((JSONObject) ((JSONObject) jo.get("MRData")).get("DriverTable")).get("Drivers");


            conn.disconnect();

//            System.out.println(drivers.toJSONString());

        } catch(Exception e){
            System.out.println("Exception in NetClientGet:-" + e);
        }
        ArrayList<Driver> retDrivers = new ArrayList<Driver>();

        for( int i = 0; i < drivers.size(); i++){
            JSONObject jo = (JSONObject) drivers.get(i);
            Driver temp = new Driver((String) jo.get("driverId"),(String)jo.get("permanentNumber"),(String)jo.get("code"),(String)jo.get("url"),(String)jo.get("givenName"),(String)jo.get("familyName"),(String)jo.get("dateOfBirth"),(String)jo.get("nationality"));
            retDrivers.add(temp);
        }

        return retDrivers;

    }

}
