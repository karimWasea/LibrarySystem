package Librar.Library.Api.Controller;

import Librar.Library.Servess.IServess.IPatronServess;
import Librar.Library.Modes.Dtos.PatronDto;
import Librar.Library.Utalitz.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patrons")
@Validated
public class PatronController {

    @Autowired
    private IPatronServess patronService;

    @GetMapping
    public ResponseEntity<List<PatronDto>> getAllPatrons() {
        List<PatronDto> patrons = patronService.findAllPatrons();
        return ResponseEntity.ok(patrons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPatronById(@PathVariable Long id) {
        Optional<PatronDto> patronDto = patronService.findPatronById(id);
        if (patronDto.isPresent()) {
            return ResponseEntity.ok(patronDto.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Patron not found with ID: " + id));
        }
    }

    @PostMapping
    public ResponseEntity<?>   createPatron(@Valid @RequestBody PatronDto patronDto) {
        if (patronService.checkIfPatronExists(patronDto)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("Patron with name '" + patronDto.getName() + "' already exists."));
        }
        try {
            PatronDto savedPatron = patronService.createPatron(patronDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPatron);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An error occurred while creating the patron: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatron(@PathVariable Long id, @Valid @RequestBody PatronDto patronDto) {
        if (!patronService.findPatronById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Patron with ID " + id + " does not exist."));
        }
        try {
            PatronDto updatedPatron = patronService.updatePatron(id, patronDto);
            return ResponseEntity.ok(updatedPatron);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An error occurred while updating the patron: " + e.getMessage()));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatron(@PathVariable Long id) {
        if (!patronService.findPatronById(id).isPresent()) {
            // Patron not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Patron with ID " + id + " does not exist."));
        }
        try {
            patronService.deletePatronById(id);
            // Successfully deleted
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ErrorResponse("Patron with ID " + id + " has been successfully deleted."));
        } catch (RuntimeException e) {
            // Internal server error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An error occurred while deleting the patron: " + e.getMessage()));
        }
    }

}
