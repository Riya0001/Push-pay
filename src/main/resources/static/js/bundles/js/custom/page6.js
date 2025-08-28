let sr;

function setSr() {
    sr = (
        document.getElementById('srInput').value === null
        || document.getElementById('srInput').value === undefined
    ) ? '' : document.getElementById('srInput').value.trim();
}

document.addEventListener('DOMContentLoaded', function () {
    setSr();
    bundles = sessionStorage.getItem('cart') ? JSON.parse(sessionStorage.getItem('cart')) : [];

    if(bundles.length <= 0) {
        if(sr !== ''){
            window.location.href = "/" + sr + "/bundles/order";
        } else {
            window.location.href = "/bundles/order";
        }
    }

    sessionStorage.clear();
});