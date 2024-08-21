package br.com.segurosunimed.gestaocarteiras.commons.service.schedulle;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.segurosunimed.gestaocarteiras.commons.service.business.BaseService;
import br.com.segurosunimed.gestaocarteiras.commons.util.GcGeralUtil;

@Component
public class GCSchedullerService {

	@Autowired
	GcGeralUtil geralUtil;
	
	private static final String EVERY_HOUR = "0 * * * *";
	 
	//Mapa para conter os serviços agendados tendo a expressão de tempo CRON como chave
	private Map mapSchedulledService = new HashMap<String, BaseSchedulleService>();
	
	public void setSchedulledService(String expCron, BaseSchedulleService service) {
		mapSchedulledService.put(expCron, service);
	}
	
	
    /*
     * Executa tarefas agendadas a cada hora a partir das nove horas da manhã
     */
    @Scheduled(cron = EVERY_HOUR)
	public void ExecuteHourlySchedulled() {
    	
    	Calendar now = Calendar.getInstance();
    	String cronNow = geralUtil.convertMillisToCron(now.getTimeInMillis());
    	
    	if(mapSchedulledService.containsKey(cronNow)) {
    		BaseSchedulleService schedulledService = (BaseSchedulleService) mapSchedulledService.get(cronNow);
    		schedulledService.execute();
    	}
		
	}
	
    
    
}
