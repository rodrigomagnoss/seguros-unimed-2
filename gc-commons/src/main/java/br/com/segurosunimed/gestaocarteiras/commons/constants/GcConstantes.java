package br.com.segurosunimed.gestaocarteiras.commons.constants;

/**
 * Classe de contantes gerais 
 *
 */
public interface GcConstantes {

	public static final String CONTEXTO_CONFIG 	= "CONTEXTO_CONFIG";
	public static final String CONTEXTO_CACHE 	= "CONTEXTO_CACHE";
	
	public interface CRITICIDADE {

		public static final Integer BAIXA = 3;
		public static final Integer MEDIA = 5;
		public static final Integer ALTA = 7;

	}


	public interface EXTENSAO_ARQUIVO {

		public static final String PDF 	= ".pdf";
		public static final String XML 	= ".xml";
		public static final String TXT 	= ".txt";
		public static final String HTML	= ".html";
		public static final String JSON	= ".json";

	}

	public interface ARQUIVO {

		public static final String PDF 	= "PDF";
		public static final String XML 	= "XML";

	}

	public interface ARMAZENAMENTO {

		public static final String IMAGENS 	= "imagens";

	}

	public interface INTERVALO {

		final int _1_SEGUNDO = 1000;
		final int _5_SEGUNDOS = _1_SEGUNDO * 5;
		final int _10_SEGUNDOS = _5_SEGUNDOS * 2;
		final int _15_SEGUNDOS = _5_SEGUNDOS * 3;
		final int _20_SEGUNDOS = _10_SEGUNDOS * 2;
		final int _30_SEGUNDOS = _10_SEGUNDOS * 3;
		final int _40_SEGUNDOS = _10_SEGUNDOS * 4;
		final int _60_SEGUNDOS = _30_SEGUNDOS * 2;
		final int INTER_ANALISE_ARQUIVOS = _40_SEGUNDOS;

	}	

	public static final String SIM = "S";
	public static final String NAO = "N";

	public static final String SIM_LITERAL = "Sim";
	public static final String NAO_LITERAL = "Não";

	public static final String VAZIO = "";
	public static final String NULO = null;

	public static final String GESTAO_OK = "OK";
	public static final String GESTAO_ERRO = "ERRO";

	public interface TAG{

		final String APLICACAO	= "APLICACAO_TAG";
		final String LOGGING	= "LOGGING_TAG";
		final String EMAIL		= "EMAIL_TAG";

	}

	public interface APLICACAO{

		final String NOME	= "NOME";
		final String SIGLA	= "SIGLA";

	}

	public interface AMBIENTE_EXECUCAO {

		public static final String _PARAM_AMBIENTE_EXECUCAO = "_PARAM_AMBIENTE_EXECUCAO";

		public interface TESTE_COBERTURA {

			public static final String MODO = "TESTE_COBERTURA";
		}

		public interface TESTE {

			public static final String MODO = "TESTE";
			public static final String VALOR_VAZIO = "";


			public interface EXCECAO {

				public static final String TIPO_EXEC_BAD_REQUEST = "BadRequestFileException";
				public static final String RETORNO_ESPERADO_BAD_REQUEST = "RETORNO_TESTE";

			}

			public interface SERVICO {


			}

		}
		public interface TST {
			public static final String MODO = "TST";
		}
		public interface PRD {
			public static final String MODO = "PRD";
		}
		public interface DEV {
			public static final String MODO = "DEV";
		}
		public interface HOM {
			public static final String MODO = "HOM";
		}
		public interface LOC {
			public static final String MODO = "LOC";
		}
	}

	public interface MAIL {

		public interface APPENDER {

			public static final String NOME = "GESTAO_MAIL_APPENDER";
		}

		public interface CABECALHO  {

			//Propriedade referente ao nome do destinatário
			final String DESTINATARIO_SUPORTE_NOME	= "Monitor Logs Gestão Carteiras";
			//Propriedade referente ao e-mail do destinatário
			final String DESTINATARIO_SUPORTE_EMAIL	= "rodrigo.santos@castgroup.com.br";

			//Propriedade referente ao nome do remetente
			final String REMETENTE_EMPRESA_NOME	= "Sistema Gestão de Carteiras";
			//Propriedade referente ao e-mail do remetente
			final String REMETENTE_EMPRESA_EMAIL	= "rodrigomagnoss@gmail.com";

			final String ASSUNTO_EMAIL	= "LOG $LOG_NIVEL$ - APLICACAO: $APLICACAO_SIGLA$";

		}

	}

	public interface LOGGER {


		public interface CATEGORIA {

			public static final String TESTE = "teste";

		}

		public interface APPENDER {

			public static final String NOME = "GESTAO_MAIL_LOG_APPENDER";
		}

		public interface LEVEL {

			public static final String INFO = "INFO";
			public static final String DEBUG = "DEBUG";
			public static final String ERROR = "ERROR";
			public static final String FATAL = "FATAL";
			public static final String WARN = "WARN";

		}


	}

	public interface ALTERACAO_ENTIDADE {

		public interface OPERADOR {

			static String INCLUIR = "+";
			static String EXCLUIR = "-";
			static String ALTERAR = "+-";

		}


	}

	
}