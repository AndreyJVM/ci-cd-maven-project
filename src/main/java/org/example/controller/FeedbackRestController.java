package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.FeedbackRequest;
import org.example.service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackRestController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<Map<String, String>> submitFeedback(@Valid @RequestBody FeedbackRequest request) {
        log.info("Received feedback submission from: {}", request.getName());
        feedbackService.processFeedback(request);
        return ResponseEntity.ok(Map.of("message", "Ваше сообщение успешно отправлено!"));
    }
}