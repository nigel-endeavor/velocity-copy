export const COST_HISTORY_COLUMNS = [
  { name: 'Service ID', propertyName: 'serviceId', type: 'number', hidden: false, filterable: false },
  { name: 'Service Type/Provider', propertyName: 'typeProvider', type: 'text', hidden: false, filterable: false },
  { name: 'Cost Type', propertyName: 'costType', type: 'text', hidden: false, filterable: false },
  { name: 'Old Value', propertyName: 'oldValue', type: 'currency', hidden: false, filterable: false },
  { name: 'New Value', propertyName: 'newValue', type: 'currency', hidden: false, filterable: false },
  { name: 'Change Reason', propertyName: 'changeReason', type: 'text', hidden: false, filterable: false },
  { name: 'Update Date', propertyName: 'updateDate', type: 'date', hidden: false, filterable: false },
  { name: 'Update By', propertyName: 'updateBy', type: 'text', hidden: false, filterable: false },
]