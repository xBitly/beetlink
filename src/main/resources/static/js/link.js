let selected = 'обычная';
let linkt;
let idl;
let today;
let startDate_;
let endDate_;
let statisticsData;

function copy(event) {
    event.stopPropagation();

    navigator.clipboard.writeText(linkt)
        .then(() => {
            showSuccessNotification('ссылка скопирована')
        })
        .catch(() => {
            showErrorNotification()
        });
}

async function loadLink(linkId) {
    const loading = document.getElementById("loading");
    const linkname = document.querySelector('.short-link');
    const url = document.getElementById("url");
    const iosurl = document.getElementById("ios-url");
    const androidurl = document.getElementById("android-url");
    const desktopurl = document.getElementById("desktop-url");
    const defaulturl = document.getElementById("default-url");
    const description = document.getElementById("description");

    const response = await fetch(`/api/v1/links/${linkId}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${getCookie('accessToken')}`
        }
    });

    if (response.status === 403) {
        window.location.href = '/signin';
    } else if (response.status === 404) {
        //window.location.href = '/error';
    } else {
        const link = await response.json();

        loading.style.display = 'none';
        linkname.innerText = "beetlink.ru/" + link.shortUrl;
        linkt = "beetlink.ru/" + link.shortUrl;

        if (link.iosUrl === link.androidUrl && link.iosUrl === link.desktopUrl && link.iosUrl === link.defaultUrl) {
            selected = 'обычная';
            url.style.display = 'block';
            iosurl.style.display = 'none';
            androidurl.style.display = 'none';
            desktopurl.style.ddisplay = 'none';
            defaulturl.style.ddisplay = 'none';

            url.value = link.defaultUrl;
            description.value = link.description;
        } else {
            selected = 'таргет';
            url.style.display = 'none';
            iosurl.style.display = 'block';
            androidurl.style.display = 'block';
            desktopurl.style.display = 'block';
            defaulturl.style.display = 'block';

            iosurl.value = link.iosUrl;
            androidurl.value = link.androidUrl;
            desktopurl.value = link.desktopUrl;
            defaulturl.value = link.defaultUrl;
            description.value = link.description;
        }
    }
}

document.addEventListener('DOMContentLoaded', function () {
    const currentPath = window.location.pathname;

    const pathParts = currentPath.split('/');

    const id = pathParts[pathParts.length - 1];

    idl = id;

    loadLink(id)
    const submitButton = document.querySelector('input[type="submit"]');
    submitButton.addEventListener('click', function(event) {
        handleSubmit(event, id);
    });

    const startDateInput = document.querySelector('#startDate');
    const endDateInput = document.querySelector('#endDate');

    today = new Date().toISOString().split('T')[0];
    startDate_ = today;
    endDate_ = today;

    endDateInput.setAttribute('placeholder', `${today.split('-')[2]}.${today.split('-')[1]}.${today.split('-')[0]}`);
    startDateInput.setAttribute('placeholder', `${today.split('-')[2]}.${today.split('-')[1]}.${today.split('-')[0]}`);

    const maskOptions = {
        mask: '00.00.0000'
    }
    IMask(endDateInput, maskOptions)
    IMask(startDateInput, maskOptions)

    updateStatistics(startDate_, endDate_, idl)

    startDateInput.addEventListener('input', function (){
        if(startDateInput.value.length === 10) {
            startDate_ = convertDateFormat(startDateInput.value);
            updateStatistics(startDate_, endDate_, idl)
        }
    });

    endDateInput.addEventListener('input', function (){
        if(endDateInput.value.length === 10) {
            endDate_ = convertDateFormat(endDateInput.value);
            updateStatistics(startDate_, endDate_, idl)
        }
    });


    const exportButton = document.querySelector('#export');
    exportButton.addEventListener('click', async function () {
        try {
            const csvData = convertJSONToCSV(statisticsData);
            downloadCSV(csvData);
        } catch (error) {
            console.error('Ошибка:', error);
        }
    });
});

function convertDateFormat(dateString) {
    const parts = dateString.split('.');

    if (parts.length !== 3) {
        return null;
    }

    const day = parts[0];
    const month = parts[1];
    const year = parts[2];

    return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`;
}

async function updateStatistics(startDate, endDate, linkId) {
    try {
        const response = await fetch(`/api/v1/links/${linkId}/statistics?startDate=${startDate}&endDate=${endDate}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${getCookie('accessToken')}`
            }
        });

        if (response.status === 403) {
            window.location.href = '/signin';
        }

        if (!response.ok) {
            const jsonError = await response.json();
            throw new Error(jsonError.message);
        }

        statisticsData = await response.json();
        console.log('Statistics Data:', statisticsData);

    } catch (error) {
        console.error('Ошибка:', error);
        showErrorNotification(error.message);
    }
}

function convertJSONToCSV(jsonData) {
    const separator = ',';
    const keys = Object.keys(jsonData[0]);

    let csv = keys.join(separator) + '\n';

    jsonData.forEach(item => {
        keys.forEach((key, index) => {
            if (item.hasOwnProperty(key)) {
                csv += item[key];
                if (index < keys.length - 1) {
                    csv += separator;
                }
            }
        });
        csv += '\n';
    });

    return csv;
}

function downloadCSV(csvData) {
    const blob = new Blob([csvData], { type: 'text/csv;charset=utf-8;' });

    const link = document.createElement('a');
    if (link.download !== undefined) {
        const url = URL.createObjectURL(blob);
        link.setAttribute('href', url);
        link.setAttribute('download', 'statistics.csv');
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    } else {
        showErrorNotification('ошибка загрузки');
    }
}

async function handleSubmit(event, linkId) {
    event.preventDefault();

    let url, iosUrl, androidUrl, desktopUrl, defaultUrl, description, linkData;

    description = document.getElementById('description').value.trim();

    try {
        if (selected === 'обычная') {
            console.log('1')
            url = document.getElementById('url').value.trim();
            console.log('2')
            if (url === '' || url == null) throw new Error('пустая ссылка');
            console.log('3')
            linkData = {
                iosUrl: url,
                androidUrl: url,
                desktopUrl: url,
                defaultUrl: url,
                description: description
            };
            console.log('4')
        } else {
            console.log('11')
            iosUrl = document.getElementById('ios-url').value.trim();
            console.log('22')
            androidUrl = document.getElementById('android-url').value.trim();
            console.log('33')
            desktopUrl = document.getElementById('desktop-url').value.trim();
            console.log('44')
            defaultUrl = document.getElementById('default-url').value.trim();
            console.log('55')
            if (iosUrl === '' || iosUrl == null) throw new Error('пустая ссылка');
            console.log('66')
            if (androidUrl === '' || androidUrl == null) throw new Error('пустая ссылка');
            console.log('77')
            if (desktopUrl === '' || desktopUrl == null) throw new Error('пустая ссылка');
            console.log('88')
            if (defaultUrl === '' || defaultUrl == null) throw new Error('пустая ссылка');
            console.log('99')
            linkData = {
                iosUrl: iosUrl,
                androidUrl: androidUrl,
                desktopUrl: desktopUrl,
                defaultUrl: defaultUrl,
                description: description
            };
            console.log('1010')
        }

        console.log('-1')
        await updateLink(linkData, linkId);
        console.log('-2')
        showSuccessNotification('сохранено');
    } catch (error) {
        console.error('Ошибка:', error.message);
        showErrorNotification(error.message);
    }
}

async function updateLink(linkData, linkId) {
    const response = await fetch(`/api/v1/links/${linkId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${getCookie('accessToken')}`
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
}

function showErrorNotification(message) {
    const errorNotification = document.getElementById('errorNotification');
    errorNotification.textContent = message;
    errorNotification.style.display = 'block';

    setTimeout(() => {
        errorNotification.style.display = 'none';
    }, 5000);
}

function showSuccessNotification(message) {
    const errorNotification = document.getElementById('successNotification');
    const errorMessage = document.createElement('div');
    errorNotification.appendChild(errorMessage);
    errorNotification.textContent = message;
    errorNotification.style.display = 'block';

    setTimeout(() => {
        errorNotification.style.display = 'none';
    }, 5000);
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
