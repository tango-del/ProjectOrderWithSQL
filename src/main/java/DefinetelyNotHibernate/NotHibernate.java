package DefinetelyNotHibernate;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class NotHibernate {

    static Connection connection;
    static Statement statement;
    static PreparedStatement preparedStatement;

    public static void init() throws IOException, ClassNotFoundException, SQLException {
        Properties prop = new Properties();
        String propFile = "MYSQL_Connect.properties";
        InputStream inputStream = NotHibernate.class.getClassLoader().getResourceAsStream(propFile);

        prop.load(inputStream);
        inputStream.close();

        String driver = prop.getProperty("driver");
        String server = prop.getProperty("server");
        String user = prop.getProperty("user");
        String pass = prop.getProperty("pass");

        Class.forName(driver);
        connection = DriverManager.getConnection(server, user, pass);
        statement = connection.createStatement();



        connection.close();
    }

    public static void addNewRawProducts() throws SQLException {
        preparedStatement = connection.prepareStatement("insert into products (name, price, status, created_at) values (?, ?, ?, ?)");
        preparedStatement.setString(1, "test raw");
        preparedStatement.setInt(2, 2020);
        preparedStatement.setInt(3, 0);
        preparedStatement.setString(4, "2021-10-02 12:59:00");
        preparedStatement.execute();
    }
}
