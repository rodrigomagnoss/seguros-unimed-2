package br.com.segurosunimed.gestaocarteiras.commons.exception;

import java.util.List;

import br.com.segurosunimed.gestaocarteiras.commons.dto.response.ErrorDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnauthorizedException extends BusinessException {

    public UnauthorizedException(ErrorDTO errorDTO) {
        super(errorDTO);
    }

    public UnauthorizedException(List<ErrorDTO> errors) {
        super(errors);
    }
}
