package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.FeedbackRequest;
import org.example.exception.GlobalExceptionHandler;
import org.example.service.FeedbackService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FeedbackRestController.class)
@Import(GlobalExceptionHandler.class)
class FeedbackRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FeedbackService feedbackService;

    @Test
    @DisplayName("POST /api/feedback - успешная отправка валидных данных")
    void shouldSubmitFeedbackSuccessfully() throws Exception {
        FeedbackRequest request = new FeedbackRequest(
                "Иван",
                "@ivan_tg",
                "Здравствуйте! Интересует совместный проект по автоматизации тестирования."
        );

        doNothing().when(feedbackService).processFeedback(any(FeedbackRequest.class));

        mockMvc.perform(post("/api/feedback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Ваше сообщение успешно отправлено!"));
    }

    @Test
    @DisplayName("POST /api/feedback - ошибка валидации при пустых полях")
    void shouldReturnBadRequestOnValidationFailure() throws Exception {
        FeedbackRequest invalidRequest = new FeedbackRequest("", "", "");

        mockMvc.perform(post("/api/feedback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }
}