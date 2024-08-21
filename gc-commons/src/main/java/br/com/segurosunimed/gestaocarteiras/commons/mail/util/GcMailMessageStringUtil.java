
package br.com.segurosunimed.gestaocarteiras.commons.mail.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import br.com.segurosunimed.gestaocarteiras.commons.logger.tag.GcTagFacade;


/**
 * Helper para construção da string xml para envio mensagem monitor.
 * 
 */
public class GcMailMessageStringUtil {

	private static GcMailMessageStringUtil me;
	private static final String ASPAS = "\"";
	private static final String XML_START_TAG = "<monitor-msg version=\"1.0\" timestamp=\"";
	private static final String XML_END_TAG = "\n</monitor-msg>";

	protected static Logger logU = Logger.getLogger("utilitario");

	protected GcMailMessageStringUtil() {
	}

	public static GcMailMessageStringUtil getInstance() {
		if (me == null) {
			me = new GcMailMessageStringUtil();
		}
		return me;
	}

	private String toTagString(GcTagFacade tag) throws Exception {
		if (tag != null) {
			return tag.toTagString();
		} else {
			return "";
		}
	}

	public String toTagString(GcTagFacade tag, String[] props)
			throws Exception {

//		return toTagString(tag, props, tag.getTAG());
		return "";
		
	}

	/**
	 * Utilizando reflex�o, gera a string que representa a tag informada.
	 * 
	 * @param tag
	 * @param props
	 *            propriedades a serem consideradas na montagem da string
	 * @return String que representa a tag informada.
	 */
//	public String toTagString(Object tag, String[] props, String nomeTag)
//			throws Exception {
//		if (tag == null || props == null) {
//			return "";
//		}
//		String msg = null;
//		StringBuffer tagStr = new StringBuffer("<").append(nomeTag).append(" ");
//
//		for (int i = 0; i < props.length; i++) {
//			String p = props[i];
//			String v = null;
//
//			if (p != null && p != "") {
//				try {
//					v = BeanUtils.getProperty(tag, p);
//				} catch (Exception e) {
//					throw e;
//				}
//
//				if (!StringUtils.isBlank(v)) {
//					if (p.equals("mensagem")) {
//						msg = v;
//					} else {
//						tagStr.append(p).append("=").append(ASPAS).append(v)
//								.append(ASPAS).append(" ");
//					}
//				}
//			}
//
//		}
//
//		if (msg == null) {
//			tagStr.append("/>");
//		} else {
//			tagStr.append(">").append(msg).append("</").append(nomeTag).append(
//					">");
//		}
//
//		return tagStr.toString();
//	}

	/**
	 * Utilizando reflex�o, gera a string que representa a tag informada.
	 * 
	 * @param tag
	 * @param props
	 *            propriedades a serem consideradas na montagem da string
	 * @return String que representa a tag informada.
	 */
	public String toTagString(Object principal, Object tag, String[] props, String nomeTag) throws Exception {
		
		if (props == null || principal == null) {
			return "";
		}
		
		String msg = null;
		StringBuffer tagStr = new StringBuffer("<").append(nomeTag).append(" ");
//
//		for (int i = 0; i < props.length; i++) {
//			
//			if(props[i] != null){
//				
//				String p = props[i];
//				String v = null;
//				if (p != null && p != "") {
//					if (p.equalsIgnoreCase("uri") || p.equalsIgnoreCase("acao") || p.equalsIgnoreCase("transacao")) {
//						if (p != null && p != "") {
//							try {
//								v = BeanUtils.getProperty(principal, p);
//	
//							} catch (Exception e) {
//								logU.info("Montando tag \"" + nomeTag + "\": " + e.getMessage());
//							}
//	
//							if (v != null && !StringUtils.isBlank(v)) {
//								if (p.equals("mensagem")) {
//									msg = v;
//								} else {
//									tagStr.append(p).append("=").append(ASPAS).append(v).append(ASPAS).append(" ");
//								}
//							}
//						}
//					} else {
//	
//						try {
//							if (propriedadeValida(tag, p)) {
//								
//								PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(tag, p + "Str");
//								
//								if (pd!=null) {
//									Method m = PropertyUtils.getReadMethod(pd);
//									if (m!=null)
//										v = (String)m.invoke(tag, null);
//									else
//										v = BeanUtils.getProperty(tag, p);
//								} else {
//									v = trataTiposEspeciais(PropertyUtils.getProperty(tag, p));
//								}
//							}
//							else {
//								v = BeanUtils.getProperty(tag, p);
//							}
//	
//						} catch (Exception e) {
//							logU.info("Montando tag \"" + nomeTag + "\": " + e.getMessage());
//						}
//	
//						if (v != null && !StringUtils.isBlank(v)) {
//							if (p.equals("mensagem")) {
//								msg = v;
//							} else {
//								tagStr.append(p).append("=").append(ASPAS).append(v).append(ASPAS).append(" ");
//							}
//						} else {
//							tagStr.append(p).append("=").append(ASPAS).append("null").append(ASPAS).append(" ");
//						}
//					}
//				}
//			}
//		}
//
//		if (msg == null) {
//			tagStr.append("/>");
//		} else {
//			tagStr.append(">").append(msg).append("</").append(nomeTag).append(
//					">");
//		}

		return tagStr.toString();
	}

	private String trataTiposEspeciais(Object v) {
		if (v==null)
			return null;
		
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		sf.setLenient(false);

	    DecimalFormat df = (DecimalFormat)DecimalFormat.getNumberInstance(new Locale("pt","BR"));
		df.setMinimumFractionDigits(2);
		
		if (v instanceof BigDecimal) {
			return df.format(v);
		} else if (v instanceof Date) {
			return sf.format(v);
		}
		
		return v.toString();
	}

	/**
	 * Ler um array de String e devolve o seu conteúdo em formato String,
	 * quebrando cada elemento usando <code>\n</code> ao final de cada linha.
	 */
	public String StringArrayToString(String[] st) {
		return StringArrayToString(st, null);
	}

	/**
	 * Ler um array de String e devolve o seu conteúdo em formato String,
	 * quebrando cada elemento, com o grupo de caracteres informado, ao final de
	 * cada linha.
	 * 
	 */
	public String StringArrayToString(String[] st, String lineBreak) {
		if (st == null) {
			return null;
		}

		StringBuffer bf = new StringBuffer();

		if (lineBreak == null) {
			lineBreak = "\n";
		}

		if (st != null && st.length > 0) {
			for (int n = 0; n < st.length; n++) {
				bf.append(st[n]).append(lineBreak);
			}
		}
		return bf.toString();
	}

	/**
	 * Verifica se a tag possui uma propriedade válida
	 */
	private boolean propriedadeValida(Object tag, String p) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
//		return PropertyUtils.getPropertyType(tag, p) != null && 
//				((PropertyUtils.getPropertyType(tag, p).equals(BigDecimal.class)) || (PropertyUtils.getPropertyType(tag, p).equals(Date.class)));

		return true;
	}
}
