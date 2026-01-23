import { CommonColumn } from "src/app/interfaces/columns.interface";

//If you are adding columns to this array, you will likely need to update the ngDoCheck method in dto-table.component.ts
export const DTO_TABLE_COLUMNS: CommonColumn[] = [
  { name: 'Master Customer', propertyName: 'masterCustomer', type: 'text', hidden: false, filterable: false },
  { name: 'End Customer', propertyName: 'endCustomer', type: 'text', hidden: false, filterable: false },
  { name: 'Client Location ID', propertyName: 'clientLocationId', type: 'text', hidden: false, filterable: false },
  { name: 'Address', propertyName: 'address', type: 'text', hidden: false, filterable: false },
  { name: 'Client Service ID', propertyName: 'clientServiceId', type: 'text', hidden: false, filterable: false },
  { name: 'Service Type', propertyName: 'serviceType', type: 'text', hidden: false, filterable: false },
  { name: 'Provider', propertyName: 'provider', type: 'text', hidden: false, filterable: false },
  { name: 'Client Order ID', propertyName: 'clientOrderId', type: 'text', hidden: false, filterable: false },
  { name: 'Parent Client Service ID', propertyName: 'linkedBundledClientServiceId', type: 'text', hidden: false, filterable: false },

];
