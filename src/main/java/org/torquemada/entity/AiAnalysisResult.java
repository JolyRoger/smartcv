package org.torquemada.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "ai_analysis_results", schema = "smartcv")
public class AiAnalysisResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @ManyToOne
    @JoinColumn(name = "job_description_id")
    private JobDescription jobDescription;

    @Column(name = "analysis_type")
    private String analysisType;

    private String result;
    private Integer score;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Getters, setters, constructors
}