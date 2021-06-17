package rifdhan.mohamed;

import java.sql.*;

public class sqlConnect {
    private static String url = "jdbc:mysql://127.0.0.1:3306/posystem";
    private static String user = "root";
    private static String password = "CheeseAdmin";
    private static Connection con;

    private String sql;
        public static Connection getConnection() {
            try {
                con = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return con;
        }

        public static void main(){

        }

}
