package Librar.Library.Controller;
import Librar.Library.Api.Controller.BorrowingRecordController;

import Librar.Library.Modes.Dtos.BorrowRecordDto;
import Librar.Library.Servess.IServess.IBorrowingRecord;
import Librar.Library.Utalitz.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BorrowingRecordControllerTest {

    @InjectMocks
    private BorrowingRecordController borrowingRecordController;

    @Mock
    private IBorrowingRecord borrowingRecordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBorrowingRecordByBookAndPatron_Success() {
        Long bookId = 1L;
        Long patronId = 1L;
        BorrowRecordDto borrowRecordDto = new BorrowRecordDto();
        when(borrowingRecordService.findBorrowingRecordByBookAndPatron(bookId, patronId))
                .thenReturn(Optional.of(borrowRecordDto));

        ResponseEntity<?> response = borrowingRecordController.getBorrowingRecordByBookAndPatron(bookId, patronId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(borrowRecordDto, response.getBody());
    }

    @Test
    void testGetBorrowingRecordByBookAndPatron_NotFound() {
        Long bookId = 1L;
        Long patronId = 1L;
        when(borrowingRecordService.findBorrowingRecordByBookAndPatron(bookId, patronId))
                .thenReturn(Optional.empty());

        ResponseEntity<?> response = borrowingRecordController.getBorrowingRecordByBookAndPatron(bookId, patronId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(new ErrorResponse("Borrowing record not found for book ID " + bookId + " and patron ID " + patronId),
                response.getBody());
    }

    @Test
    void testCreateBorrowingRecord_Success() {
        BorrowRecordDto borrowRecordDto = new BorrowRecordDto();
        when(borrowingRecordService.saveBorrowingRecord(any(BorrowRecordDto.class)))
                .thenReturn(borrowRecordDto);

        ResponseEntity<?> response = borrowingRecordController.createBorrowingRecord(borrowRecordDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(borrowRecordDto, response.getBody());
    }

    @Test
    void testCreateBorrowingRecord_Error() {
        BorrowRecordDto borrowRecordDto = new BorrowRecordDto();
        when(borrowingRecordService.saveBorrowingRecord(any(BorrowRecordDto.class)))
                .thenThrow(new RuntimeException("Database error"));

        ResponseEntity<?> response = borrowingRecordController.createBorrowingRecord(borrowRecordDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(new ErrorResponse("An error occurred while creating the borrowing record: Database error"),
                response.getBody());
    }
}

