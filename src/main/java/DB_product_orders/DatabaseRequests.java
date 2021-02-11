package DB_product_orders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatabaseRequests {
    // хранит полную локальную дату времени компьютера
    private static final LocalDateTime currentDateTime = LocalDateTime.now();
    // форматирует локальную дату по указанным ключам
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    // отформатированное время по указанному паттерну
    private static final String dateTime = currentDateTime.format(formatter);

    public void createRawProduct() {
        System.out.println("createRawProduct method");
    }

    public void createOrder() {
        System.out.println("createOrder method");
    }

    public void updateOrderEntryQuantity() {
        System.out.println("updateOrderEntryQuantity method");
    }
}
