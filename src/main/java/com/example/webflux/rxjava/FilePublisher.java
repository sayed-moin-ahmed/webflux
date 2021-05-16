package com.example.webflux.rxjava;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.io.BufferedReader;
import java.io.IOException;

public class FilePublisher implements Publisher<String> {

    BufferedReader reader;

    FilePublisher(BufferedReader reader){
        this.reader =  reader;
    }

    @Override
    public void subscribe(Subscriber<? super String> subscriber) {
        subscriber.onSubscribe(new FilePublisherSubscription(this,subscriber));
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }
}
