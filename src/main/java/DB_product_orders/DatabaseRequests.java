package DB_product_orders;

import Entities.Order;
import Entities.OrderItems;
import Entities.Product;
import Enums.ProductStatus;
import Interfaces.SqlRequests;
import org.hibernate.Query;

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
        Product rawProduct = new Product();

        scanner = new Scanner(System.in);

        System.out.println("Choose Product Name:");
        String name = scanner.nextLine();

        System.out.println("Choose Product price (numbers)");
        int productPrice = scanner.nextInt();

        System.out.println("Choose Product Status");
        ProductStatus userChooseProductStatus = chooseProductStatus();

        rawProduct.setPrice(productPrice);
        rawProduct.setName(name);
        rawProduct.setStatus(userChooseProductStatus);
        rawProduct.setDataCreate(dateTime);

        scanner.close();

        Connect.session.beginTransaction();
        Connect.session.save(rawProduct);
        Connect.session.getTransaction().commit();
    }

    @Override
    public void createOrder() {
        System.out.println("user id : " + userId);
        scanner = new Scanner(System.in);

        // заполняю массив с product id
        List<Integer> list = new ArrayList<>();

        String choose;

        do {
            System.out.println("choose product id");

            list.add(scanner.nextInt());

            System.out.println("want to continue?");

            choose = scanner.next();

        } while (choose.equalsIgnoreCase("Y"));

        // проверка каждого id что можно заказать

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
            order1.setStatus("open");
            order1.setCreatedAt(dateTime);

            Connect.session.beginTransaction();
            Connect.session.save(order1);

            list.forEach(f -> {
                OrderItems orderItems = new OrderItems();

                query = Connect.session.createQuery("from Product where id = " + f);

                orderItems.setProduct((Product) query.uniqueResult());

                System.out.println("Set product id " + f + " quantity");
                int quantity = scanner.nextInt();

                orderItems.setQuantity(quantity);
                orderItems.setOrder(order1);

                Connect.session.save(orderItems);

            });
            Connect.session.getTransaction().commit();
        }
    }

    @Override
    public void updateOrderEntryQuantity() {
        scanner = new Scanner(System.in);

        System.out.println("Choose Order ID:");
        int orderId = scanner.nextInt();

        System.out.println("Choose Product ID:");
        int productId = scanner.nextInt();

        Connect.session.beginTransaction();

        query = Connect.session.createQuery("from OrderItems where order = " + orderId + " and product = " + productId);
        // если возвращает несколько сущностей по указанному запросу то берём первую сущность в list
        OrderItems orderItems = (OrderItems) query.list().get(0);

        System.out.println("Current quantity : " + orderItems.getQuantity() + " set new quantity :");

        int quantity = scanner.nextInt();

        orderItems.setQuantity(quantity);

        Connect.session.saveOrUpdate(orderItems);

        Connect.session.getTransaction().commit();
    }

    @Override
    public void outputAllProduct() {
        /*
         TODO спросить как лучше использовать вывод конкретных колонок
          ProductShort с нужными колонками или сохранять все данные из Product и выводить нужные поля
         */
        Connect.session.beginTransaction();
//        query = Connect.session.createQuery("From ProductShort");
        query = Connect.session.createQuery("From Product");
        Connect.session.getTransaction().commit();
//        List<ProductShort> productShortList = query.list();
//        productShortList.forEach(System.out::println);
        List<Product> productList = query.list();
        productList.forEach(f -> {
            System.out.println("|Name: " + f.getName() + " | Price: " + f.getPrice() + " | Status: " + f.getStatus());
        });
    }

    @Override
    public void outputProductOrderedOnce() {
        /*
        SQL query
        select p.*, sum(oi.quantity) from products p
        inner join order_items oi on p.id = oi.product_id
        group by oi.product_id
        order by sum(oi.quantity) desc;
         */
        String qr = "select p, sum(quantity) from Product p inner join OrderItems on id = productId group by productId order by sum(quantity) desc";
        String qr4 = "from Product p join";
        String qr5 = "sum(quantity) from Product p join p.orderItemsSet oi group by oi.product order by sum(quantity) desc";
        String qr2 = "select p from Product p where p.id IN (select oi.productId from OrderItems oi)";
        String qr3 = "select p from Product p";

        Connect.session.beginTransaction();

        query = Connect.session.createQuery(qr4);

        System.out.println(query.list().size());

        List<Product> list = query.list();
        list.forEach(System.out::println);


//        List<Object[]> list = query.list();
//        list.forEach(f -> {
////            Product product = (Product) f[0];
////            int sum = (int) f[1];
//            for (Object o : f) {
//                System.out.println(o);
//            }
//
//        });


        Connect.session.getTransaction().commit();
    }

    @Override
    public void outputOrderIdDateWithProductPriceNameQuantByOrderId() {
        System.out.println("in development");
    }

    @Override
    public void outputOrdersById() {
        System.out.println("in development");
    }

    private boolean checkProductStatusToMakeOrder(ProductStatus productStatus) {
        switch (productStatus) {
            case in_stock:
            case running_low:
                return true;
            default:
//                System.out.println("This Product out of stock. You can`t make order for him");
                return false;
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
