
export class FileAttachment{
  name: string | undefined;
  mimeType: string | undefined;
  size: number | undefined;
  uploadDate: Date | undefined;
  uploadedByUsername: string | undefined;
  description: string | undefined;
  content: FileAttachmentContent = new FileAttachmentContent();

  expanded: boolean = false;
  dirty: boolean = false;
}

export class FileAttachmentContent{
  data: File | undefined;
}