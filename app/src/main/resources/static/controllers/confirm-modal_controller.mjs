import {Controller} from "@hotwired/stimulus";

export default class extends Controller {
  static targets = ["title", "message", "confirm"]

  connect() {
    this.element[this.identifier] = this;
    this.confirmTarget.value = "confirm"
  }

  async showConfirm(title, message, buttonStyle) {
    this.prepareModal(title, message, buttonStyle)
    let returnValue = await this.showModal()
    this.resetModal(buttonStyle)
    return returnValue === "confirm"
  }

  showModal() {
    this.element.classList.remove("is-hidden")
    this.element.showModal()
    return new Promise((resolve) => {
      this.element.addEventListener("close", () => {
        resolve(this.element.returnValue)
      }, {once: true})
    })
  }

  prepareModal(title, message, buttonStyle) {
    this.titleTarget.innerText = title ? title : ''
    this.messageTarget.innerHTML = message
    this.confirmTarget.classList.add(buttonStyle)
  }

  resetModal(buttonStyle) {
    this.titleTarget.innerText = ''
    this.messageTarget.innerHTML = ''
    this.confirmTarget.classList.remove(buttonStyle)
  }

}