package com.example.webflux.rxjava;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FlowableSample1 {
    public static void main(String[] args){
        Flowable.fromArray(1,2,3,4,5).subscribe(System.out::println);
        Flowable.just("a","b","c").observeOn(Schedulers.computation()).subscribe(System.out::println);
    }
}
