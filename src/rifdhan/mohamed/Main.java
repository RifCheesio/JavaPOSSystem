package rifdhan.mohamed;
import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner read = new Scanner(System.in);
        String empID;


        System.out.println("Enter Employee ID:");
        empID = read.nextLine();

        String uppercaseEmpID = empID.toUpperCase();
        Worker cashier = new Worker(uppercaseEmpID);

        if (uppercaseEmpID.equals(cashier.getID())) {
            menu(cashier);
        }
        else{
            System.out.println("Employee ID not found");
            main(null);
        }

	}

	public static void menu(Worker cashier) throws SQLException {
        Scanner read = new Scanner(System.in);
        int choice;
        boolean isManager = false;

        System.out.println("What would you like to do (0 to Quit) : \n 1: Enter Checkout\n 2: Check Inventory\n 3: Create New Member ");

        if(cashier.getJob().equals("Manager")){
            isManager = true;
            System.out.println(" 4: Add Product \n 5: Remove Product");
        }

        choice = read.nextInt();

        switch (choice) {
            case 0:
                System.exit(0);
            case 1:
                checkoutSystem(cashier);
            case 2:
                checkInventory(cashier);
            case 3:
                createMember(cashier);
            default:
                System.out.println("Incorrect Value, Please enter a number from the given options.");
                menu(cashier);
        }
        if(isManager){
            switch (choice){
                case 4:
                    addProduct(cashier);
                case 5:
                    removeProduct(cashier);
            }
        }
    }


    public static void checkoutSystem(Worker cashier) throws SQLException {
        boolean continueCheckout = false;
        String choice;
        String uppercaseChoice;
        String rawCode;
        String uppercaseCode;
        int productQty;
        int counter = 0;
        String productList[] = new String[50];
        int quantityList[] = new int[50];
        Scanner input = new Scanner(System.in);

        do {

            System.out.println("Enter Product ID: ");
            rawCode = input.nextLine();
            uppercaseCode = rawCode.toUpperCase();

            Product item = new Product(uppercaseCode);

            if (uppercaseCode.equals(item.ID)){
                productList[counter] = uppercaseCode;

                System.out.println("Enter Quantity: ");
                productQty = input.nextInt();
                quantityList[counter] = productQty;

            } else {
                System.out.println("Incorrect Code!");
                checkoutSystem(cashier);
            }

            System.out.println("Finish Checkout? Y/N");
            choice = input.next();
            input.nextLine();
            uppercaseChoice = choice.toUpperCase();

            if (uppercaseChoice.equals("Y")){
                continueCheckout = false;

            } else if (uppercaseChoice.equals("N")){
                continueCheckout = true;
                counter++;
            }


        }while(continueCheckout);

        billing(productList, quantityList, cashier);

    }


    public static void checkInventory(Worker cashier) throws SQLException {
        Connection con = sqlConnect.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM posystem.products");
        while(rs.next()){
            System.out.println(rs.getString(1));
        }

    }

    public static void addProduct(Worker cashier) throws SQLException {
        Scanner input = new Scanner(System.in);
        Connection con = sqlConnect.getConnection();
        Statement stmt = con.createStatement();

        System.out.println("Enter Values for Product: ");
        System.out.println("Product ID: ");
        String productID = input.nextLine();

        System.out.println("Product Name: ");
        String productName = input.nextLine();

        System.out.println("Product Quantity: ");
        String productQty = input.nextLine();

        System.out.println("Product Category: ");
        String productCategory = input.nextLine();

        System.out.println("Product Price: ");
        String productCost = input.nextLine();

        stmt.executeUpdate("INSERT INTO posystem.products VALUES (\"" + productID + "\", \"" + productName + "\", \"" + productQty+"\", \"" + productCategory+"\", \"" + productCost +"\")");

        menu(cashier);
    }

    public static void removeProduct(Worker cashier) throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Product ID of Product to be Removed: ");
        String productID = input.nextLine();
        Product.removeProduct(productID);
        menu(cashier);
    }

    public static void createMember(Worker cashier) throws SQLException {
        Scanner input = new Scanner(System.in);
        String customerName;
        int customerNumber;

        System.out.println("Enter Customer Number: ");
        customerNumber = input.nextInt();
        input.nextLine();
        System.out.println("Enter Customer Name: ");
        customerName = input.nextLine();

        Customer customer = new Customer(customerNumber);
        customer.createMember(customerNumber, customerName);


    }

    public static void billing(String[] productList, int[] quantityList, Worker cashier) throws SQLException {
        Scanner input = new Scanner(System.in);
        String isCustomerMember;
        int i = 0;

        String cashierID = cashier.getID();
        String cashierName = cashier.getName();
        int cashierTotalCheckouts = cashier.getCheckouts();
        int totalCheckoutCost = 0;




        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println("Cashier: " + cashierID + " " + cashierName + "Checkout Number: " + cashierTotalCheckouts);
        System.out.println("------------------------------------------------------------------------------------------------");
        while(productList[i] != null){
            Product item = new Product(productList[i]);
            int totalQTYCost = quantityList[i] * item.price;
            String output = String.format("|%-30s|", productList[i] + " " + item.name + " " + item.price + " " + quantityList[i] + " " + totalQTYCost );
            System.out.println(output);
            item.updateStock(quantityList[i]);
            totalCheckoutCost += totalQTYCost;
            i++;
        }
        System.out.println("Total Cost: " + totalCheckoutCost);
        System.out.println("------------------------------------------------------------------------------------------------");
        cashierTotalCheckouts++;
        cashier.setCheckouts(cashierTotalCheckouts);

        System.out.println("Is Customer a Member? Y/N");
        isCustomerMember = input.nextLine().toUpperCase();

        if(isCustomerMember.equals("Y")){
            System.out.println("Enter Customer Phone Number: ");
            int customerNumber = input.nextInt();
            Customer customer = new Customer(customerNumber);
            customer.makePurchase(totalCheckoutCost, customerNumber);
        }



        menu(cashier);
    }


}

