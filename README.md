# Portfolio & QR Code Generator Service

Production-ready web application combining a personal portfolio and an interactive QR code generator microservice. Built with Java 17 and Spring Boot 3, containerized with Docker, and deployed via a fully automated CI/CD pipeline.

- **Production URL:** [https://vorobevaqa.ru](https://vorobevaqa.ru)
- **Docker Hub:** `andreyvorobevaqa/portfolio-service`

---

## Tech Stack

- **Backend:** Java 17, Spring Boot 3, Spring MVC, Thymeleaf
- **QR Engine:** Google ZXing (Core & JavaSE 3.5.3)
- **Frontend:** Bootstrap 5.3, FontAwesome 6, Vanilla JS (ES6 modules, theme management)
- **Build & Tools:** Maven, Lombok
- **Infrastructure:** Docker, Docker Compose, Nginx (Reverse Proxy, SSL, Gzip), Ubuntu VDS
- **CI/CD:** GitHub Actions (Automated build, test verification, Docker Hub push, SSH zero-downtime deploy)

---

## Key Features

- **Personal Portfolio:** Responsive showcase with dedicated sections for completed engineering projects and professional skills.
- **Dark/Light Theme:** Native theme toggle synchronized with Bootstrap 5.3 color modes (`data-bs-theme`) without page flicker.
- **QR Code REST API:** High-performance QR code generation endpoint with Base64 output.
- **Client Features:** One-click PNG download, Web Share API integration, instant clipboard copy.
- **Production-Tuned:** Nginx reverse proxy with HTTP/2, Let's Encrypt SSL, and Gzip compression. JVM container constraints configured via `JAVA_OPTS`.

---

## API Reference

### Generate QR Code

- **Endpoint:** `POST /api/qr`
- **Content-Type:** `application/json`

#### Request Body


#### Response (`200 OK`)


---

## Quick Start with Docker

### Run from Docker Hub

```bash
docker run -d \
  --name my-java-app \
  -p 8080:8080 \
  -e JAVA_OPTS="-Xmx256m -xms128m" \
  andreyvorobevaqa/portfolio-service:latest
```

---

## 🔄 CI/CD Pipeline Architecture

1. **Build & Test:** Runs `mvn clean verify` using Temurin JDK 17 on every push/PR.
2. **Containerization:** Builds optimized Docker image and pushes tagged versions (`latest`, `${{ github.sha }}`) to Docker Hub.
3. **Automated Deployment:** Deploys target image to remote VDS over SSH with configuration sync and zero-downtime container recreation.

---

## Author

- **Name:** Andrey Vorobev
- **GitHub:** [@AndreyJVM](https://github.com/AndreyJVM)
- **Telegram:** [@AndreyAQA](https://t.me/AndreyAQA)
- **Website:** [vorobevaqa.ru](https://vorobevaqa.ru)