package Librar.Library.Modes.Dtos;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class BorrowRecordDto {

    private Long id;
    @NotEmpty

    @NotNull(message = "Book ID is mandatory")
    @Positive(message = "Book ID must be a positive number")
    private Long bookId;
    @NotEmpty

    @NotNull(message = "Patron ID is mandatory")
    @Positive(message = "Patron ID must be a positive number")
    private Long patronId;
    @NotEmpty

    @NotNull(message = "Borrow date is mandatory")
    @FutureOrPresent(message = "Borrow date must be today or in the future")
    private LocalDate borrowDate;
    @NotEmpty


    @NotNull(message = "Return date is mandatory")
    @FutureOrPresent(message = "Return date must be today or in the future")
    private LocalDate returnDate;


    public void setId(Long id) {
        this.id = id;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public void setPatronId(Long patronId) {
        this.patronId = patronId;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}

