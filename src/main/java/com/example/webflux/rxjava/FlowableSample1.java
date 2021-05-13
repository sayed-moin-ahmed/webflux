package com.example.webflux.rxjava;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FlowableSample1 {
    public static void main(String[] args) {
        Flowable.fromArray(1, 2, 3, 4, 5).subscribe(System.out::println);//Array
        Flowable.just("a", "b", "c").observeOn(Schedulers.computation()).map(String::intern).subscribe(System.out::println).dispose();//Thread
        Observable.just("Hello reactive World!").subscribe(System.out::println); // Just generate the data
        Observable.just("Hello World").subscribe(System.out::println, RuntimeException::new); // exception handling
        Observable.just("Hello World", "reactive!!!").subscribe(new CustomObserver<>()); // custom observer
        Observable.range(1, 10) // use of range filter and map
                .filter(value -> value % 2 == 0)
                .map(value -> 2 * value).subscribe(new CustomObserver<>());
        Observable.interval(1, TimeUnit.SECONDS)  //interval
                .take(5)
                .blockingSubscribe(new CustomObserver());
        Observable.interval(1, TimeUnit.SECONDS)  //interval
                .observeOn(Schedulers.io())
                .take(5)
                .blockingSubscribe(new CustomObserver());

        Observable.fromIterable(IntStream.rangeClosed(1, 10).mapToObj(Integer::toString).collect(Collectors.toList())).subscribe(new CustomObserver<>());
        Observable.fromCallable(() -> "From callable").subscribe(new CustomObserver<>());
        Observable.fromFuture(CompletableFuture.supplyAsync(()->"From Completeable Future")).subscribe(new CustomObserver<>());

    }

}
