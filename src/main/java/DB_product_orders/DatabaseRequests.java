package DB_product_orders;

import Entities.Product;
import Enums.ProductStatus;
import Interfaces.SqlRequests;
import org.hibernate.Query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DatabaseRequests implements SqlRequests {
    private Scanner scanner;
    private static Query query;
    private static int userId;
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
        userId = (int) (Math.random() * 50 + 1);
        System.out.println("user id : " + userId);

        scanner = new Scanner(System.in);


        System.out.println("Choose Product ID");
        int productId = scanner.nextInt();

        query = Connect.session.createQuery("select status from Product where id = " + productId);
        System.out.println(query.uniqueResult());
    }

    @Override
    public void updateOrderEntryQuantity() {
        System.out.println("updateOrderEntryQuantity method");
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
