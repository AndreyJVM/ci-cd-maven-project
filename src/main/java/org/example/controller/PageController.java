package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.SkillCategory;
import org.example.service.GitHubService;
import org.example.service.SkillService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final SkillService skillService;
    private final GitHubService gitHubService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("skills", skillService.getAllSkills());
        model.addAttribute("categories", SkillCategory.values());
        return "about";
    }

    @GetMapping("/projects")
    public String projects(Model model) {
        model.addAttribute("repos", gitHubService.getRecentRepositories());
        return "projects";
    }

    @GetMapping("/qr")
    public String qrPage() {
        return "qr";
    }
}