let sr;

const svgContents = {
    remove: "<svg width=\"14\" height=\"15\" viewBox=\"0 0 14 15\" fill=\"none\"\n" +
        "                                             xmlns=\"http://www.w3.org/2000/svg\">\n" +
        "                                            <path d=\"M10.5 4L3.5 11\" stroke=\"#222222\" stroke-width=\"0.583333\"\n" +
        "                                                  stroke-linecap=\"round\"\n" +
        "                                                  stroke-linejoin=\"round\"/>\n" +
        "                                            <path d=\"M3.5 4L10.5 11\" stroke=\"#222222\" stroke-width=\"0.583333\"\n" +
        "                                                  stroke-linecap=\"round\"\n" +
        "                                                  stroke-linejoin=\"round\"/>\n" +
        "                                        </svg>&nbsp;",
    cart: "<svg width=\"15\" height=\"18\" viewBox=\"0 0 15 18\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
        "    <g clip-path=\"url(#clip0_308_364)\">\n" +
        "        <path d=\"M12.6414 18.0001H1.98349C1.96792 17.9924 1.95307 17.9811 1.93645 17.9776C0.824373 17.7311 0.238805 16.9849 0.294675 15.8482C0.369639 14.322 0.460868 12.7969 0.54644 11.2711C0.647217 9.47094 0.751178 7.67081 0.850186 5.87031C0.87706 5.38013 1.11433 5.1442 1.60478 5.14349C2.36326 5.14244 3.12173 5.14349 3.88021 5.14349C3.9428 5.14349 4.00503 5.14349 4.08177 5.14349C4.08177 5.04348 4.08141 4.96813 4.08177 4.89279C4.08636 4.24872 4.06444 3.60323 4.10086 2.96128C4.2073 1.09317 6.04817 -0.307288 7.88973 0.0585851C9.45656 0.369877 10.5149 1.63688 10.5318 3.2282C10.5379 3.7962 10.5329 4.36421 10.5329 4.93222C10.5329 4.99522 10.5329 5.05826 10.5329 5.14349C10.6139 5.14349 10.6765 5.14349 10.739 5.14349C11.5152 5.14349 12.2914 5.14102 13.0675 5.14453C13.4738 5.14629 13.7429 5.3921 13.7652 5.78404C13.8522 7.30353 13.9342 8.82369 14.0194 10.3432C14.0842 11.4947 14.1517 12.6458 14.2164 13.7973C14.2581 14.5397 14.3143 15.2816 14.332 16.0246C14.3504 16.7997 13.9961 17.3892 13.3168 17.7638C13.111 17.8772 12.8674 17.9234 12.6414 18.0001ZM9.23907 5.1347C9.23907 4.43429 9.24581 3.75641 9.23449 3.07854C9.23164 2.91233 9.18778 2.74119 9.13265 2.58272C8.825 1.6985 7.91765 1.1629 7.00182 1.31537C6.07501 1.46961 5.39294 2.24643 5.37527 3.18277C5.36536 3.71521 5.37279 4.24763 5.37242 4.78043C5.37242 4.89593 5.37242 5.01142 5.37242 5.1347H9.23907Z\" fill=\"#9E007E\"/>\n" +
        "    </g>\n" +
        "    <defs>\n" +
        "        <clipPath id=\"clip0_308_364\">\n" +
        "            <rect width=\"14.625\" height=\"18\" fill=\"white\"/>\n" +
        "        </clipPath>\n" +
        "    </defs>\n" +
        "</svg>",
    emptyCart: "<svg fill=\"#9E007E\" width=\"48px\" height=\"48px\" viewBox=\"0 0 32 32\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
        "<title>times</title>\n" +
        "<path d=\"M17.769 16l9.016-9.016c0.226-0.226 0.366-0.539 0.366-0.884 0-0.691-0.56-1.251-1.251-1.251-0.346 0-0.658 0.14-0.885 0.367v0l-9.015 9.015-9.016-9.015c-0.226-0.226-0.539-0.366-0.884-0.366-0.69 0-1.25 0.56-1.25 1.25 0 0.345 0.14 0.658 0.366 0.884v0l9.015 9.016-9.015 9.015c-0.227 0.226-0.367 0.539-0.367 0.885 0 0.691 0.56 1.251 1.251 1.251 0.345 0 0.658-0.14 0.884-0.366v0l9.016-9.016 9.015 9.016c0.227 0.228 0.541 0.369 0.888 0.369 0.691 0 1.251-0.56 1.251-1.251 0-0.347-0.141-0.661-0.369-0.887l-0-0z\"></path>\n" +
        "</svg>"
}

let bundles = [];

const oneDeviceBundleTemplate = {
    title: "1 Device Bundle"
};

const threeDeviceBundleTemplate = {
    title: "3 Devices Bundle"
};

function generateCartList() {
    const container = document.getElementById("cart-list-container");
    container.innerHTML = ""; // Clear container before rendering
    container.style.padding = "20px 26px";

    // Create a single table to contain all bundles
    const table = document.createElement('table');
    table.className = "table table-borderless";
    table.style.marginBottom = "20px";

    let tableContent = '<thead></thead><tbody>';

    // Loop through each bundle and add its content to the table
    bundles.forEach((item, index) => {
        tableContent += generateBundleItemTable(item, index);

        if (index < bundles.length - 1) {
            tableContent += generateHorizontalRule();
        }
    });

    tableContent += '</tbody>';
    table.innerHTML = tableContent;

    container.appendChild(table);

    addEventListeners();
}

function generateBundleItemTable(bundle, index) {
    return `
        <tr>
            <td colspan="3" style="font-weight: bold; background-color: #FBFBFB; padding-bottom: 0; white-space: nowrap;">${bundle.title || 'Device'}</td>
            <td style="text-align: right; font-weight: bold; background-color: #FBFBFB; padding-bottom: 0; white-space: nowrap;">Price: $${centsToDollars(bundle.bundlePrice)}</td>
        </tr>
        <tr>
            <td colspan="3" style="background-color: #FBFBFB; padding-top: 0;"></td>
            <td style="text-align: right; font-size: 12px; background-color: #FBFBFB; padding-top: 0; white-space: nowrap;"><i>First Month Payment: $${centsToDollars(bundle.firstMonthPayment)}</i></td>
        </tr>
        <tr>
            <td style="font-weight: 300; background-color: #FBFBFB; color: #5D5D5D; white-space: nowrap;">Device</td>
            <td style="font-weight: 300; background-color: #FBFBFB; color: #5D5D5D; white-space: nowrap;">Solution</td>
            <td style="font-weight: 300; background-color: #FBFBFB; color: #5D5D5D; white-space: nowrap;">Subscription Term</td>
            <td style="font-weight: 300; background-color: #FBFBFB; color: #5D5D5D; white-space: nowrap;">Payment Option</td>
        </tr>
        <tr>
            <td style="background-color: #FBFBFB; color: black; white-space: nowrap;">${bundle.deviceDenomination}</td>
            <td style="background-color: #FBFBFB; color: black; white-space: nowrap;">${bundle.solutionSelection}</td>
            <td style="background-color: #FBFBFB; color: black; white-space: nowrap;">${bundle.subscriptionTerm}</td>
            <td style="background-color: #FBFBFB; color: black; white-space: nowrap;">${bundle.paymentOption}</td>
        </tr>
        <tr>
            <td colspan="3" style="background-color: #FBFBFB;"></td>
            <td style="text-align: right; background-color: #FBFBFB;">
                ${generateRemoveBundleButton(index)} <!-- Pass index here -->
            </td>
        </tr>
    `;
}

function generateHorizontalRule() {
    return `
        <tr>
            <td colspan="4" style="padding: 0; background-color: #FBFBFB;">
                <hr style="margin: 10px 0; border: none; border-top: 1px solid #ccc;">
            </td>
        </tr>
    `;
}

function generateRemoveBundleButton(index) {
    return `
        <button class="btn btn-link removeButtonInList" data-index="${index}" style="display: inline-flex; align-items: center; border: none; padding: 0; text-decoration: none; color: black;">
            ${svgContents.remove}
            <span style="font-size: 14px; padding-left: 8px;">Remove</span>
        </button>
    `;
}

// Function to add event listeners
function addEventListeners() {
    // Select all remove buttons
    const removeButtons = document.querySelectorAll('.removeButtonInList');

    // Iterate over each button and add click event listener
    removeButtons.forEach((button) => {
        button.addEventListener('click', async function () {
            const index = parseInt(button.getAttribute('data-index')); // Get the index from the button

            if (index !== -1) {
                bundles.splice(index, 1); // Remove the bundle from the array

                await setBundlesPriceKeyAndCalculate();

                sessionStorage.setItem('cart', JSON.stringify(bundles));
                generateCartList(); // Re-render table after removal
                generateSummaryBarDeviceTable();

                updateSummaryBarTotal();
                updateCartCounterBadge();
            }
        });
    });
}

function generateSummaryBarDeviceTable() {
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
                        style="color: black; font-weight: bold;">${bundle.title}
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

function reviewAndCheckout() {
    const cart = sessionStorage.getItem('cart') || null;

    if (cart !== null && cart.length > 0) {
        if(sr !== ''){
            window.location.href = "/" + sr + "/bundles/order/checkout/shipping";
        } else {
            window.location.href = "/bundles/order/checkout/shipping";
        }
    } else {
        if(sr !== ''){
            window.location.href = "/" + sr + "/bundles";
        } else {
            window.location.href = "/bundles";
        }
    }
}

function generateEmptyCart() {
    const container = document.getElementById("cart-list-container");
    container.innerHTML = ""; // Clear container before rendering
    const div = document.createElement('div');
    div.className = "";
    div.id = `emptyCartContainer`;
    div.style.backgroundColor = "rgb(251, 251, 251)";
    div.style.padding = "16px 20px";

    div.innerHTML = `
    <div class="text-center" style="background-color: rgb(251, 251, 251);">
        <div>${svgContents.emptyCart}</div>
        <div class="ttp-color-fuchsia">Empty Cart</div>
    </div>`
    ;

    container.appendChild(div);
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

function updateCartCounterBadge() {
    document.getElementById('cart-counter-badge').textContent = '' + bundles.length;
}

function centsToDollars(cents) {
    let dollars = cents / 100;
    return dollars.toFixed(2);
}

async function setBundlesPriceKeyAndCalculate() {
    try {
        const response = await fetch(`/api/bundles/calculate${sr !== '' ? ('?sr='+sr) : ''}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(bundles)
        });

        if (response.ok) {
            bundles = await response.json();
        } else {
            console.error("Failed to calculate bundles:", response.status);
            switchContainers(false, true);
        }
    } catch (error) {
        console.error("Error in calculation:", error);
        switchContainers(false, true);
    }
}

// Function to show the modal
function showServerErrorModal() {
    const myModal = new bootstrap.Modal(document.getElementById('serverErrorModal'));
    myModal.show();
}

// Function to hide the modal
function hideServerErrorModal() {
    const myModal = new bootstrap.Modal(document.getElementById('serverErrorModal'));
    myModal.hide();
}

function switchContainers(showMain, showError) {
    if (window.innerWidth < 769) {
        document.getElementById('main-container').style.display = showMain ? 'block' : 'none';
        document.getElementById('server-error-container').style.display = showError ? 'block' : 'none';
    } else {
        if (showError) {
            showServerErrorModal();
        }
    }
}

function customizeLink() {
    if(sr !== ''){
        window.location.href = "/" + sr + "/bundles/order/customize";
    } else {
        window.location.href = "/bundles/order/customize";
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
    bundles = sessionStorage.getItem('cart') ? JSON.parse(sessionStorage.getItem('cart')) : [];

    if (bundles.length > 0) {
        generateCartList();
    } else {
        generateEmptyCart();
    }

    generateSummaryBarDeviceTable();

    updateSummaryBarTotal();
    updateCartCounterBadge();
});