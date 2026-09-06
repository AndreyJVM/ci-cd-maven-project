package ru.vorobevaqa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.vorobevaqa.controller.api.QrRestController;
import ru.vorobevaqa.dto.QrRequest;
import ru.vorobevaqa.exception.GlobalExceptionHandler;
import ru.vorobevaqa.service.QrCodeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QrRestController.class)
@Import(GlobalExceptionHandler.class)
class QrRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private QrCodeService qrCodeService;

    @Test
    @DisplayName("POST /api/qr - успешная генерация возвращает 200 и валидный JSON")
    void shouldReturnQrCodeOnValidRequest() throws Exception {
        QrRequest request = new QrRequest();
        request.setUrl("https://vorobevaqa.ru");

        String fakeBase64 = "fakeBase64String";
        when(qrCodeService.generateQrCodeBase64(anyString())).thenReturn(fakeBase64);

        mockMvc.perform(post("/api/qr")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.url").value("https://vorobevaqa.ru"))
                .andExpect(jsonPath("$.qrCode").value(fakeBase64));
    }

    @Test
    @DisplayName("POST /api/qr - ошибка валидации (невалидный URL) возвращает 400 Bad Request")
    void shouldReturnBadRequestWhenUrlIsInvalid() throws Exception {
        QrRequest request = new QrRequest();
        request.setUrl("invalid-url");

        mockMvc.perform(post("/api/qr")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }
}