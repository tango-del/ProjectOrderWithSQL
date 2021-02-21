package DB_product_orders;

import Entities.Order;
import Entities.OrderItems;
import Entities.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.*;
import java.util.Properties;

public class Connect {
    static Session session;
    private static SessionFactory sessionFactoryObj;
    static Properties prop;
    private static InputStream inputStream;

    public static Session getSession() {
        if (session == null) {
            session = buildSessionFactory().openSession();
        }
        return session;
    }

    public static void closeSession() {
        session.close();
        sessionFactoryObj.close();
    }



    private static SessionFactory buildSessionFactory() {
        // Creating Configuration Instance & Passing Hibernate Configuration File
        Configuration configObj = new Configuration();

//        configObj.configure("hibernate.cfg.xml");   ->  for read directly from hibernate.cfg.xml

        try {
            fillPropertiesFromFile();

        } catch (IOException e) {
            e.printStackTrace();
        }

        configObj.addAnnotatedClass(Order.class);
        configObj.addAnnotatedClass(OrderItems.class);
        configObj.addAnnotatedClass(Product.class);
        configObj.setProperties(prop);

        // Since Hibernate Version 4.x, ServiceRegistry Is Being Used
        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build();

        // Creating Hibernate SessionFactory Instance
        sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
        return sessionFactoryObj;
    }

    private static void fillPropertiesFromFile() throws IOException {
        try {
            String propFile = "local.properties";
            inputStream = Connect.class.getClassLoader().getResourceAsStream(propFile);

            prop = new Properties();

            prop.load(inputStream);

        } finally {
            inputStream.close();
        }
    }
}
