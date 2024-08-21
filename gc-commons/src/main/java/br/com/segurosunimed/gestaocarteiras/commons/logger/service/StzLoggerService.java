//package br.com.segurosunimed.gestaocarteiras.commons.logger.service;
//
//import java.text.DecimalFormat;
//import java.text.NumberFormat;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.slf4j.MDC;
//import org.slf4j.event.LoggingEvent;
//
///**
// * gestao_commons 1.0 - Serviço de envio de logs 
// * 
// * @author Rodrigo Magno
//  * @since 04/05/2020
// *
// */
//public class GcLoggerService {
//		
//    private static final String PREFIXO_LOG = "LOG:";
//
//    private static final Logger LOGGER = LoggerFactory.getLogger("service");
//	
//	private static GcLoggerService INSTANCE = new GcLoggerService();
//	
//	GcContextoEntidade contextoSistema = null;
//	
//	private NumberFormat formatter = new DecimalFormat("000000");
//    
//	public GcLoggerService() { }
//	
//	public static GcLoggerService getInstance(){
//	   return INSTANCE;
//	}
//
//	/**
//	 * Adicionar dados de profile no log para n�veis ERROR e FATAL
//	 * @param event Objeto que represente o log
//	 * 
//	 * @author Rodrigo Magno
//	  * @since 02/02/2011 - ECP 6.1 - SPR 15o
//	 */
//	public void adicionarProfileLog(gestao_commonsContextoEntidade contexto) {
//		gestao_commonsProfileEntidade profile = recuperarProfileLog(contexto);
//		if(profile != null && profile.getLogProfile() != null){
//			adicionarAtributoContextoLog("profile",profile.getLogProfile());
////			adicionarProfileLogMensagemEmail(contexto);
//		}
//	}
//
//	/**
//	 * Adiciona um atributo ao contexto do log para visualiza��o no console
//	 * 
//	 * @author Rodrigo Magno
//	  * @since 15/02/2011 - ECP 6.1 - SPR 15
//	 */
//	public void adicionarAtributoContextoLog(String atributoNome, String atributoValor) {
//		MDC.put(atributoNome, atributoValor);
//	}
//
//	/**
//	 * Adicinar dados de profile de log nas mensagens de email de log
//	 * 
//	 * @author Rodrigo Magno
//	  * @since 03/02/2011 - ECP 6.1 - SPR 15
//	 */
//	protected void adicionarProfileLogMensagemEmail(LoggingEvent event) {
//		
////		if(event.getMessage() instanceof PlcJMonitorMsg){
////			PlcJMonitorMsg msg 	= (PlcJMonitorMsg) event.getMessage();
////			if(msg != null){
////				if(msg.getTags() != null && !msg.getTags().isEmpty()){
////					for (Iterator iterator = msg.getTags().iterator(); iterator.hasNext();) {
////						PlcLoggingTag tag = (PlcLoggingTag) iterator.next();
////						if(StringUtils.isNotBlank(tag.getMensagem())){
////							tag.setMensagem(adicionarProfileLogMensagem(tag.getMensagem()));
////						}
////					}
////				}
////				if(StringUtils.isNotBlank(msg.getConteudoMensagemBruta())){
////					msg.setConteudoMensagemBruta(adicionarProfileLogMensagem(msg.getConteudoMensagemBruta()));
////				}
////			}
////		}
//	}
//
//	/**
//	 * Adicinar dados de profile de log nas mensagens de email de log
//	 * 
//	 * @author Rodrigo Magno
//	  * @since 03/02/2011 - ECP 6.1 - SPR 15
//	 */
////	public String adicionarProfileLogMensagem(EcpBaseContextVO contextoVO, String mensagem) {
////		gestao_commonsProfileEntidade profile = recuperarProfileLog();
////		if(StringUtils.isNotBlank(mensagem) && !mensagem.contains(PREFIXO_LOG)){
////			if(mensagem.contains("#")){
////				mensagem = mensagem.replace("#", "#"+profile.getLogProfile());
////			}else{
////				mensagem = profile.getLogProfile()+mensagem;
////			}
////		}
////		return mensagem;
////	}
//
//	public String adicionarProfileLogMensagem(String mensagem) {
////		return adicionarProfileLogMensagem(getContexto(), mensagem);
//		return "";
//	}
//
//	/**
//	 * Envia uma mensagem de log com n�vel ERROR sem exce��o
//	 * @param e Excecao capturada
//	 *
//	 * @author Rodrigo Magno
//	  * @since 09/02/2011 - ECP 6.1 - SPR 15
//	 */
//	public String logErro(Logger logger, String mensagem) {
//		return dispararLog(getContexto(),logger, mensagem, gestao_commonsConstantes.LOGGER.LEVEL.ERROR, null);
//	}
//
////	public String dispararLogErro(EcpBaseContextVO contexto, Logger logger, String mensagem) {
////		return dispararLog(contexto,logger, mensagem, EcpConstantes.LOG.NIVEL.ERROR, null);
////		return "";
////	}
//
//	/**
//	 * Envia uma mensagem de log com n�vel ERROR com exce��o
//	 * @param e Excecao capturada
//	 *
//	 * @author Rodrigo Magno
//	  * @since 15/02/2011 - ECP 6.1 - SPR 15
//	 */
//	public String dispararLogErro(Logger logger, String mensagem, Exception e) {
////		return dispararLog(getContexto(),logger, mensagem, EcpConstantes.LOG.NIVEL.ERROR, e);
//		return "";
//	}
//
////	public String dispararLogErro(EcpBaseContextVO contexto, Logger logger, String mensagem, Exception e) {
////		return dispararLog(contexto, logger, mensagem, EcpConstantes.LOG.NIVEL.ERROR, e);
////		return "";
////	}
//
//	public String dispararLogErroSistema(Logger logger, String mensagem, Exception e) {
////		return dispararLogErro(getContextoSistema(),logger, mensagem, e);
//		return "";
//	}
//
//	/**
//	 * Envia uma mensagem de log com n�vel FATAL
//	 * @param e Excecao capturada
//	 *
//	 * @throws PlcException Dispara exce��o quando o parametro de exce��o for nulo.
//	 * 
//	 *@author Rodrigo Magno
//	  * @since 14/02/2011 - ECP 6.1 - SPR 15
//	 */
//	public String dispararLogFatal(Logger logger, String mensagem, Exception e) throws Exception {
////		if(e != null){
////			return dispararLog(getContexto(), logger, mensagem, EcpConstantes.LOG.NIVEL.FATAL, e);
////		}else{
////			String msgErro = "Ao disparar log fatal o parametro de excecao nao pode ser nulo.";
////			Exception ex = new NullPointerException(msgErro);
////			dispararLogErro(logger, msgErro,ex);
////			throw new PlcException(ex);
////		}
//		return "";
//	}
//
////	public String dispararLogFatal(EcpBaseContextVO contexto, Logger logger, String mensagem, Exception e) throws PlcException {
////		return dispararLog(contexto, logger, mensagem, EcpConstantes.LOG.NIVEL.FATAL, e);
////		return "";
////	}
//
//	public String dispararLogFatalSistema(Logger logger, String mensagem, Exception e) throws Exception {
////		return dispararLogFatal(getContextoSistema(), logger, mensagem, e);
//		return "";
//	}
//
//	/**
//	 * Envia uma mensagem de log com n�vel WARN com exce��o
//	 * 
//	 *@author Rodrigo Magno
//	  * @since 14/02/2011 - ECP 6.1 - SPR 15
//	 */
//	public String dispararLogWarn(Logger logger, String mensagem, Exception e) {
////		return dispararLog(getContexto(), logger, mensagem, EcpConstantes.LOG.NIVEL.WARN, e);
//		return "";
//	}
//
////	public String dispararLogWarn(EcpBaseContextVO contexto, Logger logger, String mensagem, Exception e) {
////		return dispararLog(contexto, logger, mensagem, EcpConstantes.LOG.NIVEL.WARN, e);
////	}
//
//	public String dispararLogWarnSistema(Logger logger, String mensagem, Exception e) {
////		return dispararLogWarn(getContextoSistema(), logger, mensagem, e);
//		return "";
//	}
//
//	/**
//	 * Envia uma mensagem de log com n�vel WARN sem exce��o
//	 *@author Rodrigo Magno
//	  * @since 14/02/2011 - ECP 6.1 - SPR 15
//	 */
//	public String dispararLogWarn(Logger logger, String mensagem) {
////		return dispararLog(getContexto(), logger, mensagem, EcpConstantes.LOG.NIVEL.WARN, null);
//		return "";
//	}
//
////	public String dispararLogWarn(EcpBaseContextVO contexto,Logger logger, String mensagem) {
////		return dispararLog(contexto, logger, mensagem, EcpConstantes.LOG.NIVEL.WARN, null);
////	}
//
//	public String dispararLogWarnSistema(Logger logger, String mensagem) {
////		return dispararLogWarn(getContextoSistema(), logger, mensagem);
//		return "";
//	}
//
//	/**
//	 * Envia uma mensagem de erro log com n�vel DEBUG
//	 *@author Rodrigo Magno
//	  * @since 14/02/2011 - ECP 6.1 - SPR 15
//	  * @see <a href="http://www.powerlogic.com.br/eprj/backlog.do?evento=Editar&chPlc=139068">Backlog</a>
//	 */
//	public String dispararLogDebug(Logger logger, String mensagem) {
////		return dispararLog(getContexto(), logger, mensagem, EcpConstantes.LOG.NIVEL.DEBUG, null);
//		return "";
//	}
//
////	public String dispararLogDebug(EcpBaseContextVO contextoVO, Logger logger, String mensagem) {
////		return dispararLog(contextoVO, logger, mensagem, EcpConstantes.LOG.NIVEL.DEBUG, null);
////	}
//
//	public String dispararLogDebugSistema(Logger logger, String mensagem) {
////		return dispararLogDebug(getContextoSistema(), logger, mensagem);
//		return "";
//	}
//
//	/**
//	 * Envia uma mensagem de erro log com n�vel INFO
//	 * 
//	 * @author Rodrigo Magno
//	  * @since 02/12/2015 - ECP 6.1
//	  * @see <a href="http://www.powerlogic.com.br/eprj/backlog.do?evento=Editar&chPlc=">Backlog</a>
//	 */
//	public String dispararLogInfo(Logger logger, String mensagem) {
////		return dispararLog(getContexto(), logger, mensagem, EcpConstantes.LOG.NIVEL.INFO, null);
//		return "";
//	}
//
////	public String dispararLogInfo(EcpBaseContextVO contextoVO, Logger logger, String mensagem) {
////		return dispararLog(contextoVO, logger, mensagem, EcpConstantes.LOG.NIVEL.INFO, null);
////	}
//
//	public String dispararLogInfoSistema(Logger logger, String mensagem) {
////		return dispararLogInfo(getContextoSistema(), logger, mensagem);
//		return "";
//	}
//
//	/**
//	 * Envia uma mensagem de log conforme o n�vel especificado
//	 * @param e Excecao capturada
//	 * 
//	 * @author Rodrigo Magno
//	  * @since 22/12/2010 - ECP 6.1 - SPR 14
//	  * @see <a href="http://www.powerlogic.com.br/eprj/backlog.do?evento=Editar&chPlc=">Backlog</a>
//	 */
//	private String dispararLog(GcContextoEntidade contexto, Logger logger, String mensagem, String nivel, Exception e) {
//
//		String msgLog = mensagem;
////		if(EcpConstantes.LOG.NIVEL.FATAL.equals(nivel) || EcpConstantes.LOG.NIVEL.ERROR.equals(nivel)){
////			mensagem = adicionarProfileLogMensagem(contextoVO, mensagem);
////			msgLog = mensagem.replace("#", "");
////		}
//		
//		GcNoStackTraceException excecaoNoStack = null;
//		
//		//if(EcpConstantes.LOG.NIVEL.ERROR.equals(nivel)){
//			excecaoNoStack = GcExceptionHelper.getInstance().isNoStackTraceException(e);
//			if(excecaoNoStack != null){
//				StackTraceElement[] stacks = excecaoNoStack.getStackTrace();
//				if(stacks != null && stacks.length > 0){
//					int cont = 0;
//					String primLinha = "";
//					do{
//						primLinha = ""+stacks[cont];
//						if(cont > 0){
//							msgLog += "\n";
//						}
//						if(!StringUtils.contains(primLinha, "EcpExcecaoHelper.java:")){
//							msgLog += " { "+primLinha+" }";
//						}
//						cont++;
//					}while(cont <= 5);
//				}
//			}
//		//}
//		
////		if(EcpConstantes.LOG.NIVEL.INFO.equals(nivel)){
////			logger.info(msgLog);
////		}
////		if(EcpConstantes.LOG.NIVEL.DEBUG.equals(nivel)){
////			if(logger.isDebugEnabled()){
////				logger.debug(msgLog);
////			}
////		}
////		if(EcpConstantes.LOG.NIVEL.WARN.equals(nivel)){
////			logger.warn(msgLog);
////		}
////		
////		if(EcpConstantes.LOG.NIVEL.ERROR.equals(nivel)){
////			if(excecaoNoStack != null){
////				logger.warn(msgLog);
////			}
////			else{
////				if(e != null){
////					logger.error(msgLog,e);
////				}else{
////					logger.error(msgLog);
////				}
////			}
////		}
////		if(EcpConstantes.LOG.NIVEL.FATAL.equals(nivel)){
////			logger.fatal(msgLog,e);
////		}
//		
//		return mensagem;
//	}
//
//	/**
//	 * Recuperar o profile de log adicionando no contexto. Se n�o existir, cria.
//	 * 
//	 * @author Rodrigo Magno
//	  * @since 03/02/2011 - ECP 6.1 - SPR 15
//	  * @see <a href="http://www.powerlogic.com.br/eprj/backlog.do?evento=Editar&chPlc=">Backlog</a>
//	 */
//	protected gestao_commonsProfileEntidade recuperarProfileLog(gestao_commonsContextoEntidade contexto) {
//		
//		gestao_commonsProfileEntidade profile = contexto.getProfile();
//		if(profile == null || profile.getLogProfile() == null){
//			profile = criarProfileLog(contexto);
//			contexto.setProfile(profile);
//		}
//		return profile;
//	}
//
//	/**
//	 * Recuperar o profile de log adicionando no contexto. Se n�o existir, cria.
//	 * 
//	 * @author Rodrigo Magno
//	  * @since 03/02/2011 - ECP 6.1 - SPR 15
//	  * @see <a href="http://www.powerlogic.com.br/eprj/backlog.do?evento=Editar&chPlc=">Backlog</a>
//	 */
////	protected gestao_commonsProfileEntidade recuperarProfileLog() {
////		EcpBaseContextVO contextoVO = getContexto();
////		return recuperarProfileLog(contextoVO);
////	}
//
//	/**
//	 * Recupera o contexto da aplica��o ou cria um caso for nulo
//	 * 
//	 * @return {@link EcpBaseContextVO}
//	 */
//	protected gestao_commonsContextoEntidade getContextoSistema() {
//
//		if(contextoSistema == null){
//			gestao_commonsMontarContextoHelper contextMontaHelper = gestao_commonsMontarContextoHelper.getInstance();
//			contextoSistema = (gestao_commonsContextoEntidade) contextMontaHelper.criarNovoContexto();
//		}
//		
//		return contextoSistema;
//	}
//
//	/**
//	 * Remover o profile de log do contexto.
//	 * 
//	 * @author Rodrigo Magno
//	  * @since 03/02/2011 - ECP 6.1 - SPR 15
//	  * @see <a href="http://www.powerlogic.com.br/eprj/backlog.do?evento=Editar&chPlc=">Backlog</a>
//	 */
////	public void removerProfileLog() {
////		
////		EcpBaseContextVO contexto = getContexto();
////		if(contexto != null){
////			contexto.setProfile(null);
////		}
////		MDC.put("profile", "");
////	}
//
//	/**
//	 * Criar o profile do log adicionando informa��es do c�digo do log e do usu�rio e adiciona no contexto
//	 * 
//	 * @author Rodrigo Magno
//	  * @since 03/02/2011 - ECP 6.1 - SPR 15
//	  * @see <a href="http://www.powerlogic.com.br/eprj/backlog.do?evento=Editar&chPlc=">Backlog</a>
//	 */
//	protected gestao_commonsProfileEntidade criarProfileLog(gestao_commonsContextoEntidade contexto) {
//		
//		gestao_commonsProfileEntidade profileLog = contexto.criarProfile();
//		
//		if(contexto != null){
//			profileLog = contexto.getProfile() != null ? contexto.getProfile() : profileLog;
//		}
//		profileLog.setLogId(formatter.format(recuperarCodigoLog()));
////		profileLog.setLogProfile("("+PREFIXO_LOG+profileLog.getLogId()+"$USUARIO$) "); 
////		profileLog.setLogProfile(profileLog.getLogProfile().replace("$USUARIO$","-"+usuario));
//		
//		contexto.setProfile(profileLog);
//		
//		return profileLog;
//	}
////
////	protected gestao_commonsProfileEntidade criarProfileLog() {
////		EcpBaseContextVO contextoVO = getContexto();
////		return criarProfileLog(contextoVO);
////	}
//
//	/**
//	 * Recuperar o c�digo do log atual, incrementado de 1
//	 * @return C�digo do log
//	 * 
//	 * @author Rodrigo Magno
//	  * @since 03/02/2011 - ECP 6.1 - SPR 15
//	  * @see <a href="http://www.powerlogic.com.br/eprj/backlog.do?evento=Editar&chPlc=">Backlog</a>
//	 */
//	protected Long recuperarCodigoLog() {
//		return null;
//		
////		PlcCacheAplicacaoVO cacheAplicacao = PlcCacheAplicacaoVO.getInstance();
////		Long codigoLog = 0L;
////		if(cacheAplicacao.recuperaObjeto("CODIGO_LOG") != null){
////			codigoLog = (Long) cacheAplicacao.recuperaObjeto("CODIGO_LOG");
////		}
////		cacheAplicacao.adicionaObjeto("CODIGO_LOG", ++codigoLog);
////		
////		return codigoLog;
//	}
//
////	public void setContextoSistema(EcpBaseContextVO novoContextoSistema) {
////		contextoSistema = novoContextoSistema;
////	}
////
//	public gestao_commonsContextoEntidade getContexto() {
//		
//		gestao_commonsContextoEntidade contexto = null;
//		try {
//			contexto = (gestao_commonsContextoEntidade) gestao_commonsGerenciadorContexto.getContextVO(); 
//		} catch (Exception e) {
//			contexto = getContextoSistema();
//		}
//
//		return contexto;
//	}
//	
//}