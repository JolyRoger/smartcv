package org.torquemada.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.torquemada.entity.Resume;
import org.torquemada.model.AIAnalysisResultDto;
import org.torquemada.model.MatchRequestDto;
import org.torquemada.dto.ResumeDto;
import org.torquemada.service.FortuneGenerator;
import org.torquemada.service.ResumeService;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeService resumeService;

    @GetMapping("/fortune")
    public Mono<String> fortune() {
        var sentence = FortuneGenerator.sentence();
        log.info("Explain <{}>", sentence);
        return Mono.just(sentence);
//        var prompt = String.format("Please explain the sense of the following sentence: \"%s\"", sentence);
//        var resumeDto = new ResumeDto(0L, 0L, "", prompt);
//        return resumeService.analyzeResumeWithAI(resumeDto).map(AIAnalysisResultDto::analysis);
    }

    @PostMapping("/create")
    public ResponseEntity<Resume> createResume(@RequestBody ResumeDto resumeDto) {
        return ResponseEntity.ok(resumeService.createResume(resumeDto));
    }

    @PostMapping("/analyze")
    public Mono<AIAnalysisResultDto> analyzeResume(@RequestBody ResumeDto resumeDto) {
        return resumeService.analyzeResumeWithAI(resumeDto);
    }

    @PostMapping("/match")
    public Mono<AIAnalysisResultDto> matchJob(@RequestBody MatchRequestDto matchRequest) {
        return resumeService.matchWithJobDescription(matchRequest);
    }
}
