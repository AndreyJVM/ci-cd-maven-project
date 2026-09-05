/**
 * Модуль отправки формы обратной связи.
 * Взаимодействует с POST /api/feedback
 */
document.addEventListener('DOMContentLoaded', () => {
    const feedbackForm = document.getElementById('feedbackForm');
    if (!feedbackForm) return;

    feedbackForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const submitBtn = document.getElementById('feedbackSubmitBtn');
        const alertBox = document.getElementById('feedbackAlert');
        const nameInput = document.getElementById('feedbackName');
        const contactInput = document.getElementById('feedbackContact');
        const messageInput = document.getElementById('feedbackMessage');

        const payload = {
            name: nameInput.value.trim(),
            contact: contactInput.value.trim(),
            message: messageInput.value.trim()
        };

        alertBox.className = 'alert d-none py-2 px-3 small mb-3';
        submitBtn.disabled = true;
        const originalBtnContent = submitBtn.innerHTML;
        submitBtn.innerHTML = '<span class="spinner-border spinner-border-sm me-2" role="status"></span>Отправка...';

        try {
            const response = await fetch('/api/feedback', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(payload)
            });

            const data = await response.json();

            if (response.ok) {
                alertBox.className = 'alert alert-success py-2 px-3 small mb-3';
                alertBox.innerHTML = '<i class="fas fa-circle-check me-2"></i>' + (data.message || 'Сообщение успешно отправлено!');
                feedbackForm.reset();
            } else {
                alertBox.className = 'alert alert-danger py-2 px-3 small mb-3';
                alertBox.innerHTML = '<i class="fas fa-circle-exclamation me-2"></i>' + (data.error || 'Ошибка при отправке сообщения.');
            }
        } catch (error) {
            alertBox.className = 'alert alert-danger py-2 px-3 small mb-3';
            alertBox.innerHTML = '<i class="fas fa-circle-exclamation me-2"></i>Не удалось связаться с сервером. Попробуйте позже.';
        } finally {
            alertBox.classList.remove('d-none');
            submitBtn.disabled = false;
            submitBtn.innerHTML = originalBtnContent;
        }
    });
});