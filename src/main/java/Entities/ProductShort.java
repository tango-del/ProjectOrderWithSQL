package Entities;

import Enums.ProductStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Setter
@Getter
public class ProductShort {

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

    //outputAllProduct
    @Override
    public String toString() {
        return "|Name: " + name + " | Price: " + price + " | Status: " + status;
    }
}
