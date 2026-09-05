package org.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class QrCodeServiceTest {

    private QrCodeService qrCodeService;

    @BeforeEach
    void setUp() {
        qrCodeService = new QrCodeService();
    }

    @Test
    @DisplayName("Успешная генерация валидного Base64 PNG изображения")
    void shouldGenerateValidBase64QrCode() {
        String testUrl = "https://vorobevaqa.ru";
        int width = 300;
        int height = 300;

        String base64Image = qrCodeService.generateQrCodeBase64(testUrl, width, height);

        // Проверяем, что результат не пустой
        assertNotNull(base64Image, "Base64 строка не должна быть null");
        assertFalse(base64Image.isBlank(), "Base64 строка не должна быть пустой");

        // Проверяем, что результат валидно декодируется в массив байт
        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
        assertTrue(decodedBytes.length > 0, "Массив байт не должен быть пустым");

        // Проверяем сигнатуру заголовка PNG файла (0x89, 'P', 'N', 'G')
        assertEquals((byte) 0x89, decodedBytes[0]);
        assertEquals((byte) 'P', decodedBytes[1]);
        assertEquals((byte) 'N', decodedBytes[2]);
        assertEquals((byte) 'G', decodedBytes[3]);
    }

    @Test
    @DisplayName("Выброс исключения при передаче null вместо текста")
    void shouldThrowExceptionWhenTextIsNull() {
        assertThrows(RuntimeException.class, () ->
                qrCodeService.generateQrCodeBase64(null, 300, 300)
        );
    }
}