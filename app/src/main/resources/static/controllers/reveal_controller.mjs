import {Controller} from "@hotwired/stimulus";

export default class extends Controller {
  static targets = ["item"]
  static classes = ["reveal"]

  show() {
    this.itemTargets.forEach(item => {
      item.classList.add(this.revealClass)
    })
  }

  hide() {
    this.itemTargets.forEach(item => {
      item.classList.remove(this.revealClass)
    })
  }
}

