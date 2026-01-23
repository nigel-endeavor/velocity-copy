import {Directive, ElementRef, HostBinding, HostListener, Input} from '@angular/core';

@Directive({
  selector: '[appDropdown]'
})
export class DropdownDirective {
  @Input() multiSelect: boolean = false;
  @Input() disabled = false;

  @HostBinding('class.open') isOpen = false;

  private wasInside: boolean = false;

  constructor(private elRef: ElementRef) {}

  @HostListener('click', ['$event']) toggleOpen(event: Event) {
    let element: HTMLElement = event.target as HTMLElement;
    if (!this.disabled) {
      if ((this.multiSelect && element.tagName == 'LI') || element.classList.contains('v-search')) {
        this.isOpen = true;
      } else {
        this.isOpen = this.elRef.nativeElement.contains(event.target) ? !this.isOpen : false;
      }
      this.wasInside = true;
    }
  }

  @HostListener('document:click') clickOutside() {
    if (!this.wasInside) {
      this.isOpen = false;
    }
    this.wasInside = false;
  }
}
