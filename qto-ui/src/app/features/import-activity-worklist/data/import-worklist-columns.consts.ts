import { CommonColumn } from "src/app/interfaces/columns.interface";

export const IMPORT_WORKLIST_COLUMNS: CommonColumn[] = [
  { name: 'Import Type', propertyName: 'importType', type: 'text', hidden: false, filterable: false },
  { name : 'File Name', propertyName: 'fileAttachment.name', type: 'text', hidden: false, filterable: false },
  { name: 'Status', propertyName: 'status', type: 'text', hidden: false, filterable: false },
  { name: 'Status Details', propertyName: 'statusDetails', type: 'text', hidden: false, filterable: false, wrapText: true },
  { name: 'Start Date', propertyName: 'importStartDate', type: 'datetime', hidden: false, filterable: false },
  { name: 'End Date', propertyName: 'importEndDate', type: 'datetime', hidden: false, filterable: false },
  { name: 'Uploaded By', propertyName: 'fileAttachment.uploadedByUserName', type: 'text', hidden: false, filterable: false },
];