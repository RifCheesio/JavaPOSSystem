package rifdhan.mohamed;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Customer {
    private static Connection con = sqlConnect.getConnection();
    private static Statement stmt;

    static {
        try {
            stmt = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static ResultSet rs;

    private String name;
    private int number;
    private int loyaltyPoints;
    private int storeCredits;

    public Customer(int customerNumber) throws SQLException {

        rs = stmt.executeQuery("SELECT * FROM posystem.customers WHERE customer_number = \"" + customerNumber +"\"");
        if (rs.next()){
            name = rs.getString("customer_name");
            number = rs.getInt("customer_number");
            loyaltyPoints = rs.getInt("customer_loyalty_points");
            storeCredits = rs.getInt("customer_store_credits");

        }


    }

    public static void makePurchase(int totalCheckoutCost, int customerNumber) throws SQLException {
        int addLoyaltyPoints = totalCheckoutCost / 10;
        int currentLoyaltyPoints = rs.getInt("customer_loyalty_points");
        int updatedLoyaltyPoints = currentLoyaltyPoints + addLoyaltyPoints;

        int rs = stmt.executeUpdate("UPDATE posystem.customers SET customer_loyalty_points = \'" + updatedLoyaltyPoints + "\'" + " WHERE customer_number = " + customerNumber);

        System.out.println("Customer received " + addLoyaltyPoints + "Loyalty Points");

    }

    public static void createMember(int customerNumber, String customerName) throws SQLException {
        int number = customerNumber;
        String name = customerName;
        int loyaltyPoints = 10;
        int storeCredit = 0;

        stmt.executeQuery("INSERT INTO posystem.customers VALUES (" + customerNumber + ", \'" + customerName + "\', " + loyaltyPoints + ", " + storeCredit + ")");

    }


    //Set values
    public int setCustomerNumber(int number){return this.number = number;};
    public int setStoreCredits(int credits){return  this.storeCredits = credits; };
    public int setLoyaltyPoints(int points){
        return this.loyaltyPoints = points;
    };
    public String setName(String name){
      return this.name = name;
    };

    //Get values
    public int getCustomerNumber(){return number; };
    public int getLoyaltyPoints() {
        return loyaltyPoints;
    };
    public String getName(){ return name;};
    public int getStoreCredits(){return storeCredits; };
}
