package Librar.Library.Modes;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String title;
    private String author;
    private int publicationYear;
    private String isbn;


}

