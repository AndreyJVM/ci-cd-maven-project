package ru.vorobevaqa.controller;

import lombok.RequiredArgsConstructor;
import ru.vorobevaqa.dto.SkillDto;
import ru.vorobevaqa.entity.SkillCategory;
import ru.vorobevaqa.service.SkillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillRestController {

    private final SkillService skillService;

    @GetMapping
    public ResponseEntity<List<SkillDto>> getSkills(@RequestParam(value = "category", required = false) SkillCategory category) {
        if (category != null) {
            return ResponseEntity.ok(skillService.getSkillsByCategory(category));
        }
        return ResponseEntity.ok(skillService.getAllSkills());
    }
}