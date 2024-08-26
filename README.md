
	PROCEDIMENTO PARA CRIAR REPOSITÓRIO MAVEN NO GIT PARA MÓDULO GC-COMMONS

	
	A) PREPARAÇÃO DO AMBIENTE NO GIT (FEITO UMA VEZ)

		A.1) Criar novo repositório para fontes do módulo "gc-commons"

		A.2) Fazer o clone do branch "master" do repositório do módulo "gc-commons" para máquina local

		A.3) Copiar os arquivos do módulo "gc-commons" para pasta clonada do branch "master" e fazer commit/push para o Git.

		A.4) Criar no Git um branch com nome "maven" a partir do branch "master"

		A.5) Fazer o clone do branch "maven" do repositório do módulo "gc-commons" para máquina local

		A.6) Excluir os arquivos da pasta do módulo "gc-commons" do branch "maven" e fazer commit/push. No final a pasta do branch deve ficar vazia de início.
		
		A.7) Adicionar o endereço do branch "maven" como um repositório remoto no arquivo settings.xml, conforme trecho abaixo

                		<repository> 
                  			<id>MavenGit</id> 
                  			<name>Repositorio</name> 
                  			<url>https://github.com/<PROJETO>/<REPOSITORIO>/raw/maven/</url> 
                  			<releases> 
                    				<enabled>true</enabled> 
                  			</releases> 
                  			<snapshots> 
                    				<enabled>true</enabled> 
					<updatePolicy>interval:12000000</updatePolicy>
                  			</snapshots> 
                		</repository> 
		

	B) PROCESSO CONTÍNUO PARA ATUALIZAR VERSÃO  (FEITO A CADA NOVA VERSÃO)

		B.1) Abrir prompt de comando local na pasta do branch "master" e na pasta do módulo "gc-commons" e executar o comando abaixo para constuir o arquivo jar da nova versão do módulo "gc-commons"
	    	ATENÇÃO: informar a versão o módulo na propriedade "-Dcommons.version"
		
			mvn package -Dcommons.version=1.5.0

		B.2) Abrir prompt de comando local na pasta do branch "maven" e executar o comando abaixo para geração dos arquivos da nova versão a serem enviados para o Git
	    	ATENÇÃO: informar a versão o módulo na propriedade "-Dcommons.version"

			mvn install:install-file -Dcommons.version=1.5.0 -DgroupId=br.com.unimed -DartifactId=gc-commons -Dversion=${commons.version} -Dfile=..\master\gc-commons\target\gc-commons-${commons.version}.jar -Dpackaging=jar -DlocalRepositoryPath=. -DcreateChecksum=true -DgeneratePom=true

		B.3) Abrir prompt de comando local na pasta do branch "maven" e executar os comandos abaixo para adicionar e fazer o "commit" dos arquivos módulo "gc-commons" para o branch "maven" local

			git add .
			git commit -m "Versão 1.5.0"
	
		B.4) Abrir prompt de comando local na pasta do branch "maven" e executar o comandos abaixo para fazer o "push" dos arquivos do módulo "gc-commons" para o branch "maven" remoto

			git push origin maven
