#!/bin/bash
set -e  # Остановить скрипт при любой ошибке

# === Конфигурация ===
APP_NAME="ci-cd-maven-project"
DOCKER_IMAGE="andreyvorobevaqa/ci-cd-maven-project:latest"
CONTAINER_NAME="my-app"
PORT=8080

echo "🚀 Deploying $APP_NAME to VDS..."

# Останавливаем и удаляем старый контейнер (если есть)
echo "🛑 Stopping old container..."
docker stop $CONTAINER_NAME 2>/dev/null || true
docker rm $CONTAINER_NAME 2>/dev/null || true

# Скачиваем свежий образ
echo "📥 Pulling latest image..."
docker pull $DOCKER_IMAGE

# Запускаем новый контейнер
echo "🚀 Starting new container..."
docker run -d \
    --name $CONTAINER_NAME \
    -p $PORT:8080 \
    --restart unless-stopped \
    --health-cmd="wget --quiet --tries=1 --spider http://localhost:8080/health || exit 1" \
    --health-interval=30s \
    $DOCKER_IMAGE

# Ждем несколько секунд и проверяем
sleep 5
if docker ps | grep -q $CONTAINER_NAME; then
    echo "✅ Деплой успешен!"
    echo "🌐 Приложение доступно на http://$(curl -s ifconfig.me):$PORT"
    echo "📋 Логи: docker logs $CONTAINER_NAME"
else
    echo "❌ Ошибка деплоя. Проверьте логи:"
    docker logs $CONTAINER_NAME
    exit 1
fi