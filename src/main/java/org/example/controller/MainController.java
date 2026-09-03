package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.QrRequest;
import org.example.dto.QrResponse;
import org.example.service.QrCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final QrCodeService qrCodeService;

    // ===== СТРАНИЦЫ =====

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

    @GetMapping("/qr")
    public String qrPage() {
        return "qr";
    }

    // ===== API QR-ГЕНЕРАТОРА =====

    @PostMapping("/api/qr")
    @ResponseBody
    public ResponseEntity<QrResponse> generateQrCode(@Valid @RequestBody QrRequest request) {
        log.info("Generating QR code for URL: {}", request.getUrl());
        String qrCode = qrCodeService.generateQrCodeBase64(request.getUrl(), 300, 300);
        return ResponseEntity.ok(new QrResponse(request.getUrl(), qrCode));
    }
}