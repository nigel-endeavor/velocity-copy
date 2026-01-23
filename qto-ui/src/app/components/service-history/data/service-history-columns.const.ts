export const SERVICE_HISTORY_COLUMNS = [
  { name: 'Provider', propertyName: 'provider', type: 'text', hidden: false, filterable: false },
  { name: 'Order Type', propertyName: 'orderType', type: 'text', hidden: false, filterable: false },
  { name: 'Sub Order Type', propertyName: 'subOrderType', type: 'text', hidden: false, filterable: false },
  { name: 'Provider Order #', propertyName: 'providerOrderNum', type: 'text', hidden: false, filterable: false },
  { name: 'Created Date', propertyName: 'created', type: 'date', hidden: false, filterable: false },
  { name: 'Complete Date', propertyName: 'complete', type: 'date', hidden: false, filterable: false },
  { name: 'Cancelled Date', propertyName: 'cancelled', type: 'date', hidden: false, filterable: false },
  { name: 'Parent Service ID', propertyName: 'parentServiceId', type: 'number', hidden: false, filterable: false },
  { name: 'Service ID', propertyName: 'id', type: 'number', hidden: false, filterable: false }
]