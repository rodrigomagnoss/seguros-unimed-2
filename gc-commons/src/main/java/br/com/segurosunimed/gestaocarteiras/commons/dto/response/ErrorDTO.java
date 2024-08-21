package br.com.segurosunimed.gestaocarteiras.commons.dto.response;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorDTO {
	
    private String errorId;
    private String errorType;
    private String messageId;
    private String message;
    private Throwable exception;
    
    public ErrorDTO(String errorType, String messageId) {
        this.errorId = UUID.randomUUID().toString();
        this.errorType = errorType;
        this.message = messageId;
    }

    public ErrorDTO(String errorType, String messageId, Throwable ex) {
        this.errorId = UUID.randomUUID().toString();
        this.errorType = errorType;
        this.message = messageId;
        this.exception = ex;
    }

    public ErrorDTO(String errorId, String errorType, String message) {
        this.errorId = errorId;
        this.errorType = errorType;
        this.message = message;
    }

    public ErrorDTO(String errorId, String errorType, String message, Throwable ex) {
        this.errorId = errorId;
        this.errorType = errorType;
        this.message = message;
        this.exception = ex;
    }
}
