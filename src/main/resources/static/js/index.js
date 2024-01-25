function setMaxHeight() {
    const divFunBlocks = document.querySelectorAll('.div-fun-block');
    let maxHeightFun = 0;

    divFunBlocks.forEach((block, index) => {
        if (index === 0) {
            maxHeightFun = block.clientHeight;
        } else {
            maxHeightFun = Math.max(maxHeightFun, block.clientHeight);
        }
    });

    maxHeightFun += 3;
    divFunBlocks.forEach((block) => {
        block.style.height = maxHeightFun + 'px';
    });
}

function setMaxHeightClient() {
    const divClientBlocks = document.querySelectorAll('.div-client-block');
    let maxHeightClient = 0;

    divClientBlocks.forEach((block, index) => {
        if (index === 0) {
            maxHeightClient = block.clientHeight;
        } else {
            maxHeightClient = Math.max(maxHeightClient, block.clientHeight);
        }
    });

    maxHeightClient += 3;
    divClientBlocks.forEach((block) => {
        block.style.height = maxHeightClient + 'px';
    });
}

function setMaxHeightPlans() {
    const divPlansBlocks = document.querySelectorAll('.div-plans-block');
    let maxHeightPlans = 0;

    divPlansBlocks.forEach((block, index) => {
        if (index === 0) {
            maxHeightPlans = block.clientHeight;
        } else {
            maxHeightPlans = Math.max(maxHeightPlans, block.clientHeight);
        }
    });

    maxHeightPlans += 3;
    divPlansBlocks.forEach((block) => {
        block.style.height = maxHeightPlans + 'px';
    });
}

document.addEventListener('DOMContentLoaded', setMaxHeight);
document.addEventListener('DOMContentLoaded', setMaxHeightClient);
document.addEventListener('DOMContentLoaded', setMaxHeightPlans);
