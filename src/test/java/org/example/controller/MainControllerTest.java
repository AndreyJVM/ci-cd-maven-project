package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.QrRequest;
import org.example.service.QrCodeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MainController.class)
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private QrCodeService qrCodeService;

    @Test
    @DisplayName("GET / - главная страница отдает статус 200")
    void shouldReturnIndexPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    @DisplayName("GET /about - страница 'О себе' отдает статус 200")
    void shouldReturnAboutPage() throws Exception {
        mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("about"));
    }

    @Test
    @DisplayName("GET /projects - страница проектов отдает статус 200")
    void shouldReturnProjectsPage() throws Exception {
        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(view().name("projects"));
    }

    @Test
    @DisplayName("GET /qr - страница генератора отдает статус 200")
    void shouldReturnQrPage() throws Exception {
        mockMvc.perform(get("/qr"))
                .andExpect(status().isOk())
                .andExpect(view().name("qr"));
    }

    @Test
    @DisplayName("POST /api/qr - успешная генерация возвращает 200 и валидный JSON")
    void shouldReturnQrCodeOnValidRequest() throws Exception {
        QrRequest request = new QrRequest();
        request.setUrl("https://vorobevaqa.ru");

        String fakeBase64 = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNk+A8AAQUBAScY42YAAAAASUVORK5CYII=";

        // Мокаем правильное имя метода: generateQrCodeBase64
        when(qrCodeService.generateQrCodeBase64(anyString(), anyInt(), anyInt())).thenReturn(fakeBase64);

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
        request.setUrl("not-a-valid-url");

        mockMvc.perform(post("/api/qr")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }
}