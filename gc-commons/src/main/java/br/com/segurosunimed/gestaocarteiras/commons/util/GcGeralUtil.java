package br.com.segurosunimed.gestaocarteiras.commons.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.segurosunimed.gestaocarteiras.commons.dto.response.ErrorDTO;

/**
 * Classe padrão Singleton para apoio nos processamentos
 * 
 * @author Rodrigo Magno
 * @since 21/01/2022
 * 
 */
@Component
public class GcGeralUtil {

    static final Logger LOGGER = LoggerFactory.getLogger(GcGeralUtil.class);
	
	private static final GcGeralUtil INSTANCE = new GcGeralUtil();
	
    @Autowired
    private MessageSource messageSource;

	public static GcGeralUtil getInstance(){
		return INSTANCE;
	}
	
	
	public String getMessageI18n(ErrorDTO errorDTO) {
		return getMessageI18n(errorDTO.getMessage());
	}

	public String getMessageI18n(String message) {
		String i18nMessage = message;
		try {
			i18nMessage = messageSource.getMessage(message, new Object[]{}, Locale.getDefault());			
		} catch (NoSuchMessageException e) {
			//LOGGER.warn("Erro ao recuperar mensagem: nenhum valor encontrada para mensagem '"+message+"'");
		}
		return i18nMessage;
	}
	
	/**
	 * Remove os caracteres nao numericos de um CNPJ utilizando exprssões regulares
	 * @param cnpj CNPJ para remocao dos caracteres nao numericos
	 * @return CNPJ composto apenas dos caracteres numericos
	 */
	public String getCNPJSomenteNumeros(String cnpj){
		int[] grupos = {1,2,3,4,5};
		//Formatar cnpj para conter apenas números
		final String EXPRESSAO_CNPJ_SOMENTE_NUMEROS_URL = "(\\d{1,2})(?:[-.\\s])?(\\d{3})(?:[-.\\s])?(\\d{3})(?:[-\\.\\s\\/])?(\\d{4})(?:[-.\\s])?(\\d{2})(?![\\s])*";
		return getCombinacaoExpressao(cnpj, EXPRESSAO_CNPJ_SOMENTE_NUMEROS_URL, grupos);
	}
	
	/**
	 * Metodo para retornar a combinacao de uma expressao regurar encontrada em em um texto
	 * 
	 * @param umTextoParaCombinar Texto para verificar combinacao com a expressão regular
	 * @param expressaoRegular Expresssao regular utilizada para encontrar combinacao no texto
	 * @param grupos Array com os grupos para recuperar da combinacao
	 * @return true, caso a combinacao seja encontrada e false caso contrário
	 */
	public  String getCombinacaoExpressao(String umTextoParaCombinar, String expressaoRegular, int[] grupos) {
		Pattern regPattern = Pattern.compile(expressaoRegular,Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		Matcher matcher = regPattern.matcher(umTextoParaCombinar);
		StringBuilder expRet = new StringBuilder();
		if(matcher.find() && grupos != null){
			for (int i = 0; i < grupos.length; i++) {
				int grp = grupos[i];
				expRet.append(matcher.group(grp));
			}
			return expRet.toString();
		}
		
		return "";
	}

	/**
	 * Metodo para retornar a combinacao de uma expressao regurar encontrada em em um texto
	 * 
	 * @param umTextoParaCombinar Texto para verificar combinacao com a expressão regular
	 * @param expressaoRegular Expresssao regular utilizada para encontrar combinacao no texto
	 * @param grupos Array com os grupos para recuperar da combinacao
	 * @return true, caso a combinacao seja encontrada e false caso contrário
	 */
	public  String[] getGruposCombinacaoExpressao(String umTextoParaCombinar, String expressaoRegular, int[] grupos) {
		Pattern regPattern = Pattern.compile(expressaoRegular,Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		Matcher matcher = regPattern.matcher(umTextoParaCombinar);
		String[] expRet = new String[] {};
		if(matcher.find() && grupos != null){
			for (int i = 0; i < grupos.length; i++) {
				int grp = grupos[i];
				expRet = ArrayUtils.add(expRet, matcher.group(grp));
//				expRet[expRet.length] = matcher.group(grp);
			}
		}
		
		return expRet;
	}
	
	/**
	 * Retira os acentos de uma string
	 * Metodo que sobrepoe o mesmo mestodos da classe PlcGeralUtil mas forca encoding na geracao da string acentuada
	 * @param termo String para retirar acentos
	 * @return String sem acentos
	 * @throws UnsupportedEncodingException 
	 */
	String acentos 	= new String("áàââäéèêëíìîîïóòôôöúùûüçñýÿÁÀÂÂÄÉÈÊËÍÌÎÎÏÓÒÔÔÖÚÙÛÜÇÑÝŸ".getBytes());
	String semAcentos 		   = "aaaaaeeeeiiiiiooooouuuucnyyAAAAAEEEEIIIIIOOOOOUUUUCNYY";
	public static String retirarAcentos(String termo) throws UnsupportedEncodingException
	{

        String nfdNormalizedString = Normalizer.normalize(termo, Normalizer.Form.NFD); 
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
	}

	/**
	 * Substituir o valor da expressão pelo valor passado para substituicao
	 */
	public  String substituirPorExpRegular(String termoSubstituir, String expSubstituir) throws UnsupportedEncodingException
	{
        Pattern pattern = Pattern.compile(expSubstituir);
        return pattern.matcher(termoSubstituir).replaceAll("");
	}

	public String getExtensaoUrl(String urlDiretorio) {
		Pattern regPattern = Pattern.compile("(\\\\(pdf|xml)\\\\)(.*)",Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		Matcher matcher = regPattern.matcher(urlDiretorio);
		String extensaoArqInterpretacao = "";
		if(matcher.find()){
			extensaoArqInterpretacao = matcher.group(2);
		}
		return extensaoArqInterpretacao;
	}


	/**
	 * Metodo para identacao de dados JSON
	 * 
	 * @param strJson Texto representando o JSON não identado
	 * 
	 * @return Texto com dados JSON identados
	 */
	public  String prettyJSON(String strJson) {
//		Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
//		
//		try {
//			JsonParser parser = new JsonParser();
//			JsonObject json = parser.parse(strJson).getAsJsonObject();        
//			strJson = gson.toJson(json);
//		} catch (JsonSyntaxException e) {
//			strJson = gson.toJson(strJson);
//		}catch (Exception e){
//			throw e;
//		}
//		
//		return strJson;
		
		return "";
	}

	/**
	 * Metodo para identacao de dados JSON
	 * 
	 * @param strJson Texto representando o JSON não identado
	 * 
	 * @return Texto com dados JSON identados
	 */
	public Map mapaJSON(String jsonObjeto) {
//		Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
//		
//		try {
//
//			Map<String, Object> mapaEficiencia = gson.fromJson(jsonObjeto, HashMap.class);
//
//			return mapaEficiencia;
//			
//		} catch (JsonSyntaxException e) {
//			throw e;
//		}catch (Exception e){
//			throw e;
//		}
		return null;
	}

	/**
	 * Metodo para auxiliar no acesso a endereções https sem restrições de acesso SSL 
	 */
	public void bypassSSL() {
		final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public void checkClientTrusted(final java.security.cert.X509Certificate[] certs, final String authType) {
			}

			@Override
			public void checkServerTrusted(final java.security.cert.X509Certificate[] certs, final String authType) {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		} };
		try {
			final SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			SSLContext.setDefault(sc);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo para recuperar um cliente HttpClient sem restrições de acesso SSL 
	 * @return HttpClient Instância do objeto criado
	 */
	public HttpClient getHttpClientForSSL(){
		
		final SSLConnectionSocketFactory sslsf;
		try {
		    sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault(),
		            NoopHostnameVerifier.INSTANCE);
		} catch (NoSuchAlgorithmException e) {
		    throw new RuntimeException(e);
		}

		final Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
		        .register("http", new PlainConnectionSocketFactory())
		        .register("https", sslsf)
		        .build();

		final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
		cm.setMaxTotal(100);
		HttpClient httpclient = HttpClients.custom()
		        .setSSLSocketFactory(sslsf)
		        .setConnectionManager(cm)
		        .build();
		
		return httpclient;
		
	}

	
    /**
     * Metodo para trocar o caracter hifen (-) pelo caracter underscore (_) numa String de campos em formato JSON para geracao do objeto pojo 
     * @param jsonCamposV2 String Campos no formato JSON extraídos de uma análise de pdf
     * @return String Campos com o caracter hifen (-) trocados pelo caracter underscore (_) 
     */
	public static final String EXPRESSAO_HIFEN_IN_JSON_KEY =  "(-)|: \"[^\"]*\"";
	public String replaceHiffenToUnderscore(String jsonCamposV2) {
		Pattern regex = Pattern.compile(EXPRESSAO_HIFEN_IN_JSON_KEY);
		return replaceCamposJsonToObject(jsonCamposV2, regex, "_");
	}
	
    /**
     * Metodo para trocar o caracter underscore (_) pelo caracter hifen (-) numa String de campos em formato JSON para geracao do objeto pojo 
     * @param jsonCamposV2 String Campos no formato JSON extraídos de uma análise de pdf
     * @return String Campos com o caracter underscore (_) trocados pelo caracter hifen (-)
     */
	public String replaceUnderescoreToHiffen(String jsonCamposV2) {
		Pattern regex = Pattern.compile(EXPRESSAO_HIFEN_IN_JSON_KEY.replace("-", "_"));
		return replaceCamposJsonToObject(jsonCamposV2, regex, "-");
	}

	/**
	 * Metodo auxiliar para trocar caracteres na string dos campos extraidos usando expressao regular
	 * @param jsonCamposV2 Objeto pojo representando os campos JSON
	 * @param regex Expressão regular para trocar os caracteres
	 * @param strSubs String Caracter para substituicao
	 * @return String Campos com os caracteres trocados. 
	 */
	public String replaceCamposJsonToObject(String jsonCamposV2, Pattern regex, String strSubs) {
		
		Matcher m = regex.matcher(jsonCamposV2);
		StringBuffer b = new StringBuffer();
		while (m.find()) {
			if(m.group(1) != null){
				m.appendReplacement(b, strSubs);
			}
			else if (m.group(0) != null){
				try {
					m.appendReplacement(b, m.group(0));
					
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
		}
		m.appendTail(b);
		String jsonCamposForPdfV2 = b.toString();
		return jsonCamposForPdfV2;
	}


	/**
	 * Define um intervalo de tempo conforme o valor em milisegundos
	 * @param miliseconds Intervalo de tempo em milisegundos
	 */
	public void tempoEsperaMili(int miliseconds){
		
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * Template para geracao de conteudo JSON para indexacao de logs
	 * 
	 * @param artefato Nome do artefato de origem do log
	 * @param ambiente Ambiente de execucao
	 * @param level Nivel do log
	 * @param tipo Tipo do log
	 * @param aplicacao Nome da aplicacao
	 * @param categoria Categoria do log
	 * @param teste Define se o log eh de teste. Valore possiveis: S ou N
	 * @param linha Numero da linha de origem do log
	 * @param metodo Metodo de origem do log
	 * @param mensagem Mensagem do log
	 * 
	 * @return Template de indexacao do log em formato JSON
	 */
	public String montarLogJson(String artefato, 
								String ambiente, 
								String level,
								String tipo,
								String aplicacao,
								String categoria,
								String teste,
								String linha,
								String metodo,
								String mensagem){

		Calendar hoje = Calendar.getInstance();
		String jsonLog = 
		"{"
		+ "\"log\":{"
			   +"\"artefato\":\""+artefato+"\","
			   +"\"tipo\":\""+tipo+"\","
			   +"\"level\":\""+level+"\","
			   +"\"ambiente\":\""+ambiente+"\","
			   +"\"aplicacao\":\""+aplicacao+"\","
			   +"\"categoria\":\""+categoria+"\","
			   +"\"teste\":\""+teste+"\","
			   +"\"mensagem\":\""+mensagem+"\","
			   +"\"linha\":\""+linha+"\","
			   +"\"metodo\":\""+metodo+"\","
			   +"\"datainclusao\":\""+hoje.getTime()+"\","
			   +"\"datainclusaolocal\":\""+hoje.getTime().toLocaleString()+"\","
			   +"\"datainclusaoinmilis\":\""+hoje.getTimeInMillis()+"\""
			   +"}"
		+ "}";
		
		return jsonLog;

	}

	/**
	 * Check if field is present for the class
	 * @param properties
	 * @param propertyName
	 * @return
	 */
	public boolean isPropertyPresent(Class propertyClass, final String propertyName) {
		
		Set<String> sourceFieldList = getAllFields(propertyClass);
		
	    return isPropertyPresent(sourceFieldList, propertyName);
	}

	/**
	 * Check if field is present for the class
	 * @param properties
	 * @param propertyName
	 * @return
	 */
	public boolean isPropertyPresent(Set<String> sourceFieldList, final String propertyName) {
		
	    if (sourceFieldList.contains(propertyName)) {
	        return true;
	    }
	    return false;
	}

	/**
     * Recursive method to get fields
     * 
     * @param type
     * @return
     */
    public static Set<String> getAllFields(final Class<?> type) {
        
    	Set<String> fields = new HashSet<String>();
        //loop the fields using Java Reflections
        for (Field field : type.getDeclaredFields()) {
            fields.add(field.getName());
        }
        
        //recursive call to getAllFields
        if (type.getSuperclass() != null) {
            fields.addAll(getAllFields(type.getSuperclass()));
        }
        return fields;
    }


	public String substituirFinalPor(String textoOriginal, String textoSubstituir, String termoSubstituir) {
		String retorno = textoOriginal;
		
		if(StringUtils.contains(textoOriginal, termoSubstituir)){
			retorno = textoOriginal.substring(0, textoOriginal.lastIndexOf(termoSubstituir)) + 
			textoSubstituir + 
			textoOriginal.substring(textoOriginal.lastIndexOf(termoSubstituir) + 1, textoOriginal.length());
			
		}
		return retorno;
	}

	public String substituirFinalPor(String textoOriginal, String textoSubstituir) {
		return textoOriginal.substring(0, textoOriginal.lastIndexOf(".")) + 
				textoSubstituir + 
				textoOriginal.substring(textoOriginal.lastIndexOf(".") + 1, textoOriginal.length());
	}

	public static Properties getProperties(String filePath) throws IOException, URISyntaxException {

		Properties props = new Properties();
		URI uri = Objects.requireNonNull(GcGeralUtil.class.getClassLoader().getResource(filePath)).toURI();
		FileInputStream file = new FileInputStream(uri.getPath());
		props.load(file);
		return props;

	}

	
	public static String converteObjetoParaJson(final Object objeto) throws Exception {
		final StringWriter sw = new StringWriter();
		final ObjectMapper mapper = new ObjectMapper();
		final MappingJsonFactory jsonFactory = new MappingJsonFactory();
		final JsonGenerator jsonGenerator = jsonFactory.createGenerator(sw);
		try {
			mapper.writeValue(jsonGenerator, objeto);
			return sw.getBuffer().toString();
		} catch (Exception e) {
			throw e;
		} finally {
			sw.close();
		}
	}

	public static String logObjetoParaJsonIdentado(final Object objeto) throws Exception {
		
	    ObjectMapper objMapper = new ObjectMapper();
	    String requestBody = objMapper.writeValueAsString(objeto);
	    objMapper.enable(SerializationFeature.INDENT_OUTPUT);            
	    String logJson = objMapper.writeValueAsString(objMapper.readTree(requestBody));
	    
	    return logJson; 
	    
	}

	public static <T> T converteJsonParaObjeto(final String json, final
			Class<T> objeto) throws IOException, InstantiationException,
	IllegalAccessException {
		final ObjectMapper mapeador = new ObjectMapper();
		mapeador.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final T obj = mapeador.readValue(json, objeto);
		return obj;
	}

}
