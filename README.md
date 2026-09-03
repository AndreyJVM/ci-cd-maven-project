# Сайт-визитка

Персональный сайт-портфолио с современным дизайном, тёмной темой и встроенным генератором QR-кодов.

## Сайт

[https://vorobevaqa.ru](https://vorobevaqa.ru)

## Функциональность

- **Персональное портфолио** — информация обо мне, навыки и проекты
- **Тёмная тема** — переключение светлой/тёмной темы с сохранением в localStorage
- **Генератор QR-кодов** — быстрая генерация QR-кодов по любой ссылке с возможностью скачать
- **Адаптивный дизайн** — корректно отображается на всех устройствах
- **Современный UI** — Bootstrap 5 + кастомные стили

## Технологии

| Компонент | Технология |
|-----------|------------|
| **Backend** | Java 17, Spring Boot 3, Thymeleaf |
| **Frontend** | Bootstrap 5, FontAwesome 6, JavaScript |
| **QR-коды** | ZXing (Google) |
| **Инфраструктура** | Docker, GitHub Actions, Nginx |
| **Деплой** | VDS, Let's Encrypt (HTTPS) |

## Структура проекта
```shell
ci-cd-maven-project/
├── .github/workflows/
│ └── docker-publish.yml # CI/CD пайплайн
├── src/main/
│ ├── java/org/example/
│ │ ├── controller/ # Контроллеры
│ │ ├── dto/ # DTO для API
│ │ ├── entity/ # JPA сущности
│ │ ├── repository/ # Репозитории
│ │ └── service/ # Сервисы (QR, ссылки)
│ └── resources/
│ ├── templates/
│ │ ├── fragments/ # Общие фрагменты (head, navbar, footer)
│ │ ├── index.html # Главная
│ │ ├── about.html # Обо мне
│ │ ├── projects.html # Проекты
│ │ └── qr.html # Генератор QR-кодов
│ └── static/
│ └── images/ # Изображения
├── Dockerfile # Multi-stage сборка
├── docker-compose.yml
└── pom.xml
```
