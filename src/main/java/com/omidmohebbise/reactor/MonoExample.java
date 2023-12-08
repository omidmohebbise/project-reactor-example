package com.omidmohebbise.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Reactive Stream
 * 1. Asynchronous
 * 2. Non-blocking
 * 3. Backpressure
 * Publisher -> (subscribe) Subscriber
 */
@Slf4j
public class MonoExample {
    public static void main(String[] args) {
        MonoExample monoExample = new MonoExample();
        monoExample.simpleMonoExample();
    }

    public void simpleMonoExample() {
        String name = "Omid Mohebbi";
        Mono<String> mono = Mono.just(name).log();
        mono.subscribe();


        log.info("--------------------------------------------------");
        log.info("mono: {}", mono);
    }
}
