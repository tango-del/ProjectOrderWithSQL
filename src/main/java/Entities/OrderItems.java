package Entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
@Setter
@Getter
@ToString
public class OrderItems {

//    @Id
//    @Column(name = "product_id")
//    private int productId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Id
    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItems() {
    }
}
