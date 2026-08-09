package org.example.controller;

import org.example.dto.CreateLinkRequest;
import org.example.dto.LinkResponse;
import org.example.entity.Link;
import org.example.service.LinkService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    private final LinkService linkService;
    private final String baseUrl;

    public MainController(LinkService linkService,
                          @Value("${app.base-url:https://vorobevaqa.ru}") String baseUrl) {
        this.linkService = linkService;
        this.baseUrl = baseUrl;
    }

    // Страницы портфолио
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/projects")
    public String projects() {
        return "projects";
    }

    @GetMapping("/links")
    public String linkPage() {
        return "links";
    }

    // API для сокращения ссылок
    @PostMapping("/api/links")
    @ResponseBody
    public ResponseEntity<LinkResponse> createShortLink(@Valid @RequestBody CreateLinkRequest request) {
        Link link = linkService.createShortLink(request.getUrl());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new LinkResponse(link, baseUrl));
    }

    @GetMapping("/{shortCode}")
    public String redirectToOriginal(@PathVariable String shortCode) {
        String originalUrl = linkService.getOriginalUrlAndIncrement(shortCode);
        return "redirect:" + originalUrl;
    }

    @GetMapping("/api/links/{shortCode}/stats")
    @ResponseBody
    public ResponseEntity<LinkResponse> getLinkStats(@PathVariable String shortCode) {
        Link link = linkService.getLinkStats(shortCode);
        return ResponseEntity.ok(new LinkResponse(link, baseUrl));
    }
}