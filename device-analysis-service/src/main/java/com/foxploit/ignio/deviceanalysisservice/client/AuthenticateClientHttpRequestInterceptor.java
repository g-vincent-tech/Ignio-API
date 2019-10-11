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
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTU3MzM3MjE3NX0.-ggtAchgzPVdO4yMaWwkjwLKth3U0nvFMCmxW45h3H2j0CCeF3zN47u_ociWuEi95stmZLVKN3C33WBqjard4w";
        httpRequest.getHeaders().add("Authorization","Bearer "+token);
        return clientHttpRequestExecution.execute( httpRequest, bytes );
    }

}
