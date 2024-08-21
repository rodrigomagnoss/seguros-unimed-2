package br.com.segurosunimed.gestaocarteiras.commons.teste.base;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
})

/**
 * Classe principal para inicio dos testes automatizados e verificacao de cobertura de testes
 * 
 * @author Rodrigo Magno
 */
public class GcSuiteBaseTeste {

	static Logger logT = Logger.getLogger("teste");

	@BeforeClass
	public static void iniciandoSuiteTeste() {
		logT.info("INI SUITE");
	}

	@AfterClass
	static public void finalizandoSuiteTeste() {
		logT.info("FIM SUITE");
	}
	
	/**
	 * Verifica a cobertura de testes para a classe informada
	 * @param clazz Objeto de referencia da classe para verificar cobertura de testes
	 * @return Numero de metodos sem anotacao de motivo para ignorar ou acao a executar para cobertura de testes
	 * @throws NoSuchMethodException
	 */
//	private int verificarCoberturaTestePorClasse(Class clazz) throws NoSuchMethodException{
//
//		Method[] methods = clazz.getDeclaredMethods();
//        int nMethod = 0;
//        for (Method appMetodo : methods) {
////        	if(StringUtils.endsWith(clazz.getName(), "Test")){
//            	if(	appMetodo.getAnnotation(Ignore.class) == null && 
//            		!appMetodo.getName().contains("getInstance")){
//                    String packTesteClass = clazz.getCanonicalName().replace(".azure.", ".teste.azure.");  
//					Class testeClasse = null;
//					@SuppressWarnings("unused")
////					Method testeMethod = null;
//                	String testeClasseNome = "";
//                	String testeMetodoNome = "";
//                    try {
//                    	testeClasseNome = packTesteClass+"Test";
//						testeClasse = Class.forName(testeClasseNome);
//						testeMetodoNome = "test"+StringUtils.capitalize(appMetodo.getName());
//						boolean testeMetodoExiste = false;
//						for (Method testeMetodo : testeClasse.getDeclaredMethods()) {
//							String testeNomeMetodo = testeMetodo.getName(); 
//							if(testeMetodo.getAnnotation(Before.class) == null &&
//									testeMetodo.getAnnotation(BeforeClass.class) == null &&
//									testeMetodo.getAnnotation(After.class) == null &&
//									testeMetodo.getAnnotation(AfterClass.class) == null )
//							if (testeNomeMetodo.equals(testeMetodoNome)) {
//								testeMetodoExiste = true;
//							}
//						}
//						if(!testeMetodoExiste){
//							throw new NoSuchMethodException();						
//						}
//					} catch (NoSuchMethodException e) {
//						if(nMethod == 0){
//							logT.info("COBERTURA DE TESTES PARA CLASSE: "+clazz.getName());
//							logT.info("METODOS DA CLASSE SEM TESTE");
//						}
//	                    System.out.printf("#GC# TEST - #%d. %s", ++nMethod, appMetodo);
//	                    System.out.println();
//					} catch (ClassNotFoundException e) {
//						logT.error("Erro - Verificar cobertura de testes. Classe nao encontrada: "+testeClasseNome, e);
//					}
//                 }
////        	}
//
//        }
//		return nMethod;
//	}	
	
	/**
	 * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
	 *
	 * @param packageName The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
//	private static Class[] getAllClasses(String packageName)
//	        throws ClassNotFoundException, IOException {
//		
//	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//	    assert classLoader != null;
//	    String path = packageName.replace('.', '/');
//	    Enumeration<URL> resources = classLoader.getResources(path);
//	    List<File> dirs = new ArrayList<File>();
//	    while (resources.hasMoreElements()) {
//	        URL resource = resources.nextElement();
//	        dirs.add(new File(resource.getFile()));
//	    }
//	    ArrayList<Class> classes = new ArrayList<Class>();
//	    for (File directory : dirs) {
//	        classes.addAll(findClasses(directory, packageName));
//	    }
//	    return classes.toArray(new Class[classes.size()]);
//	}

	/**
	 * Recursive method used to find all classes in a given directory and subdirs.
	 *
	 * @param directory   The base directory
	 * @param packageName The package name for classes found inside the base directory
	 * @return The classes
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