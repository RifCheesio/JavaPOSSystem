package rifdhan.mohamed;

import java.awt.*;
import java.sql.*;
import java.util.Scanner;

public class Product {
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

    public String name;
    public String ID;
    public String category;
    public int quantity;
    public int price;


    public Product(String productID) throws SQLException {

        String sqlID = "\"" + productID + "\"";
        ID = productID;

        rs = stmt.executeQuery("SELECT * FROM posystem.products WHERE product_id = " + sqlID);
        if(rs.next()){
            name = rs.getString("product_name");
            category = rs.getString("product_category");
            price = rs.getInt("product_price");
            quantity = rs.getInt("product_quantity");
        }

    }

    public void Product(String productName, String productID, String productCategory, int productPrice) {
        name = productName;
        ID = productID;
        category = productCategory;
        price = productPrice;
    }

    public void updateStock(int minusQuantity) throws SQLException {
        Product product = new Product(this.ID);
        int newQuantity = product.quantity - minusQuantity;

        stmt.executeUpdate("UPDATE posystem.products SET product_quantity = " + newQuantity + " WHERE product_id = \'" + this.ID +"\'");
    }



    public static void removeProduct(String productID) throws SQLException {
        Scanner input = new Scanner(System.in);
        Product product = new Product(productID);
        String choice;

        System.out.println("Delete This Product from Database? Y/N");
        System.out.println(product.ID + " " + product.name);
        choice = input.nextLine();

        if (choice.equals("Y")) {
            System.out.println("Deleting Product from Database");
            int rs = stmt.executeUpdate("DELETE * FROM posystem.products WHERE product_id = \"" + productID + "\"");
        }else if (choice.equals("N")){
            System.out.println("Returning to Menu");
        }
    }

}
