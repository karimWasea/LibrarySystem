package Librar.Library.Servess.IServess;

import Librar.Library.Modes.Dtos.BookDto;
import java.util.List;
import java.util.Optional;


public interface IBookServes {
    List<BookDto> findAllBooks();
    Optional<BookDto> findBookById(Long id);
    BookDto saveBook(BookDto bookDto); // Create or add a new book
    void deleteBookById(Long id);
      boolean checkIfBookExists(BookDto bookDto);
    // New methods
    BookDto updateBook(Long id, BookDto bookDto); // Update an existing book
    BookDto createBook(BookDto bookDto); // Create a new book
}
