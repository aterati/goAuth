/**
 * 
 */
package com.rv.goAuth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;

import com.nimbusds.oauth2.sdk.http.HTTPResponse;

/**
 * Login controller class to define path mappings
 * 	and handler methods.
 * 
 * @author aterati
 *
 */
@Controller
public class LoginController {
	
	
	@Autowired
	OAuth2AuthorizedClientService authorizedClientService;
	
	Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
	String authorizationRequestBaseUri = "oauth2/authorization";
	
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/loginSuccess")
	public String loginSuccess(Model model, OAuth2AuthenticationToken oauthToken) {
		
		OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(), 
				oauthToken.getName());
		
		String endPointUri = client.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
		
		if(!StringUtils.isEmpty(endPointUri)) {
			
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders header = new HttpHeaders();
			header.add(HttpHeaders.AUTHORIZATION, "Bearer" + client.getAccessToken().getTokenValue());
			HttpEntity entity = new HttpEntity("", header);
			ResponseEntity<Map> response = restTemplate.exchange(endPointUri, HttpMethod.GET, entity, Map.class);
			Map userAttributes = response.getBody();
			model.addAttribute("name", userAttributes.get("name"));
		}
		
		return "homePage";
	}
	
	
	/*
	 * @Autowired
	 * ClientRegistrationRepository clientRegistrationRepository;
	 * Iterable<ClientRegistration> clientRegistrations = null; ResolvableType type
	 * = ResolvableType.forInstance(clientRegistrationRepository)
	 * .as(Iterable.class);
	 * 
	 * if (type != ResolvableType.NONE &&
	 * ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
	 * clientRegistrations = (Iterable<ClientRegistration>)
	 * clientRegistrationRepository; }
	 * 
	 * clientRegistrations.forEach(registration ->
	 * oauth2AuthenticationUrls.put(registration.getClientName(),
	 * authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
	 *
	 * @GetMapping({"/","/login"}) public String homePage(Model m) {
	 * 
	 * m.addAttribute("url", ""); return "login"; }
	 */

	
}
