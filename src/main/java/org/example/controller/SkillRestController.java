package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.SkillDto;
import org.example.entity.SkillCategory;
import org.example.service.SkillService;
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