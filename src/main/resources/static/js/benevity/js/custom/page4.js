let SR;
const SS_CART_ITEM = 'cart' + STORE_INITIAL;
const SS_CHECKOUT_ITEM = 'checkout' + STORE_INITIAL;
const SS_BENEVITY_CAUSE_ITEM = 'benevityCause' + STORE_INITIAL;
// Select the form
const cartCheckoutPage1Form = document.getElementById('cart-checkout-page-1-form');

let bundles = [];
let benevityCause = [];

const statesByCountry = {
    "Canada": [
        "Alberta", "British Columbia", "Manitoba", "New Brunswick", "Newfoundland and Labrador",
        "Nova Scotia", "Ontario", "Prince Edward Island", "Quebec", "Saskatchewan"
    ],
    "United States": [
        "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut",
        "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana",
        "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts",
        "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska",
        "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina",
        "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island",
        "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont",
        "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"
    ]
};

// Add a submit event listener
cartCheckoutPage1Form.addEventListener('submit', function (event) {
    event.preventDefault();

    const isValidEmail = validateFormEmailEntry("emailInput", "invalidInputMessage");
    if (!isValidEmail) {
        return;
    }
    const isValidStartDate = isCampaignStartDateValidCustom();
    if(!isValidStartDate) {
        return;
    }

    checkboxConfirmationForBilling();

    // Collect form data
    const formData = new FormData(cartCheckoutPage1Form);

    let formDataObj = {};
    for (const [key, value] of formData.entries()) {
        formDataObj[key] = value;
    }

    formDataObj.checkboxToUseShippingAddress = document.getElementById('checkboxToUseShippingAddressInput').checked;

    if (formDataObj.checkboxToUseShippingAddress) {
        formDataObj.billingState = formDataObj.shippingState;
    }

    formDataObj.today = new Date().toISOString().split('T')[0];

    sessionStorage.setItem(SS_CHECKOUT_ITEM, JSON.stringify(formDataObj));

    postData(formDataObj);
    hideModal();
});
function isCampaignStartDateValidCustom() {
    const input = document.getElementById("campaignStartDateInput");
    const defaultHelper = document.getElementById("campaign-start-date-helper-default");
    const validHelper = document.getElementById("campaign-start-date-helper-valid");
    const invalidHelper = document.getElementById("campaign-start-date-helper-invalid");


    const selectedDate = new Date(input.value);
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    const daysDiff = Math.ceil((selectedDate - today) / (1000 * 60 * 60 * 24));

    document.querySelectorAll(".daysDiff").forEach(span => {
        span.textContent = daysDiff;
    });

    if (daysDiff < 30) {
        defaultHelper.style.display = 'none';
        validHelper.style.display = 'none';
        invalidHelper.style.display = 'block';
        input.focus();
        input.scrollIntoView({ behavior: "smooth", block: "center" });
        return false;
    } else {
        defaultHelper.style.display = 'none';
        validHelper.style.display = 'block';
        invalidHelper.style.display = 'none';
        return true;
    }
}

// Function to show the modal and prevent closing
function showModal() {
    const myModal = new bootstrap.Modal(document.getElementById('messageModal'), {
        backdrop: 'static', // Prevent closing when clicking outside
        keyboard: false     // Prevent closing when pressing Escape
    });
    myModal.show();
}

// Function to hide the modal
function hideModal() {
    const myModal = new bootstrap.Modal(document.getElementById('messageModal'));
    myModal.hide();
}

// Function to show the modal
function showErrorModal() {
    const myModal = new bootstrap.Modal(document.getElementById('errorModal'));
    myModal.show();
}

// Function to hide the modal
function hideErrorModal() {
    const myModal = new bootstrap.Modal(document.getElementById('errorModal'));
    myModal.hide();
}

// Function to show the modal
function showInformationModal() {
    const myModal = new bootstrap.Modal(document.getElementById('informationModal'));
    myModal.show();
}

// Function to hide the modal
function hideInformationModal() {
    const myModal = new bootstrap.Modal(document.getElementById('informationModal'));
    myModal.hide();
}

function onPrevious() {
    window.history.back();
}

function updateCartCounterBadge() {
    document.getElementById('cart-counter-badge').textContent = '' + bundles.length;
}


const campaignStartInput = document.getElementById("campaignStartDateInput");

campaignStartInput.addEventListener("change", function () {
    const selectedDate = new Date(this.value);
    const today = new Date();
    today.setHours(0, 0, 0, 0); // Normalize today's date

    const daysDiff = Math.ceil((selectedDate - today) / (1000 * 60 * 60 * 24));

    document.querySelectorAll(".daysDiff").forEach(span => {
        span.textContent = daysDiff;
    });

    const defaultHelper = document.getElementById("campaign-start-date-helper-default");
    const validHelper = document.getElementById("campaign-start-date-helper-valid");
    const invalidHelper = document.getElementById("campaign-start-date-helper-invalid");

    if (!this.value) {
        defaultHelper.style.display = 'block';
        validHelper.style.display = 'none';
        invalidHelper.style.display = 'none';
    } else if (daysDiff < 30) {
        defaultHelper.style.display = 'none';
        validHelper.style.display = 'none';
        invalidHelper.style.display = 'block';
    } else {
        defaultHelper.style.display = 'none';
        validHelper.style.display = 'block';
        invalidHelper.style.display = 'none';
    }

    console.log("Selected Program Start Date:", this.value, "| Days from today:", daysDiff);
});


function fillFields(checkout) {
    if (checkout !== null) {
        document.getElementById('organizationNameInput').value = checkout.organizationName;
        document.getElementById('phoneInput').value = checkout.phone;
        document.getElementById('emailInput').value = checkout.email;
        document.getElementById('campaignStartDateInput').value = checkout.campaignStartDate;

        document.getElementById('shippingCountryInput').value = checkout.shippingCountry;
        updatePostalCodePattern('shipping');

        document.getElementById('shippingFullNameInput').value = checkout.shippingFullName;
        document.getElementById('shippingAddressLine1Input').value = checkout.shippingAddressLine1;
        document.getElementById('shippingAddressLine2Input').value = checkout.shippingAddressLine2;
        document.getElementById('shippingCityInput').value = checkout.shippingCity;
        document.getElementById('shippingZipCodeInput').value = checkout.shippingZipCode;
        document.getElementById('shippingPhoneInput').value = checkout.shippingPhone;
        document.getElementById('shippingStateInput').value = checkout.shippingState;

        document.getElementById('checkboxToUseShippingAddressInput').checked = checkout.checkboxToUseShippingAddress;

        document.getElementById('billingCountryInput').value = checkout.billingCountry;
        updatePostalCodePattern('billing');

        document.getElementById('billingFullNameInput').value = checkout.billingFullName;
        document.getElementById('billingAddressLine1Input').value = checkout.billingAddressLine1;
        document.getElementById('billingAddressLine2Input').value = checkout.billingAddressLine2;
        document.getElementById('billingCityInput').value = checkout.billingCity;
        document.getElementById('billingZipCodeInput').value = checkout.billingZipCode;
        document.getElementById('billingPhoneInput').value = checkout.billingPhone;
        document.getElementById('billingStateInput').value = checkout.billingState;
    } else {
        if (benevityCause !== null) {
            document.getElementById('organizationNameInput').value = benevityCause.name;
            document.getElementById('phoneInput').value = benevityCause.phone;

            document.getElementById('shippingCountryInput').value = benevityCause.address.country;
            updatePostalCodePattern('shipping');

            document.getElementById('shippingAddressLine1Input').value = benevityCause.address.street;
            document.getElementById('shippingCityInput').value = benevityCause.address.city;
            document.getElementById('shippingZipCodeInput').value = benevityCause.address.zip;
            document.getElementById('shippingPhoneInput').value = benevityCause.phone;
            document.getElementById('shippingStateInput').value = benevityCause.address.state;

            document.getElementById('checkboxToUseShippingAddressInput').checked = true;
        } else {
            redirectToBenevityOrganizationDetails();
        }
    }
}

function checkboxConfirmationForBilling() {
    const useShipping = document.getElementById('checkboxToUseShippingAddressInput').checked;
    if (useShipping) {
        runConfirmationForBilling();
    }
}

function runConfirmationForBilling() {
    document.getElementById('billingFullNameInput').value = document.getElementById('shippingFullNameInput').value;
    document.getElementById('billingAddressLine1Input').value = document.getElementById('shippingAddressLine1Input').value;
    document.getElementById('billingAddressLine2Input').value = document.getElementById('shippingAddressLine2Input').value;
    document.getElementById('billingCityInput').value = document.getElementById('shippingCityInput').value;
    document.getElementById('billingZipCodeInput').value = document.getElementById('shippingZipCodeInput').value;
    document.getElementById('billingCountryInput').value = document.getElementById('shippingCountryInput').value;
    document.getElementById('billingPhoneInput').value = document.getElementById('shippingPhoneInput').value;
    document.getElementById('billingStateInput').value = document.getElementById('shippingStateInput').value;
}

function validatePOBox(id, scrollTo) {
    const valueLc = document.getElementById(id).value.toLowerCase();
    if (valueLc.includes('po box') || valueLc.includes('p.o box') || valueLc.includes('p.o. box') ||
        valueLc.includes('lockbox') || valueLc.includes('lock box')) {
        poBoxDetectedAction(true, id, scrollTo, true);
        return false;
    } else {
        poBoxDetectedAction(false, id);
        return true;
    }
}

function poBoxDetectedAction(isDetected, id, scrollTo, focusEnabled) {
    if (isDetected) {
        if (focusEnabled) document.getElementById(id).focus();
        document.querySelector(`label[for="${id}"]`).style.color = '#9E007E';
        // document.getElementById(id + 'Alert').style.display = 'block';
        document.getElementById(id + 'Alert').style.color = '#9E007E';
        document.getElementById(id).classList.add('empty-field');
        // document.getElementById('accordion-shipping-section').classList.add('empty-card-border');
        controlAccordionCollapse(scrollTo, 'show');
        scroll(scrollTo);
    } else {
        document.querySelector(`label[for="${id}"]`).style.color = '#000000';
        // document.getElementById(id + 'Alert').style.display = 'none';
        document.getElementById(id + 'Alert').style.color = '#000000';
        document.getElementById(id).classList.remove('empty-field');
    }
}

function scroll(id) {
    const element = document.getElementById(id)
    if (element) {
        element.scrollIntoView({behavior: 'smooth', block: 'start'});
    }
}

function scrollToEmptyCard() {
    const firstEmptyCard = document.querySelector('.empty-card-border');
    if (firstEmptyCard) {
        firstEmptyCard.scrollIntoView({behavior: 'smooth', block: 'start'});
    }
}

function controlAccordionCollapse(sectionId, action) {
    const collapseElement = document.getElementById(`collapseDevice_${sectionId}`);
    const bsCollapse = new bootstrap.Collapse(collapseElement, {
        toggle: false
    });

    if (action === 'show') {
        bsCollapse.show();
    } else if (action === 'hide') {
        bsCollapse.hide();
    } else if (action === 'toggle') {
        bsCollapse.toggle();
    }
}

function postData(formDataObj) {

    // Check PO Box for shippingAddressLine1Input
    const isValidPoBox = validatePOBox('shippingAddressLine1Input', 'accordion-shipping-section');

    if (!isValidPoBox) {
        return;
    }

    const inputElement = document.getElementById('billingAddressLine1Input');

    if (inputElement.required) {
        // Check PO Box for billingAddressLine1Input
        const isValidPoBoxForBilling = validatePOBox('billingAddressLine1Input', 'accordion-billing-section');

        if (!isValidPoBoxForBilling) {
            return;
        }
    }

    switchContainers(false, true, false);

    // Prepare the data
    const cartJson = JSON.stringify(bundles);
    const benevityJson = JSON.stringify(benevityCause);
    const checkoutJson = JSON.stringify(formDataObj);

    // Create a form element
    const form = document.createElement('form');
    form.method = 'POST';
    if (SR !== '') {
        form.action = '/' + SR + '/benevity/order/checkout/shipping';
    } else {
        form.action = '/benevity/order/checkout/shipping';
    }

    // Create input elements for each part of checkoutInfo
    const cartInput = document.createElement('input');
    cartInput.type = 'hidden';
    cartInput.name = 'cart';
    cartInput.value = cartJson;

    const benevityInput = document.createElement('input');
    benevityInput.type = 'hidden';
    benevityInput.name = 'benevityCause';
    benevityInput.value = benevityJson;

    const checkoutInput = document.createElement('input');
    checkoutInput.type = 'hidden';
    checkoutInput.name = 'checkout';
    checkoutInput.value = checkoutJson;

    // Append each input to the form
    form.appendChild(cartInput);
    form.appendChild(benevityInput);
    form.appendChild(checkoutInput);

    // Append the form to the body and submit it
    document.body.appendChild(form);
    form.submit();
}

function formatPhoneNumber(input) {
    // Remove all non-digit characters
    let value = input.value.replace(/\D/g, '');

    let formattedValue = value;

    if (value.length > 3 && value.length <= 6) {
        // Format as (123) 456
        formattedValue = '(' + value.substring(0, 3) + ') ' + value.substring(3);
    } else if (value.length > 6) {
        // Format as (123) 456-7890
        formattedValue = '(' + value.substring(0, 3) + ') ' + value.substring(3, 6) + '-' + value.substring(6, 10);
    } else if (value.length > 0) {
        // Format as (123
        formattedValue = '(' + value.substring(0, 3);
    }

    input.value = formattedValue;
}

function updatePostalCodePattern(isStartsWith) {
    const country = document.getElementById(isStartsWith + "CountryInput").value;
    const postalCodeInput = document.getElementById(isStartsWith + "ZipCodeInput");

    // Load states based on selected country
    const stateSelect = document.getElementById(isStartsWith + "StateInput");
    stateSelect.innerHTML = '<option selected hidden></option>';
    if (statesByCountry[country]) {
        statesByCountry[country].forEach(state => {
            const option = document.createElement("option");
            option.value = state;
            option.text = state;
            stateSelect.appendChild(option);
        });
    }

    // Set the pattern and placeholder based on the country
    if (country === "Canada") {
        postalCodeInput.setAttribute("pattern", "^[A-Za-z]\\d[A-Za-z] \\d[A-Za-z]\\d$");
        postalCodeInput.setAttribute("placeholder", "A1B 2C3");
        postalCodeInput.maxLength = 7;
    } else if (country === "United States") {
        postalCodeInput.setAttribute("pattern", "^\\d{5}(-\\d{4})?$");
        postalCodeInput.setAttribute("placeholder", "12345 or 12345-6789");
        postalCodeInput.maxLength = 10;
    } else {
        // If country is not selected, clear pattern
        postalCodeInput.removeAttribute("pattern");
        postalCodeInput.removeAttribute("placeholder");
        postalCodeInput.maxLength = "";
    }
}

// Function to toggle the visibility and 'required' attribute
function toggleBillingAddressSection() {
    const checkbox = document.getElementById('checkboxToUseShippingAddressInput');
    const billingAddressSection = document.querySelector('.billing-address-section');
    const billingInputs = billingAddressSection.querySelectorAll('input, select');

    if (checkbox.checked) {
        // Hide billing section and remove 'required' from all inputs
        billingAddressSection.style.display = 'none';
        billingInputs.forEach(input => {
            input.required = false;
        });
    } else {
        // Show billing section and add 'required' to inputs
        billingAddressSection.style.display = 'block';
        billingInputs.forEach(input => {
            input.required = input.id !== 'billingAddressLine2Input';
        });
    }
}

function switchContainers(showMain, showCalculation, showError) {
    if (window.innerWidth < 769) {
        document.getElementById('main-container').style.display = showMain ? 'block' : 'none';
        document.getElementById('calculation-container').style.display = showCalculation ? 'block' : 'none';
        document.getElementById('error-container').style.display = showError ? 'block' : 'none';
    } else {
        if (showCalculation) {
            showModal();
        }

        if (showError) {
            showErrorModal();
        }
    }
}

function setSr() {
    SR = (
        document.getElementById('srInput').value === null
        || document.getElementById('srInput').value === undefined
    ) ? '' : document.getElementById('srInput').value.trim();
}

function addEventListeners() {
    document.getElementById('phoneInput').addEventListener('input', function () {
        document.getElementById("shippingPhoneInput").value = this.value;
    });
}

function fillBenevityOrganizationalDetails() {
    if (benevityCause !== null) {
        updateBenevityCauseContent(benevityCause);
    }
}

function updateBenevityCauseContent(benevityIdInput) {
    const logoElement = document.getElementById('cause-logo');
    const logo =
        benevityIdInput.logoUrl !== null ?
            benevityIdInput.logoUrl : "https://logos.benevity.org/400x400/public/clogos/123.png";

    logoElement.innerHTML =
        `<img id="cause-logo" src="${logo}" alt="Logo" width="50" height="50" class="me-3">`;

    const nameElement = document.getElementById('cause-name');
    nameElement.textContent = benevityIdInput.name;

    const idElement = document.getElementById('cause-id');
    idElement.textContent = `Benevity ID: ${benevityIdInput.id}`;
}

function redirectToBenevityOrder() {
    if (SR !== '') {
        window.location.href = "/" + SR + "/benevity/order";
    } else {
        window.location.href = "/benevity/order";
    }
}

function redirectToBenevityOrganizationDetails() {
    if (SR !== '') {
        window.location.href = "/" + SR + "/benevity/order/checkout/organization-details";
    } else {
        window.location.href = "/benevity/order/checkout/organization-details";
    }
}

document.addEventListener('DOMContentLoaded', function () {
    setSr();
    const errModalText = document.getElementById('error-modal-text').textContent;

    if (errModalText !== '') {
        switchContainers(false, false, true);
    }

    bundles = sessionStorage.getItem(SS_CART_ITEM) ? JSON.parse(sessionStorage.getItem(SS_CART_ITEM)) : [];

    if (bundles.length <= 0) {
        redirectToBenevityOrder();
    }

    benevityCause =
        sessionStorage.getItem(SS_BENEVITY_CAUSE_ITEM) ? JSON.parse(sessionStorage.getItem(SS_BENEVITY_CAUSE_ITEM)) : null;

    if (benevityCause === null) {
        redirectToBenevityOrganizationDetails();
    }

    const checkout = sessionStorage.getItem(SS_CHECKOUT_ITEM) ? JSON.parse(sessionStorage.getItem(SS_CHECKOUT_ITEM)) : null;

    fillBenevityOrganizationalDetails();

    fillFields(checkout);

    updateCartCounterBadge();

    const checkbox = document.getElementById('checkboxToUseShippingAddressInput');

    // Initial setup on page load
    toggleBillingAddressSection();

    // Add event listener to checkbox
    checkbox.addEventListener('change', toggleBillingAddressSection);

    addEventListeners();
    const campaignStartInput = document.getElementById('campaignStartDateInput');
    const today = new Date().toISOString().split('T')[0];
    campaignStartInput.setAttribute('min', today);
    campaignStartInput.addEventListener('input', () => {
        const selectedDate = new Date(campaignStartInput.value);
        const todayDate = new Date(today);
        if (selectedDate < todayDate) {
            campaignStartInput.value = '';
        }
    });
    campaignStartInput.addEventListener('keydown', (e) => e.preventDefault());
});

