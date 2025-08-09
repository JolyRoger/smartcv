package org.torquemada.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.torquemada.service.FortuneGenerator;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resume")
public class FortuneController {

    @GetMapping("/fortune")
    public Mono<String> fortune() {
        var sentence = FortuneGenerator.sentence();
        log.info("Explain <{}>", sentence);
        return Mono.just(sentence);
    }
}
