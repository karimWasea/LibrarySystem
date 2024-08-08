package Librar.Library.Servess.Repository;

import Librar.Library.Modes.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRecordReprastory extends JpaRepository<BorrowingRecord, Long> {
}
