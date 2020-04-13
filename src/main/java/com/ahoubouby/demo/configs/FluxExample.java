package com.ahoubouby.demo.configs;
/*
 * ahoubouby created on 13/04/2020
 * E-MAIL: ahoubouby@gmail.com
 */

import com.ahoubouby.demo.dmain.Todo;
import com.ahoubouby.demo.dmain.TodoBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Configuration
public class FluxExample {
    static private Logger LOG = LoggerFactory.getLogger(FluxExample.class);

    public FluxExample() {

    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            EmitterProcessor<Todo> stream = EmitterProcessor.create();
            Mono<List<Todo>> promise = stream
                    .filter(Todo::isCompleted)
                    .doOnNext(s -> LOG.info("FLUX >>> ToDo: {}", s.getDesc()))
                    .collectList()
                    .subscribeOn(Schedulers.single());
            stream.onNext(TodoBuilder.create().withDescription("hela helo").withCompleted(true).build());
            stream.onNext(new Todo("Listen Classical Music"));
            stream.onNext(new Todo("Workout in the Mornings"));
            stream.onNext(new Todo("Organize my room"));
            stream.onNext(new Todo("Go to the Car Wash"));
            stream.onNext(new Todo("SP1 2018 is coming"));
            stream.onComplete();
            promise.block();
        };
    }
}
