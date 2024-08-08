package Librar.Library.Controller;


import Librar.Library.Servess.IServess.IBookServes;
import Librar.Library.Modes.Dtos.BookDto;
import Librar.Library.Utalitz.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import Librar.Library.Api.Controller.BookController;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookControllerTest {

    @Mock
    private IBookServes bookService;

    @InjectMocks
    private BookController bookController;

    private BookDto bookDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Test Title");
        bookDto.setIsbn("1234567890");
        bookDto.setAuthor("Author Name");
    }

    @Test
    void testGetAllBooks() {
        List<BookDto> bookList = Arrays.asList(bookDto);
        when(bookService.findAllBooks()).thenReturn(bookList);

        ResponseEntity<List<BookDto>> response = bookController.getAllBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookList, response.getBody());
        verify(bookService, times(1)).findAllBooks();
    }

    @Test
    void testGetBookById() {
        when(bookService.findBookById(1L)).thenReturn(Optional.of(bookDto));

        ResponseEntity<?> response = bookController.getBookById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookDto, response.getBody());
        verify(bookService, times(1)).findBookById(1L);
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookService.findBookById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = bookController.getBookById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Book not found with ID: 1", ((ErrorResponse) response.getBody()).getMessage());
        verify(bookService, times(1)).findBookById(1L);
    }

    @Test
    void testCreateBook_Success() {
        when(bookService.checkIfBookExists(bookDto)).thenReturn(false);
        when(bookService.saveBook(bookDto)).thenReturn(bookDto);

        ResponseEntity<?> response = bookController.createBook(bookDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(bookDto, response.getBody());
        verify(bookService, times(1)).checkIfBookExists(bookDto);
        verify(bookService, times(1)).saveBook(bookDto);
    }

    @Test
    void testCreateBook_Conflict() {
        when(bookService.checkIfBookExists(bookDto)).thenReturn(true);

        ResponseEntity<?> response = bookController.createBook(bookDto);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Book with title 'Test Title' and ISBN '1234567890' already exists.", ((ErrorResponse) response.getBody()).getMessage());
        verify(bookService, times(1)).checkIfBookExists(bookDto);
        verify(bookService, never()).saveBook(bookDto);
    }

    @Test
    void testUpdateBook_Success() {
        when(bookService.findBookById(1L)).thenReturn(Optional.of(bookDto));
        when(bookService.updateBook(1L, bookDto)).thenReturn(bookDto);

        ResponseEntity<?> response = bookController.updateBook(1L, bookDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookDto, response.getBody());
        verify(bookService, times(1)).findBookById(1L);
        verify(bookService, times(1)).updateBook(1L, bookDto);
    }

    @Test
    void testUpdateBook_NotFound() {
        when(bookService.findBookById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = bookController.updateBook(1L, bookDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Book with ID 1 does not exist.", ((ErrorResponse) response.getBody()).getMessage());
        verify(bookService, times(1)).findBookById(1L);
        verify(bookService, never()).updateBook(anyLong(), any(BookDto.class));
    }

    @Test
    void testDeleteBook_Success() {
        when(bookService.findBookById(1L)).thenReturn(Optional.of(bookDto));

        ResponseEntity<?> response = bookController.deleteBook(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(bookService, times(1)).findBookById(1L);
        verify(bookService, times(1)).deleteBookById(1L);
    }

    @Test
    void testDeleteBook_NotFound() {
        when(bookService.findBookById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = bookController.deleteBook(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Book with ID 1 does not exist.", ((ErrorResponse) response.getBody()).getMessage());
        verify(bookService, times(1)).findBookById(1L);
        verify(bookService, never()).deleteBookById(anyLong());
    }
}

