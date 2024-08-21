package br.com.segurosunimed.gestaocarteiras.commons.dto;

import java.io.Serializable;
import java.util.Date;

import br.com.segurosunimed.gestaocarteiras.commons.enums.GcEventType;
import br.com.segurosunimed.gestaocarteiras.commons.enums.GcLogType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogDTO implements Serializable {

    private Long id;
    private GcLogType level;
    private GcEventType eventType;
    private String sessionId;
    private String loggedUser;
    private String microservice;
    private String message;
    private Date eventDate;
    private Date createdAt;
    private String errorId;
    private String errorType;
    private String extraData;

}
