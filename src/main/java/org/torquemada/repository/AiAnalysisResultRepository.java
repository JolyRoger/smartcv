package org.torquemada.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.torquemada.entity.AiAnalysisResult;

import java.util.List;

@Repository
public interface AiAnalysisResultRepository extends JpaRepository<AiAnalysisResult, Long> {
    List<AiAnalysisResult> findByResumeId(Long resumeId);
    List<AiAnalysisResult> findByJobDescriptionId(Long jobDescriptionId);
}