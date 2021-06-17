package rifdhan.mohamed;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Worker {
    private static Connection con = sqlConnect.getConnection();
    private Statement stmt = con.createStatement();
    private ResultSet rs;

    private String name;
    private String ID;
    private String job;
    private int checkouts;

    public String toString(){
        return ("Employee ID: " + ID + "\n Employee Name:" + name + "\n Role: " + job + "\n Checkouts: " + checkouts);
    }

    public Worker(String employeeID) throws SQLException {

        String sqlID = "\"" + employeeID + "\"";

        rs = stmt.executeQuery("SELECT * FROM posystem.employees WHERE employee_id = " + sqlID);
        if (rs.next()){
            ID = rs.getString("employee_id");
            name = rs.getString("employee_name");
            job = rs.getString("employee_level");
            checkouts = rs.getInt("employee_checkouts");

        }
    }

    //set values
    public int setCheckouts(int customers){return this.checkouts = customers; };

    //get values
    public String getID(){return ID;};
    public String getName(){return name;};
    public String getJob(){return job; };
    public int getCheckouts(){return checkouts;};


}
