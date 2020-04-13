package com.ahoubouby.demo.configs;
/*
 * ahoubouby created on 13/04/2020
 * E-MAIL: ahoubouby@gmail.com
 */

import com.ahoubouby.demo.dmain.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Configuration
public class MonoExample {
    static private Logger LOG = LoggerFactory.getLogger(MonoExample.class);

    @Bean
    public CommandLineRunner runMonoExample() {
        return args -> {
            MonoProcessor<Todo> promise = MonoProcessor.create();
            Mono<Todo> result = promise.doOnSuccess(p -> LOG.info("LOG >> {}", p.getDesc()))
                    .doOnTerminate(() -> LOG.info("LOG >> Done"))
                    .doOnError(t -> LOG.error(t.getMessage(), t))
                    .subscribeOn(Schedulers.single());
            promise.onNext(new Todo("Buy my ticket for SpringOne Platform 2018"));
            //promise.onError(
            new IllegalArgumentException("There is an error processing the ToDo...");
            result.block(Duration.ofMillis(10000));
        };
    }
}
