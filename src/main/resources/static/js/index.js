function setMaxHeight(boolean) {
    const divFunBlocks = document.querySelectorAll('.div-fun-block');

    divFunBlocks.forEach((block) => {
        block.style.height = null;
    });

    if (boolean){
        let maxHeightFun = 0;

        divFunBlocks.forEach((block, index) => {
            maxHeightFun = Math.max(maxHeightFun, block.clientHeight);
        });

        maxHeightFun += 3;

        divFunBlocks.forEach((block) => {
            block.style.height = maxHeightFun + 'px';
        });
    }
}

function setMaxHeightClient(boolean) {
    const divClientBlocks = document.querySelectorAll('.div-client-block');

    divClientBlocks.forEach((block) => {
        block.style.height = null;
    });

    if (boolean) {
        let maxHeightClient = 0;

        divClientBlocks.forEach((block, index) => {
            maxHeightClient = Math.max(maxHeightClient, block.clientHeight);
        });

        maxHeightClient += 3;
        divClientBlocks.forEach((block) => {
            block.style.height = maxHeightClient + 'px';
        });
    }
}

function setMaxHeightPlans(boolean) {
    const divPlansBlocks = document.querySelectorAll('.div-plans-block');

    divPlansBlocks.forEach((block) => {
        block.style.height = null;
    });

    if (boolean) {
        let maxHeightPlans = 0;

        divPlansBlocks.forEach((block, index) => {
            maxHeightPlans = Math.max(maxHeightPlans, block.clientHeight);
        });

        maxHeightPlans += 3;
        divPlansBlocks.forEach((block) => {
            block.style.height = maxHeightPlans + 'px';
        });
    }
}

document.addEventListener('DOMContentLoaded', function() {
    setMaxHeight(window.innerWidth > 767);
    setMaxHeightClient(window.innerWidth > 767);
    setMaxHeightPlans(window.innerWidth > 767);
});

window.addEventListener('resize', function() {
    console.log(true)
    setMaxHeight(window.innerWidth > 767);
    setMaxHeightClient(window.innerWidth > 767);
    setMaxHeightPlans(window.innerWidth > 767);
});
