package com.example.webflux.rxjava;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.Objects;

public class FilePublisherSubscription implements Subscription {
    private FilePublisher filePublisher;
    private Subscriber subscriber;
    public FilePublisherSubscription(FilePublisher filePublisher, Subscriber<? super String> subscriber) {
        this.filePublisher = filePublisher;
        this.subscriber = subscriber;
    }

    @Override
    public void request(long n) {
        try{
            String line;
            for(int i = 0; i<n && Objects.nonNull(filePublisher) && Objects.nonNull(line = filePublisher.readLine());i++){
                if(Objects.nonNull(subscriber))
                    subscriber.onNext(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancel() {
        filePublisher = null;
    }
}
