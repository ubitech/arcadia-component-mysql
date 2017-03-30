package eu.arcadia.maestro.mysql;

import eu.arcadia.maestro.api.MetricsProvider;
import eu.arcadia.maestro.mysql.impl.MySQLMetricsProvider;
import eu.arcadia.maestro.mysql.util.Metric;
import java.util.Arrays;

/**
 *
 * @author Christos Paraskeva (ch.paraskeva at gmail dot com)
 */
public class MySQLTest {

    //Native command for connect to MySQL : mysql --protocol=TCP -h localhost -uroot -p
    public static void main(String[] args) {
        //Properties will be set by the maestro
        System.setProperty("db_user", "root");
        System.setProperty("db_password", "*******");
        System.setProperty("db_port", "3306");
        System.setProperty("db_host", "195.46.17.235");
        System.setProperty("db_name", "");

        //Instanciate 
        MetricsProvider metricsProvider = new MySQLMetricsProvider();

        Arrays.asList(Metric.values()).forEach(metric -> {
            System.out.println(String.format("Metric: %s\tValue: %s", metric, metricsProvider.getMetric(metric.name())));

        });

    }

}
