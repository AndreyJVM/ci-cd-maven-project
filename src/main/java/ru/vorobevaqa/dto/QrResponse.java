package ru.vorobevaqa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QrResponse {

    private final String url;
    private final String qrCode;
}