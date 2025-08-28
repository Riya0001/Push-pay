let SR;
const DATA_NAME_SR = 'srInput';
const SS_GET_STARTED_ITEM = 'getStartedItem' + STORE_INITIAL;

// Pricing plans array
const plansMonthly = [
    {
        title: "5 Device Bundle",
        subtitle: "6-Month Subscription",
        currencySymbol: "$",
        price: "139",
        pricePeriod: " / month x 6",
        pricePeriodExp: "",
        description: "Half a Year, Five Devices, Full Flexibility",
        mostPopular: false,
        separator: " ",
        moreDetails: {
            contentOne: {
                title: "What you get:",
                list: [
                    "One Floor Stand with 3 tiptap Devices",
                    "One Counter Display with 1 tiptap Device",
                    "One Lanyard Display with 1 Custom Donation Device",
                    "Battery (refurbished)",
                    "Standard Signage",
                    "All Setup and Activation Fees"
                ],
            },
            contentTwo: {
                title: "Associated Fees",
                list: [
                    "Merchant Account Fee: $0",
                    "tiptap Processing Fees: 3.5% and $0.20 per transaction",
                    "Benevity Processing Fees: 2.9%"
                ],
            }
        },
        default: {
            title: "5 Device Bundle",
            deviceDenomination: "",
            solutionSelection: SOLUTION_SELECTION,
            subscriptionTerm: "6 Months",
            paymentOption: "Monthly",
            bundlePrice: 0,
            firstMonthPayment: 0,
            secondMonthAndBeyond: 0,
            payInFull: 0,
            priceKey: 'NONE',
            priceKeyIndex: -1
        }
    },
    {
        title: "5 Device Bundle",
        subtitle: "1 Year Subscription",
        currencySymbol: "$",
        price: "109",
        pricePeriod: " / month x 12",
        pricePeriodExp: "",
        description: "One Year, Five Devices, Maximum Savings",
        mostPopular: true,
        separator: " ",
        moreDetails: {
            contentOne: {
                title: "What you get:",
                list: [
                    "One Floor Stand with 3 tiptap Devices",
                    "One Counter Display with 1 tiptap Device",
                    "One Lanyard Display with 1 Custom Donation Device",
                    "Battery (refurbished)",
                    "Standard Signage",
                    "All Setup and Activation Fees"
                ],
            },
            contentTwo: {
                title: "Associated Fees",
                list: [
                    "Merchant Account Fee: $0",
                    "tiptap Processing Fees: 3.5% and $0.20 per transaction",
                    "Benevity Processing Fees: 2.9%"
                ],
            }
        },
        default: {
            title: "5 Device Bundle",
            deviceDenomination: "",
            solutionSelection: SOLUTION_SELECTION,
            subscriptionTerm: "1 Year",
            paymentOption: "Monthly",
            bundlePrice: 0,
            firstMonthPayment: 0,
            secondMonthAndBeyond: 0,
            payInFull: 0,
            priceKey: 'NONE',
            priceKeyIndex: -1
        }
    },
];

const plansYearly = [
    {
        title: "5 Device Bundle",
        subtitle: "6-Month Subscription",
        currencySymbol: "$",
        price: "795",
        pricePeriod: " For six months",
        pricePeriodExp: "",
        description: "Half a Year, Five Devices, Full Flexibility",
        mostPopular: false,
        separator: " ",
        moreDetails: {
            contentOne: {
                title: "What you get:",
                list: [
                    "One Floor Stand with 3 tiptap Devices",
                    "One Counter Display with 1 tiptap Device",
                    "One Lanyard Display with 1 Custom Donation Device",
                    "Battery (refurbished)",
                    "Standard Signage",
                    "All Setup and Activation Fees"
                ],
            },
            contentTwo: {
                title: "Associated Fees",
                list: [
                    "Merchant Account Fee: $0",
                    "tiptap Processing Fees: 3.5% and $0.20 per transaction",
                    "Benevity Processing Fees: 2.9%"
                ],
            }
        },
        default: {
            title: "5 Device Bundle",
            deviceDenomination: "",
            solutionSelection: SOLUTION_SELECTION,
            subscriptionTerm: "6 Months",
            paymentOption: "Pay In Full",
            bundlePrice: 0,
            firstMonthPayment: 0,
            secondMonthAndBeyond: 0,
            payInFull: 0,
            priceKey: 'NONE',
            priceKeyIndex: -1
        }
    },
    {
        title: "5 Device Bundle",
        subtitle: "1 Year Subscription",
        currencySymbol: "$",
        price: "1,245",
        pricePeriod: " For twelve months",
        pricePeriodExp: "",
        description: "One Year, Five Devices, Maximum Savings",
        mostPopular: true,
        separator: " ",
        moreDetails: {
            contentOne: {
                title: "What you get:",
                list: [
                    "One Floor Stand with 3 tiptap Devices",
                    "One Counter Display with 1 tiptap Device",
                    "One Lanyard Display with 1 Custom Donation Device",
                    "Battery (refurbished)",
                    "Standard Signage",
                    "All Setup and Activation Fees"
                ],
            },
            contentTwo: {
                title: "Associated Fees",
                list: [
                    "Merchant Account Fee: $0",
                    "tiptap Processing Fees: 3.5% and $0.20 per transaction",
                    "Benevity Processing Fees: 2.9%"
                ],
            }
        },
        default: {
            title: "5 Device Bundle",
            deviceDenomination: "",
            solutionSelection: SOLUTION_SELECTION,
            subscriptionTerm: "1 Year",
            paymentOption: "Pay In Full",
            bundlePrice: 0,
            firstMonthPayment: 0,
            secondMonthAndBeyond: 0,
            payInFull: 0,
            priceKey: 'NONE',
            priceKeyIndex: -1
        }
    },
];

const toggleInput = document.getElementById('toggle');

// Function to generate the card HTML dynamically
function generateCard(plan, index) {
    const contentOne = plan.moreDetails.contentOne.list.map(item => `
        <tr>
            <td class="left-align-center" style="width: 20px">
                <svg width="14" height="14" viewBox="0 0 19 16" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path fill-rule="evenodd" clip-rule="evenodd" d="M18.6226 1.90211L6.71762 15.5078L0.708984 9.49912L2.16739 8.04072L6.61716 12.4905L17.0704 0.543945L18.6226 1.90211Z" fill="#1D4ED8"/>
                </svg>
            </td>
           <td class="left-align-center" style="font-size: 14px">${item}</td>
        </tr>
    `).join(''); // Join all rows into a single string

    const contentTwo = plan.moreDetails.contentTwo.list.map(item => `
        <tr>
          <td class="left-align-center" style="width: 20px">
                <svg width="14" height="14" viewBox="0 0 19 16" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path fill-rule="evenodd" clip-rule="evenodd" d="M18.6226 1.90211L6.71762 15.5078L0.708984 9.49912L2.16739 8.04072L6.61716 12.4905L17.0704 0.543945L18.6226 1.90211Z" fill="#1D4ED8"/>
                </svg>
            </td>
          <td class="left-align-center" style="font-size: 14px">${item}</td>
        </tr>
    `).join(''); // Join all rows into a single string


    return `
<div class="col-sm-6">
    <div class="card ${plan.mostPopular ? 'most-popular' : ''}">
        ${plan.mostPopular ? `
            <div class="most-popular-badge text-center">
                <span class="most-popular-text">Most Popular <svg width="15" height="14" viewBox="0 0 17 16" fill="none" xmlns="http://www.w3.org/2000/svg">
<path d="M7.16681 16.0004C7.0088 16.0012 6.8543 15.9538 6.72397 15.8644C6.59363 15.7751 6.49367 15.6481 6.43744 15.5004L5.19244 12.2629C5.16722 12.1977 5.12865 12.1384 5.07919 12.089C5.02973 12.0395 4.97049 12.0009 4.90525 11.9757L1.66681 10.7298C1.51929 10.6732 1.39241 10.5731 1.30291 10.4429C1.21341 10.3127 1.1655 10.1584 1.1655 10.0004C1.1655 9.84239 1.21341 9.6881 1.30291 9.55788C1.39241 9.42766 1.51929 9.32764 1.66681 9.27103L4.90431 8.02603C4.96955 8.00081 5.0288 7.96224 5.07825 7.91278C5.12771 7.86333 5.16628 7.80408 5.1915 7.73884L6.43744 4.5004C6.49405 4.35288 6.59407 4.226 6.72429 4.1365C6.85451 4.047 7.0088 3.99909 7.16681 3.99909C7.32482 3.99909 7.47912 4.047 7.60933 4.1365C7.73955 4.226 7.83957 4.35288 7.89619 4.5004L9.14119 7.7379C9.1664 7.80314 9.20497 7.86239 9.25443 7.91184C9.30389 7.9613 9.36314 7.99987 9.42837 8.02509L12.6471 9.26353C12.8006 9.32043 12.9329 9.42326 13.0259 9.55803C13.1189 9.69279 13.1681 9.85293 13.1668 10.0167C13.1644 10.1719 13.1155 10.3229 13.0264 10.45C12.9372 10.5772 12.812 10.6746 12.6668 10.7298L9.42931 11.9748C9.36407 12 9.30482 12.0386 9.25537 12.088C9.20591 12.1375 9.16734 12.1967 9.14212 12.262L7.89619 15.5004C7.83995 15.6481 7.73999 15.7751 7.60966 15.8644C7.47932 15.9538 7.32483 16.0012 7.16681 16.0004Z" fill="white"/>
<path d="M3.41681 5.5004C3.32417 5.5004 3.23371 5.47233 3.15734 5.41989C3.08097 5.36745 3.02228 5.29311 2.989 5.20665L2.46212 3.83665C2.4507 3.80669 2.43308 3.77948 2.41041 3.75681C2.38773 3.73413 2.36052 3.71651 2.33056 3.70509L0.960561 3.17821C0.874117 3.14493 0.799785 3.08623 0.747359 3.00986C0.694932 2.93349 0.66687 2.84303 0.66687 2.7504C0.66687 2.65777 0.694932 2.56731 0.747359 2.49094C0.799785 2.41457 0.874117 2.35588 0.960561 2.32259L2.33056 1.79571C2.36049 1.78424 2.38768 1.7666 2.41034 1.74393C2.43301 1.72127 2.45065 1.69408 2.46212 1.66415L2.98431 0.306339C3.01376 0.226403 3.06444 0.156002 3.13089 0.102705C3.19735 0.0494072 3.27707 0.0152275 3.3615 0.00383935C3.46286 -0.00848245 3.56543 0.0133859 3.65295 0.0659766C3.74047 0.118567 3.80792 0.19887 3.84462 0.294152L4.3715 1.66415C4.38297 1.69408 4.40061 1.72127 4.42328 1.74393C4.44595 1.7666 4.47313 1.78424 4.50306 1.79571L5.87306 2.32259C5.95951 2.35588 6.03384 2.41457 6.08626 2.49094C6.13869 2.56731 6.16675 2.65777 6.16675 2.7504C6.16675 2.84303 6.13869 2.93349 6.08626 3.00986C6.03384 3.08623 5.95951 3.14493 5.87306 3.17821L4.50306 3.70509C4.4731 3.71651 4.44589 3.73413 4.42322 3.75681C4.40054 3.77948 4.38292 3.80669 4.3715 3.83665L3.84462 5.20665C3.81134 5.29311 3.75265 5.36745 3.67628 5.41989C3.59991 5.47233 3.50945 5.5004 3.41681 5.5004Z" fill="white"/>
<path d="M13.1668 8.0004C13.0658 8.00037 12.9671 7.96972 12.8838 7.91248C12.8005 7.85524 12.7365 7.77411 12.7002 7.67978L11.9865 5.82446C11.9739 5.79177 11.9547 5.76208 11.9299 5.73732C11.9051 5.71255 11.8754 5.69327 11.8427 5.68071L9.98744 4.96696C9.89318 4.93064 9.81213 4.86663 9.75497 4.78334C9.69781 4.70006 9.66721 4.60142 9.66721 4.5004C9.66721 4.39939 9.69781 4.30074 9.75497 4.21746C9.81213 4.13418 9.89318 4.07016 9.98744 4.03384L11.8427 3.32009C11.8754 3.30753 11.9051 3.28825 11.9299 3.26349C11.9547 3.23872 11.9739 3.20903 11.9865 3.17634L12.6949 1.33415C12.7273 1.24704 12.7826 1.17032 12.8551 1.11215C12.9276 1.05399 13.0144 1.01656 13.1065 1.00384C13.2171 0.99045 13.329 1.01439 13.4245 1.07185C13.5199 1.12931 13.5934 1.21701 13.6334 1.32103L14.3471 3.17634C14.3597 3.20903 14.379 3.23872 14.4037 3.26349C14.4285 3.28825 14.4582 3.30753 14.4909 3.32009L16.3462 4.03384C16.4404 4.07016 16.5215 4.13418 16.5787 4.21746C16.6358 4.30074 16.6664 4.39939 16.6664 4.5004C16.6664 4.60142 16.6358 4.70006 16.5787 4.78334C16.5215 4.86663 16.4404 4.93064 16.3462 4.96696L14.4909 5.68071C14.4582 5.69327 14.4285 5.71255 14.4037 5.73732C14.379 5.76208 14.3597 5.79177 14.3471 5.82446L13.6334 7.67978C13.5971 7.77411 13.5331 7.85524 13.4498 7.91248C13.3665 7.96972 13.2679 8.00037 13.1668 8.0004Z" fill="white"/>
</svg>
</span>
            </div>` : ''}
              <div class="card-body">
                    <h5 class="card-title ttp-color-fuchsia">${plan.title}</h5>
                    <h5 class="text-black">${plan.subtitle}</h5>
                    <p class="text-muted">${plan.description}</p>
                    <div>
                        <span class="h3 text-black fw-bold">${plan.currencySymbol + plan.price}</span>
                        <span class="text-muted"> ${plan.separator} ${plan.pricePeriod} ${plan.pricePeriodExp}</span>
                    </div>
                    <a href="#" class="btn ttp-btn-primary mt-3 w-100" onclick="onNext(${index})">Get Started</a>
                
                    <div class="mt-3">
                        <div class="text-muted small" style="font-weight: bold;">${plan.moreDetails.contentOne.title}</div>
                        <table class="table table-borderless" style="margin-bottom: 0">
                            <tbody>
                                ${contentOne}
                            </tbody>
                        </table>
                        <br>
                        <div class="text-muted small" style="font-weight: bold;">${plan.moreDetails.contentTwo.title}</div>
                        <table class="table table-borderless" style="margin-bottom: 0">
                            <tbody>
                                ${contentTwo}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
            `;
}

// Get the container element
const pricingPlansContainer = document.getElementById('pricing-plans');
let currentPayPeriod = "yearly";

function setSwitcher() {
    const isChecked = toggleInput.checked;
    const fadeDuration = 150; // duration in milliseconds

    // Fade out the current content
    pricingPlansContainer.style.transition = `opacity ${fadeDuration}ms ease-out`;
    pricingPlansContainer.style.opacity = '0'; // Trigger the fade-out

    setTimeout(() => {
        if (isChecked) { // yearly
            document.getElementById('toggle-yearly').style.color = 'white';
            document.getElementById('toggle-monthly').style.color = 'black';

            // Reset plans container
            pricingPlansContainer.innerHTML = null;

            plansYearly.forEach((plan, index) => {
                pricingPlansContainer.innerHTML += generateCard(plan, index);
            });

            currentPayPeriod = "yearly";
        } else { // monthly
            document.getElementById('toggle-yearly').style.color = 'black';
            document.getElementById('toggle-monthly').style.color = 'white';

            // Reset plans container
            pricingPlansContainer.innerHTML = null;

            plansMonthly.forEach((plan, index) => {
                pricingPlansContainer.innerHTML += generateCard(plan, index);
            });

            currentPayPeriod = "monthly";
        }

        // Fade in the new content
        pricingPlansContainer.style.transition = `opacity ${fadeDuration}ms ease-in`;
        pricingPlansContainer.style.opacity = '1'; // Trigger the fade-in

    }, fadeDuration);
}

function onNext(i) {
    if (currentPayPeriod === 'yearly' || currentPayPeriod === 'monthly') {
        let selectedPlan = null;

        if (currentPayPeriod === 'yearly') {
            selectedPlan = plansYearly[i];
        } else {
            selectedPlan = plansMonthly[i];
        }

        const cart = [1];
        cart[0] = selectedPlan.default;

        sessionStorage.setItem(SS_GET_STARTED_ITEM, JSON.stringify(cart));
        if (SR !== '') {
            window.location.href = "/" + SR + URL_NEXT;
        } else {
            window.location.href = URL_NEXT;
        }
    } else {
        console.error("Unknown Pay Period : " + currentPayPeriod);
    }
}

function changeText() {
    const accordionButtons = document.querySelectorAll('.accordionButton');

    for (let i = 0; i < accordionButtons.length; i++) {
        if (accordionButtons[i].innerText === "View More Details") {
            accordionButtons[i].innerText = "View Less Details";
        } else {
            accordionButtons[i].innerText = "View More Details";
        }
    }
}

function setSr() {
    SR = (
        document.getElementById(DATA_NAME_SR).value === null
        || document.getElementById(DATA_NAME_SR).value === undefined
    ) ? '' : document.getElementById(DATA_NAME_SR).value.trim();
}

document.addEventListener('DOMContentLoaded', function () {
    sessionStorage.clear();
    setSr();

    setSwitcher();

    toggleInput.addEventListener('change', () => {
        setSwitcher();
    });
});