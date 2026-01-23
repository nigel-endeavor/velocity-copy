import { CommonColumn } from '../../../interfaces/columns.interface';

export const END_CUSTOMERS_WORKLIST_COLUMNS: CommonColumn[] = [
  { name: 'Name', propertyName: 'name', type: 'text', hidden: false, filterable: false, width: '250px' },
  { name: 'Client ID', propertyName: 'clientId', type: 'text', hidden: false, filterable: false },
  { name: 'Location Count', propertyName: 'inventoryLocationCount', type: 'number', hidden: false, filterable: false },
  { name: 'Total MRC', propertyName: 'inventoryMrc', type: 'currency', hidden: false, filterable: false },
  { name: 'Total MRR', propertyName: 'inventoryMrr', type: 'currency', hidden: false, filterable: false },
  { name: 'Total NRR', propertyName: 'inventoryNrr', type: 'currency', hidden: false, filterable: false },
  { name: 'Previous Task', propertyName: 'lastCompletedTask', type: 'text', hidden: false, filterable: false, wrapText: true },
  { name: 'Next Task', propertyName: 'nextTask', type: 'text', hidden: false, filterable: false, wrapText: true },
  { name: 'Tasks Remaining', propertyName: 'remainingTasks', type: 'number', hidden: false, filterable: false },
  { name: 'Task Assignment', propertyName: 'nextTaskAssignedTo', type: 'text', hidden: false, filterable: false },
  { name: 'Progress', propertyName: 'progressPercentage', type: 'progress', hidden: false, filterable: true },
  { name: 'Billing Contact', propertyName: 'billingContactName', type: 'text', hidden: false, filterable: false },
  { name: 'Billing Phone', propertyName: 'billingContactPhone', type: 'text', hidden: false, filterable: false },
  { name: 'Billing Email', propertyName: 'billingContactEmail', type: 'text', hidden: false, filterable: false },
  { name: 'Active', propertyName: 'active', type: 'boolean', hidden: false, filterable: false }
];
