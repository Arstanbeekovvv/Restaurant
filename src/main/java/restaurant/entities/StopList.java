package restaurant.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.MERGE;

@Getter
@Setter
@Entity
@Table(name = "stop_lists")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StopList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "stop_list_seq", allocationSize = 1)
    private Long id;
    private String reason;
    private LocalDate date;

    @OneToOne(cascade = {DETACH, MERGE})
    private MenuItem menuItem;

}