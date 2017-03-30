package eu.arcadia.maestro.mysql.util;

/**
 *
 * @author Christos Paraskeva (ch.paraskeva at gmail dot com)
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton pattern example using Java Enumeration
 */
public enum DSHandler {

    INSTANCE;

    private Connection connection = null;

    private final Properties prop = new Properties();

    //All fields are already set in System.properties
    private final String DBName =   "";// System.getProperty("db_name");
    private final String DBUser = System.getProperty("db_user");
    private final String DBPass = System.getProperty("db_password");
    private final String DBPort = System.getProperty("db_port");
    private final String DBHost = System.getProperty("db_host");
    private final String DBURL;

    DSHandler() {
        this.DBURL = "jdbc:mysql://" + this.DBHost + ":" + this.DBPort + "/" + this.DBName + "?user=" + this.DBUser + "&password=" + this.DBPass + "&useEncoding=true&characterEncoding=UTF-8&useSSL=false";
    }

    /**
     * Creates a new connection to MySQL database system
     *
     * @return An instance of MySQL Connection object
     */
    public Connection getConnection() {
        try {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(DSHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                this.connection = DriverManager.getConnection(this.DBURL);
            } catch (SQLException ex) {
                Logger.getLogger(DSHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DSHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.connection;
    }

    /**
     * Close all the resources used to access a Database
     *
     * @param connection A MySql Connection instance
     * @param preparedStatement A PreparedStatment stream
     * @param resultSet A ResultSet stream
     * @return
     */
    public boolean closeDBStreams(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        boolean status = true;
        if (null != resultSet) {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                Logger.getLogger(DSHandler.class.getName()).log(Level.SEVERE, null, ex);
                status = false;
            }
        }
        if (null != preparedStatement) {
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DSHandler.class.getName()).log(Level.SEVERE, null, ex);
                status = false;
            }
        }
        if (null != connection) {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DSHandler.class.getName()).log(Level.SEVERE, null, ex);
                status = false;
            }
        }

        if (status) {
            //Logger.getLogger(DSHandler.class.getName()).info("Successfuly closed all Database streams...");
        } else {
            Logger.getLogger(DSHandler.class.getName()).severe("Could not close all Database streams... This may lead to memory leak..");
        }

        return status;
    }

    public boolean rollback(Connection connection) {
        boolean isSuccess = false;
        if (null != connection) {
            try {
                connection.rollback();
                isSuccess = !isSuccess;
            } catch (SQLException ex) {
                Logger.getLogger(DSHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return isSuccess;
    }

    public static void main(String[] args) {
        Logger.getLogger(DSHandler.class.getName()).log(Level.INFO, "Database URL: {0}", new Object[]{DSHandler.INSTANCE.DBURL});
        Connection ds = DSHandler.INSTANCE.getConnection();
        PreparedStatement stm;
        try {
            stm = ds.prepareStatement("SHOW GLOBAL STATUS WHERE Variable_name = 'Aborted_clients'");
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Logger.getLogger(DSHandler.class.getName()).info(rs.getString("Value"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DSHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
