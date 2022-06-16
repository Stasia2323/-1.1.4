package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.mapping.Property;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static String url = "jdbc:mysql://localhost:3306/test";
    private static String user = "root";
    private static String password = "Aa2248395";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }




    private static SessionFactory sessionFactory;
    private Util(){}
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties setting=new Properties();

                setting.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
                setting.put(Environment.URL, url);
                setting.put(Environment.USER, user);
                setting.put(Environment.PASS, password);
                setting.put(Environment.DIALECT,"org.hibernate.dialect.MySQLDialect");
                setting.put(Environment.SHOW_SQL,"true");
                setting.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                configuration.setProperties(setting);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry= new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory=configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    return sessionFactory;
}


    public static void closeconn() {
        try {
            getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closesesFac() {
        try {
            getSessionFactory().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
