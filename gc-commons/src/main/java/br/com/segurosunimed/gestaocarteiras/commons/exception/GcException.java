package br.com.segurosunimed.gestaocarteiras.commons.exception;

import java.io.FileNotFoundException;

import br.com.segurosunimed.gestaocarteiras.commons.enums.GcFileType;
import br.com.segurosunimed.gestaocarteiras.commons.util.GcMimeTypesUtil;

/**
 * Classe padrão Singleton para todas as exceções disparadas serviços</br></br>
 * 
 * @author Rodrigo Magno
 * @since 04/12/2019
 * 
 */
public final class GcException {

	private static final GcException INSTANCE = new GcException();
	
	public static GcException getInstance(){
		return INSTANCE;
	}
	

	/**
	 * Exceção para quando uma requisição na aplicação retorna erro
	 * 
	 */
	public class BadRequestException extends Exception{

		private static final long serialVersionUID = 1L;

		public BadRequestException(){
			
		}

		public BadRequestException(Throwable cause){
			super(cause);
		}

		public BadRequestException(String erro){
			super(erro);
		}

		public BadRequestException(String erro, Throwable cause){
			super(erro, cause);
		}

	}
	
	/**
	 * Exceção para quando o arquivo XML de interpretação não é encontrado
	 * 
	 */
	public class FileXMLNotFoundException extends Exception{

		private static final long serialVersionUID = 1L;

		public FileXMLNotFoundException(){
			
		}

		public FileXMLNotFoundException(Throwable cause){
			super(cause);
		}

		public FileXMLNotFoundException(String message, Throwable cause){
			super(message, cause);
		}

	}

	/**
	 * Exceção para quando o arquivo PDF de interpretação não é encontrado
	 * 
	 */
	public class FilePDFNotFoundException extends Exception{

		private static final long serialVersionUID = 1L;

		public FilePDFNotFoundException(){
			
		}

		public FilePDFNotFoundException(Throwable cause){
			super(cause);
		}

		public FilePDFNotFoundException(String message, Throwable cause){
			super(message, cause);
		}

	}

	/**
	 * Exceção para quando o arquivo JSON de interpretação não é encontrado
	 * 
	 */
	public class FileJSONNotFoundException extends Exception{

		private static final long serialVersionUID = 1L;

		public FileJSONNotFoundException(){
			
		}

		public FileJSONNotFoundException(Throwable cause){
			super(cause);
		}

		public FileJSONNotFoundException(String message, Throwable cause){
			super(message, cause);
		}
	}

	public class FileXMLNotInformedException extends Exception{

		private static final long serialVersionUID = 1L;

		public FileXMLNotInformedException(){
			
		}

		public FileXMLNotInformedException(Throwable cause){
			super(cause);
		}
	}

	/**
	 * Exceção para quando o arquivo PDF não é informado como parâmetro na chamada da função serverless
	 * 
	 */
	public class FilePDFNotInformedException extends Exception{

		private static final long serialVersionUID = 1L;

		public FilePDFNotInformedException(){
			
		}

		public FilePDFNotInformedException(Throwable cause){
			super(cause);
		}
	}

	/**
	 * Exceção para quando o arquivo XML de um interpretador não é encontado
	 * 
	 */
	public class FileInterpretadorNotFoundException extends Exception{

		private static final long serialVersionUID = 1L;
		
		public FileInterpretadorNotFoundException(Throwable cause){
			super(cause);
		}
	}

	/**
	 * Exceção para quando o arquivo XML está mal formado
	 * 
	 */
	public class FileXMLMalFormedException extends Exception{

		private static final long serialVersionUID = 1L;
		
		public FileXMLMalFormedException(Throwable cause){
			super(cause);
		}
	}

	/**
	 * Exceção para quando algum erro ocorre na utilização do XML do interpretador
	 * 
	 */
	public class InterpretadorXMLException extends Exception{

		private static final long serialVersionUID = 1L;
		
		public InterpretadorXMLException(Throwable cause){
			super(cause);
		}
	}

	/**
	 * Exceção para quando o arquivo PDF é utilizado para recuperar seu interpretador
	 * 
	 */
	public class InterpretadorPDFLException extends Exception{

		private static final long serialVersionUID = 1L;
		
		public InterpretadorPDFLException(Throwable cause){
			super(cause);
		}
	}

	/**
	 * Exceção para quando o arquivo PDF não pode ser lido pelo Fomr Recognizer
	 * 
	 */
	public class UnableReadFileException extends Exception{

		private static final long serialVersionUID = 1L;
		
		public UnableReadFileException(Throwable cause){
			super(cause);
		}
		
		public UnableReadFileException(String erro){
			super(erro);
		}
		
	}

	/**
	 * Exceção para quando os dados não são lidos do arquivo PDF
	 * 
	 */
	public class EmptyFileDataException extends Exception{

		private static final long serialVersionUID = 1L;
		
		public EmptyFileDataException(Throwable cause){
			super(cause);
		}
		
		public EmptyFileDataException(String erro){
			super(erro);
		}
		
	}

	/**
	 * Exceção para quando o arquivo tem um content type não suportado pelo serviço de análise
	 * 
	 */
	public class UnsupportedMediaTypeException extends Exception{

		private static final long serialVersionUID = 1L;
		
		public UnsupportedMediaTypeException(Throwable cause){
			super(cause);
		}
		
		public UnsupportedMediaTypeException(String erro){
			super(erro);
		}
		
	}

	/**
	 * Exceção para quando uma requisição de um arquivo na apliação retorn erro
	 * 
	 */
	public class BadRequestFileException extends Exception{

		private static final long serialVersionUID = 1L;
		
		public BadRequestFileException(Throwable cause){
			super(cause);
		}
		
		public BadRequestFileException(String erro){
			super(erro);
		}
		
	}

	//Getters das instâncias das exceções
	
	public FileXMLNotFoundException getFileXMLNotFoundException(Exception e) {
		return new FileXMLNotFoundException(e);
	}

	public FileXMLNotFoundException getFileXMLNotFoundException(String message, Exception e) {
		return new FileXMLNotFoundException(message, e);
	}

	public FilePDFNotFoundException getFilePDFNotFoundException(Exception e) {
		return new FilePDFNotFoundException(e);
	}

	public FilePDFNotFoundException getFilePDFNotFoundException(String message, Exception e) {
		return new FilePDFNotFoundException(message, e);
	}

	public FileJSONNotFoundException getFileJSONNotFoundException(Exception e) {
		return new FileJSONNotFoundException(e);
	}

	public FileJSONNotFoundException getFileJSONNotFoundException(String message, Exception e) {
		return new FileJSONNotFoundException(message, e);
	}

	public InterpretadorPDFLException getFileInterpretadorPDFLException(Exception e) {
		return new InterpretadorPDFLException(e);
	}

	public FileXMLMalFormedException getFileXMLMalFormedException(Exception e) {
		return new FileXMLMalFormedException(e);
	}

	public InterpretadorXMLException getInterpretadorXMLException(Exception e) {
		return new InterpretadorXMLException(e);
	}

	public FileInterpretadorNotFoundException getFileInterpretadorNotFoundException(Exception e) {
		return new FileInterpretadorNotFoundException(e);
	}

	public FilePDFNotInformedException getFilePDFNotInformedException(Exception e) {
		return new FilePDFNotInformedException(e);
	}

	public FilePDFNotInformedException getFilePDFNotInformedException() {
		return new FilePDFNotInformedException();
	}

	public FileXMLNotInformedException getFileXMLNotInformedException(Exception e) {
		return new FileXMLNotInformedException(e);
	}

	public FileXMLNotInformedException getFileXMLNotInformedException() {
		return new FileXMLNotInformedException();
	}

	public BadRequestException getBadRequestException() {
		return new BadRequestException();
	}
	public BadRequestException getBadRequestException(Exception e) {
		return new BadRequestException(e);
	}
	public BadRequestException getBadRequestException(String erro) {
		return new BadRequestException(erro);
	}

	public UnableReadFileException getUnableReadFileException(Exception e) {
		return new UnableReadFileException(e);
	}
	public UnableReadFileException getUnableReadFileException(String erro) {
		return new UnableReadFileException(erro);
	}

	public EmptyFileDataException getEmptyFileDataException(Exception e) {
		return new EmptyFileDataException(e);
	}
	public EmptyFileDataException getEmptyFileDataException(String erro) {
		return new EmptyFileDataException(erro);
	}
	
	public UnsupportedMediaTypeException getUnsupportedMediaTypeException(Exception e) {
		return new UnsupportedMediaTypeException(e);
	}
	public UnsupportedMediaTypeException getUnsupportedMediaTypeException(String erro) {
		return new UnsupportedMediaTypeException(erro);
	}
		
	public BadRequestFileException getBadRequestFileException(Exception e) {
		return new BadRequestFileException(e);
	}
	public BadRequestFileException getBadRequestFileException(String erro) {
		return new BadRequestFileException(erro);
	}

	public Exception getFileNotFoundException(String nomeArquivo, FileNotFoundException e) {
		return getFileNotFoundException(nomeArquivo, e.getMessage(), e);
	}

	public Exception getFileNotFoundException(String nomeArquivo, String message, FileNotFoundException e) {

		String mimeType = GcMimeTypesUtil.getMimeTypeFromFileName(nomeArquivo);
		if(GcFileType.PDF.mimetype().equals(mimeType)){
			return getFilePDFNotFoundException(message, e);
		}
		if(GcFileType.XML.mimetype().equals(mimeType)){
			return getFileXMLNotFoundException(message, e);
		}
		if(GcFileType.JSON.mimetype().equals(mimeType)){
			return getFileJSONNotFoundException(message, e);
		}
		
		return e;
	}

	public class DirectoryNotFoundException extends BadRequestException{

		private static final long serialVersionUID = 1L;

		public DirectoryNotFoundException(){
			
		}

		public DirectoryNotFoundException(Throwable cause){
			super(cause);
		}

		public DirectoryNotFoundException(String message){
			super(message);
		}

		public DirectoryNotFoundException(String message, Throwable cause){
			super(message, cause);
		}

	}

	public BadRequestException getDirectoryNotFoundException() {
		return new DirectoryNotFoundException();
	}
	
	public BadRequestException getDirectoryNotFoundException(String message, Exception e) {
		return new DirectoryNotFoundException(message, e);
	}

	public class VersionMismatchException extends BadRequestException{

		private static final long serialVersionUID = 1L;

		public VersionMismatchException(){
			
		}

		public VersionMismatchException(Throwable cause){
			super(cause);
		}

		public VersionMismatchException(String message, Throwable cause){
			super(message, cause);
		}

	}

	public Exception getVersionMismatchException(BadRequestException e) {
		return new VersionMismatchException(e);
	}

	public Exception getVersionMismatchException(String message, BadRequestException e) {
		return new VersionMismatchException(message, e);
	}

}
