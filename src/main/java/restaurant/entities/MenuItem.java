package restaurant.entities;

import jakarta.persistence.*;
import lombok.*;
import restaurant.enums.IsVegetarian;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.REMOVE;

@Getter
@Setter
@Entity
@Table(name = "menu_items")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "menu_item_seq", allocationSize = 1)
    private Long id;
    private String name;
    @Column(length = 10000)
    private String image;
    private BigDecimal price;
    @Column(length = 1000)
    private String description;
    @Column(name = "is_vegetarian")
    private boolean isVegetarian;

    @ManyToOne(cascade = {DETACH})
    private Restaurant restaurant;

    @ManyToMany(cascade = {DETACH}, mappedBy = "menuItems")
    private List<Cheque> cheques;

    @OneToOne(mappedBy = "menuItem", cascade = {REMOVE})
    private StopList stopList;

    @ManyToOne(cascade = {DETACH})
    private Subcategory subcategory;

    public void addCheque(Cheque cheque){
        if (this.cheques == null) this.cheques = new ArrayList<>();
        this.cheques.add(cheque);
    }

}