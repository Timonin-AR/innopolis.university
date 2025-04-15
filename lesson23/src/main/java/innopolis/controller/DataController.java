package innopolis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Stream;

@RestController
public class DataController {

//    @GetMapping("/hello")
//    public Mono<String> getHello(){
//        return Mono.just("Вечер в хату");
//    }

    @GetMapping("/list")
    public Flux<Integer> getList(){
        return Flux.fromStream(Stream.iterate(1, e -> e +1).limit(10000));
    }


}
