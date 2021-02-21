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
        query = Connect.session.createQuery("From Product");
//        query = Connect.session.createQuery("select * From Product");
        Connect.session.getTransaction().commit();
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

        Hibernate query
        select p, sum(oi.quantity) from Product p
        join p.orderItemsSet oi
        group by oi.product
        order by sum(oi.quantity) desc
         */
        Connect.session.beginTransaction();

        String qr = "select p, sum(oi.quantity) from Product p " +
                "join p.orderItemsList oi " +
                "group by oi.product " +
                "order by sum(oi.quantity) desc";

        Query query = Connect.session.createQuery(qr);

        System.out.println(query.list().size());

        List<Object[]> list = query.list();
        list.forEach(f -> {
            Product product = (Product) f[0];

            System.out.println("|ID: " + product.getId() + " | Name: " + product.getName() + " | Price: " + product.getPrice() + " | Total Quantities: " + f[1]);
        });

        Connect.session.getTransaction().commit();
    }

    @Override
    public void outputOrderIdDateWithProductPriceNameQuantByOrderId() {
        /*
        select o.id,
               oi.quantity * p.price as total_price,
               p.name,
               oi.quantity,
               o.created_at
        from orders o
        left join order_items oi on o.id = oi.order_id
        left join products p on oi.product_id = p.id
        where o.id = 24;
         */
        scanner = new Scanner(System.in);

        Connect.session.beginTransaction();

        System.out.println("Choose Order ID:");

        int orderId = scanner.nextInt();

        scanner.close();

        // TODO почему только один join, и почему не нужно указывать какой именно join
//        String q1 = "select o.id, oi.quantity * oi.product.price, oi.product.name, oi.quantity, o.createdAt from Order o " +
//                "join o.orderItems oi " +
//                "where o.id = " + orderId;

        String q1 = "select o.id, oi.quantity * oi.product.price, oi.product.name, oi.quantity, " +
                "DATE_FORMAT(o.createdAt, '%Y-%m-%d %H:%i') " +
                "from Order o " +
                "join o.orderItems oi " +
                "where o.id = " + orderId;

        query = Connect.session.createQuery(q1);

        Connect.session.getTransaction().commit();

        List<Object[]> list = query.list();
        list.forEach(f -> {
            System.out.println("|ID: " + f[0] + " |Total Price: " + f[1] + " |Product Name: " + f[2] + " |Quantity: " + f[3] + " |Order Created at: " + f[4]);
        });
    }

    @Override
    public void outputOrdersById() {
        /*
        select o.id,
               oi.quantity * p.price as total_price,
               p.name,
               oi.quantity,
               o.created_at
        from orders o
        inner join order_items oi on o.id = oi.order_id
        left join products p on oi.product_id = p.id
        order by o.id;
         */
        Connect.session.beginTransaction();

        String q1 = "select o.id, oi.quantity * oi.product.price, oi.product.name, oi.quantity, " +
                "DATE_FORMAT(o.createdAt, '%Y-%m-%d %H:%i') " +
                "from Order o " +
                "join o.orderItems oi " +
                "order by o.id";

        query = Connect.session.createQuery(q1);

        List<Object[]> list = query.list();
        list.forEach(f -> {
            System.out.println("|ID: " + f[0] + " |Total Price: " + f[1] + " |Product Name: " + f[2] + " |Quantity: " + f[3] + " |Order Created at: " + f[4]);
        });

        Connect.session.getTransaction().commit();
    }

    @Override
    public void removeProductById() {
        Connect.session.beginTransaction();

        scanner = new Scanner(System.in);

        System.out.println("choose Product ID:");
        int productId = scanner.nextInt();

        query = Connect.session.createQuery("from Product where id = " + productId);

        Product product = (Product) query.uniqueResult();

        product.setDeleted(true);

        Connect.session.saveOrUpdate(product);

        // TODO удалить ли сущности order_items с указанным Product ID
//        query = Connect.session.createQuery("delete from OrderItems where product = " + productId);
//        query.executeUpdate();

        Connect.session.getTransaction().commit();
    }

    @Override
    public void removeAllProducts() {
        Connect.session.beginTransaction();

        scanner = new Scanner(System.in);

        System.out.println("Please enter DB password:");

        String pass = scanner.nextLine();

        if (Connect.prop.getProperty("hibernate.connection.password").equals(pass)) {

            System.out.println("Confirm to delete all Products: Y / N");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("y")) {
                // TODO запрос SQL требует подтверждение действия, Hibernate не требует
                query = Connect.session.createQuery("update Product set isDeleted = true");

                query.executeUpdate();
                System.out.println("Deleted successfully");
            } else {
                System.out.println("You canceled deletion");
            }
        } else {
            System.out.println("Wrong Password");
        }

        Connect.session.getTransaction().commit();
    }

    private boolean checkProductStatusToMakeOrder(ProductStatus productStatus) {
        switch (productStatus) {
            case in_stock:
            case running_low:
                return true;
            default:
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
