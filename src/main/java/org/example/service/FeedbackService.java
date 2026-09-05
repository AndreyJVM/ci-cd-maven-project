package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.FeedbackRequest;
import org.example.entity.FeedbackMessage;
import org.example.repository.FeedbackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final TelegramNotificationService telegramService;

    @Transactional
    public void processFeedback(FeedbackRequest request) {
        FeedbackMessage feedback = FeedbackMessage.builder()
                .name(request.getName().trim())
                .contact(request.getContact().trim())
                .message(request.getMessage().trim())
                .build();

        feedbackRepository.save(feedback);
        log.info("Saved feedback message from: {}", feedback.getName());

        telegramService.sendFeedbackNotification(
                feedback.getName(),
                feedback.getContact(),
                feedback.getMessage()
        );
    }
}