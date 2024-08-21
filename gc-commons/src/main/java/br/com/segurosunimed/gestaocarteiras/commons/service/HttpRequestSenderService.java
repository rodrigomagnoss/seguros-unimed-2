package br.com.segurosunimed.gestaocarteiras.commons.service;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.segurosunimed.gestaocarteiras.commons.dto.BaseDTO;
import br.com.segurosunimed.gestaocarteiras.commons.dto.response.ErrorDTO;
import br.com.segurosunimed.gestaocarteiras.commons.exception.BusinessException;
import br.com.segurosunimed.gestaocarteiras.commons.exception.CustomInternalServerErrorException;

public class HttpRequestSenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestSenderService.class);

//    @Value("${gc.microservices.core.username}")
    private String username;

//    @Value("${gc.microservices.core.password}")
    private String password;

    
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    /**
     * Método para envio de requisição GET
     * 
     * @param <T> Classe do artefato de retorno
     * @param urlReq Url para requisição
     * @param objRequest Instância genérica de referência do objeto de retorno
     * 
     * @return Instância de referência com dados de retorno
     */
    public  <T> T sendRequest(String urlReq, Object objRequest) {
    	
        LOGGER.info("Chamando URL: {}", urlReq);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<String> response;
        
        urlReq = !StringUtils.contains(urlReq, "http://") ? "http://" + urlReq : urlReq; 
        
        try {
            response = restTemplate.exchange(
                    urlReq,
                    HttpMethod.GET,
                    requestEntity,
                    String.class
            );
            if (response.getStatusCode() == HttpStatus.OK) {
                if (objRequest instanceof TypeReference) {
                    return (T) mapper.readValue(response.getBody(), (TypeReference) objRequest);
                }
                return mapper.readValue(response.getBody(), (Class<T>) objRequest);
            } else {
                LOGGER.error("Erro ao chamar core-bus | StatusCode: {} | Response Body {}", response.getStatusCodeValue(), response.getBody());
                throw new CustomInternalServerErrorException(new ErrorDTO("internal_server_error", "internal.server.error"));
            }
        } catch (HttpClientErrorException e) {
            LOGGER.error("Não foi possível chamar o core-bus", e);
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                try {
                    ErrorDTO errorDTO = new ErrorDTO();
                    errorDTO.setMessage(mapper.readValue(e.getResponseBodyAsString(), ErrorDTO.class).getMessage());
                    errorDTO.setErrorType("bad_request");
                    throw new BusinessException(errorDTO);
                } catch (IOException ex) {
                    LOGGER.error("Erro ao parsear resposta", ex);
                    throw new CustomInternalServerErrorException(new ErrorDTO("internal_server_error", "internal.server.error"));
                }
            } else {
                throw new CustomInternalServerErrorException(new ErrorDTO("internal_server_error", "internal.server.error"));
            }
        } catch (Exception e) {
            LOGGER.error("Não foi possível chamar o core-bus", e);
            throw new CustomInternalServerErrorException(new ErrorDTO("internal_server_error", "internal.server.error"));
        }
    }

    /**
     * Método para envio de requisição GET
     *
     * @param <T> Classe do artefato de retorno
     * @param urlReq Url para requisição
     * @param objRequest Instância genérica de referência do objeto de retorno
     *
     * @return Instância de referência com dados de retorno
     */
    public  <T> T sendRequest(String urlReq, Object objRequest, HttpHeaders headers) {
        
        LOGGER.info("Chamando URL: {}", urlReq);
        
        if(headers == null) {
        	headers = new HttpHeaders();
        }
        headers.setBasicAuth(username, password);
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<String> response;
        
        urlReq = !StringUtils.contains(urlReq, "http://") ? "http://" + urlReq : urlReq;
        
        try {
            response = restTemplate.exchange(
                    urlReq,
                    HttpMethod.GET,
                    requestEntity,
                    String.class
                    );
            if (response.getStatusCode() == HttpStatus.OK) {
                if (objRequest instanceof TypeReference) {
                    return (T) mapper.readValue(response.getBody(), (TypeReference) objRequest);
                }
                return mapper.readValue(response.getBody(), (Class<T>) objRequest);
            } else {
                LOGGER.error("Erro ao chamar endereço de servico | StatusCode: {} | Response Body {}", response.getStatusCodeValue(), response.getBody());
                throw new CustomInternalServerErrorException(new ErrorDTO("internal_server_error", "internal.server.error"));
            }
        } catch (HttpClientErrorException e) {
            LOGGER.error("Não foi possível chamar endereço de servico", e);
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                try {
                    ErrorDTO errorDTO = new ErrorDTO();
                    errorDTO.setMessage(mapper.readValue(e.getResponseBodyAsString(), ErrorDTO.class).getMessage());
                    errorDTO.setErrorType("bad_request");
                    throw new BusinessException(errorDTO);
                } catch (IOException ex) {
                    LOGGER.error("Erro ao parsear resposta", ex);
                    throw new CustomInternalServerErrorException(new ErrorDTO("internal_server_error", "internal.server.error"));
                }
            } else {
                throw new CustomInternalServerErrorException(new ErrorDTO("internal_server_error", "internal.server.error"));
            }
        } catch (Exception e) {
            LOGGER.error("Não foi possível chamar endereço de servico", e);
            throw new CustomInternalServerErrorException(new ErrorDTO("internal_server_error", "internal.server.error"));
        }
    }
    
    /**
     * Método para envio de requisição POST
     * 
     * @param <T> Classe do artefato de retorno
     * @param baseDTO Instância do artefato com dados para envio no post
     * @param urlReq Url para requisição
     * @param objRequest Instância genérica de referência do objeto de retorno
     * 
     * @return Instância de referência com dados de retorno
     */
    public  <T> T sendRequest(String urlRequest, Object objResponse, BaseDTO baseDTO) {
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        return sendRequest(urlRequest, objResponse, baseDTO, headers);
    }

    /**
     * Método para envio de requisição POST
     * 
     * @param <T> Classe do artefato de retorno
     * @param baseDTO Instância do artefato com dados para envio no post
     * @param urlReq Url para requisição
     * @param objRequest Instância genérica de referência do objeto de retorno
     * 
     * @return Instância de referência com dados de retorno
     */
    public  <T> T sendRequest(String urlRequestl, Object objResponse, BaseDTO baseDTO, HttpHeaders headers ) {
    	
    	return sendRequest(urlRequestl, objResponse, baseDTO, headers, HttpMethod.POST);
    }

    /**
     * Método para envio de requisição
     * 
     * @param <T> Classe do artefato de retorno
     * @param baseDTO Instância do artefato com dados para envio no post
     * @param urlReq Url para requisição
     * @param objRequest Instância genérica de referência do objeto de retorno
     * 
     * @return Instância de referência com dados de retorno
     */
    public  <T> T sendRequest(String urlRequestl, Object objResponse, BaseDTO baseDTO, HttpHeaders headers, HttpMethod httpMethod) {

    	LOGGER.info("Chamando URL: {}", urlRequestl);
        
        ResponseEntity<String> response;
        
        try {

        	headers.add(headers.ACCEPT_ENCODING, Charsets.UTF_8.name());

            ObjectMapper objMapper = new ObjectMapper();
            objMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
            objMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);           
            String requestBody = objMapper.writeValueAsString(baseDTO);
            LOGGER.info(objMapper.writeValueAsString(objMapper.readTree(requestBody)));
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        	urlRequestl = !StringUtils.contains(urlRequestl, "http://") ? "http://" + urlRequestl : urlRequestl; 
        	
        	restTemplate.
        		getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        	
            response = restTemplate.exchange(
                    urlRequestl,
                    (httpMethod != null ? httpMethod : HttpMethod.POST),
                    requestEntity,
                    String.class
            );
            if (response.getStatusCode() == HttpStatus.OK) {
                if (objResponse instanceof TypeReference) {
                    return (T) mapper.readValue(response.getBody() , (TypeReference) objResponse);
                }
                return mapper.readValue(response.getBody(), (Class<T>) objResponse);
            } else {
                LOGGER.error("Erro ao chamar endereco de servico| StatusCode: {} | Response Body {}", response.getStatusCodeValue(), response.getBody());
                throw new CustomInternalServerErrorException(new ErrorDTO("internal_server_error", "Erro ao chamar endereco de servico| StatusCode: {} | Response Body {}"));
            }
        } catch (HttpClientErrorException e) {
            LOGGER.error("Não foi possível chamar endereco de servico", e);
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                try {
                    ErrorDTO errorDTO = new ErrorDTO();
                    errorDTO.setMessage(mapper.readValue(e.getResponseBodyAsString(), ErrorDTO.class).getMessage());
                    errorDTO.setErrorType("bad_request");
                    throw new BusinessException(errorDTO);
                } catch (IOException ex) {
                    LOGGER.error("Erro ao parsear resposta", ex);
                    throw new CustomInternalServerErrorException(new ErrorDTO("internal_server_error", "internal.server.error"));
                }
            } else {
                throw new CustomInternalServerErrorException(new ErrorDTO("internal_server_error", "internal.server.error"));
            }
        } catch (Exception e) {
        	LOGGER.error("Não foi possível chamar o endereco servico", e);
            throw new CustomInternalServerErrorException(new ErrorDTO("internal_server_error", StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "internal.server.error", e));
        }
    }
    
}
