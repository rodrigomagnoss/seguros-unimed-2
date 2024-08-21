package br.com.segurosunimed.gestaocarteiras.commons.exception;

import java.util.List;

import br.com.segurosunimed.gestaocarteiras.commons.dto.response.ErrorDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestException extends BusinessException {

    public BadRequestException(ErrorDTO errorDTO) {
        super(errorDTO);
    }

    public BadRequestException(List<ErrorDTO> errors) {
        super(errors);
    }
}
