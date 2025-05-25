package com.watersupply.Notification.component;

import feign.RequestInterceptor;
import feign.RequestTemplate;



public class TokenRelayInterceptor implements RequestInterceptor {

    private String token;

    public TokenRelayInterceptor(String token)
    {
        this.token=token;
    }

    @Override
    public void apply(RequestTemplate template) {
         if (token != null && !token.isBlank()) {
        String cleanedToken = token.trim();
        if (!cleanedToken.startsWith("Bearer ")) {
            cleanedToken = "Bearer " + cleanedToken;
        }
        template.header("Authorization", cleanedToken);
        System.out.println("Sending token: [" + token + "]");

    }
    }

   

}
