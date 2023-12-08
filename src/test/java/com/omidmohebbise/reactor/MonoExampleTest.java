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

}
