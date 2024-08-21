package br.com.segurosunimed.gestaocarteiras.commons.security.annotation;

import br.com.segurosunimed.gestaocarteiras.commons.security.constants.BaseRoleSecurityConstants;
import br.com.segurosunimed.gestaocarteiras.commons.security.constants.ScopeSecurityConstants;

public @interface BaseCheckSecurity {

	final static String ROLE_ACESSO_ADMIN_GERAL = BaseRoleSecurityConstants.ROLE_ACESSO_ADMIN_GERAL;

	final String PERMISSAO_READ_ONLY 	= ScopeSecurityConstants.READ_ONLY;
	final String PERMISSAO_FULL_ACCESS	= ScopeSecurityConstants.FULL_ACCESS;

	public @interface Access {
	

//		@PreAuthorize("isAuthenticated() and hasPermission(\'"+ROLE_PERFIL+"\', \'"+PERMISSION_FULL_ACCESS+"\')")
//		@Retention(RUNTIME)
//		@Target(METHOD)
//		public @interface ReadOnlyPerfil { }
//
//		@PreAuthorize("isAuthenticated() and hasPermission(\'"+ROLE_PERFIL+"\', \'"+PERMISSION_READ_ONLY+"\')")
//		@Retention(RUNTIME)
//		@Target(METHOD)
//		public @interface FullAccessPerfil{ }
//
//		@PreAuthorize("isAuthenticated() and hasPermission(\'"+ROLE_FUNCIONALIDADE+"\', \'"+PERMISSION_FULL_ACCESS+"\')")
//		@Retention(RUNTIME)
//		@Target(METHOD)
//		public @interface ReadOnlyFuncionalidade{ }
//
//		@PreAuthorize("isAuthenticated() and hasPermission(\'"+ROLE_FUNCIONALIDADE+"\', \'"+PERMISSION_READ_ONLY+"\')")
//		@Retention(RUNTIME)
//		@Target(METHOD)
//		public @interface FullAccessFuncionalidade{ }

	}
	
}
