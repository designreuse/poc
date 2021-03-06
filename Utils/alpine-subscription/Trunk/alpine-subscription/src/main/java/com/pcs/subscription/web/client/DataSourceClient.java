/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.subscription.web.client;

import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.alpine.token.Token;
import com.pcs.alpine.token.TokenManager;
import com.pcs.subscription.web.dto.ErrorDTO;

/**
 * Galaxy Platform Client exposes rest client and helps to build
 * {@link DataSourceResponse}
 * 
 * @author pcseg296 RIYAS PH
 * @date October 2015
 * @since alpine-1.0.0
 * 
 */
@Service
public class DataSourceClient extends RestTemplate {

	@Autowired
	private DataSourceClientFactory restTemplateFactory;

	private ObjectMapper mapper = new ObjectMapper();

	/**
	 * Calls post method for a given api url and builds the
	 * {@link DataSourceResponse}
	 * 
	 * @param url
	 * @param request
	 * @param responseType
	 * @param uriVariables
	 * @return GalaxyPlatformResponse<T>
	 */
	public <T> DataSourceResponse<T> postResource(String url, Object request,
	        Class<T> responseType, Object... uriVariables) {
		return this.accessResource(url, request, responseType, HttpMethod.POST,
		        uriVariables);
	}

	public <T> DataSourceResponse<T> postResource(String url, Object request,
	        Class<T> responseType, MultiValueMap<String, String> headers,
	        Object... uriVariables) {
		return this.accessResource(url, request, responseType, HttpMethod.POST,
		        headers, uriVariables);
	}

	/**
	 * Calls GET methods for a given api url and builds the
	 * {@link DataSourceResponse}
	 * 
	 * @param url
	 * @param responseType
	 * @param Object
	 *            ... uriVariables
	 * @return GalaxyPlatformResponse<T>
	 */
	public <T> DataSourceResponse<T> getResource(String url,
	        Class<T> responseType, Object... uriVariables) {
		return this.accessResource(url, null, responseType, HttpMethod.GET,
		        uriVariables);

	}

	/**
	 * Calls DELETE methods for a given api url and builds the
	 * {@link DataSourceResponse}
	 * 
	 * @param url
	 * @param Object
	 *            ... uriVariables
	 * @return GalaxyPlatformResponse<T>
	 */
	public <T> DataSourceResponse<T> deleteResource(String url,
	        Class<T> responseType, Object... uriVariables) {
		return this.accessResource(url, null, responseType, HttpMethod.DELETE,
		        uriVariables);
	}

	/**
	 * Calls PUT methods for a given api url and builds the
	 * {@link DataSourceResponse}
	 * 
	 * @param url
	 * @param responseType
	 * @param Object
	 *            request
	 * @param Object
	 *            ... urlVariables
	 * @return GalaxyPlatformResponse<T>
	 */
	public <T> DataSourceResponse<T> putResource(String url, Object request,
	        Class<T> responseType, Object... urlVariables) {
		return this.accessResource(url, request, responseType, HttpMethod.PUT,
		        urlVariables);
	}

	/**
	 * Central method to access different HTTP methods (POST,GET,PUT,DELETE) The
	 * main entry point of GalaxyPlatformClient which extends RestTemplate
	 * builds the {@link DataSourceResponse}
	 * 
	 * @param url
	 * @param request
	 * @param responseType
	 * @param method
	 *            (POST,GET,PUT,DELETE)
	 * @param headers
	 * @param uriVariables
	 * @return GalaxyPlatformResponse<T>
	 */
	public <T> DataSourceResponse<T> accessResource(String url, Object request,
	        Class<T> responseType, HttpMethod method,
	        MultiValueMap<String, String> headers, Object... uriVariables) {

		headers = this.getHeaderParam();
		DataSourceResponse<T> response = new DataSourceResponse<T>();
		try {
			HttpEntity<Object> httpEntity = new HttpEntity<Object>(request,
			        headers);
			RequestCallback requestCallback = httpEntityCallback(httpEntity,
			        responseType);
			ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(responseType);
			ResponseEntity<T> apiResponse = this.execute(url, method,
			        requestCallback, responseExtractor, uriVariables);
			response.setHeaders(apiResponse.getHeaders());
			response.setEntity(apiResponse.getBody());
			response.setStatus(apiResponse.getStatusCode());
			logger.debug(apiResponse.toString());
		} catch (HttpClientErrorException e) {
			response.setStatus(e.getStatusCode());
			response.setErrorMessage(getErrorMessage(e));
			logger.error("error message", e);
		} catch (RestClientException e) {
			response.setStatus(INTERNAL_SERVER_ERROR);
			ErrorDTO error = new ErrorDTO();
			error.setErrorMessage("Server is not available");
			response.setErrorMessage(error);
			logger.error("Exception occured contacting platform api", e);
		}

		return response;
	}

	/**
	 * Delegate to
	 * {@link DataSourceClient#accessResource(String, Object, Class, HttpMethod, MultiValueMap, Object...)}
	 * without headers
	 * 
	 * @param url
	 * @param request
	 * @param responseType
	 * @param method
	 * @param uriVariables
	 * @return GalaxyPlatformResponse<T>
	 */
	public <T> DataSourceResponse<T> accessResource(String url, Object request,
	        Class<T> responseType, HttpMethod method, Object... uriVariables) {
		return this.accessResource(url, request, responseType, method, null,
		        uriVariables);
	}

	/**
	 * Build dto objects from json string
	 * 
	 * @param json
	 * @param clazz
	 * @return object of type clazz
	 */
	protected <T> T getEntity(String json, Class<T> clazz) {
		T object = null;
		if (isNotBlank(json) && clazz != null) {
			try {
				object = mapper.readValue(json, clazz);
			} catch (Exception e) {
				logger.error("Exception occured deserializing Entity : "
				        + clazz.getSimpleName() + " from api response", e);
			}
		}
		return object;
	}

	/**
	 * Builds {@link ErrorDTO} from the {@link HttpClientErrorException} as the
	 * exceptions from the api call returns the {@link ErrorDTO} along with the
	 * corresponding http response status code
	 * 
	 * @param httpClientErrorException
	 * @return errorMessageDTO
	 */
	protected ErrorDTO getErrorMessage(
	        HttpClientErrorException httpClientErrorException) {
		ErrorDTO errorMessageDTO = null;
		if (httpClientErrorException != null) {
			errorMessageDTO = getEntity(
			        httpClientErrorException.getResponseBodyAsString(),
			        ErrorDTO.class);
		}
		return errorMessageDTO;
	}

	private MultiValueMap getHeaderParam() {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		Token token = TokenManager.getToken(restTemplateFactory.getClientId(),
		        restTemplateFactory.getClientSecret());
		headers.set("Authorization", "Bearer " + token.getAccessToken());
		return headers;
	}
}
