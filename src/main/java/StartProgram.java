import DB_product_orders.Connect;
import DB_product_orders.DatabaseRequests;
import org.hibernate.Session;

import java.util.Scanner;

public class StartProgram {
    public static void main(String[] args) {
        Session session = Connect.getSession(); // create connection
        init();
    }

    public static void init() {
        System.out.println("Operations:");
        System.out.println("1 - Create Product");
        System.out.println("2 - Create Order");
        System.out.println("3 - Update Order quantities");

        Scanner scanner = new Scanner(System.in);

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
            default:
                System.out.println("In development");
        }
    }
}
