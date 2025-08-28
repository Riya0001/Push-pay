let SR;
let checkout;
let benevityCause;
let bundles = [];
let shippingAndFees;

const URL_ORDER = '/benevity/education/order';
const URL_ORGANIZATION_DETAILS =  '/benevity/education/order/checkout/organization-details';
const URL_PAYMENT =  '/benevity/education/order/checkout/payment';
const INPUT_NAME_ON_CARD_ID =  'nameOnCardInput';
const INPUT_CARD_NUMBER_INPUT_ID =  'cardNumberInput';
const INPUT_CVV_INPUT_ID =  'cvvInput';
const INPUT_EXP_DATE_ID =  'expirationDateInput';
const MODAL_MESSAGE_P5 =  'messageP5Modal';

const INPUT_SUMMARY_BAR_TOTAL_ID =  'summary-bar-total';
const INPUT_SUMMARY_BAR_SHIPPING_ID =  'summary-bar-shipping';
const INPUT_SUMMARY_BAR_FEES_ID =  'summary-bar-fees';
const INPUT_CART_COUNTER_BADGE =  'cart-counter-badge';

const DATA_NAME_SR = 'srInput';

const SS_CART_ITEM = 'cart' + STORE_INITIAL;
const SS_CHECKOUT_ITEM = 'checkout' + STORE_INITIAL;
const SS_BENEVITY_CAUSE_ITEM = 'benevityCause' + STORE_INITIAL;

const FORM = document.getElementById('cart-checkout-page-1-form');

// Add a submit event listener
FORM.addEventListener('submit', function (event) {
    event.preventDefault();

    const formData = new FormData(FORM);
    let formDataObj = {};

    for (const [key, value] of formData.entries()) {
        formDataObj[key] = value;
    }

    postData(formDataObj);
    hideP5Modal();
});

function cleanData() {
    document.getElementById(INPUT_NAME_ON_CARD_ID).value = "";
    document.getElementById(INPUT_CARD_NUMBER_INPUT_ID).value = "";
    document.getElementById(INPUT_CVV_INPUT_ID).value = "";
    document.getElementById(INPUT_EXP_DATE_ID).value = "";
}

function maskData() {
    let nameOnCardInput = document.getElementById(INPUT_NAME_ON_CARD_ID);
    nameOnCardInput.value = nameOnCardInput.value.trim().split(' ').map(s => s.charAt(0) + '***').join(' ');

    let cardNumberInput = document.getElementById(INPUT_CARD_NUMBER_INPUT_ID);
    let cardNumber = cardNumberInput.value.trim().replace(/\s+/g, '');

    if (cardNumber.length > 4) {
        cardNumberInput.value = '*'.repeat(cardNumber.length - 4) + cardNumber.slice(-4);
    } else {
        cardNumberInput.value = cardNumber;
    }

    let cvvInput = document.getElementById(INPUT_CVV_INPUT_ID);
    cvvInput.value = '*'.repeat(cvvInput.value.trim().length);

    let expirationDateInput = document.getElementById(INPUT_EXP_DATE_ID);
    expirationDateInput.value = expirationDateInput.value.trim().replace(/^(\d{2})\/(\d{2})$/, '**/$2');
}

// Function to show the modal and prevent closing
function showMessageP5Modal() {
    const myModal = new bootstrap.Modal(document.getElementById(MODAL_MESSAGE_P5), {
        backdrop: 'static', // Prevent closing when clicking outside
        keyboard: false     // Prevent closing when pressing Escape
    });
    myModal.show();
}

// Function to hide the modal
function hideP5Modal() {
    const myModal = new bootstrap.Modal(document.getElementById(MODAL_MESSAGE_P5));
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
    document.getElementById(INPUT_SUMMARY_BAR_TOTAL_ID).textContent = '$' + centsToDollars(getTotalPrice());

    // Add calculated shipping and fees
    document.getElementById(INPUT_SUMMARY_BAR_SHIPPING_ID).textContent = '$' + centsToDollars(shippingAndFees.shippingPrice);
    document.getElementById(INPUT_SUMMARY_BAR_FEES_ID).textContent = '$' + centsToDollars(shippingAndFees.otherFees);
}

function getTotalPrice() {
    let total = 0;
    bundles.forEach((bundle) => {
        total += bundle.firstMonthPayment;
    })

    // Add shipping and Fees
    total += Number(shippingAndFees.shippingPrice);
    total += Number(shippingAndFees.otherFees);

    return total;
}

function onPrevious() {
    window.history.back();
}

function updateCartCounterBadge() {
    document.getElementById(INPUT_CART_COUNTER_BADGE).textContent = '' + bundles.length;
}

function formatCardNumber(input) {
    // Remove all non-digit characters
    let value = input.value.replace(/\D/g, '');

    // Limit the length to a maximum of 19 digits
    if (value.length > 19) {
        value = value.slice(0, 19);
    }

    // Format the number into groups of 4
    const formattedValue = value.match(/.{1,4}/g)?.join(' ') || '';

    // Set the formatted value back to the input
    input.value = formattedValue;

    // Allow up to 23 characters for the input (including spaces)
    if (input.value.length > 23) {
        input.value = input.value.slice(0, 23);
    }
}

function formatExpirationDate(input) {
    // Remove all non-digit characters
    let value = input.value.replace(/\D/g, '');

    // Limit the length to 4 digits (MMYY)
    if (value.length > 4) {
        value = value.slice(0, 4);
    }

    // Format the input as MM/YY
    if (value.length > 2) {
        value = value.slice(0, 2) + '/' + value.slice(2);
    }

    // Set the formatted value back to the input
    input.value = value;
}

function postData(formDataObj) {
    maskData();
    switchContainers(false, true);

    // Prepare the data
    const cartJson = JSON.stringify(bundles);
    const benevityJson = JSON.stringify(benevityCause);
    const paymentJson = JSON.stringify(formDataObj);
    const checkoutJson = sessionStorage.getItem(SS_CHECKOUT_ITEM) ? JSON.parse(sessionStorage.getItem(SS_CHECKOUT_ITEM)) : null;

    // Create a form element
    const form = document.createElement('form');
    form.method = 'POST';

    if (SR !== '') {
        form.action = '/' + SR + URL_PAYMENT;
    } else {
        form.action = URL_PAYMENT;
    }

    // Create input elements for each part of checkoutInfo
    const cartInput = document.createElement('input');
    cartInput.type = 'hidden';
    cartInput.name = 'cart';
    cartInput.value = cartJson

    const benevityInput = document.createElement('input');
    benevityInput.type = 'hidden';
    benevityInput.name = 'benevityCause';
    benevityInput.value = benevityJson;

    const checkoutInput = document.createElement('input');
    checkoutInput.type = 'hidden';
    checkoutInput.name = 'checkout';
    checkoutInput.value = JSON.stringify(checkoutJson)

    const paymentInput = document.createElement('input');
    paymentInput.type = 'hidden';
    paymentInput.name = 'payment';
    paymentInput.value = paymentJson

    // Append each input to the form
    form.appendChild(cartInput);
    form.appendChild(benevityInput);
    form.appendChild(checkoutInput);
    form.appendChild(paymentInput);

    // Append the form to the body and submit it
    document.body.appendChild(form);
    form.submit();
}

function centsToDollars(cents) {
    let dollars = cents / 100;
    return dollars.toFixed(2);
}

function switchContainers(showMain, showProcessing) {
    if (window.innerWidth < 769) {
        hideP5Modal();
        document.getElementById('main-p5-container').style.setProperty('display', showMain ? 'block' : 'none', 'important');
        document.getElementById('processing-container').style.setProperty('display', showProcessing ? 'block' : 'none', 'important');
    } else {
        if (showProcessing) {
            showMessageP5Modal();
        }
    }
}

function setSr() {
    SR = (
        document.getElementById(DATA_NAME_SR).value === null
        || document.getElementById(DATA_NAME_SR).value === undefined
    ) ? '' : document.getElementById(DATA_NAME_SR).value.trim();
}

function redirectToBenevityOrder() {
    if (SR !== '') {
        window.location.href = "/" + SR + URL_ORDER;
    } else {
        window.location.href = URL_ORDER;
    }
}

function redirectToBenevityOrganizationDetails() {
    if (SR !== '') {
        window.location.href = "/" + SR + URL_ORGANIZATION_DETAILS;
    } else {
        window.location.href = URL_ORGANIZATION_DETAILS;
    }
}

document.addEventListener('DOMContentLoaded', function () {
    setSr();
    hideP5Modal();
    cleanData();

    bundles = sessionStorage.getItem(SS_CART_ITEM) ? JSON.parse(sessionStorage.getItem(SS_CART_ITEM)) : [];

    if (bundles.length <= 0) {
        redirectToBenevityOrder();
    }

    benevityCause =
        sessionStorage.getItem(SS_BENEVITY_CAUSE_ITEM) ? JSON.parse(sessionStorage.getItem(SS_BENEVITY_CAUSE_ITEM)) : null;

    if (benevityCause === null) {
        redirectToBenevityOrganizationDetails();
    }

    checkout = sessionStorage.getItem(SS_CHECKOUT_ITEM) ? JSON.parse(sessionStorage.getItem(SS_CHECKOUT_ITEM)) : null;

    if (checkout === null) {
        redirectToBenevityOrganizationDetails();
    }

    const shippingFee = parseFloat(document.getElementById('shippingFeeInput').value);
    const taxFee = parseFloat(document.getElementById('taxFeeInput').value);

    shippingAndFees = {
        shippingPrice: Math.trunc(shippingFee * 100),
        otherFees: Math.trunc(taxFee * 100)
    }

    generateSummaryBarDeviceTable(bundles);

    updateSummaryBarTotal();
    updateCartCounterBadge();
});
