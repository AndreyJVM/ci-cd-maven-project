package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Slf4j
@Service
public class TelegramNotificationService {

    private final RestClient restClient;
    private final String botToken;
    private final String chatId;

    public TelegramNotificationService(
            @Value("${telegram.bot.token:}") String botToken,
            @Value("${telegram.bot.chat-id:}") String chatId) {
        this.restClient = RestClient.create();
        this.botToken = botToken;
        this.chatId = chatId;
    }

    public void sendFeedbackNotification(String name, String contact, String message) {
        if (botToken == null || botToken.isBlank() || chatId == null || chatId.isBlank()) {
            log.info("Telegram notification skipped: credentials not provided in environment.");
            return;
        }

        String text = String.format(
                "📬 *Новое сообщение с сайта*\n\n" +
                        "👤 *От кого:* %s\n" +
                        "📞 *Контакт:* %s\n\n" +
                        "💬 *Сообщение:*\n%s",
                escapeMarkdown(name),
                escapeMarkdown(contact),
                escapeMarkdown(message)
        );

        try {
            String url = "https://api.telegram.org/bot" + botToken + "/sendMessage";
            restClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of(
                            "chat_id", chatId,
                            "text", text,
                            "parse_mode", "Markdown"
                    ))
                    .retrieve()
                    .toBodilessEntity();
            log.info("Telegram notification sent successfully.");
        } catch (Exception e) {
            log.error("Failed to send Telegram notification: {}", e.getMessage());
        }
    }

    private String escapeMarkdown(String input) {
        if (input == null) return "";
        return input.replace("_", "\\_")
                .replace("*", "\\*")
                .replace("[", "\\[")
                .replace("`", "\\`");
    }
}