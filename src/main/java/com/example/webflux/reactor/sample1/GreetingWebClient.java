package com.example.webflux.reactor.sample1;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class GreetingWebClient {

    public static void main(String[] args){
        WebClient webClient =  WebClient.create("http://localhost:8080");
        String result = webClient.get()
                .uri("/hello")
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class)).block();
        System.out.println("Result>>"+result);
    }
}
