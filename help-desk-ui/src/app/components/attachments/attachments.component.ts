import { Component, EventEmitter, Input, OnChanges, OnInit, Output } from '@angular/core';
import { FileAttachment } from 'src/app/models/file-attachment';
import { AbstractFileAttachmentService } from 'src/app/services/abstract-file-attachment.service';
// import {editEnabled} from "../../order-detail-page/ngrx/order-details.selectors";
// import { LoadingSpinnerComponent } from '../loading-spinner/loading-spinner.component';
import { catchError } from 'rxjs';
import { HelpDeskService } from 'src/app/services/help-desk.service';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-attachments',
  templateUrl: './attachments.component.html',
  styleUrls: ['./attachments.component.scss'],
  imports: [
    CommonModule,
  ]
})
export class AttachmentsComponent implements OnInit{
  @Output() attachmentsSelected: EventEmitter<File[]> = new EventEmitter<File[]>();

  showDialog: boolean = false;
  isLoading: boolean = false;

  // @Input() type: string;
  // @Input() id: number;

  attachmentMap: Map<any, any[]> = new Map<any, any[]>(); //key is serviceId value is an array of attachments, for locations key is null
  attachmentService: AbstractFileAttachmentService | undefined;
  attachmentCriteria: any;
  serviceAttachmentCriteria: any;

  constructor(
    private helpDeskService: HelpDeskService,
  ) {}

  ngOnInit(): void {

  }

  ngOnChanges(): void {
  }

  loadAttachments(): void {
    this.attachmentMap.clear();
    // this.attachmentService.search(this.attachmentCriteria).subscribe((res: PaginatedResult<FileAttachment>) => {
    //   this.attachmentMap.set(null, res.collection);
    // });
  }

  toggleShowDialog(): void {
    this.showDialog = !this.showDialog;
  }

  onUploadClicked(): void {
    const input: HTMLInputElement = document.getElementById('file-input') as HTMLInputElement;
    const description: HTMLInputElement = document.getElementById('description-input') as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      let attachments: FileAttachment[] = [];
      for (var i = 0; i < input.files?.length; i++) {
        let attachment = new FileAttachment();
        attachment.content.data = input.files[i];
        attachment.description = description.value;
        attachments.push(attachment);
      }
      this.isLoading = true;
      // this.attachmentService.upload(attachments, this.attachmentCriteria).pipe(
      //   catchError((error) => {
      //     this.isLoading = false;
      //     throw error;
      //   })
      // ).subscribe((res: any) => {
      //   this.showDialog = false;
      //   this.isLoading = false;
      //   this.loadAttachments();
      // });
    }
  }

  onAttachmentClicked(event: any, attachment: any): void {
    if (attachment.expanded) {
      return;
    }
    attachment.expanded = true;
    //adds a click handler to the page to update attachment description when clicking outside attachment element
    const onClickHandler = function(e: any) {
      if (!event.srcElement.contains(e.target)) {
        attachment.expanded = false;
        document.removeEventListener('click', onClickHandler);
      }
    }
    document.addEventListener('click', onClickHandler);
  }

  setDirty(attachment: FileAttachment): void {
    attachment.dirty = true;
  }

  onFileSelected(event: any): void {
    const attachments: File[] = Array.from(event.target.files);
    this.attachmentsSelected.emit(attachments);
  }

}
