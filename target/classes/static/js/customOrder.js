//
// function showSnackBar(snackBarToShow) {
//     let snackBarElement = document.getElementById(snackBarToShow);
//     snackBarElement.className = "show";
//     setTimeout(
//         function() {
//             snackBarElement.className = snackBarElement.className.replace("show", "");
//         }, 3000
//     );
// }
//
// function validateQuantity(value){
//     return (value <= 0) ? null : value;
// }
//
// // **************************************************************************************************************************
//
// function removeOptionTags(selectBox) {
//     while (selectBox.options.length > 0) {
//         selectBox.remove(0);
//     }
// }
// function fillOptionTags(arrayValue, targetId) {
//     let inputId = document.getElementById(targetId);
//     let counter = 0;
//
//     removeOptionTags(inputId);
//
//     for (let i = 0; i < arrayValue.length; i++) {
//         const value = arrayValue[i];
//
//         const newOption = document.createElement('option');
//         const optionText = document.createTextNode(languageFilter(value));
//
//         newOption.appendChild(optionText);
//
//         if (counter === 0) {
//             newOption.setAttribute('value', "");
//             newOption.setAttribute('hidden', "");
//         } else {
//             newOption.setAttribute('value', value);
//         }
//
//         inputId.appendChild(newOption);
//         counter++;
//     }
// }
//
// function setBranch() {
//     let targetId = 'selectedBranchInput';
//     let branchList = null;
//     let selectedProvincialCommandInput = document.getElementById("provincialCommandSelectInput").value;
//
//     switch(document.getElementById("provincialCommandSelectInput").value) {
//         case provincialCommands[10]:
//             branchList = branch13;
//             break;
//         case provincialCommands[11]:
//             branchList = branch15;
//             break;
//         case provincialCommands[12]:
//             branchList = branch16;
//             break;
//         case provincialCommands[13]:
//             branchList = branch17;
//             break;
//         case provincialCommands[14]:
//             branchList = branch18;
//             break;
//         case provincialCommands[15]:
//             branchList = branch19;
//             break;
//         default:
//             break;
//     }
//
//     if (branchList != null){
//         setProvincialCommandOptionVisibilities(true)
//         setProvincialCommandInput(selectedProvincialCommandInput)
//         fillOptionTags(branchList, targetId)
//     }
//
// }
//
// function setProvincialCommandInput(value) {
//     document.getElementById("provincialCommandInput").value = value;
//     document.getElementById("provincialCommandTextInput").value = value;
//     document.getElementById("provincialCommandTextInput").setAttribute("readonly", "");
// }
//
// function setProvincialCommandOptionVisibilities(otherRequested) {
//     let elementId = ["provincialCommandSelectInput", "provincialCommandTextInput"];
//
//     if (otherRequested){
//         elementId[0] = "provincialCommandTextInput";
//         elementId[1] = "provincialCommandSelectInput";
//     }
//
//     document.getElementById(elementId[0]).value = null
//     document.getElementById(elementId[0]).style.display = 'none';
//     document.getElementById(elementId[0]).removeAttribute("required");
//
//     document.getElementById(elementId[1]).style.display = 'block';
//     document.getElementById(elementId[1]).setAttribute("required", "");
// }
//
//
//
//
//
//
//
//
//
// function getSelectedLanguage() {
//     setSelectedLanguage()
//     return document.getElementById('selectedLanguageInput').value;
// }
//
// function setSelectedLanguage() {
//     document.getElementById('selectedLanguageInput').value = document.getElementById('currentLanguageInput').value;
// }
//
// function languageFilter(value){
//     let returnValue = value;
//
//     if ( value === "Please select a Provincial Command"){
//         if (getSelectedLanguage() === "French"){
//             returnValue = "Veuillez sélectionner une direction provinciale"
//         }
//     }
//
//     if ( value === "Please select a branch"){
//         if (getSelectedLanguage() === "French"){
//             returnValue = "Veuillez sélectionner une succursale"
//         }
//     }
//
//     return returnValue;
// }