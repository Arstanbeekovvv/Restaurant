package restaurant.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.REMOVE;

@Getter
@Setter
@Entity
@Table(name = "categories")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "category_seq", allocationSize = 1)
    private Long id;
    private String name;

    @OneToMany(cascade = {REMOVE}, mappedBy = "category")
    private List<Subcategory> subcategories;

    public void addSubcategory(Subcategory subcategory){
        if (this.subcategories == null) this.subcategories = new ArrayList<>();
        this.subcategories.add(subcategory);
    }

}