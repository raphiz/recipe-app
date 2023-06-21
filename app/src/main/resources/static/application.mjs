import * as Turbo from "@hotwired/turbo"
Turbo.setProgressBarDelay(5);

Turbo.setConfirmMethod((message, element, submitter) => {
    let title = submitter.dataset.turboConfirmTitle
    let buttonStyle = submitter.dataset.turboConfirmButtonStyle
    let dialog = document.getElementById("turbo-confirm")["confirm-modal"];
    return dialog.showConfirm(title, message, buttonStyle)
})

const eventSource = new EventSource("/events");
Turbo.session.connectStreamSource(eventSource);
