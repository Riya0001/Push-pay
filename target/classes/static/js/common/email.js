function validateFormEmailEntry(inputId, messageId) {
    const emailInput = document.getElementById(inputId);
    const messageElement = document.getElementById(messageId);

    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    if (!emailPattern.test(emailInput.value.trim())) {
        messageElement.textContent = "Invalid email format";
        messageElement.style.display = "block";

        // Focus on the input field and scroll to it
        emailInput.focus();
        emailInput.scrollIntoView({ behavior: "smooth", block: "center" });

        return false;
    } else {
        messageElement.style.display = "none";
        return true;
    }
}