package br.com.segurosunimed.gestaocarteiras.commons.exception;

import java.util.ArrayList;
import java.util.List;

import br.com.segurosunimed.gestaocarteiras.commons.dto.response.ErrorDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {

    private List<ErrorDTO> errors = new ArrayList<>();

    public BusinessException(ErrorDTO errorDTO) {
        this.errors.add(errorDTO);
    }

    public BusinessException(List<ErrorDTO> errors) {
        this.errors = errors;
    }
}
