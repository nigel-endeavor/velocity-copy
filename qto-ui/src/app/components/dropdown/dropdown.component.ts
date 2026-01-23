import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import { debounceTime, Subject } from "rxjs";

@Component({
  selector: 'app-dropdown',
  templateUrl: './dropdown.component.html',
  styleUrls: ['./dropdown.component.scss']
})
export class DropdownComponent implements OnInit, OnChanges {

  @Input() options: any[] = [];
  @Input() icon: string | undefined;
  @Input() placeholder = 'Select...';
  @Input() labelKey = '';
  @Input() idKey = '';
  @Input() canClear = true;
  @Input() multiSelect: boolean = false;
  @Input() canSearch = false;
  @Input() lazyload = false;
  @Input() disabled = false;
  @Input() params: {
    limit: number,
    offset: number,
    total: number
  } | null = {
    limit: 0,
    offset: 0,
    total: 0
  };
  @Input() searchValue = '';

  @Output() onSelectedChanged = new EventEmitter<string>();
  @Output() search = new EventEmitter<string>();
  @Output() loadNewPage = new EventEmitter<number>();

  private _selected: string | Set<string>| undefined;
  private subject: Subject<string> = new Subject();
  public isLoading = false;


  constructor() {
    this.subject.pipe(debounceTime(500)).subscribe((query: string) => {
      this.search.emit(query);
    });
  }

  @Input()
  set selected(value: string | string[] | undefined) {
    if (Array.isArray(value)) {
      value = value ? value.join(', ') : ''
    }
    if (this.multiSelect) {
      if (value) {
        this._selected = new Set(value.split(', '));
      } else {
        this._selected = new Set();
      }
    } else {
      this._selected = value;
    }
  }

  get selected(): string | undefined {
    if (this.multiSelect && this._selected) {
      this._selected = this._selected as Set<string>;
      return this._selected.size > 0 ? [...this._selected].join(', ') : undefined;
    } else {
      this._selected = this._selected as string;
      return this._selected ? this._selected : undefined;
    }
  }

  ngOnInit(): void {
    if (!this._selected) {
      this.selected = undefined;
    }
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['options']) {
      this.isLoading = false;
    }
  }

  onOptionClicked(option?: any, event?: MouseEvent): void {
    if (this.disabled) {
      return;
    }
    event?.stopPropagation();
    if (this.idKey) {
      this.selected = option;
      this.onSelectedChanged.emit(JSON.stringify(this.selected));
      return
    }
    if (option && this.multiSelect && this._selected) {
      this._selected = this._selected as Set<string>;
      if (this._selected.has(option)) {
        this._selected.delete(option);
      } else {
        this._selected.add(option);
      }
    } else {
      this.selected = option;
    }
    this.onSelectedChanged.emit(this.selected);
  }

  getSelectedLabel() {
    // @ts-ignore
    return JSON.parse(this.selected)[this.labelKey];
  }

  isSelected(option: string): boolean {
    if (this.multiSelect && this._selected) {
      this._selected = this._selected as Set<string>;
      return this._selected.has(option);
    } else {
      return this._selected == option;
    }
  }

  emitSearch(query: string): void {
    this.subject.next(query);
  }

  getSelectedFsArray(): string[] {
    return this._selected ? [...this._selected] : []
  }

  isInSelected(option: string): boolean {
    return this._selected ? [...this._selected].includes(option) : false;
  }

  onScroll(event: any) {
    if (this.lazyload && this.params) {
      if (this.params.offset + this.params.limit < this.params.total) {
        if (event.target.scrollTop >= (this.options.length * 26 - 375)) {
          const optionsCount = this.options.includes('Empty') ? this.options.length - 1 : this.options.length;
          this.loadNewPage.emit(optionsCount);
          this.isLoading = true;
        }
      }
    }
  }
}
