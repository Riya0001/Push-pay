let sr;
const svgContents = {
    deviceDenomination: "<svg width=\"16\" height=\"17\" viewBox=\"0 0 16 17\" fill=\"none\"\n" +
        "                                 xmlns=\"http://www.w3.org/2000/svg\">\n" +
        "                            <g clip-path=\"url(#clip0_431_1880)\">\n" +
        "                                <path fill-rule=\"evenodd\" clip-rule=\"evenodd\"\n" +
        "                                      d=\"M4.8 0.5H14.4C15.28 0.5 16 1.22 16 2.1V11.7C16 12.58 15.28 13.3 14.4 13.3H4.8C3.92 13.3 3.2 12.58 3.2 11.7V2.1C3.2 1.22 3.92 0.5 4.8 0.5ZM0 4.5C0 4.06 0.36 3.7 0.8 3.7C1.24 3.7 1.6 4.06 1.6 4.5V14.1C1.6 14.54 1.96 14.9 2.4 14.9H12C12.44 14.9 12.8 15.26 12.8 15.7C12.8 16.14 12.44 16.5 12 16.5H1.6C0.72 16.5 0 15.78 0 14.9V4.5Z\"\n" +
        "                                      fill=\"#9E007E\"/>\n" +
        "                            </g>\n" +
        "                            <defs>\n" +
        "                                <clipPath id=\"clip0_431_1880\">\n" +
        "                                    <rect width=\"16\" height=\"16\" fill=\"white\" transform=\"translate(0 0.5)\"/>\n" +
        "                                </clipPath>\n" +
        "                            </defs>\n" +
        "                        </svg>",
    solutionSelection: "<svg width=\"17\" height=\"18\" viewBox=\"0 0 16 17\" fill=\"none\"\n" +
        "                                             xmlns=\"http://www.w3.org/2000/svg\">\n" +
        "                                        <g clip-path=\"url(#clip0_431_1933)\">\n" +
        "                                            <path d=\"M6.50003 16.5005C6.34202 16.5013 6.18752 16.4539 6.05719 16.3645C5.92685 16.2752 5.82689 16.1482 5.77066 16.0005L4.52566 12.763C4.50044 12.6978 4.46187 12.6385 4.41241 12.5891C4.36296 12.5396 4.30371 12.5011 4.23847 12.4758L1.00003 11.2299C0.852515 11.1733 0.725632 11.0733 0.636131 10.943C0.54663 10.8128 0.498718 10.6585 0.498718 10.5005C0.498718 10.3425 0.54663 10.1882 0.636131 10.058C0.725632 9.92778 0.852515 9.82777 1.00003 9.77115L4.23753 8.52615C4.30277 8.50093 4.36202 8.46236 4.41147 8.4129C4.46093 8.36345 4.4995 8.3042 4.52472 8.23896L5.77066 5.00052C5.82728 4.85301 5.92729 4.72612 6.05751 4.63662C6.18773 4.54712 6.34202 4.49921 6.50003 4.49921C6.65804 4.49921 6.81234 4.54712 6.94255 4.63662C7.07277 4.72612 7.17279 4.85301 7.22941 5.00052L8.47441 8.23802C8.49962 8.30326 8.5382 8.36251 8.58765 8.41197C8.63711 8.46142 8.69636 8.5 8.76159 8.52521L11.9803 9.76365C12.1339 9.82055 12.2661 9.92339 12.3591 10.0581C12.4521 10.1929 12.5013 10.353 12.5 10.5168C12.4976 10.672 12.4487 10.823 12.3596 10.9501C12.2704 11.0773 12.1452 11.1747 12 11.2299L8.76253 12.4749C8.69729 12.5001 8.63805 12.5387 8.58859 12.5881C8.53913 12.6376 8.50056 12.6968 8.47534 12.7621L7.22941 16.0005C7.17317 16.1482 7.07321 16.2752 6.94288 16.3645C6.81255 16.4539 6.65805 16.5013 6.50003 16.5005Z\"\n" +
        "                                                  fill=\"#9E007E\"/>\n" +
        "                                            <path d=\"M2.75003 6.00052C2.65739 6.00052 2.56693 5.97245 2.49056 5.92001C2.41419 5.86757 2.3555 5.79323 2.32222 5.70677L1.79535 4.33677C1.78393 4.30681 1.7663 4.2796 1.74363 4.25693C1.72095 4.23426 1.69375 4.21663 1.66378 4.20521L0.293783 3.67834C0.207338 3.64505 0.133007 3.58635 0.08058 3.50998C0.0281534 3.43362 9.15527e-05 3.34316 9.15527e-05 3.25052C9.15527e-05 3.15789 0.0281534 3.06743 0.08058 2.99106C0.133007 2.9147 0.207338 2.856 0.293783 2.82271L1.66378 2.29584C1.69372 2.28437 1.7209 2.26672 1.74356 2.24406C1.76623 2.22139 1.78387 2.19421 1.79535 2.16427L2.31753 0.806461C2.34698 0.726525 2.39766 0.656124 2.46411 0.602827C2.53057 0.549529 2.6103 0.51535 2.69472 0.503961C2.79608 0.49164 2.89865 0.513508 2.98617 0.566099C3.07369 0.618689 3.14115 0.698992 3.17785 0.794274L3.70472 2.16427C3.71619 2.19421 3.73383 2.22139 3.7565 2.24406C3.77917 2.26672 3.80635 2.28437 3.83628 2.29584L5.20628 2.82271C5.29273 2.856 5.36706 2.9147 5.41949 2.99106C5.47191 3.06743 5.49997 3.15789 5.49997 3.25052C5.49997 3.34316 5.47191 3.43362 5.41949 3.50998C5.36706 3.58635 5.29273 3.64505 5.20628 3.67834L3.83628 4.20521C3.80632 4.21663 3.77911 4.23426 3.75644 4.25693C3.73376 4.2796 3.71614 4.30681 3.70472 4.33677L3.17785 5.70677C3.14457 5.79323 3.08587 5.86757 3.00951 5.92001C2.93314 5.97245 2.84267 6.00052 2.75003 6.00052Z\"\n" +
        "                                                  fill=\"#9E007E\"/>\n" +
        "                                            <path d=\"M12.5 8.50052C12.399 8.50049 12.3003 8.46984 12.217 8.4126C12.1337 8.35536 12.0697 8.27423 12.0335 8.1799L11.3197 6.32459C11.3072 6.29189 11.2879 6.2622 11.2631 6.23744C11.2384 6.21268 11.2087 6.19339 11.176 6.18084L9.32066 5.46709C9.2264 5.43077 9.14535 5.36675 9.08819 5.28347C9.03103 5.20018 9.00043 5.10154 9.00043 5.00052C9.00043 4.89951 9.03103 4.80087 9.08819 4.71758C9.14535 4.6343 9.2264 4.57028 9.32066 4.53396L11.176 3.82021C11.2087 3.80766 11.2384 3.78837 11.2631 3.76361C11.2879 3.73884 11.3072 3.70915 11.3197 3.67646L12.0282 1.83427C12.0605 1.74716 12.1158 1.67044 12.1883 1.61228C12.2608 1.55411 12.3477 1.51668 12.4397 1.50396C12.5503 1.49057 12.6622 1.51451 12.7577 1.57197C12.8531 1.62944 12.9267 1.71713 12.9666 1.82115L13.6803 3.67646C13.6929 3.70915 13.7122 3.73884 13.7369 3.76361C13.7617 3.78837 13.7914 3.80766 13.8241 3.82021L15.6794 4.53396C15.7737 4.57028 15.8547 4.6343 15.9119 4.71758C15.969 4.80087 15.9996 4.89951 15.9996 5.00052C15.9996 5.10154 15.969 5.20018 15.9119 5.28347C15.8547 5.36675 15.7737 5.43077 15.6794 5.46709L13.8241 6.18084C13.7914 6.19339 13.7617 6.21268 13.7369 6.23744C13.7122 6.2622 13.6929 6.29189 13.6803 6.32459L12.9666 8.1799C12.9303 8.27423 12.8663 8.35536 12.7831 8.4126C12.6998 8.46984 12.6011 8.50049 12.5 8.50052Z\"\n" +
        "                                                  fill=\"#9E007E\"/>\n" +
        "                                        </g>\n" +
        "                                        <defs>\n" +
        "                                            <clipPath id=\"clip0_431_1933\">\n" +
        "                                                <rect width=\"16\" height=\"16\" fill=\"white\" transform=\"translate(0 0.5)\"/>\n" +
        "                                            </clipPath>\n" +
        "                                        </defs>\n" +
        "                                    </svg>",
    subscriptionTerm: "<svg width=\"18\" height=\"18\" viewBox=\"0 0 20 20\" fill=\"none\"\n" +
        "                                 xmlns=\"http://www.w3.org/2000/svg\">\n" +
        "                            <path d=\"M11.6667 12.5L8.33337 15.8333L11.6667 19.1667\" stroke=\"#9E007E\"\n" +
        "                                  stroke-width=\"1.875\"/>\n" +
        "                            <path d=\"M15.0519 7.08335C15.5638 7.97013 15.8334 8.97606 15.8334 10C15.8334 11.024 15.5638 12.0299 15.0519 12.9167C14.5399 13.8035 13.8035 14.5399 12.9167 15.0518C12.0299 15.5638 11.024 15.8334 10 15.8334\"\n" +
        "                                  stroke=\"#9E007E\" stroke-width=\"1.875\" stroke-linecap=\"round\"/>\n" +
        "                            <path d=\"M8.33329 7.5L11.6666 4.16667L8.33329 0.833333\" stroke=\"#9E007E\"\n" +
        "                                  stroke-width=\"1.875\"/>\n" +
        "                            <path d=\"M4.94814 12.9166C4.43616 12.0299 4.16663 11.0239 4.16663 9.99998C4.16663 8.97602 4.43616 7.97009 4.94814 7.08331C5.46013 6.19653 6.19651 5.46015 7.08329 4.94817C7.97007 4.43618 8.976 4.16665 9.99996 4.16665\"\n" +
        "                                  stroke=\"#9E007E\" stroke-width=\"1.875\" stroke-linecap=\"round\"/>\n" +
        "                        </svg>",
    paymentOption: "<svg width=\"22\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\"\n" +
        "                                 xmlns=\"http://www.w3.org/2000/svg\">\n" +
        "                            <rect x=\"3\" y=\"6\" width=\"18\" height=\"13\" rx=\"2\" stroke=\"#9E007E\" stroke-width=\"2\"/>\n" +
        "                            <path d=\"M7 15H7.01\" stroke=\"#9E007E\" stroke-width=\"2\" stroke-linecap=\"round\"/>\n" +
        "                            <path d=\"M4 11H21\" stroke=\"#9E007E\" stroke-width=\"2\" stroke-linecap=\"round\"/>\n" +
        "                        </svg>",
    remove: "<svg width=\"14\" height=\"15\" viewBox=\"0 0 14 15\" fill=\"none\"\n" +
        "                                             xmlns=\"http://www.w3.org/2000/svg\">\n" +
        "                                            <path d=\"M10.5 4L3.5 11\" stroke=\"#222222\" stroke-width=\"0.583333\"\n" +
        "                                                  stroke-linecap=\"round\"\n" +
        "                                                  stroke-linejoin=\"round\"/>\n" +
        "                                            <path d=\"M3.5 4L10.5 11\" stroke=\"#222222\" stroke-width=\"0.583333\"\n" +
        "                                                  stroke-linecap=\"round\"\n" +
        "                                                  stroke-linejoin=\"round\"/>\n" +
        "                                        </svg>&nbsp;"
}


// Select the form
const cartCheckoutPage2Form = document.getElementById('cart-checkout-page-1-form');

// Initialize bundle array and render the initial set of cards
let bundles = [];

let shippingAndFees;

// Add a submit event listener
cartCheckoutPage2Form.addEventListener('submit', function(event) {
    event.preventDefault();

    const formData = new FormData(cartCheckoutPage2Form);
    let formDataObj = {};

    for (const [key, value] of formData.entries()) {
        formDataObj[key] = value;
    }

    postData(formDataObj);
    hideP5Modal();
});

function cleanData() {
    document.getElementById('nameOnCardInput').value = "";
    document.getElementById('cardNumberInput').value = "";
    document.getElementById('cvvInput').value = "";
    document.getElementById('expirationDateInput').value = "";
}

function maskData() {
    let nameOnCardInput = document.getElementById('nameOnCardInput');
    nameOnCardInput.value = nameOnCardInput.value.trim().split(' ').map(s => s.charAt(0) + '***').join(' ');

    let cardNumberInput = document.getElementById('cardNumberInput');
    let cardNumber = cardNumberInput.value.trim().replace(/\s+/g, '');

    if (cardNumber.length > 4) {
        cardNumberInput.value = '*'.repeat(cardNumber.length - 4) + cardNumber.slice(-4);
    } else {
        cardNumberInput.value = cardNumber;
    }

    let cvvInput = document.getElementById('cvvInput');
    cvvInput.value = '*'.repeat(cvvInput.value.trim().length);

    let expirationDateInput = document.getElementById('expirationDateInput');
    expirationDateInput.value = expirationDateInput.value.trim().replace(/^(\d{2})\/(\d{2})$/, '**/$2');
}

// Function to show the modal and prevent closing
function showMessageP5Modal() {
    const myModal = new bootstrap.Modal(document.getElementById('messageP5Modal'), {
        backdrop: 'static', // Prevent closing when clicking outside
        keyboard: false     // Prevent closing when pressing Escape
    });
    myModal.show();
}

// Function to hide the modal
function hideP5Modal() {
    const myModal = new bootstrap.Modal(document.getElementById('messageP5Modal'));
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

    // Add calculated shipping and fees
    document.getElementById('summary-bar-shipping').textContent = '$' + centsToDollars(shippingAndFees.shippingPrice);
    document.getElementById('summary-bar-fees').textContent = '$' + centsToDollars(shippingAndFees.otherFees);
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
    document.getElementById('cart-counter-badge').textContent = '' + bundles.length;
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
    const paymentJson = JSON.stringify(formDataObj);
    const checkoutJson = sessionStorage.getItem('checkout') ? JSON.parse(sessionStorage.getItem('checkout')) : null;

    // Create a FormData object to hold the values
    const formData = new FormData();
    formData.append('cart', cartJson);
    formData.append('checkout', paymentJson);

    // Create a form element
    const form = document.createElement('form');
    form.method = 'POST';

    if(sr !== ''){
        form.action = '/' + sr + '/bundles/order/checkout/payment';
    } else {
        form.action = '/bundles/order/checkout/payment';
    }

    // Create input elements for each part of checkoutInfo
    const cartInput = document.createElement('input');
    cartInput.type = 'hidden';
    cartInput.name = 'cart';
    cartInput.value = cartJson

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
    sr = (
        document.getElementById('srInput').value === null
        || document.getElementById('srInput').value === undefined
    ) ? '' : document.getElementById('srInput').value.trim();
}

document.addEventListener('DOMContentLoaded', function () {
    setSr();
    hideP5Modal();
    cleanData();

    bundles = sessionStorage.getItem('cart') ? JSON.parse(sessionStorage.getItem('cart')) : [];

    if(bundles.length <= 0) {
        if(sr !== ''){
            window.location.href = "/" + sr + "/bundles/order";
        } else {
            window.location.href = "/bundles/order";
        }
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
