package util;

import model.User;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    private static DBHelper instance;

    private DBHelper() {
    }

    public static DBHelper getInstance() {
        if (instance == null) {
            instance = new DBHelper();
        }

        return instance;
    }

    public Configuration getConfiguration() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(User.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/db_example?serverTimezone=Europe/Moscow");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "1234");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "validate");

        return configuration;
    }

    public Connection getConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());
            StringBuilder url = new StringBuilder();

            url.
                    append("jdbc:mysql://").        //db type
                    append("localhost:").           //host name
                    append("3306/").                //port
                    append("db_example?").          //db name
                    append("user=root&").          //login
                    append("password=1234").       //password
                    append("&serverTimezone=Europe/Moscow");

            System.out.println("URL: " + url + "\n");
            Connection connection = DriverManager.getConnection(url.toString());
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }
}