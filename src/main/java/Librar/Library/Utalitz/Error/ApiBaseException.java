package Librar.Library.Utalitz.Error;


import org.springframework.http.HttpStatus;

public abstract class ApiBaseException extends RuntimeException {

    public ApiBaseException(String message) {
        super(message);
    }

    public abstract HttpStatus getStatusCode();
}
