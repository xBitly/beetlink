let selected = 'обычная';

function toggleSelection(element) {
    const selectedElement = document.querySelector('.selected');
    const notSelectedElement = document.querySelector('.not-selected');

    selectedElement.classList.remove('selected');
    selectedElement.classList.add('not-selected');

    notSelectedElement.classList.remove('not-selected');
    notSelectedElement.classList.add('selected');

    selected = selectedElement.textContent;

    if (selected.includes('таргет')) {
        showFieldsForSelected();
    } else {
        showFieldsForNotSelected();
    }
}

function showFieldsForSelected() {
    document.getElementById('url').style.display = 'block';
    document.getElementById('ios-url').style.display = 'none';
    document.getElementById('android-url').style.display = 'none';
    document.getElementById('desktop-url').style.display = 'none';
    document.getElementById('default-url').style.display = 'none';
}

function showFieldsForNotSelected() {
    document.getElementById('url').style.display = 'none';
    document.getElementById('ios-url').style.display = 'block';
    document.getElementById('android-url').style.display = 'block';
    document.getElementById('desktop-url').style.display = 'block';
    document.getElementById('default-url').style.display = 'block';
}

document.addEventListener('DOMContentLoaded', function () {
    const submitButton = document.querySelector('input[type="submit"]');
    submitButton.addEventListener('click', handleSubmit);
});

async function handleSubmit(event) {
    event.preventDefault();

    const selectedType = document.querySelector('.selected').textContent.trim();
    let url, iosUrl, androidUrl, desktopUrl, defaultUrl, description, linkData;

    description = document.getElementById('description').value.trim();

    try {
        if (selectedType === 'обычная') {
            url = document.getElementById('url').value.trim();
            if (url === '' || url == null) throw new Error('пустая ссылка');
            linkData = {
                iosUrl: url,
                androidUrl: url,
                desktopUrl: url,
                defaultUrl: url,
                description: description
            };
        } else {
            iosUrl = document.getElementById('ios-url').value.trim();
            androidUrl = document.getElementById('android-url').value.trim();
            desktopUrl = document.getElementById('desktop-url').value.trim();
            defaultUrl = document.getElementById('default-url').value.trim();
            if (iosUrl === '' || iosUrl == null) throw new Error('пустая ссылка');
            if (androidUrl === '' || androidUrl == null) throw new Error('пустая ссылка');
            if (desktopUrl === '' || desktopUrl == null) throw new Error('пустая ссылка');
            if (defaultUrl === '' || defaultUrl == null) throw new Error('пустая ссылка');
            linkData = {
                iosUrl: iosUrl,
                androidUrl: androidUrl,
                desktopUrl: desktopUrl,
                defaultUrl: defaultUrl,
                description: description
            };
        }

        const response = await createLink(linkData);
        handleResponse(response);
    } catch (error) {
        console.error('Ошибка:', error.message);
        showErrorNotification(error.message);
    }
}

async function createLink(linkData) {
    const response = await fetch('/api/v1/links/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${getAccessToken()}`
        },
        body: JSON.stringify(linkData),
    });

    if (response.status === 403) {
        window.location.href = '/signin';
    }

    if (!response.ok) {
        const jsonError = await response.json();
        throw new Error(jsonError.message);
    }

    return response.json();
}

function handleResponse(response) {
    window.location.href = `/links/${response.id}`;
}

function showErrorNotification(message) {
    const errorNotification = document.getElementById('errorNotification');
    errorNotification.textContent = message;
    errorNotification.style.display = 'block';

    setTimeout(() => {
        errorNotification.style.display = 'none';
    }, 5000);
}

function getAccessToken() {
    const cookies = document.cookie.split('; ');
    for (const cookie of cookies) {
        const [name, value] = cookie.split('=');
        if (name === 'accessToken') {
            return value;
        }
    }
    return null;
}
