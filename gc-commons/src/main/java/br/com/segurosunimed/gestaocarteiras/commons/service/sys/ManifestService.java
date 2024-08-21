package br.com.segurosunimed.gestaocarteiras.commons.service.sys;

import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import br.com.segurosunimed.gestaocarteiras.commons.dto.response.ManifestDTO;
import io.micrometer.core.instrument.util.StringUtils;

@Service
public class ManifestService {

    public ManifestDTO mostrarDadosManifesto() {

    	ManifestDTO manifestDTO = new ManifestDTO();
    	
		Attributes attr = recuperarAtributosManifesto();
		if(attr != null){
    		String datetime = attr.getValue("Datetime");
    		String organization = attr.getValue("Organization");
    		String projeto = attr.getValue("Implementation-Title");
    		String version = attr.getValue("Implementation-Version");
    		
    		manifestDTO.setProjeto(projeto);
    		manifestDTO.setVersion(version);
    		manifestDTO.setOrganizacao(organization);

    		String formatDateTime = StringUtils.isNotBlank(datetime) ? datetime : version; 

    		if(StringUtils.isNotBlank(formatDateTime)) {
        		try {
	        		Pattern regPattern = Pattern.compile("(\\d{2})(\\d{2})(\\d{4})(\\d{2})(\\d{2})",Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
	        		Matcher mtch = regPattern.matcher(formatDateTime);
	        		mtch.find();
            		formatDateTime = 	mtch.group(1)+"/"+mtch.group(2)+"/"+mtch.group(3)+" "+mtch.group(4)+":"+mtch.group(5);
    			} catch (Exception e) {
    				formatDateTime = "";
    			}
    		}
    		
    		manifestDTO.setDatetime(formatDateTime);
		
		}

		return manifestDTO;
	}
	
    protected Attributes recuperarAtributosManifesto() {

    	try {
//    		String classPath = getClassPathForManifest();
//    		String manifestPath = "";
//    		if (!classPath.startsWith("jar")) {
//    			// Class not from JAR
//        		manifestPath = classPath.substring(0, classPath.lastIndexOf("/classes/") + 1) + 
//            		    "classes/META-INF/MANIFEST.MF";
////        		String manifestPath = classPath.substring(0, classPath.lastIndexOf("!") + 1) + 
////    		    "/META-INF/MANIFEST.MF";
//    		}else{
//        		manifestPath = classPath.substring(0, classPath.lastIndexOf("!") + 1) + 
//            		    "/META-INF/MANIFEST.MF";
//    		}

    		String classPath = getClassPathForManifest();
    		String manifestPath = "";
    		if (!classPath.startsWith("jar")) {
    			// Class not from JAR
//        		System.out.println("CLASSPATH NOT JAR");
        		manifestPath = classPath.substring(0, classPath.lastIndexOf("/classes/") + 1) + 
            		    "classes/META-INF/MANIFEST.MF";
//        		String manifestPath = classPath.substring(0, classPath.lastIndexOf("!") + 1) + 
//    		    "/META-INF/MANIFEST.MF";
    		}else{
//        		System.out.println("CLASSPATH WITH JAR");
        		manifestPath = classPath.substring(0, classPath.lastIndexOf("!") + 1) + 
            		    "/META-INF/MANIFEST.MF";
        		manifestPath = manifestPath.replace("/BOOT-INF", "");
        		manifestPath = manifestPath.replace("/classes!", "");
    		}
    		
    		Manifest manifest = new Manifest(new URL(manifestPath).openStream());
    		Attributes attr = manifest.getMainAttributes();    		
    		return attr;
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
		return null;
		
	}
    
    
    protected String getClassPathForManifest(){
    	
		Class clazz = this.getClass();
		String className = clazz.getSimpleName() + ".class";
		String classPath = clazz.getResource(className).toString();
		
		return classPath;
    	
    }
	
}
