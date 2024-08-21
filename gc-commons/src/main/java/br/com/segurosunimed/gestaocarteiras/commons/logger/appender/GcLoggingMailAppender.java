package br.com.segurosunimed.gestaocarteiras.commons.logger.appender;
//package br.com.segurosunimed.gestaocarteiras.commons.logger.appender;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.commons.lang3.StringUtils;
//
//import br.com.segurosunimed.gestaocarteiras.commons.logger.tag.GcConAplicacaoTag;
//import br.com.segurosunimed.gestaocarteiras.commons.logger.tag.GcExcecaoTag;
//
///**
// * gestao_commons. Appender Log4J para envio de emails
// *   
// * @author Rodrigo Magno
// */
//public class GcLoggingMailAppender extends GcMailAppender {
//
//	private static final Logger logL = Logger.getLogger("logging");
//	private static final Logger logE = Logger.getLogger("indexacao");
//
//	GcLogIndexacaoServiceAzure indexLogService = gestao_commonsLogIndexacaoServiceAzure.getInstance();
//	GcHistoricoIndexacaoServiceAzure indexHistService = gestao_commonsHistoricoIndexacaoServiceAzure.getInstance();
//	GcConfigContextoAzure invConCtx = gestao_commonsConfigContextoAzure.getInstance();
//	GcCacheContextoAzure invConCache = gestao_commonsCacheContextoAzure.getInstance();
//	GcLoggingDespachanteAppender invConLogDespachante = GcLoggingDespachanteAppender.getInstance();
//	
//	public GcLoggingMailAppender() {
//		super();
//	}
//
//	
//	@Override
//	public void append(LoggingEvent evt){
//		
//		Object logMsg = evt.getMessage();
//		
//		gestao_commonsMailMessageLogging messageLog = new gestao_commonsMailMessageLogging(); 
//		processaLoggingMsg(evt, messageLog);
//
//		messageLog.setAplicacao(getAplicacao());
//		if (messageLog.getData() == null) {
//			messageLog.setData(new Date());
//		}
//
//		int level = evt.getLevel().toInt();
//
//		if (level == Level.ERROR_INT || level == Level.FATAL_INT) {
//			despacharEmail(messageLog);
//		}
//
//	}
//
//	protected boolean despachaLogAntes(gestao_commonsMailMessageLogging logMessage)
//			throws Exception {
//		
//		return true;
//	}
//	
//	protected void despachaLogApos() throws Exception {
//		
//		
//	}
//
//	/**
//	 * Complementa mensagem de email antes do envio.
//	 * 1) Atribui codificação UTF-8 para assunto
//	 * @throws Exception 
//	 */
//	protected gestao_commonsMailMessage complementarEmailAntesEnviar(gestao_commonsMailMessageFacade mail) throws Exception {
//		
//		logL.debug("Complementar email de appender antes de enviar");
//		
////		String newSubject = loggerServ.adicionarProfileLogMensagem(oldSubject);
//		
//		try {
//			gestao_commonsMailMessage invConMail = (gestao_commonsMailMessage) mail;
////			String oldSubject = mail.getSubject();
////			mail.setSubject(MimeUtility.encodeText(mail.getSubject(), "UTF-8", "B"));
////			mail.setBody(MimeUtility.encodeText(mail.getBody(), "UTF-8", "B"));
////			mail.setBody((MimeUtility.encodeText(mail.getBody(), "UTF-8", "B")).getBytes());
////			mail.setSubject(newSubject);
//			return invConMail;
//		} catch (Exception e) {
//			logL.warn("Erro ao complementar mensagem de email. Excecao do sistema: "+e,e);
////			mail.setSubject(oldSubject);
//			throw new Exception(e);
//		}
//
//
//	}
//	
//	@Override
//	public synchronized void doAppend(LoggingEvent event) {
//
//    	gestao_commonsMailMessage mailMsg = (gestao_commonsMailMessage) event.getMessage();
//    	gestao_commonsMailMessageLogging logMessage = (gestao_commonsMailMessageLogging) mailMsg.getMailMessage();
//
//    	boolean inibirEmail = false;
//    	boolean inibirIndex = false;
//        Throwable th = event.getThrowableInformation() != null ? event.getThrowableInformation().getThrowable() : null;
//        if(th != null){
//            if(gestao_commonsNoIndexLoggingException.class.isAssignableFrom(th.getClass())){
//        		inibirIndex = true;
//            }
//            if(gestao_commonsNoMailLoggingException.class.isAssignableFrom(th.getClass())){
//        		inibirEmail = true;
//            }
//        }
//
//		processaLoggingMsg(event, logMessage);
//		
//		int level = event.getLevel().toInt();
//		String classe 	= event.getLocationInformation().getClassName();
//		String metodo 	= event.getLocationInformation().getMethodName();
//		String linha 	= event.getLocationInformation().getLineNumber();
//		
//		logMessage.setClasse(StringUtils.substringAfterLast(classe, "."));
//		logMessage.setMetodo(metodo);
//		logMessage.setLinha(linha);
//		logMessage.setCategoria(event.categoryName);
//		logMessage.setLevel(event.getLevel().toString());
//
//		//Indexar dados de log
//		if (level == Level.INFO_INT || level == Level.WARN_INT || level == Level.ERROR_INT || level == Level.FATAL_INT) {
//
//			try {
//				if(logE.isInfoEnabled() && !inibirIndex){
//
//					if ("indexacao".equals(event.categoryName) && (level == Level.ERROR_INT && level == Level.FATAL_INT)) {
//						String stack = montarStackTraceLog(logMessage);
//						System.out.println("Alerta - Erro na indexacao de logs.");
//						System.out.println(stack);
//					}else{
//						
//						if(logE.isInfoEnabled()){
//							indexarLogs(logMessage);
//						}
//					}
//
//				}
//			} catch (Exception e) {
//				logL.warn("Alerta: Erro inesperado - Indexar dados de log");
//			}
//		
//			//Enviar histórico de log via servico
//			try {
//				GcConAplicacaoTag invConAppTag = new GcConAplicacaoTag();
//				logMessage.setAplicacao(invConAppTag);
//				despacharLogHistorico(logMessage);
//			} catch (Exception e) {
//				logL.warn("Alerta - Erro inesperado - Enviar historico de log");
//			}
//			
//		}
//
//		if(!inibirEmail){
//			//Enviar email de log
//			if (level == Level.ERROR_INT || level == Level.FATAL_INT) {
//
//				if(podeDespacharEmail(logMessage)){
//					despacharEmail(logMessage);
//				}
//
//			}
//			
//		}
//
//    	
//		
//	}
//	
//	private boolean podeDespacharEmail(gestao_commonsMailMessageLogging logMessage) {
//		Map<String, Object> mapaConfigContexto = invConCtx.recuperarMapaContexto(gestao_commonsConstantes.SIM);
//		String modo = (String) mapaConfigContexto.get(gestao_commonsConstantes.AMBIENTE_EXECUCAO._PARAM_AMBIENTE_EXECUCAO);
//		boolean ehModoTeste = gestao_commonsConstantes.AMBIENTE_EXECUCAO.TESTE.MODO.equals(modo);
//		
//		boolean podeDespacharAmbiente 	= !gestao_commonsConstantesAzure.AMBIENTE_EXECUCAO.LOC.MODO.equals(gestao_commonsConstantesAzure.GC_AMBIENTE_EXECUCAO);
//		boolean podeDespacharModo 		= ehModoTeste;
//		
//		return podeDespacharAmbiente || podeDespacharModo;
//	}
//
//
//	/**
//	 * Processa mensagem de Log.
//	 * 
//	 */
//	private void processaLoggingMsg(LoggingEvent evt, gestao_commonsMailMessageFacade messageMail) {
//
//		gestao_commonsMailMessageLogging messageMailLog = (gestao_commonsMailMessageLogging) messageMail;
//		
////		LocationInfo loc = evt.getLocationInformation();
////		ThrowableInformation err = evt.getThrowableInformation();
////
////        String level = Level.toLevel(messageMailLog.getLevel()).toString();
//
//        if (evt.getThrowableInformation() != null) {
//            Throwable _throw = evt.getThrowableInformation().getThrowable();
//            GcExcecaoTag exTag = new GcExcecaoTag(_throw);
//            messageMailLog.setExcecaoTag(exTag);
//        }
//		
//		messageMailLog.setDataHoraEnvio(new Date());
//
////		try {
////			despachante(messageMailLog);
////		} catch (Exception exception) {
////			errorHandler.error("Nao foi possivel enviar Mail [" + this.name + "].", exception, 0);
////		}
//	}
//	
//	@Override
//	protected boolean despacharEmail(gestao_commonsMailMessage  msg) {
//		
//		logL.debug("Executar o envio de email de log");
//		
//		gestao_commonsMailMessageLogging logMessage = (gestao_commonsMailMessageLogging) msg;
//
////		if(logMessage == null || logMessage.getExcecaoTag() == null || logMessage.getExcecaoTag().getStackTrace() == null){
////			logL.warn("Alerta - Mensagem de email de log nao enviado porque mensagem ou stack trace nulos");
////			return false;
////		}
//
//		String level = logMessage.getLevel();
//		String subject = "[" + get_siglaAplicacao() + "] LOG: " + level +" - ENV: "+gestao_commonsConstantesAzure.GC_AMBIENTE_EXECUCAO;
//		String to = (gestao_commonsMailMessageLogging.LEVEL_ERROR.equals(level) ? get_emailErro() : get_emailFatal());
//		String toName = (gestao_commonsMailMessageLogging.LEVEL_ERROR.equals(level) ? get_emailErro() : get_emailFatal());
//		
//		String from = StringUtils.isBlank(get_emailEmpresa()) ? "gc@unimedseguros.com.br" : get_emailEmpresa();
//		String fromName = StringUtils.isBlank(get_nomeAplicacao()) ? gestao_commonsConstantesAzure.APLICACAO.GC.NOME : get_nomeAplicacao();
//		String categoria = logMessage.getCategoria();
//
//		msg.setToAddress(to);
//		msg.setToName(toName);
//		msg.setFromAddress(from);
//		msg.setFromName(fromName + "<"+from+">");
//		msg.setSubject(subject);
//		String body = logMessage.getBody();
//		msg.setMensagem(body);
//		
//		String stack = montarStackTraceLog(logMessage);
//
//		String bodyHTML = aplicaHtmlBase(categoria, logMessage.getNomeAplicacao(), logMessage.getNomeServidor(), logMessage.getObjeto(), logMessage.getDataHoraEnvio(), logMessage.getMensagem(), logMessage.getUsuarioCorrente(), stack);
//
////		msg.setBody(bodyHTML);
//		msg.setBody(body + "\\n\\n" + stack);
//		
//		return super.despacharEmail(msg);
//	}
//
//	/*
//	 * @deprecated
//	 */
//	protected void despacharLogHistorico(gestao_commonsMailMessageLogging  logMessage) throws Exception {
//		
////		logL.debug("Executar o envio de historico de log");
//		
//		invConLogDespachante.despacharLogHistorico(logMessage);
//		
//		setEnviadoLog(logMessage.isLogEnviado());
//
//	}
//
//	protected gestao_commonsMailMessageFacade getMailMessage() {
//		
//		return new gestao_commonsMailMessage();
//	}
//
//	protected gestao_commonsMailMessageSender getMailSenderInstance() {
//
//		return gestao_commonsMailMessageSender.getInstance();
//	}
//
//	protected boolean despachaEmailAntes(gestao_commonsMailMessageFacade message) throws Exception{
//
//		return true;
//	}
//
//	protected void despachaEmailApos() throws Exception {
//
//	}
//
//
//	@Override
//	protected void processMessage(LoggingEvent evt, gestao_commonsMailMessageFacade mailMessage) {
//		
//		processaLoggingMsg(evt, mailMessage);
//		
//		super.processMessage(evt, mailMessage);
//	}
//	
//	/**
//	 * Adiciona um atributo ao contexto do log 
//	 */
//	public void adicionarAtributoContextoLog(String atributoNome, String atributoValor) {
//		MDC.put(atributoNome, atributoValor);
//	}
//
//	/**
//	 * Cria, prepara, complementa mapa de dados de log para indexacao
//	 * 
//	 * @param message Objeto que contem os dados do log para indexacao
//	 * @throws Exception
//	 */
//	@SuppressWarnings("unchecked")
//	@gestao_commonsTesteAcao(acao=gestao_commonsEnumAcaoTeste.ADIAR)
//	public void indexarLogs(gestao_commonsMailMessageLogging message) throws Exception {
//		
//		String tipo = StringUtils.isNotBlank(message.getTipoObjeto()) ? message.getTipoObjeto() : gestao_commonsConstantesAzure.TP_MSG_LOG;
//
//		if(gestao_commonsConstantesAzure.TP_MSG_EVT.equals(tipo)){
//			logL.debug("Indexar log de fluxo");
//		}
//
//		gestao_commonsHelperAzure invConHelper = gestao_commonsHelperAzure.getInstance();
//
//		try {
//
//			invConHelper.bypassSSL();
//
//			String classe = message.getClasse();
//			String metodo = message.getMetodo();
//			String linha = message.getLinha();
//			String level = message.getLevel();
//			String categoria = message.getCategoria();
//			String body = message.getBody();
//			String excecao = message.getExcecaoTag() != null ? message.getExcecaoTag().getClasse() : "";
//			String causa = message.getExcecaoTag() != null ? message.getExcecaoTag().getMensagem() : "";
//
//			HashMap mapaIndexacao = new HashMap<String, Object>();
//			Calendar agora = Calendar.getInstance();
//			
//			String id = message.getLogID();
//			mapaIndexacao.put("datainclusaolocal", agora.getTime().toLocaleString());
//			mapaIndexacao.put("datainclusaoinmilis", agora.getTimeInMillis());
//			mapaIndexacao.put("datainclusao", agora.getTime());
////			mapaLog.put("id", id);
//
//			mapaIndexacao.put("teste", getModoTeste());
//			mapaIndexacao.put("tipo", tipo);
//			mapaIndexacao.put("artefato", classe);
//			mapaIndexacao.put("metodo", metodo);
//			mapaIndexacao.put("linha", linha);
//			mapaIndexacao.put("level", level);
//			mapaIndexacao.put("categoria", categoria);
//			mapaIndexacao.put("modulo", "interpretador-java");
//
//			mapaIndexacao.put("aplicacao", get_siglaAplicacao());
//			mapaIndexacao.put("ambiente", gestao_commonsConstantesAzure.GC_AMBIENTE_EXECUCAO);
//			mapaIndexacao.put("mensagem", body);
//			mapaIndexacao.put("excecao", StringUtils.isNotBlank(excecao) ? excecao : "N/A");
//			mapaIndexacao.put("causa", StringUtils.isNotBlank(causa) ? causa : "N/A");
//
//			if(gestao_commonsConstantesAzure.TP_MSG_EVT.equals(tipo)){
//				Map mapaIndexacaoAux = new HashMap();
//				mapaIndexacaoAux.putAll(mapaIndexacao);
////				indexLogService.indexarLogAsync(mapaIndexacaoAux);
//				mapaIndexacao.put("tipo", gestao_commonsConstantesAzure.TP_MSG_LOG);
//			}
//			
//			indexLogService.indexarLog(mapaIndexacao);
//			
//		}catch(Exception e){
//			logL.error("Erro inesperado - Indexar dados de log. Mensagem original: "+e,new gestao_commonsNoLoggingException(e));
//		}
//	}
//	
////	/**
////	 * Verifica se a entidade necessita de algum complemento e, em seguida, despacha para envio de mail.
////	 * 
////	 */
////	protected void despachante(gestao_commonsMailMessageLogging entidade) throws Exception {
////
////		if (!(entidade instanceof gestao_commonsMailMessage)) {
////			return;
////		}
////
////		enviaMail((gestao_commonsMailMessage) entidade);
////	}
////
//	
//}
