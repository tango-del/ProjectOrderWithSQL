package Interfaces;

public interface SqlRequests {
    void createRawProduct();
    void createOrder();
    void updateOrderEntryQuantity();
    void outputAllProduct();
    void outputProductOrderedOnce();
    void outputOrderIdDateWithProductPriceNameQuantByOrderId();
    void outputOrdersById();
    void removeProductById();
    void removeAllProducts();
}
