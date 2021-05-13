package com.example.webflux.rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class CustomObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(@NonNull Disposable d) {
        System.out.println("Is disposed..."+d.isDisposed());
    }

    @Override
    public void onNext(@NonNull T type) {
        System.out.println("Value..."+type);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        System.out.println("Error..."+e.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("OnComplete...");
    }
}
