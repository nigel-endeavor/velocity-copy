export const SURCHARGE_COLUMNS = [
  { name: 'Level', propertyName: 'surchargeType.level', type: 'text', hidden: false, filterable: false },
  { name: 'Type', propertyName: 'surchargeType.type', type: 'text', hidden: false, filterable: false },
  { name: 'Amount', propertyName: 'surchargeType.amount', type: 'currency', hidden: false, filterable: false },
  { name: 'Added Date', propertyName: 'surchargeDate', type: 'date', hidden: false, filterable: false },
  { name: 'Added By', propertyName: 'addedBy', type: 'text', hidden: false, filterable: false },
  { name: 'Invoiced', propertyName: 'finalized', type: 'boolean', hidden: false, filterable: false }
]
