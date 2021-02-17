import DB_product_orders.Connect;
import DB_product_orders.DatabaseRequests;
import Entities.Order;
import Entities.OrderItems;
import Enums.ProductStatus;
import org.hibernate.Query;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class StartProgram {
    public static Scanner scanner;

    public static void main(String[] args) {
        Session session = Connect.getSession(); // create connection

        init();
        session.close();
    }

    public static void init() {
        System.out.println("Operations:");
        System.out.println("1 - Create Product");
        System.out.println("2 - Create Order");
        System.out.println("3 - Update Order quantities");

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
            default:
                System.out.println("In development");
        }
        scanner.close();
    }

    public static void testCreateOrder() {
        Session session = Connect.getSession(); // create connection
        session.beginTransaction();

        Order order = new Order();
        order.setUserId(3532);
        order.setStatus("active");

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = currentDateTime.format(formatter);

        order.setCreatedAt(dateTime);

//        session.save(order);

        OrderItems orderItems1 = new OrderItems();
        orderItems1.setProductId(12);
        orderItems1.setQuantity(14);
        orderItems1.setOrder(order);

//        session.save(orderItems1);

        OrderItems orderItems2 = new OrderItems();
        orderItems2.setProductId(14);
        orderItems2.setQuantity(2);
        orderItems2.setOrder(order);

//        session.save(orderItems2);

        session.getTransaction().commit();
        session.close();
    }
}
