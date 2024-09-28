package blps.lab.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "entity_id", nullable = false)
    private Long entityId;
}