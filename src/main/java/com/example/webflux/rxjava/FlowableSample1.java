package com.example.webflux.rxjava;

import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.schedulers.ImmediateThinScheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import reactor.core.Disposables;

import java.io.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FlowableSample1 {
    public static void main(String[] args) {
        //observableSample();
        flowableSample();
    }

    private static void flowableSample() {
        Flowable.fromArray(1, 2, 3, 4, 5).subscribe(System.out::println);//Array
        Flowable.just("a", "b", "c").subscribeOn(Schedulers.computation()).subscribe(System.out::println);//Thread
        File file = new File("Test.txt");
       /* try (PrintWriter pw = new PrintWriter(file)) {
            Flowable.range(1, 100)
                    .observeOn(Schedulers.newThread())
                    .blockingSubscribe(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (final BufferedReader br = new BufferedReader(new FileReader(file))) {
            Flowable<String> flow = Flowable.fromPublisher(new FilePublisher(br));
            flow.observeOn(Schedulers.single()).blockingSubscribe(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Single<BufferedReader> readerSingle = Single.just(file)
                .observeOn(Schedulers.computation())
                .map(FileReader::new)
                .map(BufferedReader::new);
        Flowable<String> flowable =
                readerSingle.flatMapPublisher(reader -> //4
                        Flowable.fromIterable( //5
                                () ->
                                        Stream.generate(readLineSupplier(reader)).iterator()
                        ).takeWhile(line -> !"EOF".equals(line)));
        flowable.doOnNext(it -> System.out.println("thread=" + Thread.currentThread().getName())) //7
                .doOnError(ex -> ex.printStackTrace())
                .blockingSubscribe(System.out::println);

    }
    private static Supplier<String>
    readLineSupplier(BufferedReader reader) {
        return () -> { try {
            String line = reader.readLine();
            return line == null ? "EOF" : line;
        } catch (IOException ex)
        { throw new RuntimeException(ex); }};
    }

    private static void observableSample() {
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

        Observable.just(1, 2, 3).map(e->{System.out.println("Hey...");return Integer.toString(e);}).observeOn(Schedulers.io()).subscribe(new CustomObserver<>());
        Observable.just(1, 2, 3).map(e->{System.out.println("Hey...");return Integer.toString(e);}).observeOn(Schedulers.computation()).subscribe(new CustomObserver<>());
        Observable.just(1, 2, 3).map(e->{System.out.println("Hey...");return Integer.toString(e);}).observeOn(Schedulers.single()).subscribe(new CustomObserver<>());
    }

}
