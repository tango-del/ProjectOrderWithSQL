package Entities;

import Enums.ProductStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Setter
@Getter
@ToString
public class Product {
//public class Product extends ProductShort {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProductStatus status;

    @Column(name = "created_at")
    private String dataCreate;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    @JoinColumn(name = "product_id")
    private List<OrderItems> orderItemsSet = new ArrayList<>();

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
////    @ManyToOne
////    @JoinColumn(name = "")
//    private Set<OrderItems> orderItemsSet;

    public Product() {
    }
}
