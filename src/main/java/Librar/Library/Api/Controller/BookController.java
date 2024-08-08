
package Librar.Library.Api.Controller;

import Librar.Library.Servess.IServess.IBookServes;
import Librar.Library.Modes.Dtos.BookDto;
import Librar.Library.Utalitz.Error.ConflictException;
import Librar.Library.Utalitz.Error.NotFoundException;
import Librar.Library.Utalitz.Error.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
@Validated
public class BookController {

    @Autowired
    private IBookServes bookService;

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.findAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        Optional<BookDto> bookDto = bookService.findBookById(id);
        if (bookDto.isPresent()) {
            return ResponseEntity.ok(bookDto.get());
        } else {
            throw new NotFoundException("Book not found with ID: " + id);
        }
    }

    @PostMapping
    public ResponseEntity<?> createBook(@Valid @RequestBody BookDto bookDto) {
        if (bookService.checkIfBookExists(bookDto)) {
            throw new ConflictException("Book with title '" + bookDto.getTitle() + "' and ISBN '" + bookDto.getIsbn() + "' already exists.");
        }
        try {
            BookDto savedBook = bookService.saveBook(bookDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
        } catch (RuntimeException e) {
            throw new RuntimeException("An error occurred while creating the book: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @Valid @RequestBody BookDto bookDto) {
        if (!bookService.findBookById(id).isPresent()) {
            throw new NotFoundException("Book with ID " + id + " does not exist.");
        }
        try {
            BookDto updatedBook = bookService.updateBook(id, bookDto);
            return ResponseEntity.ok(updatedBook);
        } catch (RuntimeException e) {
            throw new RuntimeException("An error occurred while updating the book: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        if (!bookService.findBookById(id).isPresent()) {
            throw new NotFoundException("Book with ID " + id + " does not exist.");
        }
        try {
            bookService.deleteBookById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            throw new RuntimeException("An error occurred while deleting the book: " + e.getMessage());
        }
    }
}
