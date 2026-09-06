package ru.vorobevaqa.controller.web;

import lombok.RequiredArgsConstructor;
import ru.vorobevaqa.entity.SkillCategory;
import ru.vorobevaqa.service.GitHubService;
import ru.vorobevaqa.service.SkillService;
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