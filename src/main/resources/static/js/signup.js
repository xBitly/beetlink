document.addEventListener('DOMContentLoaded', function () {
    const signupButton = document.querySelector('input[type="submit"]');
    signupButton.addEventListener('click', handleSignup);
});

async function handleSignup(event) {
    event.preventDefault();

    const email = document.getElementById('userEmail').value;
    const password = document.getElementById('userPassword').value;

    try {
        const response = await signupUser(email, password);
        handleResponse(response);
    } catch (error) {
        console.error('ошибка:', error.message);
        showErrorNotification(error.message);
    }

    function showErrorNotification(message) {
        const errorNotification = document.getElementById('errorNotification');
        const errorMessage = document.createElement('div');
        errorMessage.textContent = message;
        errorNotification.innerHTML = '';
        errorNotification.appendChild(errorMessage);
        errorNotification.style.display = 'block';

        setTimeout(() => {
            errorNotification.style.display = 'none';
        }, 5000);
    }
}

async function signupUser(email, password) {
    const response = await fetch('api/v1/auth/signup', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            email: email,
            password: password,
        }),
    });

    if (!response.ok) {
        const jsonError = await response.json();
        throw new Error(jsonError.message);
    }

    return response.json();
}

function handleResponse(response) {
    saveTokensToCookies(response);
    window.location.href = '/home';
}

function saveTokensToCookies(response) {
    document.cookie = `refreshToken=${response.refreshToken}; expires=${getCookieExpiration(30)}`;
    document.cookie = `accessToken=${response.accessToken}; expires=${getCookieExpiration(1)}`;
}

function getCookieExpiration(days) {
    const date = new Date();
    date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
    return date.toUTCString();
}
