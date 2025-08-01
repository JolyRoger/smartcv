package org.torquemada.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.torquemada.entity.Resume;
import org.torquemada.model.AIAnalysisResultDto;
import org.torquemada.model.MatchRequestDto;
import org.torquemada.dto.ResumeDto;
import org.torquemada.service.ResumeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping("/create")
    public ResponseEntity<Resume> createResume(@RequestBody ResumeDto resumeDto) {
        return ResponseEntity.ok(resumeService.createResume(resumeDto));
    }

    @PostMapping("/analyze")
    public ResponseEntity<AIAnalysisResultDto> analyzeResume(@RequestBody ResumeDto resumeDto) {
        return ResponseEntity.ok(resumeService.analyzeResumeWithAI(resumeDto));
    }

    @PostMapping("/match")
    public ResponseEntity<AIAnalysisResultDto> matchJob(@RequestBody MatchRequestDto matchRequest) {
        return ResponseEntity.ok(resumeService.matchWithJobDescription(matchRequest));
    }
}
