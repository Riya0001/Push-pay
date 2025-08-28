const MIN_ORDER_LIMIT = 1;
const MAX_ORDER_LIMIT = 10;
const HSBC_ORDER_LIMIT = 1;

let removeButtonArguments = []
let removeButtonArgumentsArray = []

let items =
    [
        // 3 Device Poppy Box Information
        {
            deviceName:
                {
                    english: "3 device poppy box",
                    french: "Boîte coquelicot 3 appareils"
                },
            quantityInputText: "poppyBoxDeviceQuantityInput",
            denominationInputText: "poppyBoxDeviceDenominationInput",
            denominations:
            [
                { value: "$2 $5 $10", text: "threeDevice2and5and10dollarPoppyBoxEnglishQuantityInput" }
            ],
            slideInfo:
            {
                indexItem: 1,
                slideClassName: "device-slideshow-1",
                dotIdName: "dot-1",
                arrow: "arrow-1"
            }
        }
    ]