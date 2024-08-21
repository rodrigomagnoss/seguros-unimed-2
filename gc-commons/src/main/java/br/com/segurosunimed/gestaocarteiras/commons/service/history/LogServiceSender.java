package br.com.segurosunimed.gestaocarteiras.commons.service.history;

import java.util.Date;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.segurosunimed.gestaocarteiras.commons.dto.LogDTO;
import lombok.Setter;

@Service
@Setter
public class LogServiceSender {

	protected static final Logger LOGGER = LoggerFactory.getLogger(LogServiceSender.class);

	public static final String LOGGED_USER = "logged-user";
	public static final String GC_SESSION_ID = "gc-session-id";
	public static final String MICROSERVICE = "microservice";

	@Autowired
	protected RabbitTemplate rabbitTemplate;

	@Autowired
	protected ObjectMapper mapper;

	@Value("${gc.queue-log}")
	protected String queueNameLog;
	@Value("${gc.queue.habilitado}")
	protected boolean habilitaRabbitMQ;

	public void sendLog(LogDTO logDTO) {
		if (habilitaRabbitMQ)
			try {
				if (Objects.isNull(logDTO.getLoggedUser())) {
					String loggedUser = (String) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())
							.getAttribute(LOGGED_USER, 0);
					logDTO.setLoggedUser(loggedUser);
				}
				if (Objects.isNull(logDTO.getSessionId())) {
					String sessionId = (String) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())
							.getAttribute(GC_SESSION_ID, 0);
					logDTO.setSessionId(sessionId);
				}
//				if (Objects.isNull(logDTO.getMicroservice())) {
//					String microservice = (String) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())
//							.getAttribute(MICROSERVICE, 0);
//					logDTO.setMicroservice(microservice);
//				}
				logDTO.setEventDate(new Date());
				// rabbitTemplate.convertAndSend(queueNameLog,
				// mapper.writeValueAsString(logDTO));
			} catch (Exception e) {
				LOGGER.error("Nao foi possivel enviar os logs para fila {}", queueNameLog, e);
			}
	}
}
