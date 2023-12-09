package com.omidmohebbise.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * Reactive Stream
 * 1. Asynchronous
 * 2. Non-blocking
 * 3. Backpressure
 * Publisher -> (subscribe) Subscriber
 */
@Slf4j
public class FluxExample {

    public static void main(String[] args) throws Exception {
        simpleStringFlux();

        handleErrorToContinueTheProcess();

        fluxSubscribeInterval();

        final int MAX_COUNT = 10;
        Flux<String> fluxGenerate = Flux.generate(
                () -> 0,
                (state, sink) -> {
                    sink.next("Value_" + state);
                    if (state == MAX_COUNT) {
                        sink.complete();
                    }
                    return state + 1;
                }
        );

        fluxGenerate.subscribe(s -> log.info("Value is {}", s));

    }

    private static void fluxSubscribeInterval() throws Exception {
        Flux<Integer> interval = Flux.interval(java.time.Duration.ofSeconds(1))
                .map(Long::intValue)
                .take(5);
        interval.subscribe(integer -> log.info("Number is {}", integer),
                throwable -> log.error("Error is {}", throwable.getMessage()),
                () -> log.info("Completed"));
        Thread.sleep(10000);
    }

    private static void handleErrorToContinueTheProcess() {
        Flux<Integer> flux = Flux.range(1, 5)
                .map(integer -> {
                    if (integer == 3)
                        throw new RuntimeException("Error occurred");
                    else
                        return integer;
                })
                .onErrorContinue((throwable, o) -> log.info("Error occurred. Object is {}", o));

        flux.subscribe(integer -> log.info("Number is {}", integer),
                throwable -> log.error("Error is {}", throwable.getMessage()),
                () -> log.info("Completed"));
    }

    private static void simpleStringFlux() {
        Flux<String> flux = Flux.just("Omid", "Saeed", "Sepehr")
                //.log()
                .map(String::toUpperCase)
                .doOnNext(s -> System.out.println("Element is " + s))
                .map(String::toLowerCase)
                .doOnNext(s -> System.out.println("Element is " + s))
                .doOnError(throwable -> System.out.println("Error is " + throwable.getMessage()));

        flux.subscribe(log::info);
    }
}
