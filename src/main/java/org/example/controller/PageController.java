package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.SkillCategory;
import org.example.service.SkillService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final SkillService skillService;

    @GetMapping("/about")
    public String about(Model model) {
        // Добавляем матрицу навыков и список категорий в модель шаблона
        model.addAttribute("skills", skillService.getAllSkills());
        model.addAttribute("categories", SkillCategory.values());
        return "about";
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/projects")
    public String projects() {
        return "projects";
    }

    @GetMapping("/qr")
    public String qrPage() {
        return "qr";
    }
}