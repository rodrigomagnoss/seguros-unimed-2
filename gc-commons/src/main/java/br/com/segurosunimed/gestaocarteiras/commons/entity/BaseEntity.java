package br.com.segurosunimed.gestaocarteiras.commons.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.collection.internal.PersistentBag;
import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.segurosunimed.gestaocarteiras.commons.annotation.IgnorarAoComparar;
import br.com.segurosunimed.gestaocarteiras.commons.annotation.IgnorarAoCopiar;
import br.com.segurosunimed.gestaocarteiras.commons.constants.GcConstantes.ALTERACAO_ENTIDADE;
import br.com.segurosunimed.gestaocarteiras.commons.constants.KeyConstantes;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseEntity {

	@Transient
	//Contém nome do converter para gravação de log
	private String nomeConverter;

	@Transient
	/**
	 * Informa explicitamente o operador da alteração da Entidade, quando necessário
	 * Valore possíveis:
	 * "+" = inclusão da entidade 
	 * "-" = exclusão da entidade 
	 * "+-" = alteração de dados da entidade 
	 */
	private String operadorAlteracao = "";

	public void copyProperties(Object destino) {

		String[] propriedadesIgnorar = new String[] {};
		try {
			for (Field field : this.getClass().getDeclaredFields()) {
				if(field.getAnnotation(IgnorarAoCopiar.class) != null) {
					propriedadesIgnorar = ArrayUtils.add(propriedadesIgnorar, field.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BeanUtils.copyProperties(this, destino, propriedadesIgnorar);
	}

	public  List<String> getDifference(Object s2) throws IllegalAccessException {
		List<String> values = new ArrayList<>();
		for (Field field : this.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			Object value1 = field.get(this);
			Object value2 = field.get(s2);
			if (value1 != null && value2 != null) {
				if (!Objects.equals(value1, value2)) {
					values.add(String.valueOf(field.getName()+": "+value1+" -> "+value2));
				}
			}
		}
		return values;
	}
	
	public Map<String, Object> getDeepDifferenceAsMap(Object s2) throws Exception {

		Map<String, Object> alteracoes = new HashMap();
		Map<String, Object> associados = new HashMap();
		Map<String, Object> propriedades = new HashMap();
		MultiValuedMap<String, Object> detalhes = new ArrayListValuedHashMap();
		
		alteracoes.put(KeyConstantes.CHAVE_PROPRIEDADES, propriedades);
		alteracoes.put(KeyConstantes.CHAVE_DETALHES, detalhes);
		alteracoes.put(KeyConstantes.CHAVE_ASSOCIADOS, associados);
		
		getDeepDifference(0, s2, false, false, alteracoes, KeyConstantes.CHAVE_PROPRIEDADES,"");

		ObjectMapper objMapper = new ObjectMapper();
        objMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
        objMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);        

        alteracoes.put(KeyConstantes.CHAVE_DETALHES, ((MultiValuedMap<String, Object>)alteracoes.get(KeyConstantes.CHAVE_DETALHES)).asMap());

        return alteracoes;
	}


	//METODOS ACIMA ERAM OS ORIGINAIS.
	public String getDeepDifferenceAsText(Object s2) throws Exception {
	
		Map<String, Object> alteracoes = getDeepDifferenceAsMap(s2);
		
		ObjectMapper objMapper = new ObjectMapper();
        objMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
        objMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);        

        String json = "";
        
        if( !((Map<String, Object>) alteracoes.get(KeyConstantes.CHAVE_PROPRIEDADES)).isEmpty() || 
        	!((Map<String, Object>) alteracoes.get(KeyConstantes.CHAVE_ASSOCIADOS)).isEmpty() || 
        	!((Map<String, Object>) alteracoes.get(KeyConstantes.CHAVE_DETALHES)).isEmpty()) {
        
			json = objMapper.writeValueAsString(alteracoes); 
			
			System.out.println(json);

        }else {
    		System.out.println("NENHUMA ALTERACAO IDENTIFICADA");
        }

		return json;
	}

	public MultiValuedMap<String, Object> preencherMapaDetalhes(
			MultiValuedMap<String, Object> mapaInclusao,
			MultiValuedMap<String, Object> mapaExclusao, 
			String chaveDetalhe, String chavePropriedade
			) {
		
		MultiValuedMap<String, Object> novoMapaDetalhe = new ArrayListValuedHashMap();
		
		Collection colI = (Collection) mapaInclusao.get(chaveDetalhe);
		Collection colE = (Collection) mapaExclusao.get(chaveDetalhe);
		for (Iterator iteratorI = colI.iterator(); iteratorI.hasNext();) {
			Map mapaI = (Map) iteratorI.next();
			String keyI = (String) mapaI.keySet().toArray()[0];
			String nomeI = (String) mapaI.get(keyI);
			boolean alterou = false;
			if(mapaExclusao != null && !mapaExclusao.isEmpty()) {
				for (Iterator iteratorE = colE.iterator(); iteratorE.hasNext();) {
					Map mapaE = (Map) (HashMap) iteratorE.next();
					String keyE = (String) mapaE.keySet().toArray()[0];
					String nomeE = (String) mapaE.get(keyE);
					if(mapaE.containsKey(keyI)) {
						nomeE = (String) mapaE.get(keyI);
						//Alteração
						novoMapaDetalhe.put("["+ALTERACAO_ENTIDADE.OPERADOR.ALTERAR+"]"+chavePropriedade, nomeE + " -> "+ nomeI);
						iteratorI.remove();
						iteratorE.remove();
						alterou = true;
						break;
					}
				}
			}
			if(!alterou) {
				//Inclusão
				novoMapaDetalhe.put("["+ALTERACAO_ENTIDADE.OPERADOR.INCLUIR+"]"+chavePropriedade, nomeI);
				iteratorI.remove();
			}
		}
		if(mapaExclusao != null && !mapaExclusao.isEmpty()) {
			for (Iterator iteratorE = colE.iterator(); iteratorE.hasNext();) {
				Map mapaE = (Map) (HashMap) iteratorE.next();
				String keyE = (String) mapaE.keySet().toArray()[0];
				String nomeE = (String) mapaE.get(keyE);
				//Exclusão
				novoMapaDetalhe.put("["+ALTERACAO_ENTIDADE.OPERADOR.EXCLUIR+"]"+chavePropriedade, nomeE);
				iteratorE.remove();
			}
		}
		
		return novoMapaDetalhe;
	}
	
	public void getDeepDifference(int deep, Object objComparado, boolean identar, boolean add, Object mapaAlteracoes, String nomePropAlteracao, String oper) throws IllegalAccessException {

		//Adiciona operador para o atributo comparado
		nomePropAlteracao = StringUtils.isNotBlank(oper) ? "["+oper+"]" + nomePropAlteracao : nomePropAlteracao;
		
		if(++deep >= 4) {
			return;
		}

		Field[] declaredFields = this.getClass().getDeclaredFields();

		String nomeObjComparado = objComparado != null ? objComparado.getClass().getSimpleName() : "OBJETO";
		try {
			nomeObjComparado = nomeObjComparado.replace("Entity", "");
			nomeObjComparado = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(StringUtils.capitalize(nomeObjComparado)),' ');
		} catch (Exception e) {

		}


		for (Field field : declaredFields) {			

			if(field.getAnnotation(Transient.class) != null) {
				continue;
			}

			field.setAccessible(true);
			String fieldName = field.getName();

			try {
				fieldName = fieldName.replace("Entity", "");
				fieldName = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(StringUtils.capitalize(fieldName)),' ');
			} catch (Exception e) {
				fieldName = field.getName();
			}

			if(fieldName.toLowerCase().contains("audit")) {
				continue;
			}
//			if(fieldName.toLowerCase().equals("id") && add) {
//				continue;
//			}

			if(field.getAnnotation(IgnorarAoComparar.class) != null) {
				continue;
			}

			Object valueCompare1 = field.get(this);
			Object valueCompare2 = objComparado != null ? field.get(objComparado) : null;
			if(valueCompare1 != null && valueCompare2 == null && objComparado != null && objComparado.getClass().getCanonicalName().contains("Entity")) {
				try {
					valueCompare2 = objComparado.getClass().getMethod("get"+StringUtils.capitalize(field.getName())).invoke(objComparado);
				} catch (Exception e) {
					//Nenhuma exceção será disparada
				} 
			}

			if (valueCompare1 != null && valueCompare2 != null) {
				if (!Objects.deepEquals(valueCompare1, valueCompare2)) {
					if(field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(OneToOne.class)) {
						//ASSOCIAÇÕES
						oper = ((BaseEntity) valueCompare2).getOperadorAlteracao();
						((BaseEntity)valueCompare1).getDeepDifference(deep, valueCompare2, identar, false, getMapaAlteracao(mapaAlteracoes, KeyConstantes.CHAVE_ASSOCIADOS, ""), getFieldName(fieldName),oper);
					}else if(field.isAnnotationPresent(OneToMany.class)) {
						//DETALHES
						Map mapaDetails = new HashMap();
						Iterator iteratorList = valueCompare1.getClass().isAssignableFrom(PersistentBag.class) ? 
								((PersistentBag)valueCompare1).iterator() : ((ArrayList)valueCompare1).iterator(); 
						for (Iterator iterator = iteratorList; iterator.hasNext();) {
							Object persistentBag = iterator.next();
							try {
								Long id = (Long) persistentBag.getClass()
										.getMethod("getId")
										.invoke(persistentBag);

								mapaDetails.put(id, persistentBag);
							} catch (Exception e) {
								e.printStackTrace();
							}						
						}

						int cont = 0;
						for (Iterator iterator = ((ArrayList)valueCompare2).iterator(); iterator.hasNext();) {
							Object objEntity2 = iterator.next();
							try {
								Long id2 = (Long) objEntity2.getClass()
										.getMethod("getId")
										.invoke(objEntity2);

								Object newInstanceObjEntity2 = objEntity2.getClass().newInstance();

								if(mapaDetails.containsKey(id2)) {
									//IGUAIS
									mapaDetails.remove(id2);
									iterator.remove();
								}else if (id2 != null){
									//INCLUSÃO
									if(id2 < 0) {
										objEntity2.getClass()
										.getMethod("setId", Long.class)
										.invoke(objEntity2, 0L);
									}
									((BaseEntity)objEntity2).getDeepDifference(deep, newInstanceObjEntity2, identar, false, getMapaAlteracao(mapaAlteracoes, KeyConstantes.CHAVE_DETALHES, ""), "(" +(++cont) + ")"+ getFieldName(fieldName),"+");

								}
								else if (id2 == null){
									//INCLUSÃO
									((BaseEntity)objEntity2).getDeepDifference(deep, newInstanceObjEntity2, identar, false, getMapaAlteracao(mapaAlteracoes, KeyConstantes.CHAVE_DETALHES, ""), "(" +(++cont) + ")"+ getFieldName(fieldName),"+");																		
								}
							} catch (Exception e) {				
								e.printStackTrace();
							}						
						}
						Set mapKeys = mapaDetails.keySet();
						
						for (Iterator iter = mapKeys.iterator(); iter.hasNext();) {
							Long id1 = (Long) iter.next();
							if(id1 != null) {
								//EXCLUSÃO
								Object objEntity3 = mapaDetails.get(id1);

								try {
									Object newInstanceObjEntity3 = objEntity3.getClass().newInstance();
									((BaseEntity)objEntity3).getDeepDifference(deep, newInstanceObjEntity3, identar, false, getMapaAlteracao(mapaAlteracoes, KeyConstantes.CHAVE_DETALHES, ""), "(" +id1 + ")"+ getFieldName(fieldName),"-");
								} catch (Exception e) {										
									e.printStackTrace();
								}						
							}
						}
					}
					else {												
						putAlteracao(mapaAlteracoes, nomePropAlteracao, "", fieldName, valueCompare1 + " -> " + valueCompare2);
						
					}
				}
			}
			else if (valueCompare1 != null && valueCompare2 == null) {
				if (!Objects.equals(valueCompare1, valueCompare2)) {
					if(field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(OneToOne.class)) {
						if(deep < 2) {
							((BaseEntity)valueCompare1).getDeepDifference(deep, valueCompare2, identar, true, mapaAlteracoes, KeyConstantes.CHAVE_ASSOCIADOS,"");
						}else if(deep <= 2) {
							((BaseEntity)valueCompare1).getDeepDifference(deep, valueCompare2, identar, true, getMapaAlteracao(mapaAlteracoes, nomePropAlteracao, ""), getFieldName(fieldName),"yyy");						
						}
					}else if(field.isAnnotationPresent(OneToMany.class)) {
						for (Iterator iterator = ((PersistentBag)valueCompare1).iterator(); iterator.hasNext();) {
							Object objEntity1 = iterator.next();
							try {
								Object newInstanceObjEntity1 = objEntity1.getClass().newInstance();
								((BaseEntity)objEntity1).getDeepDifference(deep, newInstanceObjEntity1, identar, true, mapaAlteracoes, KeyConstantes.CHAVE_DETALHES,"");
							} catch (Exception e) {
								putAlteracao(mapaAlteracoes, nomePropAlteracao, nomeObjComparado, fieldName, valueCompare1 + " -> " + valueCompare2);
							}						
						}
					}
					else {
						putAlteracao(mapaAlteracoes, nomePropAlteracao, "", fieldName, valueCompare1);
					}
				}
			}
		
		}
	}

	@Deprecated
	private Map<String, Object> getMapaAlteracaoOld(Map<String, Object> mapaAlteracoes, String nomePropAlteracao, String fieldName) {
		
		try {
			Map<String, Object> mapaAlteracao = (Map<String, Object>) mapaAlteracoes.get(nomePropAlteracao);
			if(StringUtils.isNoneBlank(fieldName)) {
				Map<String, Object> mapaAD = mapaAlteracao != null ? (Map<String, Object>) mapaAlteracao.get(fieldName) : null;
				if(mapaAD != null) {
					return mapaAD;
				}
				return new HashMap<String, Object>();
			}
			return mapaAlteracao;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapaAlteracoes;
	}

	private Object getMapaAlteracao(Object mapaAlteracoes, String nomePropAlteracao, String nomePropAD) {
		
		try {
			Map<String, Object> mapaAlteracao = (Map<String, Object>) ((Map<String, Object>) mapaAlteracoes).get(nomePropAlteracao);
			if(StringUtils.isNoneBlank(nomePropAD)) {
				Map<String, Object> mapaAD = mapaAlteracao != null ? (Map<String, Object>) mapaAlteracao.get(nomePropAD) : null;
				if(mapaAD == null) {
					mapaAD = new HashMap<String, Object>();
				}
				return mapaAD;
			}
			return mapaAlteracao;
		} catch (Exception e) {
			try {
				if(((Map<String, Object>) mapaAlteracoes).containsKey(nomePropAlteracao)){
					MultiValuedMap<String, Object> multiMapaAlteracao = (MultiValuedMap<String, Object>) ((Map<String, Object>) mapaAlteracoes).get(nomePropAlteracao);
					return multiMapaAlteracao;
				}
			} catch (Exception e2) {
				try {
					MultiValuedMap<String, Object> multiMapaAlteracao = (MultiValuedMap<String, Object>) mapaAlteracoes;
					Collection colAlteracao = (Collection) multiMapaAlteracao.get(nomePropAlteracao);
					Object[] objAlteracao = colAlteracao.toArray();
					if(objAlteracao != null && objAlteracao.length > 0) {
						HashMap mapaAlteracao = (HashMap) objAlteracao[objAlteracao.length - 1];
						if(StringUtils.isNotBlank(nomePropAD)) {
							if(mapaAlteracao.containsKey(nomePropAD)){
								Map<String, Object> mapaAD = mapaAlteracao != null ? (Map<String, Object>) mapaAlteracao.get(nomePropAD) : null;
								if(mapaAD == null) {
									mapaAD = new HashMap<String, Object>();
								}
								return mapaAD;
							}
							return new HashMap<String, Object>();
						}
						return mapaAlteracao;
					}
					return new ArrayListValuedHashMap<String, Object>();
				} catch (Exception e3) {
					e.printStackTrace();
				}	
			}
		}
		return null;
	}

	private void putAlteracao(Object mapaAlteracoes, String nomePropAlteracao, String nomePropAD, String key, Object value) {
		
		try {
			Map<String, Object> mapaAlteracao = (Map<String, Object>) ((Map<String, Object>) mapaAlteracoes).get(nomePropAlteracao);
			if(StringUtils.isNotBlank(nomePropAD)) {
				Map<String, Object> mapaAD = mapaAlteracao != null ? (Map<String, Object>) mapaAlteracao.get(nomePropAD) : null;
				if(mapaAD == null) {
					mapaAD = new HashMap<String, Object>();
				}
				mapaAD.put(key, value); 
			}else {
				if(mapaAlteracao == null) {
					mapaAlteracao = new HashMap<String, Object>();
				}
				mapaAlteracao.put(key, value);
				if(!((Map<String, Object>) mapaAlteracoes).containsKey(nomePropAlteracao)) {
					((Map<String, Object>) mapaAlteracoes).put(nomePropAlteracao, mapaAlteracao);
				}
			}
		} catch (Exception e) {
			try {
				MultiValuedMap<String, Object> multiMapaAlteracao = (MultiValuedMap<String, Object>) mapaAlteracoes;
				HashMap mapaAlteracao = null;
				if(multiMapaAlteracao.containsKey(nomePropAlteracao)){
					Collection colAlteracao = (Collection) multiMapaAlteracao.get(nomePropAlteracao);
					Object[] objAlteracao = colAlteracao.toArray();
					mapaAlteracao = (HashMap) objAlteracao[objAlteracao.length - 1];
					mapaAlteracao.put(key,  value);
				}else {
					mapaAlteracao = new HashMap<String, Object>();
					if(value != null && !value.toString().equals("0")) {
						mapaAlteracao.put(key,  value);
					}
					multiMapaAlteracao.put(nomePropAlteracao, mapaAlteracao);
				}
			} catch (Exception e2) {
				e.printStackTrace();
			}
		}
	}


	private String getFieldName(String fieldName) {

		return getFieldName(fieldName, "");
	}

	private String getFieldName(String fieldName, String operator) {
		
		return ""+operator+fieldName+"";
	}

}
