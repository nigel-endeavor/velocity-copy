import { Injectable } from '@angular/core';
import { BaseSearchCriteria } from '../models/base-search-criteria.model';
import { FileAttachment } from '../models/file-attachment';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AbstractFileAttachmentService{
  path = '/fileAttachments';

  upload(attachments: FileAttachment[], criteria: BaseSearchCriteria) {
    let formData = new FormData();
    for (var i = 0; i < attachments.length; i++) {
      const attachment = attachments[i];
      // formData.append('attachment', attachment.content.data);
      // formData.append('description', attachment.description);
    }
    let url = environment.qtoUrl + '/helpdesk/upload';
    // return this.http.post(url, formData, { params: this.getParams(criteria), responseType: 'text' });
  }
}
