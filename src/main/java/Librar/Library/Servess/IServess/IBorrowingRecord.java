package Librar.Library.Servess.IServess;

import Librar.Library.Modes.Dtos.BorrowRecordDto;
import java.util.Optional;

public interface IBorrowingRecord {
    Optional<BorrowRecordDto> findBorrowingRecordByBookAndPatron(Long bookId, Long patronId);
    BorrowRecordDto saveBorrowingRecord(BorrowRecordDto borrowingDto);
}
