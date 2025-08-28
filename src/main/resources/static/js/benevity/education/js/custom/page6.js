let SR;
const SS_CART_ITEM = 'cart' + STORE_INITIAL;
const SS_CHECKOUT_ITEM = 'checkout' + STORE_INITIAL;

function setSr() {
    SR = (
        document.getElementById('srInput').value === null
        || document.getElementById('srInput').value === undefined
    ) ? '' : document.getElementById('srInput').value.trim();
}

function redirectToBenevityOrder() {
    if (SR !== '') {
        window.location.href = "/" + SR + "/benevity/education/order";
    } else {
        window.location.href = "/benevity/education/order";
    }
}

function redirectToBenevityOrganizationDetails() {
    if (SR !== '') {
        window.location.href = "/" + SR + "/benevity/education/order/checkout/organization-details";
    } else {
        window.location.href = "/benevity/education/order/checkout/organization-details";
    }
}

document.addEventListener('DOMContentLoaded', function () {
    setSr();

    bundles = sessionStorage.getItem(SS_CART_ITEM) ? JSON.parse(sessionStorage.getItem(SS_CART_ITEM)) : [];

    if (bundles.length <= 0) {
        redirectToBenevityOrder();
    }

    checkout = sessionStorage.getItem(SS_CHECKOUT_ITEM) ? JSON.parse(sessionStorage.getItem(SS_CHECKOUT_ITEM)) : null;

    if (checkout === null) {
        redirectToBenevityOrganizationDetails();
    }

    sessionStorage.clear();
});