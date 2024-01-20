document.addEventListener('DOMContentLoaded', async function () {
    const signinButton = document.querySelector('input[type="submit"]');
    signinButton.addEventListener('click', handleSignin);

    const refreshToken = getCookie('refreshToken');
    if (refreshToken) {
        try {
            const response = await refreshTokens(refreshToken);
            handleResponse(response);
        } catch (error) {
            console.error('ошибка:', error.message);
        }
    }
});

async function handleSignin(event) {
    event.preventDefault();

    const email = document.getElementById('userEmail').value;
    const password = document.getElementById('userPassword').value;

    try {
        const response = await signinUser(email, password);
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

async function signinUser(email, password) {
    const response = await fetch('api/v1/auth/signin', {
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

async function refreshTokens(refreshToken) {
    const response = await fetch('api/v1/auth/refresh', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            refreshToken: refreshToken,
        }),
    });

    if (!response.ok) {
        const jsonError = await response.json();
        throw new Error(jsonError.message);
    }

    const tokens = await response.json();
    saveTokensToCookies(tokens);

    return tokens;
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

function getCookie(name) {
    const cookies = document.cookie.split('; ');
    for (const cookie of cookies) {
        const [cookieName, cookieValue] = cookie.split('=');
        if (cookieName === name) {
            return cookieValue;
        }
    }
    return null;
}