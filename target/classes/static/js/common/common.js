const TAG_NAME_FORM = 'form';

const HTTP_METHOD_POST = 'POST';
const HTTP_METHOD_GET = 'GET';

function log(logToPrint) {
    console.log(logToPrint);
}

function onPrevious() {
    window.history.back();
}

function getElementById(id) {
    return document.getElementById(id);
}

function getValueById(id) {
    return getElementById(id).value;
}

function setValueById(id, value) {
    document.getElementById(id).value = value;
}

function setInnerHTMLById(id, value) {
    document.getElementById(id).innerHTML = value;
}

function setTextContentById(id, value) {
    document.getElementById(id).textContent = value;
}

function validateVariable(variable) {
    return !(variable === null || variable === '' || variable === undefined);
}

function toJsonString(object) {
    return JSON.stringify(object);
}

function escapeSingleQuotes(jsonString) {
    return jsonString.replace(/'/g, "\\'");
}

function parseJsonString(jsonString) {
    return JSON.parse(jsonString);
}

function showByID(id, isShow) {
    if (isShow) {
        getElementById(id).style.display = 'block';
    } else {
        getElementById(id).style.display = 'none';
    }
}

function redirectTo(to) {
    window.location.href = to;
}

function createHiddenInputElement(name, value) {
    const inputElement = document.createElement('input');
    inputElement.type = 'hidden';
    inputElement.name = name;
    inputElement.value = value;
    return inputElement;
}