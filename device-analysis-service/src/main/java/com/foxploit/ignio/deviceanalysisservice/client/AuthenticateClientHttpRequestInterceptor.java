package com.foxploit.ignio.deviceanalysisservice.client;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@Configuration
public class AuthenticateClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    /**
     * Generate token headers for requests made using restTemplate Bean created in {@link com.foxploit.ignio.deviceanalysisservice.DeviceAnalysisServiceApp} class
     *
     * @apiNote Change "token" value accordingly "if not valid"
     *
     * @param httpRequest
     * @param bytes
     * @param clientHttpRequestExecution
     * @return ClientHttpResponse
     * @throws IOException
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTU3MDc3OTg1Nn0.AlyWGAzdk6WRN0_YCNFNzYzGhYcffaaUdHD6_kKa8L15kW2PPTNnQ_hfmQT81eh8BrNlQjgo8hAKcXs52QCHVQ";
        httpRequest.getHeaders().add("Authorization","Bearer "+token);
        return clientHttpRequestExecution.execute( httpRequest, bytes );
    }

}
