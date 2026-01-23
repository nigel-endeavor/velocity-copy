import { Component, Input, OnChanges, OnInit } from '@angular/core';
import { FileAttachment, FileAttachmentType, LocationFileAttachment, ServiceFileAttachment } from 'src/app/models/file-attachment';
import { PaginatedResult } from 'src/app/models/paginated-result.model';
import { AbstractFileAttachmentService } from 'src/app/services/abstract-file-attachment.service';
import { LocationFileAttachmentSearchCriteria, LocationFileAttachmentService } from 'src/app/services/location-file-attachment.service';
import { ServiceFileAttachmentSearchCriteria, ServiceFileAttachmentService } from 'src/app/services/service-file-attachment.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { OrderEditService } from 'src/app/order-detail-page/order-edit.service';
import {select, Store} from "@ngrx/store";
import {editEnabled} from "../../order-detail-page/ngrx/order-details.selectors";
import { LoadingSpinnerComponent } from '../loading-spinner/loading-spinner.component';
import { catchError } from 'rxjs';
import {
  CompanyFileAttachmentSearchCriteria,
  CompanyFileAttachmentService
} from "../../services/company-file-attachment.service";

@Component({
  standalone: true,
  selector: 'app-attachments',
  templateUrl: './attachments.component.html',
  styleUrls: ['./attachments.component.scss'],
  imports: [
    CommonModule,
    FormsModule,
    LoadingSpinnerComponent
  ]
})
export class AttachmentsComponent implements OnInit, OnChanges {
  public editEnabled$ = this.store.pipe(select(editEnabled));

  showDialog: boolean = false;
  isLoading: boolean = false;

  @Input() type: string;
  @Input() id: any;
  @Input() companyEditEnabled: boolean = false;

  attachmentMap: Map<any, any[]> = new Map<any, any[]>(); //key is serviceId value is an array of attachments, for locations key is null
  attachmentService: AbstractFileAttachmentService;
  attachmentCriteria: any;
  serviceAttachmentCriteria: any;
  masterCompanyAttachmentCriteria: any;
  endCompanyAttachmentCriteria: any;
  serviceLocationID: any;
  locationFileAttachmentCriteria: any;

  constructor(
    private locationFileAttachmentService: LocationFileAttachmentService,
    private serviceFileAttachmentService: ServiceFileAttachmentService,
    private companyFileAttachmentService: CompanyFileAttachmentService,
    private store: Store,
    public oes: OrderEditService
  ) {}

  ngOnInit(): void {

  }

  ngOnChanges(): void {
    switch(this.type) {
      case FileAttachmentType.LOCATION:
        this.attachmentService = this.locationFileAttachmentService;
        this.attachmentCriteria = new LocationFileAttachmentSearchCriteria();
        this.attachmentCriteria.locationId = this.id;
        this.serviceAttachmentCriteria = new ServiceFileAttachmentSearchCriteria();
        this.serviceAttachmentCriteria.locationId = this.id;
        this.masterCompanyAttachmentCriteria = new CompanyFileAttachmentSearchCriteria();
        this.masterCompanyAttachmentCriteria.locationId = this.id;
        this.endCompanyAttachmentCriteria = new CompanyFileAttachmentSearchCriteria();
        this.endCompanyAttachmentCriteria.companyId = this.oes.order?.company.id;
        break;
      case FileAttachmentType.SERVICE:
        const matchingLocation = this.oes.order?.locations.find(location => 
          location.services.some(service => service.id === this.id)
        );
        if (matchingLocation) {
          this.locationFileAttachmentCriteria = new LocationFileAttachmentSearchCriteria();
          this.locationFileAttachmentCriteria.locationId = matchingLocation.id
          this.masterCompanyAttachmentCriteria = new CompanyFileAttachmentSearchCriteria();
          this.masterCompanyAttachmentCriteria.locationId = matchingLocation.id
          this.endCompanyAttachmentCriteria = new CompanyFileAttachmentSearchCriteria();
          this.endCompanyAttachmentCriteria.companyId = this.oes.order?.company.id;
        }
        
        this.attachmentService = this.serviceFileAttachmentService;
        this.attachmentCriteria = new ServiceFileAttachmentSearchCriteria();
        this.attachmentCriteria.serviceId = this.id;
        break;
      case FileAttachmentType.COMPANY:
        this.attachmentService = this.companyFileAttachmentService;
        this.attachmentCriteria = new CompanyFileAttachmentSearchCriteria();
        this.attachmentCriteria.companyId = this.id;
        break;
      default:
        console.error('Error retrieving attachments of type: ' + this.type);
        return;
    }
    this.loadAttachments();
  }

  loadAttachments(): void {
    this.attachmentMap.clear();
    this.attachmentService.search(this.attachmentCriteria).subscribe((res: PaginatedResult<FileAttachment>) => {
      this.attachmentMap.set(null, res.collection);
      if (this.type == FileAttachmentType.LOCATION) {
        this.serviceFileAttachmentService.search(this.serviceAttachmentCriteria).subscribe((res: PaginatedResult<any>) => {
          res.collection.forEach(a => {
            if (!this.attachmentMap.has(a.serviceDisplayText)) {
              this.attachmentMap.set(a.serviceDisplayText, []);
            }
            this.attachmentMap.get(a.serviceDisplayText)!.push(a);
          });
        });
        this.companyFileAttachmentService.search(this.endCompanyAttachmentCriteria).subscribe((res: PaginatedResult<any>) => {
          res.collection.forEach(a => {
            if (!this.attachmentMap.has("End Customer Attachments")) {
              this.attachmentMap.set("End Customer Attachments", []);
            }
            this.attachmentMap.get("End Customer Attachments")!.push(a);
          });
        });
        this.companyFileAttachmentService.search(this.masterCompanyAttachmentCriteria).subscribe((res: PaginatedResult<any>) => {
          res.collection.forEach(a => {
            if (!this.attachmentMap.has("Master Customer Attachments")) {
              this.attachmentMap.set("Master Customer Attachments", []);
            }
            this.attachmentMap.get("Master Customer Attachments")!.push(a);
          });
        });
      }

      if (this.type == FileAttachmentType.SERVICE) {
        this.locationFileAttachmentService.search(this.locationFileAttachmentCriteria).subscribe((res: PaginatedResult<any>) => {
          res.collection.forEach(a => {
            if (!this.attachmentMap.has("Location Attachments")) {
              this.attachmentMap.set("Location Attachments", []);
            }
            this.attachmentMap.get("Location Attachments")!.push(a);
          });
        });
        this.companyFileAttachmentService.search(this.endCompanyAttachmentCriteria).subscribe((res: PaginatedResult<any>) => {
          res.collection.forEach(a => {
            if (!this.attachmentMap.has("End Customer Attachments")) {
              this.attachmentMap.set("End Customer Attachments", []);
            }
            this.attachmentMap.get("End Customer Attachments")!.push(a);
          });
        });
        this.companyFileAttachmentService.search(this.masterCompanyAttachmentCriteria).subscribe((res: PaginatedResult<any>) => {
          res.collection.forEach(a => {
            if (!this.attachmentMap.has("Master Customer Attachments")) {
              this.attachmentMap.set("Master Customer Attachments", []);
            }
            this.attachmentMap.get("Master Customer Attachments")!.push(a);
          });
        });
      }
    });
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
      this.attachmentService.upload(attachments, this.attachmentCriteria).pipe(
        catchError((error) => {
          this.isLoading = false;
          throw error;
        })
      ).subscribe((res: any) => {
        this.showDialog = false;
        this.isLoading = false;
        this.loadAttachments();
      });
    }
  }

  onAttachmentClicked(event: any, attachment: any): void {
    if (attachment.expanded) {
      return;
    }
    attachment.expanded = true;
    let self = this;
    //adds a click handler to the page to update attachment description when clicking outside attachment element
    const onClickHandler = function(e: any) {
      if (!event.srcElement.contains(e.target)) {
        if (attachment.dirty) {
          if (attachment.locationId) {
            self.locationFileAttachmentService.updateDescription(attachment).subscribe((res: any) => {
              self.loadAttachments();
            });
          } else if (attachment.serviceId) {
            self.serviceFileAttachmentService.updateDescription(attachment).subscribe((res: any) => {
              self.loadAttachments();
            });
          } else if (attachment.companyId) {
            self.companyFileAttachmentService.updateDescription(attachment).subscribe((res: any) => {
              self.loadAttachments();
            });
          }
        }
        attachment.expanded = false;
        document.removeEventListener('click', onClickHandler);
      }
    }
    document.addEventListener('click', onClickHandler);
  }

  setDirty(attachment: FileAttachment): void {
    attachment.dirty = true;
  }

  onDownloadClicked(attachment: any): void {
    let service;
    if (attachment.serviceId) {
      service = this.serviceFileAttachmentService;
    } else if (attachment.locationId) {
      service = this.locationFileAttachmentService;
    } else {
      service = this.companyFileAttachmentService;
    }
    service.download(attachment.id).subscribe((res: any) => {
      var a = document.createElement('a');
      var url = window.URL.createObjectURL(res);
      a.href = url;
      a.download = attachment.name;
      document.body.append(a);
      a.click();
      a.remove();
      window.URL.revokeObjectURL(url);
    })
  }

  onDeleteClicked(attachment: any): void {
    const confirmDelete = confirm('You are about to permanently delete this attachment.');
    if (!confirmDelete) {
      return;
    }
    let service;
    if (attachment.serviceId) {
      service = this.serviceFileAttachmentService;
    } else if (attachment.locationId) {
      service = this.locationFileAttachmentService;
    } else {
      service = this.companyFileAttachmentService;
    }
    service.onDelete(attachment).subscribe((res: any) => {
      this.loadAttachments();
    })
  }
}
