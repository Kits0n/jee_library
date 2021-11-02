package book.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import rental.entity.Rental;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode()
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String author;
    private Integer numberOfPages;
    private LocalDate publicationDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
    private List<Rental> rentals;
}
