function createLinkElement(link) {
    const linkElement = document.createElement("div");
    linkElement.className = "div-div-link"
    const createdAtDate = new Date(link.createdAt);
    const hours = String(createdAtDate.getHours()).padStart(2, '0');
    const minutes = String(createdAtDate.getMinutes()).padStart(2, '0');
    const day = String(createdAtDate.getDate()).padStart(2, '0');
    const month = String(createdAtDate.getMonth() + 1).padStart(2, '0');
    const year = createdAtDate.getFullYear();

    const createdAtFormatted = `${hours}:${minutes}, ${day}.${month}.${year}`;
    linkElement.innerHTML = `
        <div class="div-link">
            <div class="div-short-link" onclick="copy(event, '${('beetlink.ru/'+link.shortUrl)}')">
                <svg class="svg" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
                    <path d="M7 9.667C7 8.95967 7.28099 8.28131 7.78115 7.78115C8.28131 7.28099 8.95967 7 9.667 7H18.333C18.6832 7 19.03 7.06898 19.3536 7.20301C19.6772 7.33704 19.9712 7.53349 20.2189 7.78115C20.4665 8.0288 20.663 8.32281 20.797 8.64638C20.931 8.96996 21 9.31676 21 9.667V18.333C21 18.6832 20.931 19.03 20.797 19.3536C20.663 19.6772 20.4665 19.9712 20.2189 20.2189C19.9712 20.4665 19.6772 20.663 19.3536 20.797C19.03 20.931 18.6832 21 18.333 21H9.667C9.31676 21 8.96996 20.931 8.64638 20.797C8.32281 20.663 8.0288 20.4665 7.78115 20.2189C7.53349 19.9712 7.33704 19.6772 7.20301 19.3536C7.06898 19.03 7 18.6832 7 18.333V9.667Z"/>
                    <path d="M4.012 16.737C3.70534 16.5622 3.45027 16.3095 3.27258 16.0045C3.09488 15.6995 3.00085 15.353 3 15V5C3 3.9 3.9 3 5 3H15C15.75 3 16.158 3.385 16.5 4" />
                </svg>
                <h3>beetlink.ru/<span class="h3-secondary">${link.shortUrl}</span></h3>
            </div>
            <h2>${link.description}</h2>
        </div>
        <h3 class="date">${createdAtFormatted}</h3>
    `;

    linkElement.onclick = function() {
        window.location.href = "/links/" + link.id
    };


    return linkElement;
}

function copy(event, text) {
    event.stopPropagation();

    navigator.clipboard.writeText(text)
        .then(() => {
            showSuccessNotification()
        })
        .catch(() => {
            showErrorNotification()
        });
}

function showSuccessNotification() {
    const errorNotification = document.getElementById('successNotification');
    const errorMessage = document.createElement('div');
    errorNotification.appendChild(errorMessage);
    errorNotification.style.display = 'block';

    setTimeout(() => {
        errorNotification.style.display = 'none';
    }, 5000);
}

function showErrorNotification() {
    const errorNotification = document.getElementById('errorNotification');
    const errorMessage = document.createElement('div');
    errorNotification.appendChild(errorMessage);
    errorNotification.style.display = 'block';

    setTimeout(() => {
        errorNotification.style.display = 'none';
    }, 5000);
}


async function loadUserLinks(userId) {
    const loading = document.getElementById("loading");

    const response = await fetch(`/api/v1/users/${userId}/links`, {
        headers: {
            'Authorization': `Bearer ${getCookie('accessToken')}`
        }
    });

    if (response.status === 403) {
        window.location.href = '/signin';
    } else {
        const links = await response.json();
        const linksContainer = document.getElementById("links-container");

        loading.style.display = 'none';

        if (links.length === 0) {
            linksContainer.innerHTML = `
                <h3 class="h3-center">тут ничего нет, создайте свою первую ссылку</h3>
            `;
        } else {
            links.reverse().forEach(link => {
                const linkElement = createLinkElement(link);
                linksContainer.appendChild(linkElement);
            });
        }
    }
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

document.addEventListener('DOMContentLoaded', loadUserLinks("self"));
