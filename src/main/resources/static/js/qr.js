// ==========================================
// QR-ГЕНЕРАТОР — вся логика
// ==========================================

let currentQrData = null;

// === Генерация QR ===
async function generateQR() {
    const input = document.getElementById('url-input');
    const url = input.value.trim();
    const resultDiv = document.getElementById('result');
    const errorDiv = document.getElementById('error');

    resultDiv.style.display = 'none';
    errorDiv.style.display = 'none';

    if (!url) {
        showError('Пожалуйста, введите ссылку');
        return;
    }

    if (!url.startsWith('http://') && !url.startsWith('https://')) {
        showError('Введите корректную ссылку (начинающуюся с http:// или https://)');
        return;
    }

    try {
        const response = await fetch('/api/qr', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ url })
        });

        if (!response.ok) {
            throw new Error('Ошибка сервера');
        }

        const data = await response.json();
        currentQrData = data;

        document.getElementById('qr-url-text').textContent = data.url;
        document.getElementById('qr-code-image').src = 'data:image/png;base64,' + data.qrCode;
        resultDiv.style.display = 'block';
    } catch (error) {
        showError('Не удалось сгенерировать QR-код. Проверьте ссылку.');
    }
}

function showError(message) {
    document.getElementById('error-text').textContent = message;
    document.getElementById('error').style.display = 'block';
}

// === Скачать ===
function downloadQR() {
    if (!currentQrData) return;

    const link = document.createElement('a');
    link.download = 'qr-code-' + Date.now() + '.png';
    link.href = 'data:image/png;base64,' + currentQrData.qrCode;
    link.click();
}

// === Поделиться (Web Share API) ===
function shareQR() {
    if (!currentQrData) return;

    const url = currentQrData.url;

    if (navigator.share) {
        navigator.share({
            title: 'QR-код для ссылки',
            text: `QR-код для ссылки: ${url}`,
            url: url
        }).catch(() => {
            // пользователь отменил — ничего не делаем
        });
    } else {
        navigator.clipboard.writeText(url).then(() => {
            showToast('Ссылка скопирована в буфер обмена! Вы можете поделиться ею вручную.');
        }).catch(() => {
            alert(`Ссылка: ${url}\nСкопируйте её и поделитесь вручную.`);
        });
    }
}

// === Копировать ===
function copyToClipboard() {
    const text = document.getElementById('qr-url-text').textContent;

    if (navigator.clipboard) {
        navigator.clipboard.writeText(text).then(() => {
            showToast('Ссылка скопирована в буфер обмена!');
        }).catch(() => {
            fallbackCopy(text);
        });
    } else {
        fallbackCopy(text);
    }
}

function fallbackCopy(text) {
    const textarea = document.createElement('textarea');
    textarea.value = text;
    textarea.style.position = 'fixed';
    textarea.style.left = '-9999px';
    textarea.style.top = '-9999px';
    document.body.appendChild(textarea);
    textarea.select();
    document.execCommand('copy');
    document.body.removeChild(textarea);
    showToast('Ссылка скопирована в буфер обмена!');
}

// === Toast-уведомление ===
function showToast(message) {
    const existingToast = document.querySelector('.custom-toast');
    if (existingToast) existingToast.remove();

    const toast = document.createElement('div');
    toast.className = 'custom-toast';
    toast.style.cssText = `
        position: fixed;
        bottom: 20px;
        left: 50%;
        transform: translateX(-50%);
        background: #28a745;
        color: white;
        padding: 12px 24px;
        border-radius: 12px;
        font-weight: 500;
        box-shadow: 0 8px 30px rgba(0,0,0,0.2);
        z-index: 9999;
        animation: fadeInUp 0.3s ease;
    `;
    toast.textContent = message;
    document.body.appendChild(toast);

    setTimeout(() => {
        toast.style.opacity = '0';
        toast.style.transition = 'opacity 0.3s ease';
        setTimeout(() => toast.remove(), 300);
    }, 2500);
}

// === Автогенерация при загрузке ===
document.addEventListener('DOMContentLoaded', function() {
    setTimeout(generateQR, 300);
});