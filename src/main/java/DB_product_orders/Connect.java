package DB_product_orders;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Connect {
    static Session session;

    public static Session getSession() {
        if (session == null) {
            session = buildSessionFactory().openSession();
        }
        return session;
    }

    public static void closeSession() {
        session.close();
    }

    private static SessionFactory buildSessionFactory() {
        try {
            // read config xml file
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable e) {
            System.out.println("SessionFactory creation failed: " + e);
            throw new ExceptionInInitializerError(e);
        }
    }
}
