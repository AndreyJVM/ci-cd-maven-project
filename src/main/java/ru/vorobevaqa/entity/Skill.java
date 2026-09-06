package ru.vorobevaqa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "skills")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private SkillCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private SkillLevel level;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 100)
    private String iconClass; // Например: "fab fa-docker", "fab fa-java", "fas fa-terminal"

    @Column(length = 255)
    private String keyTools; // Строка через запятую: "JUnit 5, RestAssured, Allure"

    @Column(name = "display_order")
    private Integer displayOrder;
}