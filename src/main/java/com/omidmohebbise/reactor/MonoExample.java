package com.omidmohebbise.reactor;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Mono;

import java.util.Scanner;

/**
 * Reactive Stream
 * 1. Asynchronous
 * 2. Non-blocking
 * 3. Backpressure
 * Publisher -> (subscribe) Subscriber
 */
@Slf4j
public class MonoExample {
    public static int showMenu() {
        System.out.println("1. simpleMonoExample");
        System.out.println("2. monoErrorHandler");
        System.out.println("3. monoOnCompletedHandler");
        System.out.println("4. monoOnCompletedCancelSubscription");
        System.out.println("5. monoDoOnMethods");
        System.out.println("6. emptyMono");
        System.out.println("7. monoOnErrorResume");
        System.out.println("8. Exit");

        MonoExample monoExample = new MonoExample();
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        switch (number) {
            case 1:
                monoExample.simpleMonoExample();
                break;
            case 2:
                monoExample.monoErrorHandler();
                break;
            case 3:
                monoExample.monoOnCompletedHandler();
                break;
            case 4:
                monoExample.monoOnCompletedCancelSubscription();
                break;
            case 5:
                monoExample.monoDoOnMethods();
                break;
            case 6:
                monoExample.emptyMono();
                break;
            case 7:
                monoExample.monoOnErrorResume();
                break;
            case 0:
                return 0;
            default:
                System.out.println("Invalid number");

        }
        return number;
    }

    public static void main(String[] args) {
        do {
            System.out.println();
        } while (showMenu() != 0);

    }

    private void monoOnErrorResume() {
        Mono<Object> mono = Mono.error(new RuntimeException("An error occurred"))
                .doOnError(throwable -> log.error("Error message: {}", throwable.getMessage()))
                .onErrorResume(throwable -> {
                    log.info("Inside onErrorResume");
                    return Mono.just("Omid Mohebbi");
                })
                .log();
        mono.subscribe(s -> log.info("Value is {}", s),
                throwable -> log.error("Something bad happened"));
    }

    public void emptyMono() {
        String name = "Omid Mohebbi";
        Mono<Object> mono = Mono.just(name)
                .map(String::toUpperCase)
                .log()
                .doOnSubscribe(subscription -> log.info("Subscribed"))
                .doOnRequest(longNumber -> log.info("Request Received, starting doing something..."))
                .doOnNext(s -> log.info("Value is here. Executing doOnNext1 {}", s))
                .flatMap(s -> Mono.empty())
                .doOnNext(s -> log.info("Value is here. Executing doOnNext3 {}", s))
                .doOnSuccess(s ->
                        log.info("doOnSuccess executed {}", s));

        mono.subscribe(s -> log.info("Value is {}", s),
                throwable -> log.error("Something bad happened"),
                () -> log.info("Completed"),
                subscription -> subscription.request(3));

    }

    public void monoDoOnMethods() {
        String name = "Omid Mohebbi";
        Mono<String> mono = Mono.just(name)
                .map(String::toUpperCase)
                .log()
                .doOnSubscribe(subscription -> log.info("Subscribed"))
                .doOnRequest(longNumber -> log.info("Request Received, starting doing something..."))
                .doOnNext(s -> log.info("Value is here. Executing doOnNext1 {}", s))
                .doOnNext(s -> log.info("Value is here. Executing doOnNext2 {}", s))
                .doOnNext(s -> log.info("Value is here. Executing doOnNext3 {}", s))
                .doOnSuccess(s -> log.info("doOnSuccess executed {}", s));

        mono.subscribe(s -> log.info("Value is {}", s),
                throwable -> log.error("Something bad happened"),
                () -> log.info("Completed"),
                subscription -> subscription.request(3));

    }

    public void monoOnCompletedCancelSubscription() {
        String name = "Omid Mohebbi";
        Mono<String> mono = Mono.just(name).map(String::toUpperCase).log();

        mono.subscribe(s -> log.info("Value is {}", s),
                throwable -> log.error("Something bad happened"),
                () -> log.info("Completed"),
                Subscription::cancel);

    }

    public void monoOnCompletedHandler() {
        String name = "Omid Mohebbi";
        Mono<String> mono = Mono.just(name).map(String::toUpperCase).log();

        mono.subscribe(s -> log.info("Value is {}", s),
                throwable -> log.error("Something bad happened"),
                () -> log.info("Completed"));

    }

    public void monoErrorHandler() {
        String name = "Omid Mohebbi";
        Mono<String> mono = Mono.just(name).map(s -> {
            throw new RuntimeException("An error occurred");
        });

        mono.subscribe(s -> log.info("Value is {}", s),
                throwable -> log.error("Something bad happened"));

    }

    public void simpleMonoExample() {
        String name = "Omid Mohebbi";
        Mono<String> mono = Mono.just(name).log();
        mono.subscribe();


        log.info("--------------------------------------------------");
        log.info("mono: {}", mono);
    }

}
