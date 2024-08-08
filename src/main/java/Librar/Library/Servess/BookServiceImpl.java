package Librar.Library.Servess;
import Librar.Library.Servess.IServess.IBookServes;
import Librar.Library.Servess.IServess.IBorrowingRecord;

import Librar.Library.Servess.Repository.BookReprasitory;
import Librar.Library.Modes.Book;
import Librar.Library.Modes.Dtos.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements IBookServes {

    @Autowired
    private BookReprasitory bookRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "books", key = "'all'")
    public List<BookDto> findAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "books", key = "#id")
    public Optional<BookDto> findBookById(Long id) {
        return bookRepository.findById(id)
                .map(this::convertToDto);
    }

    @Transactional
    @CachePut(value = "books", key = "#bookDto.id")
    public BookDto saveBook(BookDto bookDto) {
        Book book = convertToEntity(bookDto);
        Book savedBook = bookRepository.save(book);
        return convertToDto(savedBook);
    }

    @Transactional
    @CacheEvict(value = "books", key = "#id")
    public void deleteBookById(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new RuntimeException("Book not found with id: " + id);
        }
    }

    @Transactional
    @CachePut(value = "books", key = "#id")
    public BookDto updateBook(Long id, BookDto bookDto) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        Book book = convertToEntity(bookDto);
        book.setId(id); // Ensure the ID is set for updating the existing record
        Book updatedBook = bookRepository.save(book);
        return convertToDto(updatedBook);
    }

    @Transactional
    @CachePut(value = "books", key = "#bookDto.id")
    public BookDto createBook(BookDto bookDto) {
        // Assuming saveBook handles creation, so just call it
        return saveBook(bookDto);
    }

    public boolean checkIfBookExists(BookDto bookDto) {
        // Use the properties of the book object to call the repository method
        return bookRepository.existsByCriteria(
                bookDto.getId(),         // Book ID
                bookDto.getTitle(),      // Book Title
                bookDto.getIsbn()        // Book ISBN
        );
    }

    private BookDto convertToDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setPublicationYear(book.getPublicationYear());
        dto.setIsbn(book.getIsbn());
        return dto;
    }

    private Book convertToEntity(BookDto dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPublicationYear(dto.getPublicationYear());
        book.setIsbn(dto.getIsbn());
        return book;
    }
}
