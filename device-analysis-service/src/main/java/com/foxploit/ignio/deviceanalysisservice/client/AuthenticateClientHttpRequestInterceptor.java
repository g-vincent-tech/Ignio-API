package com.foxploit.ignio.deviceanalysisservice.client;

import com.foxploit.ignio.deviceanalysisservice.DeviceanalysisserviceApp;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@Configuration
public class AuthenticateClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    /**
     * Generate token headers for requests made using restTemplate Bean created in {@link DeviceanalysisserviceApp} class
     *
     * @param httpRequest
     * @param bytes
     * @param clientHttpRequestExecution
     * @return ClientHttpResponse
     * @throws IOException
     * @apiNote Change "token" value accordingly "if not valid"
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTU3NzMzMDU5Nn0.gf8ymdFN2WpFkUp_tAa28mzdwuhEuYe4jNULg-KBdOjVkoi_JVvMcF4s9Xd_aiFHWYC-lUIkoMixv2uQJj7-SA";
        httpRequest.getHeaders().add("Authorization", "Bearer " + token);
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }

}
