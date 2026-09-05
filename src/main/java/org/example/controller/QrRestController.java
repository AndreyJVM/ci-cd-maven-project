package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.QrRequest;
import org.example.dto.QrResponse;
import org.example.service.QrCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/qr")
@RequiredArgsConstructor
public class QrRestController {

    private final QrCodeService qrCodeService;

    @PostMapping
    public ResponseEntity<QrResponse> generateQrCode(@Valid @RequestBody QrRequest request) {
        log.info("Request to generate QR code for URL: {}", request.getUrl());
        String qrCodeBase64 = qrCodeService.generateQrCodeBase64(request.getUrl());
        return ResponseEntity.ok(new QrResponse(request.getUrl(), qrCodeBase64));
    }
}