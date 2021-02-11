package DB_product_orders;

import Entities.Product;
import Enums.ProductStatus;
import org.hibernate.Query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DatabaseRequests {
    private Scanner scanner;
    private static Query query;
    // хранит полную локальную дату времени компьютера
    private static final LocalDateTime currentDateTime = LocalDateTime.now();
    // форматирует локальную дату по указанным ключам
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    // отформатированное время по указанному паттерну
    private static final String dateTime = currentDateTime.format(formatter);

    public void createRawProduct() {
        Product testProduct = new Product();

        scanner = new Scanner(System.in);

        System.out.println("Choose Product Name:");
        String name = scanner.nextLine();

        System.out.println("Choose Product price (numbers)");
        int productPrice = scanner.nextInt();

        System.out.println("Choose Product Status");
        int productStatus = chooseProductStatus();

        scanner.close();

        testProduct.setPrice(productPrice);
        testProduct.setName(name);
        testProduct.setStatus(productStatus);
        testProduct.setDataCreate(dateTime);

        Connect.session.beginTransaction();
        Connect.session.save(testProduct);
        Connect.session.getTransaction().commit();
    }

    public void createOrder() {
        System.out.println("createOrder method");
    }

    public void updateOrderEntryQuantity() {
        System.out.println("updateOrderEntryQuantity method");
    }

    private int chooseProductStatus() {
        System.out.println("1 - out_of_stock");
        System.out.println("2 - in_stock");
        System.out.println("3 - running_low");

        int productStatus = scanner.nextInt();
        switch (productStatus) {
            case 1 :
            case 2 :
            case 3 :
                return productStatus;
            default: throw new RuntimeException("incorrect choose product status");
        }
    }
}
