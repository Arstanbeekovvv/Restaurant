package restaurant.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.REMOVE;

@Getter
@Setter
@Entity
@Table(name = "subcategories")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "subcategory_seq")
    private Long id;
    private String name;

    @ManyToOne(cascade = {DETACH})
    private Category category;

    @OneToMany(mappedBy = "subcategory", cascade = {REMOVE})
    private List<MenuItem> menuItems;

    public void addMenuItem(MenuItem menuItem){
        if (this.menuItems == null) this.menuItems = new ArrayList<>();
        this.menuItems.add(menuItem);
    }
}