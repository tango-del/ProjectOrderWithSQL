import DB_product_orders.Connect;
import DB_product_orders.DatabaseRequests;
import org.hibernate.Session;

import java.util.Scanner;

public class StartProgram {
    public static Scanner scanner;
    static Session session;

    public static void main(String[] args) {
        try {
            session = Connect.getSession(); // create connection

            init();

        } catch (Exception sqlException) {
            if (null != session.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......");
                session.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if (session != null) {
                Connect.closeSession();
            }
        }
    }

    public static void init() {
        System.out.println("Operations:");
        System.out.println("1 - Create Product");
        System.out.println("2 - Create Order");
        System.out.println("3 - Update Order quantities");
        System.out.println("4 - Output commands:");
        System.out.println("5 - Remove Product by ID or All Products");

        scanner = new Scanner(System.in);

        int userChoose = scanner.nextInt();

        DatabaseRequests requests = new DatabaseRequests();

        switch (userChoose) {
            case 1:
                requests.createRawProduct();
                break;
            case 2:
                requests.createOrder();
                break;
            case 3:
                requests.updateOrderEntryQuantity();
                break;
            case 4:
                System.out.println("1 - Output name, price, status for all products");
                System.out.println("2 - Output products ordered at least once");
                System.out.println("3 - Output Order id, created date, Product price, name, quantity bu order id");
                System.out.println("4 - Output all Orders by Id");
                int outputChoose = scanner.nextInt();
                switch (outputChoose) {
                    case 1:
                        requests.outputAllProduct();
                        break;
                    case 2:
                        requests.outputProductOrderedOnce();
                        break;
                    case 3:
                        requests.outputOrderIdDateWithProductPriceNameQuantByOrderId();
                        break;
                    case 4:
                        requests.outputOrdersById();
                        break;
                    default:
                        // решить с исключением
                        throw new RuntimeException("Incorrect Output operation number. Choose between 1 - 4");
//                        System.out.println("In development");
                }
                break;
            case 5:
                System.out.println("1 - Remove Product by ID");
                System.out.println("2 - Remove All Products by DB password");
                int removeChoose = scanner.nextInt();
                switch (removeChoose) {
                    case 1:
                        requests.removeProductById();
                        break;
                    case 2:
                        requests.removeAllProducts();
                        break;
                    default:
                        // решить с исключением
                        throw new RuntimeException("Incorrect Remove operation number. Choose between 1 - 2");
                }
                break;
            default:
                // решить с исключением
                throw new RuntimeException("Incorrect operation number. Choose between 1 - 5");
//                System.out.println("In development");
        }
        scanner.close();
    }
}
