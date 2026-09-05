package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRequest {

    @NotBlank(message = "Пожалуйста, укажите ваше имя")
    @Size(min = 2, max = 100, message = "Имя должно содержать от 2 до 100 символов")
    private String name;

    @NotBlank(message = "Укажите контакт для связи (Telegram или Email)")
    @Size(min = 3, max = 150, message = "Контактная информация должна содержать от 3 до 150 символов")
    private String contact;

    @NotBlank(message = "Введите сообщение")
    @Size(min = 10, max = 3000, message = "Сообщение должно содержать от 10 до 3000 символов")
    private String message;
}