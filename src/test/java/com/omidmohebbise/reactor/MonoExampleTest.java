package com.omidmohebbise.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
public class MonoExampleTest {

    @Test
    public void testSimpleMonoExample() {
        String name = "Omid Mohebbi";
        Mono<String> mono = Mono.just(name).log();
        mono.subscribe();


        StepVerifier.create(mono)
                .expectNext("Omid Mohebbi")
                .verifyComplete();
    }

    @Test
    public void monoErrorHandler() {
        String name = "Omid Mohebbi";
        Mono<String> mono = Mono.just(name).map(s -> {
            throw new RuntimeException("An error occurred");
        });

        mono.subscribe(s -> log.info("Value is {}", s), throwable -> log.error("Something bad happened"));
        mono.subscribe(s -> log.info("Value is {}", s), Throwable::printStackTrace);

        StepVerifier.create(mono)
                .expectError(RuntimeException.class)
                .verify();

    }



}
