package ru.vorobevaqa.service;

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
        String base64Image = qrCodeService.generateQrCodeBase64(testUrl);

        assertNotNull(base64Image);
        assertFalse(base64Image.isBlank());

        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
        assertTrue(decodedBytes.length > 0);

        // Проверка заголовка PNG
        assertEquals((byte) 0x89, decodedBytes[0]);
        assertEquals((byte) 'P', decodedBytes[1]);
        assertEquals((byte) 'N', decodedBytes[2]);
        assertEquals((byte) 'G', decodedBytes[3]);
    }

    @Test
    @DisplayName("Выброс IllegalArgumentException при пустой строке или null")
    void shouldThrowExceptionWhenTextIsBlankOrNull() {
        assertThrows(IllegalArgumentException.class, () -> qrCodeService.generateQrCodeBase64(null));
        assertThrows(IllegalArgumentException.class, () -> qrCodeService.generateQrCodeBase64("   "));
    }
}