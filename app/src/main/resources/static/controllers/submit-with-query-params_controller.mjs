import {Controller} from "@hotwired/stimulus";


export default class extends Controller {

    connect() {
        this.element.addEventListener('turbo:before-fetch-request', async (event) => {
            event.preventDefault()
            const urlParams = new URLSearchParams(window.location.search);
            for (let pair of urlParams.entries()) {
                event.detail.fetchOptions.body.set(pair[0], pair[1])
            }
            event.detail.resume()
        })
    }
    
}
