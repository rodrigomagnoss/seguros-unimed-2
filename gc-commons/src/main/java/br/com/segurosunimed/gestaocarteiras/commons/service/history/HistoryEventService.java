package br.com.segurosunimed.gestaocarteiras.commons.service.history;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.segurosunimed.gestaocarteiras.commons.dto.BaseDTO;

@Service 
public class HistoryEventService {

	@Autowired
	private HistoryLogServiceSender historyLogServiceSender;

	public void onPostInsert(BaseDTO baseDTO) {

		historyLogServiceSender.sendLogCreate(baseDTO);

	}

	public void onPostUpdate(BaseDTO baseDTO) {

		historyLogServiceSender.sendLogUpdate(baseDTO);

	}

}    