package Entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "orders")
@Setter
@Getter
@ToString
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private String createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItems> orderItems;

    public Order() {
    }
}
