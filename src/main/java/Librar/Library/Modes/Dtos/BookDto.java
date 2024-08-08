package Librar.Library.Modes.Dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
@Setter
@Getter
public class BookDto {

    private Long id; // ID should not be required during creation
    @NotEmpty

    @NotBlank(message = "Title is mandatory")
    @Size(max = 100, message = "Title should not exceed 100 characters")
    private String title;
    @NotEmpty

    @NotBlank(message = "Author is mandatory")
    private String author;
    @NotEmpty

    @NotNull(message = "Publication year is mandatory")
    @Positive(message = "Publication year must be a positive number")
    private Integer publicationYear;
    @NotEmpty
    @NotEmpty

    @NotBlank(message = "ISBN is mandatory")
    @Size(max = 20, message = "ISBN should not exceed 20 characters")
    private String isbn;

    // Getter and Setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and Setter for author
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // Getter and Setter for publicationYear
    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    // Getter and Setter for isbn
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
