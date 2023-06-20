import {Controller} from "@hotwired/stimulus";
 
export default class extends Controller {
    submit() {
        this.element.requestSubmit()
    }
    
    debouncedSubmit() {
        clearTimeout(this.timer);
        this.timer = setTimeout(() => {
            this.submit()
        }, 300);
    }
}
