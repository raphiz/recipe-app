import {Controller} from "@hotwired/stimulus";

export default class extends Controller {
    static targets = ["form", "button"]

    connect() {
        this.checkValidity()

        this.handleSubmit = this.handleSubmit.bind(this)
        this.handleInput = this.handleInput.bind(this)
        this.formTarget.addEventListener("submit", this.handleSubmit)
        this.formTarget.addEventListener("input", this.handleInput)
    }

    checkValidity() {
        let isFormValid = this.formTarget.checkValidity();
        this.buttonTargets.forEach((it) => it.disabled = !isFormValid);
    }

    handleInput() {
        this.checkValidity();
    }

    handleSubmit(event) {
        if (!this.formTarget.checkValidity()) {
            event.preventDefault();
        }
    }

    disconnect() {
        this.formTarget.removeEventListener("submit", this.handleSubmit)
        this.formTarget.removeEventListener("input", this.handleInput)
    }
}

