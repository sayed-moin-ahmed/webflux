package com.example.webflux.rxjava;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.stream.Collectors;

public class FlowableSample {
    public static void main(String[] args) throws Exception {
        /*List squares = new ArrayList();
        Flowable.range(1,64)
                .observeOn(Schedulers.computation())
                .map(v -> v*v)
                .blockingSubscribe(squares::add);
        System.out.println(squares);

        Single<List<Integer>> result = Flowable.range(1,10)
                .filter(i -> i%2 == 0)
                .collect(Collectors.toList());
        System.out.println(result.blockingGet());

        Flowable.range(1, 64)
                .map(i -> {return Integer.toString(i);})
                .forEach(System.out::println)
                .dispose();

        List sq =  new ArrayList();
        Flowable.range(1,12)
                .flatMap(v -> Flowable.just(v).subscribeOn(Schedulers.computation()).map(w->w*w))
                .doOnError(ex-> ex.printStackTrace())
                .doOnComplete(()->System.out.println("Completed"))
                .blockingSubscribe(sq::add);
        System.out.println(sq);
*/
        runComputation();

    }

    public static void runComputation() throws Exception {
        Flowable<String> source = Flowable.fromCallable(
                () -> { //1
                    return "Done";
                });
        source.doOnComplete(
                () -> System.out.println("Completed runComputation"));

        Flowable<String> background =
                source.subscribeOn(Schedulers.io()); //2
        Flowable<String> foreground =
                background.observeOn(Schedulers.single()); //3
        foreground.subscribe(System.out::println,
                Throwable::printStackTrace); //4

    }
}
