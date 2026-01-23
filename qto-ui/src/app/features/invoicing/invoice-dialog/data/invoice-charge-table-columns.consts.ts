export const INVOICE_CHARGE_COLUMNS = [
  { name: 'Master Customer', propertyName: 'masterCustomerName', type: 'text', hidden: false, filterable: false },
  { name: 'End Customer', propertyName: 'endCustomerName', type: 'text', hidden: false, filterable: false },
  { name: 'Location ID', propertyName: 'locationId', type: 'text', hidden: false, filterable: false },
  { name: 'Item Description', propertyName: 'itemDesc', type: 'text', hidden: false, filterable: false },
  { name: 'Charge Description', propertyName: 'chargeDesc', type: 'text', hidden: false, filterable: false },
  { name: 'Charge Type', propertyName: 'chargeType', type: 'text', hidden: false, filterable: false },
  { name: 'Charge Level', propertyName: 'chargeLevel', type: 'text', hidden: false, filterable: false },
  { name: 'Unit Cost', propertyName: 'unitCost', type: 'currency', hidden: false, filterable: false },
  { name: 'Previously Billed', propertyName: 'previouslyBilled', type: 'currency', hidden: false, filterable: false },
  { name: 'Invoiced Amount', propertyName: 'invoicedAmount', type: 'currency', hidden: false, filterable: false },
  { name: 'Billable Event Milestone', propertyName: 'billableEventMilestoneDescription', type: 'text', hidden: false, filterable: false },
  { name: 'Billable Event Date', propertyName: 'billableEventDate', type: 'date', hidden: false, filterable: false }
]
