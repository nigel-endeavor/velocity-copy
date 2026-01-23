import {AfterViewInit, Component, ElementRef, Input, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import { Note } from '../../models/note.model';
import { NoteSearchCriteria } from '../../models/note-search-criteria.model';
import { PaginatedResult } from '../../models/paginated-result.model';
import { SecurityUtilService } from '../../services/security-util.service';
import { NoteService } from '../../services/note.service';
import { FormsModule } from '@angular/forms';
import { CommonModule, DatePipe } from '@angular/common';
import {MatLegacyCheckboxModule as MatCheckboxModule} from "@angular/material/legacy-checkbox";
import {select, Store } from '@ngrx/store';
import { editEnabled, getIsInventory } from 'src/app/order-detail-page/ngrx/order-details.selectors';
import { CompanyConfigPropertyService } from 'src/app/services/company-config-property.service';
import { lowerCase } from 'lodash';
import { MatTooltipModule } from "@angular/material/tooltip";

@Component({
  standalone: true,
  selector: 'app-note',
  templateUrl: './note.component.html',
  styleUrls: ['./note.component.scss', '../../order-detail-page/form-styles.scss'],
  imports: [
    CommonModule,
    FormsModule,
    DatePipe,
    MatCheckboxModule,
    MatTooltipModule
  ]
})
export class NoteComponent implements OnInit, OnChanges, AfterViewInit {
  @Input() type: string = '';
  @Input() parentId: string = '';
  @Input() maxNotes: number; //if undefined will use dialog
  @Input() serviceName: string; //for service notes only
  @Input() showSave: boolean;
  @Input() headerClass: string = 'label';
  @Input() audit: boolean = false;

  public isInventory$ = this.store.pipe(select(getIsInventory));
  public editEnabled$ = this.store.pipe(select(editEnabled));
  editing: boolean = false;

  showDialog: boolean = false;
  @Input() showHeaderButtons: boolean = false;

  @ViewChild('noteDialog') noteDialog: ElementRef<HTMLDialogElement>;
  @ViewChild('textarea') textarea: ElementRef<HTMLTextAreaElement>;

  title: string;
  showExpand: boolean = false;
  isLoading: boolean = false;
  internalOnlyDefault: boolean = false;
  internalOnlyTip = 'Check this box if you want the service note hidden from read-only users.';


  offset: number = 0;
  limit: number = 10;
  maxChars: number = 300;
  maxLength: number = 50000;

  note: Note;
  notes: Note[] = [];

  constructor(
    private noteService: NoteService,
    public securityUtils: SecurityUtilService,
    private store: Store,
    private companyConfigService: CompanyConfigPropertyService
  ) {}

  ngOnInit(): void {
    let internalOnlyConfigKey = this.type === 'dispute' ? 'DISPUTE_NOTE_INTERNAL_ONLY_DEFAULT' : 'SERVICE_NOTE_INTERNAL_ONLY_DEFAULT';
    this.companyConfigService.getValue(internalOnlyConfigKey).subscribe((res) => {
      if (res && res.value) {
        this.internalOnlyDefault = JSON.parse(res.value.toLowerCase());
        if (this.note) {
          this.note.internalOnly = this.internalOnlyDefault;
        }
      }
    });

    this.onTypeChanged();

    if (this.maxNotes) {
      this.limit = this.maxNotes;
    }

    if (!this.maxNotes && this.showSave == undefined) {
      this.showSave = true;
    }

    if (!this.maxNotes || this.maxNotes > this.limit ) {
      this.showExpand = true;
    }

    this.createNewNote();
  }

  ngAfterViewInit() {
    if (this.textarea) {
      this.textarea.nativeElement.focus();
    }
  }

  // run this when component inputs change
  ngOnChanges(changes:SimpleChanges): void {
    if (changes['parentId'] || changes['type']) {
      if (changes['parentId'].previousValue != undefined ) {
        this.note['serviceId'] = changes['parentId'].currentValue;
      }
      this.startOver();
    }
  }

  startOver() {
    this.notes = [];
    this.offset = 0;
    this.onTypeChanged();
    this.getNotes();
  }

  private onTypeChanged() {
    if (this.serviceName == 'Activation') {
      this.title = 'Activation Notes';
    } else {
      if (this.audit) {
        this.title = this.type.charAt(0).toUpperCase() + this.type.slice(1) + ' Audit Notes';
      } else {
        this.title = (this.serviceName ? this.serviceName + ' ': '') + this.type.charAt(0).toUpperCase() + this.type.slice(1) + ' Notes';
      }
    }

    this.noteService.setType(this.type);
  }

  private createNewNote() {
    this.note = new Note;
    this.note.category = this.serviceName ? this.serviceName : this.type.charAt(0).toUpperCase() + this.type.slice(1);
    this.note.note = '';
    this.note[this.type + 'Id'] = this.parentId;
    this.note.internalOnly = this.internalOnlyDefault;

    let createdBy = this.securityUtils.getLoggedInUser();
    this.note.createdBy = createdBy.name ? createdBy.name : createdBy.username;
  }

  getNotes() {
    this.isLoading = true;
    let searchCriteria: NoteSearchCriteria = new NoteSearchCriteria();
    searchCriteria[this.type + 'Id'] = this.parentId;
    searchCriteria.sortField = 'createdDate';
    searchCriteria.sortDir = 'DESC';
    searchCriteria.limit = this.limit;
    searchCriteria.offset = this.offset;

    let getNotesAction = this.audit ? this.noteService.getAuditNotes(searchCriteria) : this.noteService.search(searchCriteria);
    getNotesAction.subscribe((res: PaginatedResult<Note>) => {
          this.isLoading = false;
          if (res && res.collection) {
            this.notes.push(...res.collection);

            if (this.maxNotes != undefined && this.notes.length > this.maxNotes) {
              this.notes.length = this.maxNotes;
            }

            if (res.total - this.offset < this.limit) {
              this.showExpand = false;
            }

            this.offset += this.limit;
          }
        }
      );
  }

  save() {
    if (this.note.note) {
      this.noteService.setType((this.type === 'location' || this.type === 'service')
        ? (this.note.category ==="Location" ? 'location' : 'service')
        : this.type);
      this.noteService.save(this.note)
        .subscribe((res: Note) => {
          //see if the note exists in the list
          let index = this.notes.findIndex(n => n.id == res.id);
          if (index > -1) {
            this.notes[index] = res;
          } else {
            this.notes.unshift(res);
            if (this.notes.length > this.maxNotes) {
              this.notes.length = this.maxNotes;
            }
          }
          this.noteService.setType(this.type);
          this.createNewNote();
        });
    }
  }

  edit(note: Note) {
    this.note = note;
    this.note.editing = true;
  }

  cancelEdit() {
    this.startOver();
    this.createNewNote();
  }

  isAnyNoteEditing() {
    return this.notes.some(n => n.editing);
  }

  openDialog() {
    this.showDialog = true;
    this.noteDialog.nativeElement.showModal();
  }

  closeDialog() {
    this.showDialog = false;
    this.noteDialog.nativeElement.close();
    this.startOver();
  }

  isExpanded(target: EventTarget | null) {
    let element: HTMLElement = target as HTMLElement;
    let classList = element.getAttribute('class') || '';
    return classList.includes('opened');
  }

  showFull(target: EventTarget | null) {
    if (!this.maxNotes) {
      let element: HTMLElement = target as HTMLElement;
      let classList = element.getAttribute('class') || '';
      if (classList.includes('opened')) {
        element.setAttribute('class', 'noteText');
      } else {
        element.setAttribute('class', 'noteText opened');
      }
    }
  }


  getTooltip(i: number) {
    let note = this.notes[i].note.trim();
    if (!this.maxNotes && note.length > this.maxChars) {
      return 'Click to Expand/Collapse';
    } else {
      return '';
    }
  }

  onCopyToClipboardClicked(text: string) {
    navigator.clipboard.writeText(text);
  }
}
