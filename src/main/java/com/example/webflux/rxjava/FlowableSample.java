package com.example.webflux.rxjava;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class FlowableSample {
    public static void main(String[] args){
        List squares = new ArrayList();
        Flowable.range(1,64)
                .observeOn(Schedulers.computation())
                .map(v -> v*v)
                .blockingSubscribe(squares::add);
        System.out.println(squares);
    }
}
