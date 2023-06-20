import {Controller} from "@hotwired/stimulus";

export default class extends Controller {
    static targets = ["input", "control", "error"]
    static classes = ["hidden"]

    connect() {
        this.handleClick = this.handleClick.bind(this)
        this.inputTarget.addEventListener("input", this.handleClick)
    }

    handleClick() {
        this.showErrors()
    }

    showErrors() {
        this.errorTargets.forEach((errorMessage) => {
            let errorKey = errorMessage.dataset.errorKey
            if (errorKey === "required") {
                this.showErrorIf(errorMessage, this.inputTarget.validity.valueMissing)
            } else if (errorKey === "minLength") {
                this.showErrorIf(errorMessage, this.inputTarget.validity.tooShort)
            }
        })

        let valid = this.inputTarget.validity.valid;
        this.controlTarget.classList.toggle("is-error", !valid)
        this.inputTarget.setAttribute("aria-invalid", valid)
        if (valid) {
            this.inputTarget.removeAttribute("aria-describedby")
        } else {
            let visibleErrorMsgs = this.errorTargets.filter((it) => !it.classList.contains("display-none")).map((it) => it.id).join(" ");
            this.inputTarget.setAttribute("aria-describedby", visibleErrorMsgs)
        }
    }

    showErrorIf(errorMessageElement, condition) {
        if (condition) {
            errorMessageElement.classList.remove("display-none")
        } else {
            errorMessageElement.classList.add("display-none")
        }
    }

    disconnect() {
        this.inputTarget.removeEventListener("input", this.handleClick)
    }
}

