package restaurant.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.DETACH;

@Getter
@Setter
@Entity
@Table(name = "cheques")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cheque {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "cheque-seq", allocationSize = 1)
    private Long id;
    @Column(name = "price_AVG")
    private BigDecimal priceAvg;
    @Column(name = "created_at")
    private LocalDate createdAt;

    @ManyToMany(cascade = {DETACH})
    private List<MenuItem> menuItems;

    @ManyToOne(cascade = {DETACH})
    private User user;

    public void addMenuItem(List<MenuItem> menuItems){
        this.menuItems = menuItems;
    }

}