package com.example.webflux.rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FlowableSample1 {
    public static void main(String[] args){
        Flowable.fromArray(1,2,3,4,5).subscribe(System.out::println);
        Flowable.just("a","b","c").observeOn(Schedulers.computation()).map(String::intern).subscribe(System.out::println).dispose();
        Observable.just("Hello reactive World!").subscribe(System.out::println);
        Observable.just("Hello World").subscribe(System.out::println,RuntimeException::new);
        Observable.just("Hello World").subscribe(getObserver());
    }

    private static Observer<String> getObserver() {
        Observer<String> myObserver = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println("Value..."+s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Error..."+e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("OnComplete...");
            }
        } ;
        return myObserver;
    }
}
