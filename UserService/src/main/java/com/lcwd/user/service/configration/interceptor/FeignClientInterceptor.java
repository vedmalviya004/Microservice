package com.lcwd.user.service.configration.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;


@Component
public class FeignClientInterceptor implements RequestInterceptor {

    @Autowired
    private OAuth2AuthorizedClientManager manager;



@Override
public void apply(RequestTemplate requestTemplate) {
    OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest
            .withClientRegistrationId("my-internal-client")
            .principal("internal") // required for client_credentials
            .build();

    OAuth2AuthorizedClient client = manager.authorize(request);

    if (client == null) {
        throw new IllegalStateException("OAuth2 authorization failed — client is null");
    }

    String token = client.getAccessToken().getTokenValue();
    requestTemplate.header("Authorization", "Bearer " + token);
}
}






