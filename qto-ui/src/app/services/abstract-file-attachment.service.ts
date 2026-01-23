import { Injectable } from '@angular/core';
import { BaseSearchCriteria } from '../models/base-search-criteria.model';
import { FileAttachment } from '../models/file-attachment';
import { AbstractModelService } from './abstract-model.service';

@Injectable({
  providedIn: 'root'
})
export class AbstractFileAttachmentService extends AbstractModelService<FileAttachment> {
  override path = '/fileAttachments';

  upload(attachments: FileAttachment[], criteria: BaseSearchCriteria) {
    let formData = new FormData();
    for (var i = 0; i < attachments.length; i++) {
      const attachment = attachments[i];
      formData.append('attachment', attachment.content.data);
      formData.append('description', attachment.description);
    }
    const url = this.getUrl() + '/upload';
    return this.http.post(url, formData, { params: this.getParams(criteria), responseType: 'text' });
  }

  download(id: number) {
    let url = this.getUrl() + '/download?id=' + id;
    return this.http.get(url, { responseType: 'blob' });
  }

  onDelete(entity: FileAttachment) {
    let url = this.getUrl() + '/delete?id=' + entity.id;
    return this.http.get(url);
  }

  updateDescription(attachment: FileAttachment) {
    let url = this.getUrl() + '/updateDescription';
    return this.http.post(url, attachment);
  }
}
