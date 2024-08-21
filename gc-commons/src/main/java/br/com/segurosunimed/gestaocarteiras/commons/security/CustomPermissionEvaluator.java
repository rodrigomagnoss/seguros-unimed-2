package br.com.segurosunimed.gestaocarteiras.commons.security;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class CustomPermissionEvaluator implements PermissionEvaluator {
	
    @Override
    public boolean hasPermission(Authentication auth, Object feature, Object scope) {

        if ((auth == null) || (feature == null) || !(scope instanceof String)) {
            return false;
        }

        String featureType 	= feature.toString().toUpperCase();
        String scopeType 	= scope.toString().toUpperCase();

        String wsFeatureType 	= "WS_INTERNAL_ROLE";
        String wsScopeType 		= "FULL_ACCESS";

        String admFeatureType 	= "ACESSO_ADMINISTRADOR_GERAL";
        String admScopeType 	= "FULL_ACCESS";

        //Retorna permissão conforme prioridade
        //1) Permissão Administrador Geral 	- Irrestrito, não declarativa
        //2) Permissão WebServices 			- Sobrepassa perfil do recurso, declarativa
        //3) Permissão Recurso				- Recurso, declarativa	
        
        return 	
        		hasPrivilege(auth, admFeatureType, admScopeType) ||	//Permissão para Administrador Geral
        		hasPrivilege(auth, wsFeatureType, wsScopeType) ||   //Permissão para webservices
        		hasPrivilege(auth, featureType, scopeType) 			//Permissão do recurso 
        		;		
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
    	
        if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }
        
        
        return hasPrivilege(auth, targetType.toUpperCase(), permission.toString().toUpperCase());
    }

    private static boolean hasPrivilege(Authentication auth, String targetType, String permission) {
    	
        for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
            String[] permissions = grantedAuth.getAuthority().toUpperCase().split("\\|");
            String authority = permissions[0];
            String scope = permissions[1];
            if (authority.equals(targetType) && scope.equals(permission)) {
                return true;
            }
        }
        
        return false;
    }

}