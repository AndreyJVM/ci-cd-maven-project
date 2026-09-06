package ru.vorobevaqa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vorobevaqa.entity.Skill;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillDto {
    private Long id;
    private String name;
    private String categoryKey;
    private String categoryDisplayName;
    private String levelTitle;
    private int levelPercentage;
    private String badgeColor;
    private String description;
    private String iconClass;
    private List<String> tools;

    public static SkillDto fromEntity(Skill skill) {
        List<String> toolsList = (skill.getKeyTools() != null && !skill.getKeyTools().isBlank())
                ? Arrays.stream(skill.getKeyTools().split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList()
                : Collections.emptyList();

        return SkillDto.builder()
                .id(skill.getId())
                .name(skill.getName())
                .categoryKey(skill.getCategory().name())
                .categoryDisplayName(skill.getCategory().getDisplayName())
                .levelTitle(skill.getLevel().getTitle())
                .levelPercentage(skill.getLevel().getPercentage())
                .badgeColor(skill.getLevel().getBadgeColor())
                .description(skill.getDescription())
                .iconClass(skill.getIconClass())
                .tools(toolsList)
                .build();
    }
}