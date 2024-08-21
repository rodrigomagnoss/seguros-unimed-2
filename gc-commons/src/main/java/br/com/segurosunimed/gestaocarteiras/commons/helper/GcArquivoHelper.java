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
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.io.Resources;

import br.com.segurosunimed.gestaocarteiras.commons.exception.GcException;
import br.com.segurosunimed.gestaocarteiras.commons.exception.GcException.DirectoryNotFoundException;

/**
 * Classe padrao Singleton para apoio nos processamentos
 * 
 * @author Rodrigo Magno
 * @since 21/01/2022
 * 
 */
@Component
public class GcArquivoHelper {

    static final Logger LOGGER = LoggerFactory.getLogger(GcArquivoHelper.class);
	
	private static final GcArquivoHelper INSTANCE = new GcArquivoHelper();
	
	
	public static GcArquivoHelper getInstance(){
		return INSTANCE;
	}
	
	/**
	 * 
	 * Metodo que recupera o InputStream (dados em bytes) de um arquivo dado a url do seu endereço</br></br>
	 * 
	 * - Verifica se a url como online ou local.</br>
	 * - Le o arquivo e recupera o seu InputStream (conteudo em bytes). </br>
	 * - Monta o mapa com a url e InputStream do arquivo para retorno.</br>
	 * 
	 * @param strUrlArquivo String - Url do endereco do arquivo
	 * 
	 * @return Map - Mapa com os dados da url e do InputStream do arquivo
	 * 
	 * @throws MalformedURLException - Url do arquivo foi informado incorretamente ou nao existe
	 * @throws Exception - Excecao generica
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
					nomeArqEscape = URLEncoder.encode(nomeArquivo, StandardCharsets.UTF_8.name());
					if(StringUtils.containsWhitespace(nomeArquivo)){
						nomeArqEscape = nomeArqEscape.replace("+", "%20");
					}
					strUrlArquivo = strUrlArquivo.replace(nomeArquivo, nomeArqEscape);
				}
				//Endereco do arquivo online
				urlFile =  new URL(strUrlArquivo);
				URLConnection urlConn = urlFile.openConnection();
				isFile = urlConn.getInputStream();			
			}else{
				//Endereco do arquivo local
				Path fileLocation = Paths.get(strUrlArquivo);
				byte[] data = Files.readAllBytes(fileLocation);
				isFile = new ByteArrayInputStream(data);
				File file = new File(strUrlArquivo);
				urlFile = file.toURL();
			}
			//Monta mapa de retorno
			mapIS.put("url", urlFile);
			mapIS.put("IS", isFile);
		} catch (Exception e) {
			LOGGER.warn("Alerta - Recuperar InputStream do arquivo: "+strUrlArquivo+" - Mensagem original: "+e);
			throw e;
		}

		return mapIS;

	}
	
	/**
	 * Metodo extrair dados de chave/valor de um texto utilizando expressoes regulares
	 * 
	 * @param umTextoValor Texto para extracao dos dados de chave/valor por expressão regular
	 * 
	 * @return Arquivo de propriedades com os dados do arquivo, que são: 
	 * 		<br>nome: o nome do arquivo com sua extensão
			<br>raiz: nome da pasta raiz absoluta do arquivo
			<br>categoria: nome da pasta de categoria do arquivo
			<br>caminho: nomes das pastas entre a raiz e a categoria
			<br>extensao: extensão do arquivo

	 */
	public Properties extrairPropriedadesArquivo(String urlArquivo) {


////		String regexNomeArquivo = "(.*[\\\\|/])*(.*(\\.\\w{3}))$";
//		String dirTestes = StringUtils.contains(urlArquivo, "testes") ? "/testes/" : "";
//		Properties arquivoProps = new Properties();
//		String regexNomeArquivo = 
//				".*(nfse[\\\\|/]nfse-in|nfe[\\\\|/]nfe-in|cte[\\\\|/]cte-in)([\\\\|/]*(.*)[\\\\|/]("
//						+dirNfseInPdfs+"|"
//						+dirDocInXmls+
//				"))[\\\\|/](.*(\\.\\w{3,4}))$";
//		//String[] dadosArquivo = new String[]{};
//		Pattern regPattern = regPattern = Pattern.compile(regexNomeArquivo,Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
//		Matcher matcher = regPattern.matcher(urlArquivo);
//		if(matcher.find()){
//			arquivoProps.put("raiz", dirTestes + matcher.group(1));
//			arquivoProps.put("caminho", matcher.group(2));
//			arquivoProps.put("categoria", matcher.group(3));
//			arquivoProps.put("tipo", matcher.group(4));
//			arquivoProps.put("nome", matcher.group(5));
//			arquivoProps.put("extensao", matcher.group(6));
//		}
//
//		return arquivoProps;

		return null;
	}	
	
	public Map recuperarArquivosDeDiretorioLocal(String urlLote) throws Exception {
		
        // Creates a new File instance by converting the given pathname string
        // into an abstract pathname
        File diretorio = new File(urlLote);
        File arquivo = new File(urlLote);
        // Populates the array with names of files and directories
        List listaArquivos = new ArrayList();
        
		StringBuffer strRelatorio = new StringBuffer("");
		Map mapaArquivos = new HashMap();
		
		Pattern regPattern = null;
		Matcher matcher = null;
		String caminhoContainer = "";
		String caminhoDiretorio = diretorio.getAbsolutePath();
		
		try {

			if(!diretorio.exists()){
				throw GcException.getInstance().getDirectoryNotFoundException();
			}
			
			regPattern = Pattern.compile("(\\\\(xxx)\\\\)(.*)",Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
			matcher = regPattern.matcher(urlLote);
			caminhoContainer = "";
			
			if(matcher.find()){
				caminhoContainer = matcher.group(1);
			}				
			
		    strRelatorio.append("Armazenamento");
		    strRelatorio.append("<br><br>Tipo de Arquivo");
		    System.out.println("Tipo Arquivo");
		    strRelatorio.append("<br>&nbsp;&nbsp;" + caminhoContainer);
		    System.out.println("\t" + caminhoContainer);

			//caminhoDiretorio = urlLote.replaceFirst(caminhoContainer.replace("\\","\\\\"), "");
		    strRelatorio.append("<br>DiretOrio");
		    strRelatorio.append("<br>&nbsp;&nbsp;" + diretorio.getPath());
		    System.out.println("Diretorio");
		    System.out.println("\t" + diretorio.getPath());

		    
			System.out.println("Arquivo(s)");
			
			caminhoDiretorio = diretorio.toURL().toString();
			
			if(arquivo.isDirectory()){
				lerArquivosDiretorioLocal(urlLote, listaArquivos);			
			}else{
			    listaArquivos.add(urlLote);
			    String nomeArq = getNomeArquivoUrl(urlLote);
			    caminhoDiretorio = StringUtils.substringBefore(caminhoDiretorio, nomeArq);
			}

		    
		    System.out.println("");
			
		    mapaArquivos.put("relatorio", strRelatorio);
		    mapaArquivos.put("arquivos", listaArquivos);
		    mapaArquivos.put("diretorio", caminhoDiretorio);
        
		}
		catch (DirectoryNotFoundException e) {
			String msgErro = "Erro - Diretório nao encontrado. URL: "+caminhoDiretorio+", Container: "+caminhoContainer;
			throw GcException.getInstance().getBadRequestException(msgErro);
		}
		catch (Exception e) {
			String msgErro = "Erro - Listar arquivos em um diretório de uma conta. URL: "+caminhoDiretorio;
			throw GcException.getInstance().getBadRequestException(msgErro);
		}
		
		return mapaArquivos;
	}

	private void lerArquivosDiretorioLocal(String urlDiretorio, List listaArquivos) {

		String extensaoArquivos = getExtensaoUrlInterpretacao(urlDiretorio);				
        File diretorio = new File(urlDiretorio);
        File arquivo = null;

		for (int i = 0; i < diretorio.listFiles().length; i++) {

			File file = (File) diretorio.listFiles()[i];
			if(file.isDirectory()){
				lerArquivosDiretorioLocal(file.getAbsolutePath(), listaArquivos);
			}
			String strUri = file.getAbsolutePath();
			strUri = URLDecoder.decode(strUri);
			if(strUri.contains("."+extensaoArquivos)){
				if(arquivo == null || strUri.contains(arquivo.getAbsolutePath())){
				    System.out.println("\t" + strUri);
				    listaArquivos.add(strUri);
				}
			}
			
		}
	}

	public String getExtensaoUrlInterpretacao(String urlDiretorio) {
		Pattern regPattern = Pattern.compile("(\\\\(pdf|xml)\\\\)(.*)",Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		Matcher matcher = regPattern.matcher(urlDiretorio);
		String extensaoArqInterpretacao = "";
		if(matcher.find()){
			extensaoArqInterpretacao = matcher.group(2);
		}
		return extensaoArqInterpretacao;
	}
	


	/**
	 * Metodo para gravar um arquivo 
	 * 
	 * @param strUrlArquivo Endereço absoluto do arquivo 
	 * @param conteudoArquivo Texto com o XML transformado dos dados JSON extraídos
	 * 
	 * @throws IOException
	 */
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
	 * Metodo que converte um objeto InputStrem para uma String
	 * @param isXMLFile Objeto InputStream a ser convertido
	 * @return String Dados convertidos do objeto InputStream
	 * 
	 * @throws IOException
	 */
	public StringBuffer getInputStreamParaString(InputStream isXMLFile) throws IOException {
		//Transformando InputStream do arquivo XML em String sem informar charset
		InputStreamReader isReader = new InputStreamReader(isXMLFile);
		BufferedReader reader = new BufferedReader(isReader);
		StringBuffer sb = new StringBuffer();
		String str;
		while((str = reader.readLine()) != null ){
			sb.append(str);
		}
		return sb;
	}



    
    /**
     * Recupera o endereco relativo da pasta da classe da aplicacao passada como parametro
     * @param clazz Class Classe para determinacao do endereço relativo
     * @return String Endereço relativo da classe
     * @Deprecated O Metodo onde e chamado sera abandonado
     */
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
	

	public String getArquivoResources(String filePath) throws IOException, URISyntaxException {

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

	/**
	 * Retorna o valor de uma planilha Excel 97/2003 como um mapa de dados
	 * @param excelFilePath Url Do arquivo Excel
	 * @param excelSheetName Nome da folha da planilha Excel. Fixo: Sheet1
	 * @return LinkedHashMap Mapa com os dados da planilha
	 */
//	public LinkedHashMap<String, String> retornarExcelDataAsMap(String excelFilePath, String excelSheetName) throws EncryptedDocumentException, IOException {
//		
//		LinkedHashMap mapaLinhas = new LinkedHashMap();
//		InputStream is = null;
//		
//		try {
//			Map mapaIS = recuperarInputStreamArquivo(excelFilePath);
//			is = (InputStream) mapaIS.get("IS");
//		} catch (Exception e1) {
//			logH.warn("Planilha de teste funcional em lote nao encontrada");
//		}
//		
//		if(is != null){
//			
//			// Create a Workbook
//			Workbook wb = WorkbookFactory.create(is);
//			// Get sheet with the given name "Sheet1"
//			Sheet planilha = wb.getSheet(excelSheetName);
//			// Initialized an empty LinkedHashMap which retain order
//			// Get total row count
//			int numLinhas = planilha.getPhysicalNumberOfRows();
//			// Skipping first row as it contains headers
//			for (int i = 1; i < numLinhas; i++) {
//				// Get the row
//				Row linha = planilha.getRow(i);
//				int numCelulas = linha.getPhysicalNumberOfCells();
//				String nomeArquivo = linha.getCell(0).getStringCellValue();
//				
//	    		LinkedHashMap<String, String> mapaCampos = new LinkedHashMap<>();
//	            for(int c = 1; c < numCelulas; c++) {
//	                Cell celula = linha.getCell((short)c);
//	    			String nomeCampo = planilha.getRow(0).getCell(c).getStringCellValue();
//	                if(celula != null) {
//	        			String campoValor = "";
//	        			try {
//	        				campoValor = linha.getCell(c).getStringCellValue();
//	        				
//	        			} catch (Exception e) {
//	        				campoValor = linha.getCell(c).getNumericCellValue()+"";
//	        			}
//	        			mapaCampos.put(nomeCampo, campoValor);
//	                }
//	            }
//	            mapaLinhas.put(nomeArquivo, mapaCampos);
//				
//			}
//			
//			wb.close();			
//		}
//
//		return mapaLinhas;
//	}	
	
	public String getConteudoArquivoJsoup(String url, String encoding) throws Exception{
		
//		GcArquivoHelper invConHelper = GcArquivoHelper.getInstance();
//		
//		Map<String, Object> mapaIS = invConHelper.recuperarInputStreamArquivo(url);
//		
//		InputStream isArq = (InputStream) mapaIS.get("IS");
//		
//		ByteArrayOutputStream baos = invConHelper.copyInputStreamToMemory(isArq);
//		isArq = new ByteArrayInputStream(baos.toByteArray()); 
//		InputStream isXMLEncoding = new ByteArrayInputStream(baos.toByteArray()); 
//		
//		final XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader( isXMLEncoding );
//		String encodingFromXMLDeclaration = xmlStreamReader.getCharacterEncodingScheme(); 
//
//		Content content = null;
//		String strDocumento = IOUtils.toString(isArq, encoding);
//
//		return strDocumento;

		return null;
	}

}
