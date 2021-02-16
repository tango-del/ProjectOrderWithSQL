package DB_product_orders;

import Entities.Order;
import Entities.OrderItems;
import Entities.Product;
import Enums.ProductStatus;
import Interfaces.SqlRequests;
import org.hibernate.Query;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DatabaseRequests implements SqlRequests {
    private Scanner scanner;
    private static Query query;
    private static int userId = (int) (Math.random() * 50 + 1);
    // хранит полную локальную дату времени компьютера
    private static final LocalDateTime currentDateTime = LocalDateTime.now();
    // форматирует локальную дату по указанным ключам
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    // отформатированное время по указанному паттерну
    private static final String dateTime = currentDateTime.format(formatter);

    @Override
    public void createRawProduct() {
        Product testProduct = new Product();

        scanner = new Scanner(System.in);

        System.out.println("Choose Product Name:");
        String name = scanner.nextLine();

        System.out.println("Choose Product price (numbers)");
        int productPrice = scanner.nextInt();

        System.out.println("Choose Product Status");
        ProductStatus userChooseProductStatus = chooseProductStatus();

        testProduct.setPrice(productPrice);
        testProduct.setName(name);
        testProduct.setStatus(userChooseProductStatus);
        testProduct.setDataCreate(dateTime);

        scanner.close();

        Connect.session.beginTransaction();
        Connect.session.save(testProduct);
        Connect.session.getTransaction().commit();
    }

    @Override
    public void createOrder() {
//        userId = (int) (Math.random() * 50 + 1);
        System.out.println("user id : " + userId);
        scanner = new Scanner(System.in);


        // заполняю массив с product id

        List<Integer> list = new ArrayList<>();

        String choose;

        do {
            System.out.println("choose product id");
//            Integer pId = scanner.nextInt();

            list.add(scanner.nextInt());

            System.out.println("want to continue?");
            choose = scanner.next();

        } while (choose.equalsIgnoreCase("Y"));


        // проверка каждого id что можно заказать

        /*
        list.stream().filter(f -> {
            Connect.session.beginTransaction();
            query = Connect.session.createQuery("select status from Product where id = " + f);
            Connect.session.getTransaction().commit();
            return checkProductStatusToMakeOrder((ProductStatus) query.uniqueResult());
        }).forEach();
         */

        System.out.println(list.size() + "list size");

        list.removeIf(f -> {
            Connect.session.beginTransaction();
            query = Connect.session.createQuery("select status from Product where id = " + f);
            Connect.session.getTransaction().commit();
            return !checkProductStatusToMakeOrder((ProductStatus) query.uniqueResult());
        });

        System.out.println(list.size() + "list size");

        if (!list.isEmpty()) {
            Order order1 = new Order();
            order1.setUserId(userId);
            order1.setStatus("active");
            order1.setCreatedAt(dateTime);

            Connect.session.beginTransaction();

            Connect.session.save(order1);

            list.forEach(f -> {
                OrderItems orderItems = new OrderItems();
                orderItems.setProductId(f);

                System.out.println("Set product id " + f + " quantity");
                int quantity = scanner.nextInt();

                orderItems.setQuantity(quantity);
                orderItems.setOrder(order1);

                Connect.session.save(orderItems);
            });
            Connect.session.getTransaction().commit();
        }

        /*
        System.out.println("Input Products ID by Space");

        String userChoose = scanner.nextLine();

        String[] productsId = userChoose.split(" ", 0);

        Arrays.stream(productsId).filter(f -> f.chars().allMatch(Character::isDigit)).

        for (String str : productsId) {

            if (str.chars().allMatch(Character::isDigit)){

            }
        }
         */


//        int productId = scanner.nextInt();
//
//        Connect.session.beginTransaction();
//        query = Connect.session.createQuery("select status from Product where id = " + productId);
//        Connect.session.getTransaction().commit();

//        boolean result = checkProductStatusToMakeOrder((ProductStatus) query.uniqueResult());
//
//        if (result) {
//            System.out.println("select quantity");
//            int quantity = scanner.nextInt();
//            Order order = new Order();
//            order.setUserId(userId);
//            order.setStatus("active");
//            order.setCreatedAt(dateTime);
//
//            Connect.session.beginTransaction();
//            Connect.session.save(order);
//            Connect.session.getTransaction().commit();
//
//            Connect.session.beginTransaction();
//            query = Connect.session.createQuery("select id from Order where userId = " + userId + " and createdAt = '" + dateTime + "'");
//            int orderId = (int) query.uniqueResult();
//            Connect.session.getTransaction().commit();
//
//            OrderItems orderItems = new OrderItems();
////            orderItems.setOrderId(orderId);
////            orderItems.setProductId(productId);
//            orderItems.setQuantity(quantity);
//
//            Connect.session.beginTransaction();
//            Connect.session.save(orderItems);
//            Connect.session.getTransaction().commit();
//
//        } else {
//            System.out.println("This Product OUT OF STOCK");
//        }
    }

    @Override
    public void updateOrderEntryQuantity() {
        System.out.println("in development");
    }

    private boolean checkProductStatusToMakeOrder(ProductStatus productStatus) {
        switch (productStatus) {
            case in_stock:
            case running_low: return true;
            default: return false;
        }
    }

    private ProductStatus chooseProductStatus() {
        System.out.println("1 - out_of_stock");
        System.out.println("2 - in_stock");
        System.out.println("3 - running_low");

        int productStatus = scanner.nextInt();
        switch (productStatus) {
            case 1:
                return ProductStatus.out_of_stock;
            case 2:
                return ProductStatus.in_stock;
            case 3:
                return ProductStatus.running_low;
            default:
                throw new RuntimeException("incorrect choose product status");
        }
    }
}
