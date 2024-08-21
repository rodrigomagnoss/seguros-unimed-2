package br.com.segurosunimed.gestaocarteiras.commons.teste.base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import br.com.segurosunimed.gestaocarteiras.commons.util.GcTesteAcao;
import br.com.segurosunimed.gestaocarteiras.commons.util.GcTesteIgnorar;

public class GcCoberturaBaseTeste{

	static Logger logT = Logger.getLogger("teste");

    private static GcCoberturaBaseTeste instance = new GcCoberturaBaseTeste();

    public static GcCoberturaBaseTeste getInstance() {
		return instance;
	}

	protected GcCoberturaBaseTeste() {
		super();
	}

	public boolean verificarCoberturaTestes(String[] pacoteVerificarCobertura) {

		boolean todosMetodosCobertos = true;
		
		try {
			todosMetodosCobertos = verificarCoberturaTestesPorPacote(pacoteVerificarCobertura) == 0;
		} catch (NoSuchMethodException e) {
			logT.error(e.toString());
			todosMetodosCobertos = false;
		}
		
		if(todosMetodosCobertos){
			System.out.println();
			logT.info("TODO O CODIGO VERIFICADO QUANTO A COBERTURA DE TESTES UNITARIOS");
			System.out.println();
		}

		return todosMetodosCobertos;

	}

	private static int verificarCoberturaTestesPorPacote(String[] pacoteVerificarCobertura) throws NoSuchMethodException{

        System.out.println();
		logT.info("RELATORIO DE COBERTURA DE TESTES");

		int nMethod = 0;
		for (int j = 0; j < pacoteVerificarCobertura.length; j++) {
			String umPacote = pacoteVerificarCobertura[j];
			try {
				Class[] classesApp = getAllClasses(umPacote);
				for (int i = 0; i < classesApp.length; i++) {
					Class<?> clazz = classesApp[i];
					if(!clazz.isAnonymousClass()){
						nMethod = nMethod + verificarCoberturaTestePorClasse(clazz);
					}
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
        if(nMethod > 0 ){
            System.out.println();
        	throw new NoSuchMethodException("FALHA NA VERIFICACAO DA COBERTURA DE TESTES DE ALGUNS METODOS.");
        }
		
		return nMethod;

	}
	
	/**
	 * Verifica a cobertura de testes para a classe informada
	 * @param clazz Objeto de referencia da classe para verificar cobertura de testes
	 * @return Numero de metodos sem anotacao de motivo para ignorar ou acao a executar para cobertura de testes
	 * @throws NoSuchMethodException
	 */
	private static int verificarCoberturaTestePorClasse(Class clazz) throws NoSuchMethodException{

		Method[] methods = clazz.getDeclaredMethods();
        int numMetodosTeste = 0;
        int numMetodos = 0;
        for (Method appMetodo : methods) {
        		String appMetodoNome = appMetodo.getName(); 
            	if(	
            		!ehMetodoPrivado(appMetodo) &&
            		!ehMetodoTeste(appMetodo) &&
            		!ehMetodoAnotacao(appMetodo) && 
            		!ehMetodoGetterSetter(appMetodoNome) && 
            		!ehMetodoBooleano(appMetodoNome)
					){
                    String packTesteClass = clazz.getCanonicalName() != null ? clazz.getCanonicalName() : clazz.getName();  
                    packTesteClass = packTesteClass.replace(".gc.", ".gc.teste.suite.caso.");  
					Class testeClasse = null;
					@SuppressWarnings("unused")
                	String testeClasseNome = "";
                	String testeMetodoNome = "";
                    try {
                    	testeClasseNome = packTesteClass+"Teste";
						testeMetodoNome = "teste"+StringUtils.capitalize(appMetodo.getName());
						boolean testeMetodoExiste = false;
						
						try{
							testeClasse = Class.forName(testeClasseNome);
							for (Method testeMetodo : testeClasse.getDeclaredMethods()) {
								String testeNomeMetodo = testeMetodo.getName(); 
								if( !ehMetodoAnotacao(testeMetodo))
									if (StringUtils.startsWith(testeNomeMetodo,testeMetodoNome)) {
										testeMetodoExiste = true;
									}
							}
						} catch (ClassNotFoundException e) {
//							log.warn("CLASSE NAO ENCONTRADA: "+testeClasseNome);
							//Não é necessário informar classe de teste não encontrada
						}
						if(!testeMetodoExiste){
							throw new NoSuchMethodException();						
						}
					} catch (NoSuchMethodException e) {
						if(numMetodos == 0){
		                    System.out.println();
							logT.info("COBERTURA DE TESTES PARA CLASSE: "+clazz.getName());
						}
						if(appMetodo.getAnnotation(GcTesteAcao.class) != null){
							GcTesteAcao annotationTesteAcao = appMetodo.getAnnotation(GcTesteAcao.class); 
							if(annotationTesteAcao.acao().ehAdiar() || annotationTesteAcao.acao().ehAvaliar()){
			                    System.out.printf("#GC# TEST [GcBaseCoberturaTeste] - <ACAO: "+annotationTesteAcao.acao().toString()+"> - #%d. %s", ++numMetodos, appMetodo);
			                    System.out.println();
							}
						}else{
							numMetodosTeste++;
		                    System.out.printf("#GC# TEST [GcBaseCoberturaTeste] - <ACAO: TESTAR> - #%d. %s", ++numMetodos, appMetodo.getName());
		                    System.out.println();
						}
					}
                 }
        }
		return numMetodosTeste;
	}

	/**
	 * Verifica se o metodo passado é GETTER ou SETTER ou similar (ADD, INIT, etc)
	 * @param metodoNome Nome do metodo
	 * @return true se for metodo getter/setter, false caso contrario
	 */
	private static boolean ehMetodoGetterSetter(String metodoNome) {
		return 	StringUtils.startsWith(metodoNome,"getInstance") || 
				StringUtils.startsWith(metodoNome, "add") ||
				StringUtils.startsWith(metodoNome, "init") ||
				StringUtils.startsWith(metodoNome, "get") ||
				StringUtils.startsWith(metodoNome, "set");
	}

	/**
	 * Verifica se o metodo passado é um método privado
	 * @param appMetodo Instância do método
	 * @return true se for metodo privado, false caso contrario
	 */
	private static boolean ehMetodoPrivado(Method testeMetodo) {
		return Modifier.isPrivate(testeMetodo.getModifiers());
	}

	
	/**
	 * Verifica se o metodo passado é boleano ou similar
	 * @param metodoNome Nome do metodo
	 * @return true se for metodo boleano, false caso contrario
	 */
	private static boolean ehMetodoBooleano(String metodoNome) {
		return  StringUtils.startsWith(metodoNome, "eh") ||
				StringUtils.startsWith(metodoNome, "is") ||
				StringUtils.startsWith(metodoNome, "achou") ||
				StringUtils.startsWith(metodoNome, "contem");
	}

	/**
	 * Verifica se o metodo passado é de teste
	 * @param metodoNome Nome do metodo
	 * @return true se for metodo de teste, false caso contrario
	 */
	private static boolean ehMetodoTeste(Method testeMetodo) {
		return testeMetodo.getAnnotation(Test.class) != null;
	}

	private static boolean ehMetodoAnotacao(Method testeMetodo) {
		return 
			testeMetodo.getAnnotation(GcTesteIgnorar.class) != null ||
			testeMetodo.getAnnotation(Ignore.class) != null ||
			testeMetodo.getAnnotation(Before.class) != null ||
			testeMetodo.getAnnotation(BeforeClass.class) != null ||
			testeMetodo.getAnnotation(After.class) != null ||
			testeMetodo.getAnnotation(AfterClass.class) != null;
	}	
	
	/**
	 * Analisa as classes acessiveis do contexto de classes carregadas pertencentes a hierarquia do pacote informado
	 *
	 * @param packageName Nome do pacote raiz
	 * @return Classes acessiveis encontradas
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private static Class[] getAllClasses(String packageName)
	        throws ClassNotFoundException, IOException {
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    assert classLoader != null;
	    String path = packageName.replace('.', '/');
	    Enumeration<URL> resources = classLoader.getResources(path);
	    List<File> dirs = new ArrayList<File>();
	    while (resources.hasMoreElements()) {
	        URL resource = resources.nextElement();
	        dirs.add(new File(resource.getFile()));
	    }
	    ArrayList<Class> classes = new ArrayList<Class>();
	    for (File directory : dirs) {
	        classes.addAll(findClasses(directory, packageName));
	    }
	    return classes.toArray(new Class[classes.size()]);
	}

	/**
	 * Metodo recursivo para encontrar as classes de uma determinada pasta e suas subpastas
	 *
	 * @param directory   Pasta raiz
	 * @param packageName O nome do pacote para testar o endereco das classes encontradas 
	 * 
	 * @return Lista de classes encontradas
	 * 
	 * @throws ClassNotFoundException
	 */
	private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
	    List<Class> classes = new ArrayList<Class>();
	    if (!directory.exists()) {
	        return classes;
	    }
	    File[] files = directory.listFiles();
	    for (File file : files) {
	        if (file.isDirectory()) {
	            assert !file.getName().contains(".");
	            classes.addAll(findClasses(file, packageName + "." + file.getName()));
	        } else if (file.getName().endsWith(".class")) {
	            classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
	        }
	    }
	    return classes;
	}

}