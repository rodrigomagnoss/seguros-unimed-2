package br.com.segurosunimed.gestaocarteiras.commons.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
import org.apache.log4j.Logger;

import com.google.common.io.Resources;

import br.com.segurosunimed.gestaocarteiras.commons.enums.GcEnumAcaoTeste;
import br.com.segurosunimed.gestaocarteiras.commons.mail.message.GcHistMsgLog;
import br.com.segurosunimed.gestaocarteiras.commons.util.GcTesteAcao;
import br.com.segurosunimed.gestaocarteiras.commons.util.GcTesteIgnorar;

/**
 * Classe padrão Singleton para apoio nos processamentos
 * 
 * @author Rodrigo Magno
 * @since 04/12/2019
 * 
 */
public class GcHelper {

	Logger logM = Logger.getLogger("manifesto");
	Logger logH = Logger.getLogger("helper");
	
	private static final GcHelper INSTANCE = new GcHelper();
	
	public static final String EXPRESSAO_CNPJ = "(\\d{2})(?:[-.\\s])?(\\d{3})(?:[-.\\s])?(\\d{3})(?:[-\\.\\s\\/])?(\\d{4})(?:[-.\\s])?(\\d{2})(?![\\s])*";
	
	
	public static final String EXPRESSAO_HIFEN_IN_JSON_KEY =  "(-)|: \"[^\"]*\"";
	public static final String CODIGO_SETA_PRX = "&#129095;";
	public static final String CODIGO_SETA_ANT = "&#129093;";
	
	public static final String TEXTO_PRX = "PRÓXIMO";
	public static final String TEXTO_ANT = "ANTERIOR";
	
	public static GcHelper getInstance(){
		return INSTANCE;
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
	 * Método para retornar a combinação de uma expressão regurar encontrada em em um texto
	 * 
	 * @param umTextoParaCombinar Texto para verificar combinação com a expressão regular
	 * @param expressaoRegular Expressão regular utilizada para encontrar combinação no texto
	 * @param grupos Array com os grupos para recuperar da combinação
	 * @return true, caso a combinação seja encontrada e false caso contrário
	 */
	@GcTesteIgnorar
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
	 * Retira os acentos de uma string
	 * Método que sobrepõe o mesmo método da classe PlcGeralUtil mas força encoding na geração da string acentuada
	 * @param termo String para retirar acentos
	 * @return String sem acentos
	 * @throws UnsupportedEncodingException 
	 */
	String acentos 	= new String("áàãâäèéêëìíïîòóõôöûùúüçñýÿÁÀÃÂÄÈÉÊËÌÍÏÎÒÓÕÔÖÛÙÚÜÇÑ".getBytes());
	String semAcentos 		   = "aaaaaeeeeiiiiooooouuuucnyyAAAAAEEEEIIIIOOOOOUUUUCN";
	@GcTesteIgnorar
	public  String retirarAcentos(String termo) throws UnsupportedEncodingException
	{

        String nfdNormalizedString = Normalizer.normalize(termo, Normalizer.Form.NFD); 
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
	}

	/**
	 * Substituir o valor da expressão pelo valor passado para substituição
	 */
	@GcTesteIgnorar
	public  String substituirPorExpRegular(String termoSubstituir, String expSubstituir) throws UnsupportedEncodingException
	{
        Pattern pattern = Pattern.compile(expSubstituir);
        return pattern.matcher(termoSubstituir).replaceAll("");
	}

	/**
	 * 
	 * Método que recupera o InputStream (dados em bytes) de um arquivo dado a url do seu endereço</br></br>
	 * 
	 * - Verifica se a url é online ou local.</br>
	 * - Lê o arquivo e recupera o seu InputStream (conteúdo em bytes). </br>
	 * - Monta o mapa com a url e InputStream do arquivo para retorno.</br>
	 * 
	 * @param strUrlArquivo String - Url do endereço do arquivo
	 * 
	 * @return Map - Mapa com os dados da url e do InputStream do arquivo
	 * 
	 * @throws MalformedURLException - Url do arquivo foi informado incorretamente ou não existe
	 * @throws Exception - Exceção genérica
	 */
	@SuppressWarnings("deprecation")
	public  Map<String, Object> recuperarInputStreamArquivo(String strUrlArquivo) throws MalformedURLException, Exception {
	
		InputStream isFile = null;
		URL urlFile = null;
		Map<String, Object> mapIS = new HashMap<String, Object>();
		String nomeArqEscape = "";
		try {

			if(strUrlArquivo.contains("http")){
				Properties dadosArquivo = extrairPropriedadesArquivo(strUrlArquivo);
				String nomeArquivo = dadosArquivo.getProperty("nome");
				if(StringUtils.isNotBlank(nomeArquivo)){
					nomeArquivo = nomeArquivo.contains("/") ? StringUtils.substringAfterLast(nomeArquivo, "/") : nomeArquivo; 
					nomeArquivo = nomeArquivo.contains("\\") ? StringUtils.substringAfterLast(nomeArquivo, "\\") : nomeArquivo; 
//					nomeArqEscape = URLEncoder.encode(nomeArquivo, StandardCharsets.UTF_8.name()).replace("+", "%20");
					nomeArqEscape = URLEncoder.encode(nomeArquivo, StandardCharsets.UTF_8.name());
					if(StringUtils.containsWhitespace(nomeArquivo)){
						nomeArqEscape = nomeArqEscape.replace("+", "%20");
					}
					strUrlArquivo = strUrlArquivo.replace(nomeArquivo, nomeArqEscape);
				}
				//Endereço do arquivo é online
				urlFile =  new URL(strUrlArquivo);
				URLConnection urlConn = urlFile.openConnection();
				isFile = urlConn.getInputStream();			
			}else{
				//Endereço do arquivo é local
				Path fileLocation = Paths.get(strUrlArquivo);
				byte[] data = Files.readAllBytes(fileLocation);
				isFile = new ByteArrayInputStream(data);
				File nfe = new File(strUrlArquivo);
				urlFile = nfe.toURL();
			}
			//Monta mapa de retorno
			mapIS.put("url", urlFile);
			mapIS.put("IS", isFile);
		} catch (Exception e) {
			logH.warn("Alerta - Recuperar InputStream do arquivo: "+strUrlArquivo+" - Mensagem original: "+e);
			throw e;
		}

		return mapIS;

	}
	

	/**
	 * Método extrair dados de chave/valor de um texto utilizando expressões regulares
	 * 
	 * @param umTextoValor Texto para extração dos dados de chave/valor por expressão regular
	 * 
	 * @return Arquivo de propriedades com os dados do arquivo, que são: 
	 * 		<br>nome: o nome do arquivo com sua extensão
			<br>raiz: nome da pasta raiz absoluta do arquivo
			<br>categoria: nome da pasta de categoria do arquivo
			<br>caminho: nomes das pastas entre a raiz e a categoria
			<br>extensao: extensão do arquivo

	 */
	public Properties extrairPropriedadesArquivo(String urlArquivo) {

		String dirTestes = StringUtils.contains(urlArquivo, "testes") ? "/testes/" : "";
		Properties arquivoProps = new Properties();
		String regexNomeArquivo = "(.*[\\\\|/])*(.*(\\.\\w{3}))$";
		//String[] dadosArquivo = new String[]{};
		Pattern regPattern = regPattern = Pattern.compile(regexNomeArquivo,Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		Matcher matcher = regPattern.matcher(urlArquivo);
		if(matcher.find()){
			arquivoProps.put("raiz", dirTestes + matcher.group(1));
			arquivoProps.put("caminho", matcher.group(2));
			arquivoProps.put("categoria", matcher.group(3));
			arquivoProps.put("tipo", matcher.group(4));
			arquivoProps.put("nome", matcher.group(5));
			arquivoProps.put("extensao", matcher.group(6));
		}

		return arquivoProps;

	}


	/**
	 * Método para gravar um arquivo 
	 * 
	 * @param strUrlArquivo Endereço absoluto do arquivo PDF que representa a NFSe para referência
	 * @param conteudoArquivo Texto com o XML transformado dos dados JSON extraídos do PDF
	 * 
	 * @throws IOException
	 */
	@GcTesteIgnorar
	public  void gravarArquivoLocal(String strUrlArquivo, String conteudoArquivo) throws IOException {

		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(strUrlArquivo), StandardCharsets.UTF_8.name()));
		try {
			out.write(conteudoArquivo);
		} finally {
			out.close();
		}
	}

	/**
	 * Método que converte um objeto InputStrem para uma String
	 * @param isXMLNFSe Objeto InputStream a ser convertido
	 * @return String Dados convertidos do objeto InputStream
	 * 
	 * @throws IOException
	 */
	public StringBuffer getInputStreamParaString(InputStream isXMLNFSe) throws IOException {
		//Transformando InputStream do arquivo XML em String sem informar charset
		InputStreamReader isReader = new InputStreamReader(isXMLNFSe);
		BufferedReader reader = new BufferedReader(isReader);
		StringBuffer sb = new StringBuffer();
		String str;
		while((str = reader.readLine()) != null ){
			sb.append(str);
		}
		return sb;
	}


	/**
	 * Método para auxiliar no acesso a endereções https sem restrições de acesso SSL 
	 */
	@GcTesteIgnorar
	public void bypassSSL() {
		final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			@GcTesteIgnorar
			public void checkClientTrusted(final java.security.cert.X509Certificate[] certs, final String authType) {
			}

			@Override
			@GcTesteIgnorar
			public void checkServerTrusted(final java.security.cert.X509Certificate[] certs, final String authType) {
			}

			@Override
			@GcTesteIgnorar
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
	 * Método para recuperar um cliente HttpClient sem restrições de acesso SSL 
	 * @return HttpClient Instância do objeto criado
	 */
	@GcTesteIgnorar
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
     * Recupera o endereço relativo da pasta da classe da aplicação passada como parâmetro
     * @param clazz Class Classe para determinação do endereço relativo
     * @return String Endereço relativo da classe
     * @Deprecated O método onde é chamado será abandonado
     */
	@GcTesteIgnorar
	@Deprecated
	public String getResourceClassPath(Class clazz) {
		String className = clazz.getSimpleName() + ".class";
		String classPath = clazz.getResource(className).toString();
		if (!classPath.startsWith("jar")) {
			classPath = "/home/site/wwwroot/";
		}else{
		  // Class not from JAR
			classPath = StringUtils.substringBeforeLast(classPath.substring(0, classPath.lastIndexOf("!")), "/") + "/";
			classPath = classPath.replace("jar:file:/", "");
		}
		
		return classPath;
	}
	
    /**
     * Método para trocar o caracter hifen (-) pelo caracter underscore (_) numa String de campos em formato JSON para geração do objeto pojo 
     * @param jsonCamposV2 String Campos no formato JSON extraídos de uma análise de pdf
     * @return String Campos com o caracter hifen (-) trocados pelo caracter underscore (_) 
     */
	@GcTesteIgnorar
	public String replaceHiffenToUnderscore(String jsonCamposV2) {
		Pattern regex = Pattern.compile(EXPRESSAO_HIFEN_IN_JSON_KEY);
		return replaceCamposJsonToObject(jsonCamposV2, regex, "_");
	}
	
    /**
     * Método para trocar o caracter underscore (_) pelo caracter hifen (-) numa String de campos em formato JSON para geração do objeto pojo 
     * @param jsonCamposV2 String Campos no formato JSON extraídos de uma análise de pdf
     * @return String Campos com o caracter underscore (_) trocados pelo caracter hifen (-)
     */
	@GcTesteIgnorar
	public String replaceUnderescoreToHiffen(String jsonCamposV2) {
		Pattern regex = Pattern.compile(EXPRESSAO_HIFEN_IN_JSON_KEY.replace("-", "_"));
		return replaceCamposJsonToObject(jsonCamposV2, regex, "-");
	}

	/**
	 * Método auxiliar para trocar caracteres na string dos campos extraídos usando expressão regular
	 * @param jsonCamposV2 Objeto pojo representando os campos JSON
	 * @param regex Expressão regular para trocar os caracteres
	 * @param strSubs String Caracter para substituição
	 * @return String Campos com os caracteres trocados. 
	 */
	@GcTesteIgnorar
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
	@GcTesteIgnorar
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
	@GcTesteIgnorar
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
	

	@GcTesteIgnorar
	public String substituirFinalPor(String textoOriginal, String textoSubstituir, String termoSubstituir) {
		String retorno = textoOriginal;
		
		if(StringUtils.contains(textoOriginal, termoSubstituir)){
			retorno = textoOriginal.substring(0, textoOriginal.lastIndexOf(termoSubstituir)) + 
			textoSubstituir + 
			textoOriginal.substring(textoOriginal.lastIndexOf(termoSubstituir) + 1, textoOriginal.length());
			
		}
		return retorno;
	}

	@GcTesteIgnorar
	public String substituirFinalPor(String textoOriginal, String textoSubstituir) {
		return textoOriginal.substring(0, textoOriginal.lastIndexOf(".")) + 
				textoSubstituir + 
				textoOriginal.substring(textoOriginal.lastIndexOf(".") + 1, textoOriginal.length());
	}

	public String getHtml(String filePath) throws IOException, URISyntaxException {

		URL url = Resources.getResource(filePath);
		String text = Resources.toString(url, StandardCharsets.UTF_8);
		return text;
		
	}

	/**
	 * Recupera o nome do arquivo a partir de sua URL
	 * @param url Url do arquivo
	 * @return Nome do arquivo
	 */
	public String getNomeArquivoUrl(String url) {
		String nomeArquivo = "";
		
		if(url == null){
			return nomeArquivo;
		}
		
		if(url.contains("\\")){
			if(StringUtils.endsWith(url, "\\")){
				url = StringUtils.removeEnd(url, "\\");
			}
			nomeArquivo = StringUtils.substringAfterLast(url, "\\");
		}else{
			if(StringUtils.endsWith(url, "/")){
				url = StringUtils.removeEnd(url, "/");
			}
			nomeArquivo = StringUtils.substringAfterLast(url, "/");
		}
		
		return nomeArquivo;
	}


	@GcTesteIgnorar
	public ByteArrayOutputStream copyInputStreamToMemory(InputStream isXML) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// Fake code simulating the copy
		// You can generally do better with nio if you need...
		// And please, unlike me, do something about the Exceptions :D
		byte[] buffer = new byte[1024];
		int len;
		while ((len = isXML.read(buffer)) > -1 ) {
		    baos.write(buffer, 0, len);
		}
		baos.flush();
		return baos;
	}


	
	@GcTesteAcao(acao=GcEnumAcaoTeste.ADIAR)
    public Attributes recuperarAtributosManifesto() {

		logH.info(new GcHistMsgLog("Recuperar informacoes de manifesto."));
		
    	try {
    		Class clazz = this.getClass();
    		String className = clazz.getSimpleName() + ".class";
    		String classPath = clazz.getResource(className).toString();
    		if (!classPath.startsWith("jar")) {
    		  // Class not from JAR
    		  return null;
    		}
    		String manifestPath = classPath.substring(0, classPath.lastIndexOf("!") + 1) + 
    		    "/META-INF/MANIFEST.MF";
    		Manifest manifest = new Manifest(new URL(manifestPath).openStream());
    		Attributes attr = manifest.getMainAttributes();    		
    		
    		return attr;
    		
    	} catch (Exception e) {
    		logH.error("Erro inesperado - Recuperar informacoes de manifesto", e);
    	}
		return null;
		
	}


}
