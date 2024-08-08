package Librar.Library.Api.Controller;

import Librar.Library.Modes.Dtos.BorrowRecordDto;
import Librar.Library.Servess.IServess.IBorrowingRecord;
import Librar.Library.Utalitz.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/borrowing")
@Validated
public class BorrowingRecordController {

    @Autowired
    private IBorrowingRecord borrowingRecordService;

    @GetMapping("/{bookId}/{patronId}")
    public ResponseEntity<?> getBorrowingRecordByBookAndPatron(
            @PathVariable Long bookId, @PathVariable Long patronId) {
        Optional<BorrowRecordDto> recordDto = borrowingRecordService.findBorrowingRecordByBookAndPatron(bookId, patronId);
        if (recordDto.isPresent()) {
            return ResponseEntity.ok(recordDto.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Borrowing record not found for book ID " + bookId + " and patron ID " + patronId));
        }
    }

    @PostMapping
    public ResponseEntity<?> createBorrowingRecord(@Valid @RequestBody BorrowRecordDto borrowingDto) {
        try {
            BorrowRecordDto savedRecord = borrowingRecordService.saveBorrowingRecord(borrowingDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRecord);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An error occurred while creating the borrowing record: " + e.getMessage()));
        }
    }

    // Optionally, you can add more methods for updating and deleting records if needed.
}
