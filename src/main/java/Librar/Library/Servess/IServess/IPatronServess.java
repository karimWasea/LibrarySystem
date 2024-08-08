package Librar.Library.Servess.IServess;

import Librar.Library.Modes.Dtos.PatronDto;

import java.util.List;
import java.util.Optional;

public interface IPatronServess {
     List<PatronDto> findAllPatrons();
    Optional<PatronDto> findPatronById(Long id);
     void deletePatronById(Long id);
    boolean checkIfPatronExists(PatronDto PatronDto);
    // New methods
    PatronDto updatePatron(Long id, PatronDto PatronDto); // Update an existing book
    PatronDto createPatron(PatronDto PatronDto); // Create a new book
}
