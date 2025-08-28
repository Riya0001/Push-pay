const DATA_NAME_CART = 'cart';
const DATA_NAME_BENEVITY = 'benevityCause';
const DATA_NAME_SR = 'srInput';
const DATA_CAUSE_LOGO_ID = 'cause-logo';
const DATA_CAUSE_NAME_ID = 'cause-name';
const DATA_CAUSE_ID_ID = 'cause-id';
const DATA_BENEVITY_ID_TEXT = 'Benevity ID';
const DATA_BENEVITY_SEARCH_INPUT = 'benevitySearchInput';
const DATA_BENEVITY_SEARCH_INPUT_INFO = 'benevitySearchInputInfo';
const DATA_BENEVITY_SEARCH_RESULTS = 'benevitySearchResults';
const DATA_BENEVITY_ID_INPUT = 'benevityIdInput';
const DATA_BENEVITY_ID_INPUT_INFO = 'benevityIdInputInfo';
const DATA_CART_COUNTER_BADGE = 'cart-counter-badge';

const SECTION_ORG_DETAILS = 'accordion-organizational-details-section';
const SECTION_ORG_FINDER = 'accordion-organization-finder-section';

const URL_BENEVITY_ORDER = '/benevity/order';
const URL_BENEVITY_ORG_DETAILS = '/benevity/order/checkout/organization-details';
const URL_BENEVITY_EMPTY_PNG = 'https://logos.benevity.org/400x400/public/clogos/123.png';

const API_URL_BENEVITY_SEARCH_TERM = '/api/benevity/causes/search?search_term=';
const API_URL_BENEVITY_FIND_CAUSE_ID = '/api/benevity/cause?id=';

const SS_CART_ITEM = 'cart' + STORE_INITIAL;
const SS_CHECKOUT_ITEM = 'checkout' + STORE_INITIAL;
const SS_BENEVITY_CAUSE_ITEM = 'benevityCause' + STORE_INITIAL;

const formElement = getElementById('cart-checkout-page-1-find-cause-form');

let SR;
let bundles = [];
let benevityCause = [];
let selectedBenevityID;

formElement.addEventListener('submit', async function (event) {
    event.preventDefault();

    if (!validateVariable(benevityCause)) {

        if (!validateVariable(selectedBenevityID)) {
            return;
        } else {
            selectedBenevityID = selectedBenevityID.split("/")[0];
            await apiGetCauseById(selectedBenevityID);
        }

        if (!validateVariable(benevityCause)) {
            return;
        }
    }

    postData();
});

function postData() {
    // Prepare the data
    const cartJson = toJsonString(bundles);
    const benevityCauseJson = toJsonString(benevityCause);

    // Create a form element
    const form = document.createElement(TAG_NAME_FORM);
    form.method = HTTP_METHOD_POST;
    if (SR !== '') {
        form.action = '/' + SR + URL_BENEVITY_ORG_DETAILS;
    } else {
        form.action = URL_BENEVITY_ORG_DETAILS;
    }

    // Create input elements for each part of checkoutInfo
    const cartInput = createHiddenInputElement(DATA_NAME_CART, cartJson);
    const benevityCauseInput = createHiddenInputElement(DATA_NAME_BENEVITY, benevityCauseJson);

    // Append each input to the form
    form.appendChild(cartInput);
    form.appendChild(benevityCauseInput);

    // Append the form to the body and submit it
    document.body.appendChild(form);

    form.submit();
}

function setSr() {
    SR = (!validateVariable(DATA_NAME_SR)) ? '' : getValueById(DATA_NAME_SR).trim();
}

function clearBenevitySearch() {
    setValueById(DATA_BENEVITY_SEARCH_INPUT, "");
    setInnerHTMLById(DATA_BENEVITY_SEARCH_RESULTS, "");
}

function clearBenevityIdSearch() {
    setValueById(DATA_BENEVITY_ID_INPUT, "");
    setValueById(DATA_BENEVITY_ID_INPUT_INFO, "");
}

function clearAllSearchInputs() {
    clearBenevitySearch();
    clearBenevityIdSearch();
}

document.getElementById(DATA_BENEVITY_ID_INPUT).addEventListener("input", async function () {
    clearBenevitySearch();
});

document.getElementById(DATA_BENEVITY_SEARCH_INPUT).addEventListener("input", async function () {
    await apiGetCauses(this.value.trim(), true);
});

async function apiGetCauses(query, isUpdateResult) {
    if (query.length >= 1) {
        try {
            const response = await fetch(`${API_URL_BENEVITY_SEARCH_TERM}${query}`, {
                method: HTTP_METHOD_GET,
                headers: {"Accept-Encoding": "utf-8"}
            });

            if (response.ok) {
                if(isUpdateResult) {
                    const data = await response.json();
                    displayBenevitySearchInputResults(data);
                }
            } else {
                console.error("Failed to fetch data", response.status);
                setInnerHTMLById(DATA_BENEVITY_SEARCH_RESULTS,
                    ` <div class="list-group-item">Error: Unable to find causes</div>`
                );
            }
        } catch (error) {
            console.error("Error:", error);
            setInnerHTMLById(DATA_BENEVITY_SEARCH_RESULTS,
                `<div class="list-group-item">Could not find any cause</div>`
            );
        }
    } else {
        setInnerHTMLById(DATA_BENEVITY_SEARCH_RESULTS,"");
    }
}

async function apiGetCauseById(causeId) {
    benevityCause = null;
    sessionStorage.removeItem(SS_BENEVITY_CAUSE_ITEM);
    try {
        const response = await fetch(`${API_URL_BENEVITY_FIND_CAUSE_ID}${causeId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const responseForBenevityCause = await response.json();

            if(responseForBenevityCause.id !== null) {
                benevityCause = responseForBenevityCause;
                sessionStorage.setItem(SS_BENEVITY_CAUSE_ITEM, toJsonString(responseForBenevityCause));
            } else {
                console.error(`Failed to Get Benevity Cause with ID : ${causeId}`);
            }
        } else {
            console.error("Failed to Get Benevity Cause :", response.status);
        }
    } catch (error) {
        console.error("Error :", error);
    }
}

async function onFindBenevityCause(causeId) {
    if(!validateVariable(causeId)) {
        setTextContentById(DATA_BENEVITY_ID_INPUT_INFO, "Please enter an ID")
        return;
    }

    await apiGetCauseById(causeId.trim());

    log(benevityCause);

    if (benevityCause !== null) {
        sessionStorage.setItem(SS_BENEVITY_CAUSE_ITEM, toJsonString(benevityCause));
        updateBenevityCauseContent(benevityCause.id, benevityCause.name, benevityCause.logoUrl);

        causeExistCase();
        setTextContentById(DATA_BENEVITY_ID_INPUT_INFO, "")
    } else {
        causeDoesNotExistCase();
        setTextContentById(DATA_BENEVITY_ID_INPUT_INFO, "Cause does not exist");
    }
}

function causeExistCase() {
    showByID(SECTION_ORG_DETAILS, true);
    showByID(SECTION_ORG_FINDER, false);

    clearAllSearchInputs();
}

function causeDoesNotExistCase() {
    showByID(SECTION_ORG_DETAILS, false);
    showByID(SECTION_ORG_FINDER, true);
}

async function onClearBenevityCause() {
    clearAllSearchInputs();
    benevityCause = null;
    sessionStorage.removeItem(SS_BENEVITY_CAUSE_ITEM);
    sessionStorage.removeItem(SS_CHECKOUT_ITEM);

    causeDoesNotExistCase();
}

function updateBenevityCauseContent(id, name, logo) {
    sessionStorage.removeItem(SS_CHECKOUT_ITEM); // remove if exist

    const logoElement = getElementById(DATA_CAUSE_LOGO_ID);

    const logoUrl = logo ? logo : URL_BENEVITY_EMPTY_PNG;

    logoElement.innerHTML = `<img id="${DATA_CAUSE_LOGO_ID}" src="${logoUrl}" alt="Logo" width="50" height="50" class="me-3">`;

    const nameElement = document.getElementById(DATA_CAUSE_NAME_ID);
    nameElement.textContent = name;

    const idElement = document.getElementById(DATA_CAUSE_ID_ID);
    idElement.textContent = `${DATA_BENEVITY_ID_TEXT}: ${id}`;
}

function displayBenevitySearchInputResults(results) {
    const resultsDiv = document.getElementById(DATA_BENEVITY_SEARCH_RESULTS);
    resultsDiv.innerHTML = ""; // Clear previous results

    log(results)

    if (results.benevity_causes.length > 0) {
        results.benevity_causes.forEach(item => {
            const { id } = item;
            const { name, logo } = item.attributes;
            const idNameLogo = {id, name, logo};

            // Create the result element
            const resultItem = document.createElement("a");
            resultItem.href = `#`;
            resultItem.className = "list-group-item list-group-item-action d-flex align-items-center";
            resultItem.setAttribute(
                "onclick",
                `selectBenevityFromDropdown('${escapeSingleQuotes(toJsonString(idNameLogo))}')`
            );

            // Add logo or placeholder image
            const img = document.createElement("img");
            img.src = logo ? logo : URL_BENEVITY_EMPTY_PNG;
            img.alt = "Logo";
            img.width = 50;
            img.height = 50;
            img.className = "me-3";
            resultItem.appendChild(img);

            // Add the name and ID
            const textDiv = document.createElement("div");
            textDiv.innerHTML = `<strong>${name}</strong><br><small>${`${item.attributes.city}, ${(item.attributes.state || item.attributes.country).name}`} </small><br><small>${DATA_BENEVITY_ID_TEXT}: ${id} </small>`;
            resultItem.appendChild(textDiv);

            // Append to results container
            resultsDiv.appendChild(resultItem);
        });
    } else {
        // Handle no results
        const noResults = document.createElement("div");
        noResults.className = "list-group-item";
        noResults.textContent = "No results found";
        resultsDiv.appendChild(noResults);
    }
}

async function selectBenevityFromDropdown(idNameLogoStr) {
    const idNameLogo = parseJsonString(idNameLogoStr);
    log("Benevity Item Clicked:", idNameLogo);
    clearBenevitySearch();

    updateBenevityCauseContent(idNameLogo.id, idNameLogo.name, idNameLogo.logo)
    selectedBenevityID = idNameLogo.id;
    causeExistCase();
    setTextContentById(DATA_BENEVITY_SEARCH_INPUT_INFO, "")
}

function checkCart() {
    bundles = sessionStorage.getItem(SS_CART_ITEM) ?
        parseJsonString(sessionStorage.getItem(SS_CART_ITEM)) : [];

    if (bundles.length <= 0) {
        if (SR !== '') {
            window.location.href = "/" + SR + URL_BENEVITY_ORDER;
        } else {
            window.location.href = URL_BENEVITY_ORDER;
        }
    }
}

function checkBenevityCause() {
    benevityCause =
        sessionStorage.getItem(SS_BENEVITY_CAUSE_ITEM) ? parseJsonString(sessionStorage.getItem(SS_BENEVITY_CAUSE_ITEM)) : null;
}

function updateCartCounterBadge() {
    setTextContentById(DATA_CART_COUNTER_BADGE, '' + bundles.length)
}

function fillBenevityOrganizationalDetails() {
    if (benevityCause !== null) {
        updateBenevityCauseContent(benevityCause.id, benevityCause.name, benevityCause.logoUrl);
        causeExistCase(benevityCause);
    } else {
        log("No benevity cause to fill");
        causeDoesNotExistCase();

        clearAllSearchInputs();

        apiGetCauses("charity", false)
            .then(() => {
                log("Data fetched successfully");
            })
            .catch((error) => {
                console.error("Error:", error);
            });
    }
}

document.addEventListener('DOMContentLoaded', function () {
    setSr();
    checkCart();
    updateCartCounterBadge();
    checkBenevityCause();
    fillBenevityOrganizationalDetails();
});
