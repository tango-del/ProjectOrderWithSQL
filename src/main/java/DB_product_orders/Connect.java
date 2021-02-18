package DB_product_orders;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Connect {
    static Session session;
    static SessionFactory sessionFactoryObj;

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
        configObj.configure("hibernate.cfg.xml");

        // Since Hibernate Version 4.x, ServiceRegistry Is Being Used
        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build();

        // Creating Hibernate SessionFactory Instance
        sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
        return sessionFactoryObj;
//        try {
//            // read config xml file
//            return new Configuration().configure().buildSessionFactory();
//        } catch (Throwable e) {
//            System.out.println("SessionFactory creation failed: " + e);
//            throw new ExceptionInInitializerError(e);
//        }
    }
}
