package com.example.webflux.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ReactorSample {

    public static void main(String[] args){
        Mono.just("hello").subscribe(v -> System.out.println(v));
        Flux.range(1,100).subscribe(System.out::println);
    }

}
