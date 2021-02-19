package Entities;

import Enums.ProductStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Setter
@Getter
public class Product {

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

    @Column(name = "isDeleted")
    private boolean isDeleted;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    @JoinColumn(name = "product_id")
    private Set<OrderItems> orderItemsSet;

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
////    @ManyToOne
////    @JoinColumn(name = "")
//    private Set<OrderItems> orderItemsSet;


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", status=" + status +
                ", dataCreate='" + dataCreate + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
