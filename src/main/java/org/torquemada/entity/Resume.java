package org.torquemada.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "resumes", schema = "fortune")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Setter
    private String title;

    @Setter
    private String summary;

    @Setter
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Setter
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Experience> experiences;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Education> educationList;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Skill> skills;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<AiAnalysisResult> analysisResults;

    // Getters, setters, constructors
}