package Librar.Library.Servess;

import Librar.Library.Servess.IServess.IBorrowingRecord;
import Librar.Library.Modes.BorrowingRecord;
import Librar.Library.Modes.Book;
import Librar.Library.Modes.Patron;
import Librar.Library.Modes.Dtos.BorrowRecordDto;

import Librar.Library.Servess.Repository.BorrowingRecordReprastory;
import Librar.Library.Servess.Repository.BookReprasitory; // Import your book repository
import Librar.Library.Servess.Repository.PatronReprastory; // Import your patron repository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowingRecordServesImp implements IBorrowingRecord {

    @Autowired
    private BorrowingRecordReprastory borrowingRecordRepository;

    @Autowired
    private BookReprasitory bookRepository;

    @Autowired
    private PatronReprastory patronRepository;

    @Override
    public Optional<BorrowRecordDto> findBorrowingRecordByBookAndPatron(Long bookId, Long patronId) {
        // Retrieve all borrowing records from the repository
        List<BorrowingRecord> records = borrowingRecordRepository.findAll();

        // Apply custom logic to filter records
        Optional<BorrowingRecord> filteredRecord = records.stream()
                .filter(record -> record.getBook().getId().equals(bookId) && record.getPatron().getId().equals(patronId))
                .findFirst();

        return filteredRecord.map(this::convertToDto);
    }

    @Override
    @Transactional
    public BorrowRecordDto saveBorrowingRecord(BorrowRecordDto borrowingDto) {
        // Fetch Book and Patron from repositories
        Book book = bookRepository.findById(borrowingDto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        Patron patron = patronRepository.findById(borrowingDto.getPatronId())
                .orElseThrow(() -> new RuntimeException("Patron not found"));

        BorrowingRecord borrowingRecord = convertToEntity(borrowingDto, book, patron);
        BorrowingRecord savedRecord = borrowingRecordRepository.save(borrowingRecord);
        return convertToDto(savedRecord);
    }

    // Convert BorrowRecord DTO to BorrowingRecord entity
    private BorrowingRecord convertToEntity(BorrowRecordDto dto, Book book, Patron patron) {
        BorrowingRecord record = new BorrowingRecord();
        record.setId(dto.getId());
        record.setBook(book);
        record.setPatron(patron);
        record.setBorrowDate(dto.getBorrowDate());
        record.setReturnDate(dto.getReturnDate());
        return record;
    }

    // Convert BorrowingRecord entity to BorrowRecord DTO
    private BorrowRecordDto convertToDto(BorrowingRecord entity) {
        BorrowRecordDto dto = new BorrowRecordDto(); // Correct DTO type
        dto.setId(entity.getId());
        dto.setBookId(entity.getBook().getId());
        dto.setPatronId(entity.getPatron().getId());
        dto.setBorrowDate(entity.getBorrowDate());
        dto.setReturnDate(entity.getReturnDate());
        return dto;
    }
}
