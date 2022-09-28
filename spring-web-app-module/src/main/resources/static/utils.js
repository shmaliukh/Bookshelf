//return "fetchFormWithJsonBody(" + formElemId + ", " + pageToSend + ", " + method + ", " + pageToRedirect + ")";

function fetchFormWithJsonBody(formElemId, pageToSend, method, pageToRedirect) {
    let form = document.getElementById(formElemId);
    let formData = new FormData(form);
    let formValue = Object.fromEntries(formData);
    let jsonBody = JSON.stringify(formValue);
    fetch(pageToSend, {
        method: method,
        body: jsonBody,
        headers: {
            "Content-Type": "application/json"
        }
    }).then((res) => {
        try {
            if (res.ok) {
                window.location.href = pageToRedirect;
            } else {
                alert('Problem status: ' + res.status);
            }
        } catch (e) {
            alert('Error: ' + e.name + ":" + e.message + "\n" + e.stack);
        }
    });
}