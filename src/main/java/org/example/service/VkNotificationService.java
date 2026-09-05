package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class VkNotificationService {

    private final RestClient restClient;
    private final String botToken;
    private final String userId;

    private static final String VK_API_VERSION = "5.199";
    private static final String VK_API_URL = "https://api.vk.com/method/messages.send";

    public VkNotificationService(
            @Value("${vk.bot.token:}") String botToken,
            @Value("${vk.user.id:}") String userId) {
        this.restClient = RestClient.create();
        this.botToken = botToken;
        this.userId = userId;
    }

    public void sendFeedbackNotification(String name, String contact, String message) {
        if (botToken == null || botToken.isBlank() || userId == null || userId.isBlank()) {
            log.info("VK notification skipped: credentials not provided in environment.");
            return;
        }

        String text = String.format(
                "📬 Новое сообщение с сайта vorobevaqa.ru\n\n" +
                        "👤 От кого: %s\n" +
                        "📞 Контакт: %s\n\n" +
                        "💬 Сообщение:\n%s",
                name, contact, message
        );

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("user_id", userId);
        formData.add("random_id", String.valueOf(ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE)));
        formData.add("message", text);
        formData.add("access_token", botToken);
        formData.add("v", VK_API_VERSION);

        try {
            String response = restClient.post()
                    .uri(VK_API_URL)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(formData)
                    .retrieve()
                    .body(String.class);

            if (response != null && response.contains("\"error\"")) {
                log.error("VK API returned error: {}", response);
            } else {
                log.info("VK notification sent successfully to user ID: {}", userId);
            }
        } catch (Exception e) {
            log.error("Failed to send VK notification: {}", e.getMessage());
        }
    }
}