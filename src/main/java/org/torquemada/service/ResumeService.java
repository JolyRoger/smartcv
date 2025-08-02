package org.torquemada.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.torquemada.entity.Resume;
import org.torquemada.entity.User;
import org.torquemada.model.AIAnalysisResultDto;
import org.torquemada.model.MatchRequestDto;
import org.torquemada.model.OpenAiClient;
import org.torquemada.dto.ResumeDto;
import org.torquemada.model.OpenAiResponse;
import org.torquemada.repository.ResumeRepository;
import org.torquemada.repository.UserRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeService {

    private final OpenAiClient openAiClient;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    public Resume createResume(ResumeDto resumeDto) {
        return saveResume(resumeDto);
    }

    public Resume saveResume(ResumeDto dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Resume resume = new Resume();
        resume.setUser(user);
        resume.setTitle(dto.title());
        resume.setSummary(dto.summary());
        resume.setCreatedAt(LocalDateTime.now());
        resume.setUpdatedAt(LocalDateTime.now());

        return resumeRepository.save(resume);
    }

    public Mono<AIAnalysisResultDto> analyzeResumeWithAI(ResumeDto resumeDto) {
        log.info("CV Name={}", resumeDto.title());
        log.info("CV Content={}", resumeDto.summary());
        String prompt = String.format("Please explain the following sentence: \"%s\"", resumeDto.summary());
        Mono<OpenAiResponse> aiResponse = openAiClient.callOpenAi(prompt);
        return aiResponse.map(response -> new AIAnalysisResultDto(response.getFirstResponse()));
    }

    public Mono<AIAnalysisResultDto> matchWithJobDescription(MatchRequestDto matchRequest) {
        String prompt = String.format("Compare resume: %s with job description: %s", matchRequest.resume(), matchRequest.jobDescription());
        Mono<OpenAiResponse> aiResponse = openAiClient.callOpenAi(prompt);
        return aiResponse.map(response -> new AIAnalysisResultDto(response.getFirstResponse()));
    }
}