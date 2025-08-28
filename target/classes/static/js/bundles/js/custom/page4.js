let sr;
// Select the form
const cartCheckoutPage1Form = document.getElementById('cart-checkout-page-1-form');

let bundles = [];

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

    sessionStorage.setItem('checkout', JSON.stringify(formDataObj));

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

function generateSummaryBarDeviceTable(bundles) {
    const container = document.getElementById("summary-bar-container");
    container.innerHTML = ""; // Clear container before rendering

    bundles.forEach((bundle, index) => {
        const card = document.createElement('div');
        card.className = "card card-body";
        card.id = `accordionExample_${index}`;
        card.style.backgroundColor = "white";
        card.style.paddingTop = "0";

        card.innerHTML = `
            <div class="accordion-item">
              <h2 class="accordion-header ttp-bg-gray" id="summary-bar-device_${index}" style="border-radius: 5px 5px 0 0;">
                <button class="accordion-button ${index === -1 ? '' : 'collapsed'}" type="button" data-bs-toggle="collapse"
                        data-bs-target="#collapseOne_${index}" aria-expanded="${index === -1 ? 'true' : 'false'}" aria-controls="collapseOne_${index}"
                        style="color: black; font-weight: bold;">
                    <span class="ttp-color-fuchsia">#${index + 1}&nbsp;:&nbsp;</span> ${bundle.title}
                </button>
               </h2>
                <div id="collapseOne_${index}" class="accordion-collapse collapse ${index === -1 ? 'show' : ''}" data-bs-parent="#accordionExample_${index}"
                     style="background-color: #FBFBFB;">
                    <div class="accordion-body" style="padding: 0 20px 10px 20px">
                        <div class="selected-bundle" id="selected-bundle-item-${index}">

                            <table class="table table-borderless">
                                <tbody>
                                    <tr>
                                        <td style="text-align: start; padding-left: 0; background-color: #FBFBFB; color: #979797;">
                                            Devices
                                        </td>
                                        <td style="text-align: end; padding-right: 0; background-color: #FBFBFB;"
                                        id="summary-bar-device-denomination_${index}">
                                            ${bundle.deviceDenomination || '--'}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="text-align: start; padding-left: 0; background-color: #FBFBFB; color: #979797;">
                                            Solution
                                        </td>
                                        <td style="text-align: end; padding-right: 0; background-color: #FBFBFB;"
                                        id="summary-bar-solution-selection_${index}">
                                            ${bundle.solutionSelection || '--'}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="text-align: start; padding-left: 0; background-color: #FBFBFB; color: #979797;">
                                            Subscription Term
                                        </td>
                                        <td style="text-align: end; padding-right: 0; background-color: #FBFBFB;"
                                        id="summary-bar-subscription-term_${index}">
                                            ${bundle.subscriptionTerm || '--'}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="text-align: start; padding-left: 0; background-color: #FBFBFB; color: #979797;">
                                            Payment Option
                                        </td>
                                        <td style="text-align: end; padding-right: 0; background-color: #FBFBFB;"
                                        id="summary-bar-payment-option_${index}">
                                            ${bundle.paymentOption || '--'}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="text-align: start; padding-left: 0; background-color: #FBFBFB; color: #979797;">
                                            Bundle Price
                                        </td>
                                        <td style="text-align: end; padding-right: 0; background-color: #FBFBFB; font-weight: bold;"
                                         id="summary-bar-bundle-price_${index}">
                                            ${bundle.bundlePrice ? `$${centsToDollars(bundle.bundlePrice)}` : '$0'}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="text-align: start; padding-left: 0; background-color: #FBFBFB; color: #979797;">
                                            First Month Payment
                                        </td>
                                        <td style="text-align: end; padding-right: 0; background-color: #FBFBFB;"
                                        id="summary-bar-first-month-payment_${index}">
                                            ${bundle.firstMonthPayment ? `$${centsToDollars(bundle.firstMonthPayment)}` : '$0'}
                                        </td>
                                    </tr>
                                </tbody>
                            </table>

                        </div>
                    </div>
                </div>
            </div>
            <hr style="margin: 0;">
        `;

        container.appendChild(card);
    });
}

function updateSummaryBarTotal() {
    document.getElementById('summary-bar-total').textContent = '$' + centsToDollars(getTotalPrice());
}

function getTotalPrice() {
    let total = 0;
    bundles.forEach((bundle) => {
        total += bundle.firstMonthPayment;
    })
    return total;
}

function onPrevious() {
    window.history.back();
}

function updateCartCounterBadge() {
    document.getElementById('cart-counter-badge').textContent = '' + bundles.length;
}

function fillFields(checkout) {
    if (checkout !== null) {
        document.getElementById('organizationNameInput').value = checkout.organizationName;
        document.getElementById('phoneInput').value = checkout.phone;
        document.getElementById('countryForSettingsInput').value = checkout.countryForSettings;
        document.getElementById('emailInput').value = checkout.email;
        document.getElementById('campaignStartDateInput').value = checkout.campaignStartDate;
        initialCampaignStartDateAction();

        document.getElementById('shippingCountryInput').value = checkout.shippingCountry;
        updatePostalCodePattern('shipping');

        document.getElementById('shippingFullNameInput').value = checkout.shippingFullName;
        document.getElementById('shippingAddressLine1Input').value = checkout.shippingAddressLine1;
        document.getElementById('shippingAddressLine2Input').value = checkout.shippingAddressLine2;
        document.getElementById('shippingCityInput').value = checkout.shippingCity;
        document.getElementById('shippingZipCodeInput').value = checkout.shippingZipCode;
        document.getElementById('shippingStateInput').value = checkout.shippingState;
        document.getElementById('shippingPhoneInput').value = checkout.shippingPhone;
        document.getElementById('shippingEmailInput').value = checkout.shippingEmail;

        document.getElementById('checkboxToUseShippingAddressInput').checked = checkout.checkboxToUseShippingAddress;

        document.getElementById('billingCountryInput').value = checkout.billingCountry;
        updatePostalCodePattern('billing');

        document.getElementById('billingFullNameInput').value = checkout.billingFullName;
        document.getElementById('billingAddressLine1Input').value = checkout.billingAddressLine1;
        document.getElementById('billingAddressLine2Input').value = checkout.billingAddressLine2;
        document.getElementById('billingCityInput').value = checkout.billingCity;
        document.getElementById('billingZipCodeInput').value = checkout.billingZipCode;
        document.getElementById('billingStateInput').value = checkout.billingState;
        document.getElementById('billingPhoneInput').value = checkout.billingPhone;
        document.getElementById('billingEmailInput').value = checkout.billingEmail;
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
    document.getElementById('billingStateInput').value = document.getElementById('shippingStateInput').value;
    document.getElementById('billingPhoneInput').value = document.getElementById('shippingPhoneInput').value;
    document.getElementById('billingEmailInput').value = document.getElementById('shippingEmailInput').value;
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
    const checkoutJson = JSON.stringify(formDataObj);

    // Create a FormData object to hold the values
    const formData = new FormData();
    formData.append('cart', cartJson);
    formData.append('checkout', checkoutJson);

    // Create a form element
    const form = document.createElement('form');
    form.method = 'POST';
    if (sr !== '') {
        form.action = '/' + sr + '/bundles/order/checkout/shipping';
    } else {
        form.action = '/bundles/order/checkout/shipping';
    }

    // Create input elements for each part of checkoutInfo
    const cartInput = document.createElement('input');
    cartInput.type = 'hidden';
    cartInput.name = 'cart';
    cartInput.value = cartJson;

    const checkoutInput = document.createElement('input');
    checkoutInput.type = 'hidden';
    checkoutInput.name = 'checkout';
    checkoutInput.value = checkoutJson;

    // Append each input to the form
    form.appendChild(cartInput);
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

function centsToDollars(cents) {
    let dollars = cents / 100;
    return dollars.toFixed(2);
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
    sr = (
        document.getElementById('srInput').value === null
        || document.getElementById('srInput').value === undefined
    ) ? '' : document.getElementById('srInput').value.trim();
}

function addEventListeners() {
    document.getElementById('phoneInput').addEventListener('input', function () {
        document.getElementById("shippingPhoneInput").value = this.value;
    });

    document.getElementById('emailInput').addEventListener('input', function () {
        document.getElementById("shippingEmailInput").value = this.value;
    });

    document.getElementById('campaignStartDateInput').addEventListener('change', calculateDaysFromToday);
}

function setCampaignStartDateMin() {
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('campaignStartDateInput').setAttribute('min', today);
}

function calculateDaysFromToday() {
    const selectedDate = document.getElementById('campaignStartDateInput').value;
    const today = new Date().toISOString().split('T')[0];
    const elems = document.getElementsByClassName('daysDiff');
    let validDiv, invalidDiv, defaultDiv = false;

    if (selectedDate) {
        const todayDate = new Date(today);
        const userDate = new Date(selectedDate);

        const timeDiff = userDate - todayDate;
        const dayDiff = timeDiff / (1000 * 60 * 60 * 24);

        if (dayDiff >= 30) {
            validDiv = true;
        } else {
            invalidDiv = true;
        }

        for (let i = 0; i < elems.length; i++) {
            elems[i].textContent = dayDiff;
        }
    } else {
        defaultDiv = true;
    }

    showByID('campaign-start-date-helper-valid', validDiv);
    showByID('campaign-start-date-helper-invalid', invalidDiv);
    showByID('campaign-start-date-helper-default', defaultDiv);
}

function initialCampaignStartDateAction() {
    calculateDaysFromToday();
}

function showByID(id, isShow) {
    if (isShow) {
        document.getElementById(id).style.display = 'block';
    } else {
        document.getElementById(id).style.display = 'none';
    }
}

document.addEventListener('DOMContentLoaded', function () {
    setSr();
    setCampaignStartDateMin();
    const errModalText = document.getElementById('error-modal-text').textContent

    if (errModalText !== '') {
        switchContainers(false, false, true);
    }

    bundles = sessionStorage.getItem('cart') ? JSON.parse(sessionStorage.getItem('cart')) : [];

    if (bundles.length <= 0) {
        if (sr !== '') {
            window.location.href = "/" + sr + "/bundles/order";
        } else {
            window.location.href = "/bundles/order";
        }
    }

    const checkout = sessionStorage.getItem('checkout') ? JSON.parse(sessionStorage.getItem('checkout')) : null;
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
