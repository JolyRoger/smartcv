package org.torquemada.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.torquemada.model.AIAnalysisResultDto;
import org.torquemada.model.MatchRequestDto;
import org.torquemada.model.OpenAiClient;
import org.torquemada.model.ResumeDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeService {

    private final OpenAiClient openAiClient;

    public ResumeDto createResume(ResumeDto resumeDto) {
        // Save to DB (pseudo-code)
        return resumeDto;
    }

    public AIAnalysisResultDto analyzeResumeWithAI(ResumeDto resumeDto) {
        log.info("CV Name={}", resumeDto.name());
        String prompt = "Analyze the following resume: " + resumeDto.content();
        String aiResponse = openAiClient.callOpenAi(prompt);
        return new AIAnalysisResultDto(aiResponse);
    }

    public AIAnalysisResultDto matchWithJobDescription(MatchRequestDto matchRequest) {
        String prompt = String.format("Compare resume: %s with job description: %s", matchRequest.resume(), matchRequest.jobDescription());
        String aiResponse = openAiClient.callOpenAi(prompt);
        return new AIAnalysisResultDto(aiResponse);
    }
}