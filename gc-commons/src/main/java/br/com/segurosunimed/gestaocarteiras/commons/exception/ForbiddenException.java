package br.com.segurosunimed.gestaocarteiras.commons.exception;

import java.util.List;

import br.com.segurosunimed.gestaocarteiras.commons.dto.response.ErrorDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForbiddenException extends BusinessException {

    public ForbiddenException(ErrorDTO errorDTO) {
        super(errorDTO);
    }

    public ForbiddenException(List<ErrorDTO> errors) {
        super(errors);
    }
}
