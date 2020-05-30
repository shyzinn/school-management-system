package db;
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

import config.Dialogs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    Connection conn;
    // your local database server address
    String server_address = "jdbc:mysql://localhost:3306/";

    String database_name = "school_management";

    // do not change this string
    String time_zone_correction = "?useUnicode=true&useJDBCCompliantTimezoneShift"
            + "=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    String server_url_db = server_address + database_name + time_zone_correction;
    String server_url = server_address + time_zone_correction;
    String user = "root";
    String password = "root";

    public Connection connect() {
        try {
            conn = DriverManager.getConnection(server_url, user, password);
            if (conn == null) {
                System.out.println("error...");
            }
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            new Dialogs().errorAlert("Database Error", "Please check database connection",
                    e.getLocalizedMessage());
        }
        return conn;
    }

    public Connection connectToDb() {
        try {
            conn = DriverManager.getConnection(server_url_db, user, password);
            if (conn == null) {
                System.out.println("error...");
            }
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            new Dialogs().errorAlert("Database Error", "Please check database connection",
                    e.getLocalizedMessage());
        }
        return conn;
    }
}
