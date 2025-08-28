let SR;
const SS_GET_STARTED_ITEM = 'getStartedItem' + STORE_INITIAL;
const SS_CART_ITEM = 'cart' + STORE_INITIAL;
const SS_DUPLICATE_BUNDLE = 'duplicateBundle' + STORE_INITIAL;

const svgContents = {
    bundleSelection: "<svg width=\"20\" height=\"20\" viewBox=\"0 0 20 24\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
        "    <path fill-rule=\"evenodd\" clip-rule=\"evenodd\" d=\"M12.8557 2.45908C12.9092 2.59158 12.9255 2.7548 12.9581 3.08124C13.0199 3.69847 13.0507 4.00709 13.1549 4.19074C13.381 4.58951 13.8492 4.78348 14.2911 4.66139C14.4946 4.60515 14.7346 4.40875 15.2147 4.01595L15.2147 4.01594C15.4686 3.80821 15.5956 3.70434 15.7271 3.64846C16.009 3.52861 16.3304 3.54464 16.599 3.69195C16.7243 3.76064 16.8403 3.87663 17.0723 4.1086L17.8913 4.92761L17.8913 4.92762C18.1233 5.1596 18.2393 5.27559 18.308 5.40087C18.4553 5.66951 18.4713 5.99085 18.3514 6.27281C18.2956 6.40431 18.1917 6.53127 17.9839 6.78518C17.5911 7.26528 17.3947 7.50534 17.3385 7.70884C17.2164 8.15068 17.4104 8.61895 17.8091 8.84506C17.9928 8.9492 18.3014 8.98006 18.9187 9.04178C19.2451 9.07443 19.4084 9.09075 19.5409 9.14422C19.825 9.25886 20.0409 9.49741 20.1267 9.79151C20.1667 9.92868 20.1667 10.0927 20.1667 10.4208V11.5791C20.1667 11.9071 20.1667 12.0711 20.1267 12.2082C20.0409 12.5024 19.825 12.741 19.5408 12.8557C19.4083 12.9091 19.2451 12.9254 18.9188 12.958C18.3017 13.0197 17.9932 13.0506 17.8096 13.1547C17.4107 13.3808 17.2167 13.8492 17.3389 14.2911C17.3951 14.4945 17.5914 14.7345 17.9841 15.2144C18.1917 15.4682 18.2956 15.5951 18.3514 15.7265C18.4713 16.0085 18.4553 16.33 18.3079 16.5987C18.2393 16.7239 18.1233 16.8398 17.8914 17.0717L17.0723 17.8908L17.0723 17.8908C16.8403 18.1228 16.7244 18.2388 16.5991 18.3075C16.3304 18.4548 16.0091 18.4708 15.7271 18.351C15.5956 18.2951 15.4687 18.1912 15.2148 17.9835C14.7347 17.5907 14.4946 17.3943 14.2911 17.338C13.8493 17.216 13.381 17.4099 13.1549 17.8087C13.0508 17.9923 13.0199 18.301 12.9582 18.9184C12.9255 19.245 12.9092 19.4083 12.8557 19.5409C12.741 19.8249 12.5025 20.0407 12.2085 20.1265C12.0713 20.1666 11.9072 20.1666 11.579 20.1666H10.4209C10.0928 20.1666 9.92878 20.1666 9.79161 20.1266C9.4975 20.0408 9.25895 19.8249 9.14431 19.5408C9.09085 19.4083 9.07452 19.245 9.04188 18.9186C8.98015 18.3013 8.94929 17.9927 8.84514 17.809C8.61904 17.4103 8.15078 17.2163 7.70895 17.3384C7.50543 17.3946 7.26537 17.591 6.78525 17.9839C6.53132 18.1916 6.40436 18.2955 6.27285 18.3514C5.99089 18.4712 5.66957 18.4552 5.40093 18.3079C5.27564 18.2392 5.15965 18.1232 4.92766 17.8912L4.92765 17.8912L4.10867 17.0722C3.87668 16.8403 3.76069 16.7243 3.69199 16.599C3.54469 16.3303 3.52867 16.009 3.6485 15.727C3.70439 15.5955 3.80827 15.4686 4.01602 15.2147C4.40884 14.7346 4.60524 14.4945 4.66147 14.291C4.78356 13.8492 4.58959 13.3809 4.19083 13.1548C4.00718 13.0506 3.69856 13.0198 3.08132 12.958C2.75489 12.9254 2.59167 12.9091 2.45917 12.8556C2.17505 12.741 1.95915 12.5024 1.87336 12.2083C1.83334 12.0711 1.83334 11.9071 1.83334 11.579V10.4209C1.83334 10.0927 1.83334 9.92865 1.87338 9.79144C1.95919 9.49739 2.17503 9.25889 2.45908 9.14425C2.59162 9.09076 2.75489 9.07443 3.08144 9.04178C3.69887 8.98003 4.00759 8.94916 4.1913 8.84496C4.58994 8.61886 4.78385 8.1507 4.66185 7.70894C4.60563 7.50536 4.40915 7.26521 4.01618 6.78492L4.01618 6.78491C3.80832 6.53087 3.70439 6.40384 3.64849 6.27227C3.52873 5.99038 3.54475 5.66915 3.69197 5.40057C3.76068 5.27522 3.87673 5.15917 4.10881 4.92709L4.10882 4.92708L4.92769 4.10821C5.15969 3.87621 5.27568 3.76022 5.40097 3.69152C5.66961 3.54423 5.99094 3.5282 6.27289 3.64803C6.4044 3.70392 6.53138 3.80782 6.78536 4.01562L6.78536 4.01562C7.2654 4.40837 7.50541 4.60475 7.70884 4.66099C8.15076 4.78317 8.61916 4.58915 8.84526 4.19027C8.94934 4.00666 8.98019 3.69814 9.04189 3.08112C9.07452 2.75479 9.09084 2.59163 9.14427 2.45916C9.25891 2.17497 9.49752 1.95903 9.79171 1.87324C9.92883 1.83325 10.0928 1.83325 10.4208 1.83325H11.5791C11.9072 1.83325 12.0712 1.83325 12.2084 1.87326C12.5025 1.95906 12.7411 2.17495 12.8557 2.45908ZM11 14.6666C13.0251 14.6666 14.6667 13.025 14.6667 10.9999C14.6667 8.97487 13.0251 7.33325 11 7.33325C8.97497 7.33325 7.33334 8.97487 7.33334 10.9999C7.33334 13.025 8.97497 14.6666 11 14.6666Z\" fill=\"#9E007E\"/>\n" +
        "</svg>",
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

// Initialize bundle array and render the initial set of cards
let bundles = [];

const defaultPaymentOptions = ["Monthly", "Yearly"];

const paymentOptionsByTerm = {
    "Monthly": ["Monthly"],
    "6 Months": ["Monthly", "Pay In Full"],
    "1 Year": ["Monthly", "Pay In Full"]
};

const defaultSubscriptionTerms = ["6 Months", "1 Year"];

// Bundle templates
const oneDeviceBundleTemplate = {
    title: "1 Device Bundle",
    denominationOptions: ["$5", "$10", "$20", "$50"],
    solutionOptions: ["Mini Clip", "Medium Counter Display"],
    subscriptionTermOptions: defaultSubscriptionTerms,
    paymentOptions: defaultPaymentOptions,
};

const threeDeviceBundleTemplate = {
    title: "3 Devices Bundle",
    denominationOptions: ["$5 - $10 - $20", "$10 - $20 - $50"],
    solutionOptions: ["Large Counter Display", "Framed Floorstand"],
    subscriptionTermOptions: defaultSubscriptionTerms,
    paymentOptions: defaultPaymentOptions
};

// Function to generate dynamic cards
function generateCards() {
    const container = document.getElementById("bundle-card-container");
    container.innerHTML = ""; // Clear container before rendering

    bundles.forEach((item, index) => {
        const template = item.title === oneDeviceBundleTemplate.title ? oneDeviceBundleTemplate : threeDeviceBundleTemplate;

        const card = document.createElement('div');
        card.className = `card mb-4 accordion created-bundle-card ${validateCardItem(item) ? '' : 'empty-card-border'}`;
        card.id = `accordionExampleDevice_${index}`;
        card.style.backgroundColor = "rgb(251, 251, 251)";

        // Values
        const result = isCollapseBundle(index);
        const isValid = result[0];
        const isCollapse = result[1];

        const titleFormatted = isValid ? `${item.title !== '' ? item.title.toString() : 'bundle'}` :
            `Customize your Bundle`;

        card.innerHTML = `
            <div class="card-body">
                <div class="accordion-item">
                
                    <h2 class="accordion-header text-center">
                            <button class="accordion-button ${isCollapse ? 'collapsed' : ''}" type="button" data-bs-toggle="collapse"
                                data-bs-target="#collapseDevice_${index}" aria-expanded="${isCollapse ? 'false' : 'true'}" aria-controls="collapseDevice_${index}"
                                style="font-weight: bold; background-color: rgb(251, 251, 251);">
                                <span id="checkmarkIcon-card-title_${index}" style="display: ${isValid ? 'inline-block' : 'none'}">
                                    <svg width="20" height="20" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg">
                                        <circle cx="8" cy="8" r="6" stroke="#00C73C"/>
                                        <path d="M5.33333 8L7.33333 10L10.6667 6" stroke="#00C73C"/>
                                    </svg>&nbsp;
                                </span>
                                <span style="color: #9E007E !important;">Bundle #<span id="bundle-card-title-index_${index}">${Number(index + 1)}</span></span>&nbsp;:&nbsp;<span style="color: black;" id="bundle-card-title_${index}">${titleFormatted || 'New Bundle'}</span>&nbsp;<span id="is-in-cart-badge_${index}" class="badge rounded-pill text-bg-warning">New</span>
                        </button>
                    </h2>
                    
                    <div id="collapseDevice_${index}" class="accordion-collapse collapse ${isCollapse ? '' : 'show'}"
                         data-bs-parent="#accordionExampleDevice_${index}" style="background-color: rgb(251, 251, 251);">
                        <div class="accordion-body">
                           
                            <div id="card-secondary-section_${index}">
                                ${generateDeviceDenominationSection(index, item.title, item.deviceDenomination)}
                                ${generateSubscriptionTermSection(index, item.subscriptionTerm, template.subscriptionTermOptions)}
                                ${generatePaymentOptionSection(index, item.paymentOption, item.subscriptionTerm)}
                            </div>
                        </div>
                    </div>
                </div>
                <div style="padding: 0 20px 20px 20px;">
                   ${generateDuplicateAndRemoveBundleButton(index, isValid, isCollapse)}
                </div>
            </div>
        `;

        container.appendChild(card);

        // Add event listeners
        addEventListeners(index);
    });

    processInCartNewBadge();
}

function generateDeviceDenominationSection(index, title, selectedDenomination) {
    const selectedDenominationSplit = selectedDenomination.split(" and ");

    const fsOptions = ["$5 - $10 - $20", "$10 - $20 - $50"];
    const cd1Options = ["$5", "$10", "$20", "$50"];
    const cd2Options = ["Custom Donation Device"];
    const cd2Message = "(Activated By Donor's Mobile Device)";

    function createRadioButtons(options, section, selectedDenomination, forceChecked = false) {
        return options.map((option, idx) => `
        <div class="form-check d-flex align-items-center gap-2 me-4">
            <input class="form-check-input ${!selectedDenomination && !forceChecked ? 'empty-radio' : ''}" 
                type="radio" name="device-denomination-${section}_${index}" 
                id="device-denomination-${section}-${index}-${idx}" 
                value="${option}" 
                ${forceChecked || selectedDenomination === option ? 'checked' : ''} 
            >
            <label class="form-check-label d-flex align-items-center pt-1 ${!forceChecked ? 'empty-label' : ''}"
                for="device-denomination-${section}-${index}-${idx}">
                ${option === cd2Options[0] ?
            `<div class="d-flex flex-column">
                        <span>${option}</span>
                        <span class="text-muted" style="font-size: 13px">${cd2Message}</span>
                    </div>`
            : option}
            </label>
        </div>
    `).join('');
    }

    return `
        <div class="mb-5">
            <div style="display: flex; align-items: center;">
                <span style="padding-right: 10px;">${svgContents.deviceDenomination}</span>
                <span><span class="ttp-text-purple">Display Denominations</span></span>
            </div>
            <div id="device-denomination-text_${index}" class="ttp-text-gray mb-3" style="padding-top: 10px; padding-bottom: 2px; font-size: 14px;">
                Please choose your display denomination
            </div>

            ${[
        {
            id: 'fs',
            label: 'Floor Stand',
            img: 'floor-stand-small.svg',
            radios: createRadioButtons(fsOptions, 'fs', selectedDenominationSplit[0]),
            selectedDenomination: selectedDenominationSplit[0]
        },
        {
            id: 'cd1',
            label: 'Counter Display',
            img: 'counter-display-1-small.svg',
            radios: createRadioButtons(cd1Options, 'cd1', selectedDenominationSplit[1]),
            selectedDenomination: selectedDenominationSplit[1]
        },
        {
            id: 'cd2',
            label: 'Lanyard',
            img: 'lanyard-small.svg',
            radios: createRadioButtons(cd2Options, 'cd2', selectedDenominationSplit[2], true),
            selectedDenomination: selectedDenominationSplit[2]
        }
    ].map(({id, label, img, radios, selectedDenomination}) => `
                <div id="${id}-container" class="d-flex align-content-center mb-3">
                    <div id="img-id_${id}"><img src="/images/products/${img}" alt="Logo" width="50" height="50" class="me-3"></div>
                    <div>
                        <div class="text-muted">${label}</div>
                        <div id="radio-device-denomination-section-${id}_${index}" class="mt-1 d-flex justify-content-start">
                            ${radios}
                            <div class="form-check me-4 checkmark-icon" id="checkmarkIcon-device-denomination-${id}_${index}" 
                                style="display: ${selectedDenomination ? 'inline-block' : 'none'}">
                                <svg width="20" height="20" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <circle cx="8" cy="8" r="6" stroke="#00C73C"/>
                                    <path d="M5.33333 8L7.33333 10L10.6667 6" stroke="#00C73C"/>
                                </svg>
                            </div>
                        </div>
                    </div>
                </div>
            `).join('')}

            <div id="empty-device-denomination-text_${index}" class="empty-text-warnings" 
                style="display: ${!(selectedDenominationSplit[0] || selectedDenominationSplit[1] || selectedDenominationSplit[2]) ? 'none' : 'none'}">
                Please complete selections
            </div>
        </div>
    `;
}


// Function to generate the "Subscription Term" section
function generateSubscriptionTermSection(index, selectedTerm, subscriptionTermOptions) {
    let options = '';
    subscriptionTermOptions.forEach(option => {
        options += `<option value="${option}" ${selectedTerm === option ? 'selected' : ''}>${option}</option>`;
    });
    return `
        <div class="mb-5">
            <div style="display: flex; align-items: center;">
                <span style="padding-right: 10px;">${svgContents.subscriptionTerm}</span>
                <span><span class="ttp-text-purple">Subscription Term</span></span>
            </div>
            <div class="ttp-text-gray" style="padding-top: 10px; padding-bottom: 2px; font-size: 14px;">
                Choose the subscription duration that suits your needs
            </div>
            <div class="mt-2 d-flex justify-content-start">
                <select class="form-select custom-select-with-icon ${selectedTerm === null || selectedTerm === '' ? 'empty-field' : ''}" id="subscription-term_${index}">
                    <option hidden="" id="hidden-subscription-term-option_${index}">Select Term</option>
                    ${options}
                </select>
                
                 <!-- Checkmark icon -->
                <span id="checkmarkIcon-subscription-term_${index}" class="check-icon" style="padding-top: 2.4rem; display: ${selectedTerm === null || selectedTerm === '' ? 'none' : 'inline-block'}">
                  <svg width="20" height="20" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <circle cx="8" cy="8" r="6" stroke="#00C73C"/>
                    <path d="M5.33333 8L7.33333 10L10.6667 6" stroke="#00C73C"/>
                    </svg>
                </span>
                
            </div>
            <div id="empty-subscription-term-text_${index}" class="empty-text-warnings" style="display: ${selectedTerm === null || selectedTerm === '' ? 'block' : 'none'}">
                Please select a subscription term
            </div>
        </div>
    `;
}

// Function to generate the "Payment Option" section
function generatePaymentOptionSection(index, selectedOption, subscriptionTerm) {
    const paymentOptions = paymentOptionsByTerm[subscriptionTerm] || defaultPaymentOptions;

    let options = '';
    paymentOptions.forEach(option => {
        options += `<option value="${option}" ${selectedOption === option ? 'selected' : ''}>${option}</option>`;
    });

    return `
        <div class="mb-4">
            <div style="display: flex; align-items: center;">
                <span style="padding-right: 10px;">${svgContents.paymentOption}</span>
                <span><span class="ttp-text-purple">Payment Option</span></span>
            </div>
            <div class="ttp-text-gray" style="padding-top: 10px; padding-bottom: 2px; font-size: 14px;">
                Decide how you want to pay—opt for monthly payments for flexibility, or go with yearly
                payments to enjoy savings
            </div>
            <div class="mt-2 d-flex justify-content-start">
                <select class="form-select custom-select-with-icon ${selectedOption === null || selectedOption === '' ? 'empty-field' : ''}" id="payment-option_${index}">
                    <option hidden="" id="hidden-payment-option-option_${index}">Select Payment Option</option>
                    ${options}
                </select>
                
                 <!-- Checkmark icon -->
                <span id="checkmarkIcon-payment-option_${index}" class="check-icon" style="padding-top: 2.4rem; display: ${selectedOption === null || selectedOption === '' ? 'none' : 'inline-block'}">
                  <svg width="20" height="20" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <circle cx="8" cy="8" r="6" stroke="#00C73C"/>
                    <path d="M5.33333 8L7.33333 10L10.6667 6" stroke="#00C73C"/>
                    </svg>
                </span>
        
            </div>
            <div id="empty-payment-option-text_${index}" class="empty-text-warnings" style="display: ${selectedOption === null || selectedOption === '' ? 'block' : 'none'}">
                Please select a payment option
            </div>
        </div>
    `;
}

// Function to generate the "Remove Bundle" button
function generateDuplicateAndRemoveBundleButton(index, isBundleValid, isCollapsed) {
    // const isShow = isBundleValid && !isCollapsed;

    return `
        <div class="row mt-2">
            <div class="col-12 col-md-8" id="confirm-duplicate-section_${index}">
                <span id="custom-duplicate-label_${index}" style="font-size: 14px; margin-bottom: 0; display: ${isBundleValid ? 'inline-flex' : 'none'}; align-items: center;">
                    Duplicate this bundle&nbsp;&nbsp;
        
                    <input id="duplicate-quantity_${index}" type="number" min="0" max="200" value="0"
                           style="border: 1px solid #DEDEDE; border-radius: 5px; width: 50px;" 
                           oninput="updateDuplicateQuantity(${index})">&nbsp;&nbsp;times&nbsp;
        
                    <span id="total-quantity-text_${index}"></span>&nbsp;&nbsp;
        
                    <button id="duplicate-quantity-button_${index}" 
                            type="button" onclick="duplicateAction(this.id)" class="btn btn-sm confirmDuplicateButton" 
                            style="border: solid 1px #422055; font-size: 12px; color: #422055; padding: 0 6px; display: none">
                        Confirm Duplicate
                    </button>
                </span>
            </div>
        
            <div class="col-12 col-md-4 text-md-end">
                <button class="btn btn-link remove-bundle" data-index="${index}" 
                        style="border: none; padding: 0; text-decoration: none; color: black;">
                    ${svgContents.remove}
                    <span style="font-size: 14px; padding-left: 5px;">Remove Bundle</span>
                </button>
            </div>
        </div>
    `;
}

function addDeviceDenominationEventListener(index) {
    const sections = ['fs', 'cd1']; // Only fs and cd1

    sections.forEach(section => {
        document.querySelectorAll(`input[name="device-denomination-${section}_${index}"]`).forEach(checkbox => {
            checkbox.addEventListener('change', async function (event) {

                let selectedValues = [];

                if (document.querySelector(`input[name="device-denomination-fs_${index}"]:checked`)) {
                    selectedValues.push(document.querySelector(`input[name="device-denomination-fs_${index}"]:checked`).value);
                }

                if (document.querySelector(`input[name="device-denomination-cd1_${index}"]:checked`)) {
                    selectedValues.push(document.querySelector(`input[name="device-denomination-cd1_${index}"]:checked`).value);
                }

                bundles[index].deviceDenomination = selectedValues.join(" and ") || "";

                document.querySelectorAll(`[name="device-denomination-${section}_${index}"]`).forEach(input => {
                    input.classList.remove('empty-radio');
                    document.querySelector(`label[for="${input.id}"]`).classList.remove('empty-label');
                });

                document.getElementById(`empty-device-denomination-text_${index}`).style.display = 'none';

                document.getElementById(`checkmarkIcon-device-denomination-${section}_${index}`).style.display = 'inline-block';

                document.getElementById(`summary-bar-device-denomination_${index}`).textContent = bundles[index].deviceDenomination;

                if (validateDeviceDenomination(index)) {
                    await setBundlesPriceKeyAndCalculate();
                    updateBundlePriceFirstMonthPriceTotalPrice();
                    processInCartNewBadge();
                    setAddNewBundleButtonVisibility();

                    checkIfRemoveEmptyCardBorderClass(index);
                }
            });
        });
    });
}

function addSubscriptionTermEventListener(index) {
    document.getElementById(`subscription-term_${index}`).addEventListener('change', async function (event) {
        bundles[index].subscriptionTerm = event.target.value;

        // Reset payment option
        bundles[index].paymentOption = '';
        // Reset summary bar payment option
        document.getElementById('summary-bar-payment-option_' + index).textContent = '--';
        showByID('checkmarkIcon-payment-option_' + index, false);


        if (event.target.value !== null || event.target.value !== '' || event.target.value !== undefined) {
            document.getElementById('subscription-term_' + index).classList.remove('empty-field');

            document.getElementById('empty-subscription-term-text_' + index).style.display = 'none';

            showByID('checkmarkIcon-subscription-term_' + index, true);

            checkIfRemoveEmptyCardBorderClass(index);
        }

        // Update Summary Bar
        document.getElementById('summary-bar-subscription-term_' + index).textContent = event.target.value;

        updatePaymentOptions(index);

        // Highlight Payment Option
        document.getElementById('payment-option_' + index).classList.add('empty-field');
        // Highlight Border
        document.getElementById('accordionExampleDevice_' + index).classList.add('empty-card-border');


        await setBundlesPriceKeyAndCalculate();
        updateBundlePriceFirstMonthPriceTotalPrice();

        // Update In Cart/New Badge
        processInCartNewBadge();

        // Set Add New Bundle Button Visibility
        setAddNewBundleButtonVisibility();
    });
}

function addPaymentOptionEventListener(index) {
    document.getElementById(`payment-option_${index}`).addEventListener('change', async function (event) {
        bundles[index].paymentOption = event.target.value;

        if (event.target.value !== null || event.target.value !== '' || event.target.value !== undefined) {
            document.getElementById('payment-option_' + index).classList.remove('empty-field');

            document.getElementById('empty-payment-option-text_' + index).style.display = 'none';

            showByID('checkmarkIcon-payment-option_' + index, true);

            checkIfRemoveEmptyCardBorderClass(index);
        }

        // Update Summary Bar
        document.getElementById('summary-bar-payment-option_' + index).textContent = event.target.value;

        await setBundlesPriceKeyAndCalculate();
        updateBundlePriceFirstMonthPriceTotalPrice();

        // Update In Cart/New Badge
        processInCartNewBadge();

        // Set Add New Bundle Button Visibility
        setAddNewBundleButtonVisibility();
    });
}


function addRemoveBundleEventListener(index) {
    document.querySelector(`.remove-bundle[data-index="${index}"]`).addEventListener('click', async function () {
        bundles.splice(index, 1);  // Remove bundle from array

        await setBundlesPriceKeyAndCalculate();

        generateCards();    // Re-render cards after removal
        generateSummaryBarDeviceTable();

        // updateCartCounterBadge();
        updateSummaryBarTotal();

        // Update In Cart/New Badge
        processInCartNewBadge();

        // Set Add New Bundle Button Visibility
        setAddNewBundleButtonVisibility();
    });
}

// Function to add event listeners
function addEventListeners(index) {
    addDeviceDenominationEventListener(index);
    addSubscriptionTermEventListener(index);
    addPaymentOptionEventListener(index);
    addRemoveBundleEventListener(index);

    setEmptyOptionsTexts(index);
}

// Function to dynamically update payment options based on subscription term selection
function updatePaymentOptions(index) {
    const subscriptionTerm = document.getElementById(`subscription-term_${index}`).value;
    const paymentOptionDropdown = document.getElementById(`payment-option_${index}`);

    const paymentOptions = paymentOptionsByTerm[subscriptionTerm] || defaultPaymentOptions;

    // Clear existing options
    paymentOptionDropdown.innerHTML = `<option hidden="" id="hidden-payment-option-option_${index}">Select Payment Option</option>`;

    // Populate new options based on the selected subscription term
    paymentOptions.forEach(option => {
        const optionElement = document.createElement('option');
        optionElement.value = option;
        optionElement.textContent = option;
        paymentOptionDropdown.appendChild(optionElement);
    });
}

async function addNewBundleAction() {
    updateBundleArrayAction();

    await setBundlesPriceKeyAndCalculate();
    generateCards();
    generateSummaryBarDeviceTable()
    updateSummaryBarTotal();

    // Update In Cart/New Badge
    processInCartNewBadge();

    scrollToNewBundleCard(bundles.length - 1);
}

// Function to update bundle array based on input quantities
function updateBundleArray(bundleTitle, quantity) {
    // Loop through the quantity and add new bundles
    for (let i = 0; i < quantity; i++) {
        let duplicateBundle = null;

        if (bundleTitle === null) {
            duplicateBundle = getDuplicateBundle();
        }

        if (duplicateBundle !== null) {
            bundles.push(duplicateBundle); // Push saved bundle
        } else {
            bundles.push({
                title: bundleTitle,
                deviceDenomination: "",
                solutionSelection: SOLUTION_SELECTION,
                subscriptionTerm: "",
                paymentOption: "",
                bundlePrice: 0,
                firstMonthPayment: 0,
                secondMonthAndBeyond: 0,
                payInFull: 0,
                priceKey: 'NONE',
                priceKeyIndex: -1
            });
        }
    }
}

// Function to update bundle array based on input quantities
function updateBundleArrayAction() {
    bundles.push({
        title: "5 Device Bundle",
        deviceDenomination: "",
        solutionSelection: SOLUTION_SELECTION,
        subscriptionTerm: "",
        paymentOption: "",
        bundlePrice: 0,
        firstMonthPayment: 0,
        secondMonthAndBeyond: 0,
        payInFull: 0,
        priceKey: 'NONE',
        priceKeyIndex: -1
    });
}

function checkIfRemoveEmptyCardBorderClass(index) {
    const validState = isBundleValidState(index);

    if (validState) {
        document.getElementById('accordionExampleDevice_' + index).classList.remove('empty-card-border');
        controlAccordionCollapse(index, 'hide');
        showByID('custom-duplicate-label_' + index, true);
        showByID('checkmarkIcon-card-title_' + index, true);
        document.getElementById('bundle-card-title_' + index).textContent = bundles[index].title;
        scrollToNewBundleCard(index);
    } else {
        document.getElementById('accordionExampleDevice_' + index).classList.add('empty-card-border');
        controlAccordionCollapse(index, 'show');
        showByID('custom-duplicate-label_' + index, false);
        showByID('checkmarkIcon-card-title_' + index, false);
        // document.getElementById('bundle-card-title_' + index).textContent = "Customize your " + (bundles[index].title).toString().toLowerCase();
        document.getElementById('bundle-card-title_' + index).textContent = "Customize your Bundle";
    }

}

function validateBundles() {
    let result = true;

    for (let i = 0; i < bundles.length; i++) {
        result = isBundleValidState(i);
        if (result === false) break;
    }

    return result;
}

function validateCardItem(bundle) {
    return bundle.deviceDenomination !== "" && bundle.solutionSelection !== "" && bundle.subscriptionTerm !== "" && bundle.paymentOption !== "";
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
                        style="color: black; font-weight: bold;">
                    <span style="color: #9E007E !important;">#${Number(index + 1)}</span>&nbsp;:&nbsp;<span id="summary-bar-bundle-selection_${index}">${bundle.title !== "" ? bundle.title : "New Bundle"} </span>
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

function onNextNext() {
    const result = validateBundles();

    if (result) {
        sessionStorage.setItem(SS_CART_ITEM, JSON.stringify(bundles));
        sessionStorage.removeItem(SS_GET_STARTED_ITEM);
        if (SR !== '') {
            window.location.href = "/" + SR + URL_NEXT;
        } else {
            window.location.href = URL_NEXT;
        }
    } else {
        // generateValidationResult(result.message);

        switchContainers(false, true, false);
        scrollToEmptyCard();
    }
}

function addToCart() {
    const result = validateBundles();

    if (result) {
        sessionStorage.setItem(SS_CART_ITEM, JSON.stringify(bundles));
        sessionStorage.removeItem(SS_GET_STARTED_ITEM);
        if (SR !== '') {
            window.location.href = "/" + SR + URL_CART;
        } else {
            window.location.href = URL_CART;
        }
    } else {
        // generateValidationResult(result.message);

        switchContainers(false, true, false);
        scrollToEmptyCard();
    }
}

function saveDuplicateBundle(bundle) {
    sessionStorage.setItem(SS_DUPLICATE_BUNDLE, JSON.stringify(bundle));
}

function removeDuplicateBundle() {
    sessionStorage.removeItem(SS_DUPLICATE_BUNDLE);
}

function getDuplicateBundle() {
    const duplicateBundle = sessionStorage.getItem(SS_DUPLICATE_BUNDLE);
    return duplicateBundle ? JSON.parse(duplicateBundle) : null;
}

async function setBundlesPriceKeyAndCalculate() {
    try {
        const response = await fetch(`/api/bundles/calculate${SR !== '' ? ('?sr=' + SR) : ''}`, {
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
            switchContainers(false, false, true);
        }
    } catch (error) {
        console.error("Error in calculation:", error);
        switchContainers(false, false, true);
    }
}

function updateSummaryBarItemPrices() {
    for (let i = 0; i < bundles.length; i++) {
        document.getElementById('summary-bar-bundle-price_' + i).textContent = '$' + centsToDollars(bundles[i].bundlePrice);
        document.getElementById('summary-bar-first-month-payment_' + i).textContent = '$' + centsToDollars(bundles[i].firstMonthPayment);
    }
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

function updateBundlePriceFirstMonthPriceTotalPrice() {
    updateSummaryBarItemPrices();
    updateSummaryBarTotal();
}

function updateCartCounterBadge() {
    document.getElementById('cart-counter-badge').textContent = '' + bundles.length;
}

// Function to show the modal
function showModal() {
    const myModal = new bootstrap.Modal(document.getElementById('messageModal'));
    myModal.show();
}

// Function to hide the modal
function hideModal() {
    const myModal = new bootstrap.Modal(document.getElementById('messageModal'));
    myModal.hide();
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

function centsToDollars(cents) {
    let dollars = cents / 100;
    return dollars.toFixed(2);
}

async function duplicateAction(buttonId) {
    const index = buttonId.split('_')[1];
    const times = document.getElementById('duplicate-quantity_' + index).value;

    if (isBundleValidState(index)) {
        saveDuplicateBundle(bundles[index]);
        updateBundleArray(null, times);
        removeDuplicateBundle();

        await setBundlesPriceKeyAndCalculate();

        generateCards();

        generateSummaryBarDeviceTable()
        updateSummaryBarTotal();
        // updateCartCounterBadge();

        // Reset Duplicate Quantity
        document.getElementById('duplicate-quantity_' + index).value = 0;
        setAddNewBundleButtonVisibility();
    }

}

function showByID(id, isShow) {
    if (isShow) {
        document.getElementById(id).style.display = 'inline-block';
    } else {
        document.getElementById(id).style.display = 'none';
    }
}

function isBundleValidState(index) {
    return bundles[index].title !== "" &&
        validateDeviceDenomination(index) &&
        bundles[index].subscriptionTerm !== "" &&
        bundles[index].solutionSelection !== "" &&
        bundles[index].paymentOption !== "";
}

function validateDeviceDenomination(index) {
    if (!bundles[index].deviceDenomination) return false;

    // Split by " and " instead of ","
    const values = bundles[index].deviceDenomination.split(" and ");

    // Ensure both fs and cd1 are selected
    return values.length === 2 && values.every(value => value.trim() !== "");
}

function isCollapseBundle(index) {
    const validBundle = isBundleValidState(index);
    return [validBundle, validBundle];
}

function controlAccordionCollapse(index, action) {
    const collapseElement = document.getElementById(`collapseDevice_${index}`);
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

function setEmptyOptionsTexts(index) {
    const hiddenOption1 = document.getElementById('hidden-select-solution-option_' + index);

    if (hiddenOption1) {
        hiddenOption1.textContent = 'Select Display';
    }

    const hiddenOption2 = document.getElementById('hidden-subscription-term-option_' + index);

    if (hiddenOption2) {
        hiddenOption2.textContent = 'Select Term';
    }

    const hiddenOption3 = document.getElementById('hidden-payment-option-option_' + index);

    if (hiddenOption3) {
        hiddenOption3.textContent = 'Select Payment Option';
    }
}

function updateDuplicateQuantity(index) {
    const quantityInput = document.getElementById(`duplicate-quantity_${index}`);
    const totalQuantityText = document.getElementById(`total-quantity-text_${index}`);
    const duplicateButton = document.getElementById(`duplicate-quantity-button_${index}`);

    const value = parseInt(quantityInput.value) || 0;
    totalQuantityText.textContent = value > 0 ? ` for a total of ${value + 1}` : '';

    duplicateButton.style.display = value > 0 ? 'inline-flex' : 'none';

    setAddNewBundleButtonVisibility();
}

function scrollToEmptyCard() {
    const firstEmptyCard = document.querySelector('.empty-card-border');
    if (firstEmptyCard) {
        firstEmptyCard.scrollIntoView({behavior: 'smooth', block: 'start'});
    }
}

function scrollToNewBundleCard(index) {
    const element = document.getElementById('accordionExampleDevice_' + index);
    if (element) {
        element.scrollIntoView({behavior: 'smooth', block: 'start'});
    }
}


function processInCartNewBadge() {
    const cart = sessionStorage.getItem(SS_CART_ITEM) ? JSON.parse(sessionStorage.getItem(SS_CART_ITEM)) : [];

    for (let i = 0; i < bundles.length; i++) {

        const bundleKey = bundles[i].title + "-" + bundles[i].deviceDenomination + "-" + bundles[i].solutionSelection + "-" + bundles[i].subscriptionTerm + "-" + bundles[i].paymentOption + "-" + bundles[i].priceKeyIndex;

        for (let j = 0; j < cart.length; j++) {
            const cartKey = cart[j].title + "-" + cart[j].deviceDenomination + "-" + cart[j].solutionSelection + "-" + cart[j].subscriptionTerm + "-" + cart[j].paymentOption + "-" + cart[j].priceKeyIndex;

            if (bundleKey === cartKey) {
                updateInCartNewBadge(i, true);
                break;
            } else {
                updateInCartNewBadge(i, false);
            }
        }
    }
}

function setAddNewBundleButtonVisibility() {
    const buttons = document.querySelectorAll('.confirmDuplicateButton');
    let atLeastOneVisible = false;

    buttons.forEach(button => {
        if (button.style.display === 'inline-block' || button.style.display === 'inline-flex') {
            atLeastOneVisible = true;
        }
    });

    if (atLeastOneVisible) {
        document.getElementById('add-new-bundle-div').classList.add('visually-hidden');
    } else {
        document.getElementById('add-new-bundle-div').classList.remove('visually-hidden');
    }
}

function updateInCartNewBadge(index, isInCart) {
    const badge = document.getElementById('is-in-cart-badge_' + index);

    if (isInCart) {
        badge.classList.remove('text-bg-warning');
        badge.classList.add('text-bg-success');
        badge.textContent = 'In Cart';
    } else {
        badge.classList.remove('text-bg-success');
        badge.classList.add('text-bg-warning');
        badge.textContent = 'New';
    }
}

function switchContainers(showMain, showIncomplete, showError) {
    if (window.innerWidth < 769) {
        document.getElementById('main-container').style.display = showMain ? 'block' : 'none';
        document.getElementById('customization-incomplete-container').style.display = showIncomplete ? 'block' : 'none';
        document.getElementById('server-error-container').style.display = showError ? 'block' : 'none';
    } else {
        if (showIncomplete) {
            showModal();
        }

        if (showError) {
            showServerErrorModal();
        }
    }
}

function setSr() {
    SR = (
        document.getElementById('srInput').value === null
        || document.getElementById('srInput').value === undefined
    ) ? '' : document.getElementById('srInput').value.trim();
}

document.addEventListener('DOMContentLoaded', async function () {
    setSr();

    let isUpdateCartCounterBadge = true;

    bundles = sessionStorage.getItem(SS_CART_ITEM) ? JSON.parse(sessionStorage.getItem(SS_CART_ITEM)) : [];

    if (bundles.length <= 0) {
        isUpdateCartCounterBadge = false;

        const getStartedItem = sessionStorage.getItem(SS_GET_STARTED_ITEM) ? JSON.parse(sessionStorage.getItem(SS_GET_STARTED_ITEM)) : [];

        if (getStartedItem.length === 1) {
            bundles = getStartedItem;
        } else {
            if (SR !== '') {
                window.location.href = "/" + SR + URL_ORDER;
            } else {
                window.location.href = URL_ORDER;
            }
        }
    }

    await setBundlesPriceKeyAndCalculate();
    generateCards();
    generateSummaryBarDeviceTable()
    updateSummaryBarTotal();

    if (isUpdateCartCounterBadge) {
        updateCartCounterBadge();
    }
});