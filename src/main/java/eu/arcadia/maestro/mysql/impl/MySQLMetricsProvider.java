package eu.arcadia.maestro.mysql.impl;

import eu.arcadia.maestro.api.MetricsProvider;
import eu.arcadia.maestro.mysql.util.DSHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christos Paraskeva (ch.paraskeva at gmail dot com)
 */
public class MySQLMetricsProvider implements MetricsProvider {
 
    @Override
    public String getMetric(String name) {

        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet resultSet = null;
        try {
            connection = DSHandler.INSTANCE.getConnection();
            stm = connection.prepareStatement("SHOW GLOBAL STATUS WHERE Variable_name = ?");
            stm.setString(1, name);
            resultSet = stm.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("Value");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DSHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DSHandler.INSTANCE.closeDBStreams(connection, stm, resultSet);
        }

        return "";
    }

    @Override
    public <T> T getMetric(String name, Class<T> clazz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
