let typeCache: {[label: string]: boolean} = {};
export function defineType<T extends string>(label: T): T {
  // Verify the label does not already exist in the cache
  if (typeCache[label]) {
    throw new Error(`Action type '${label}' is not unique!`);
  }

  // Save the label to the cache
  typeCache[label] = true;

  return label;
}

const SharedTableActions: Record<string, string> = {
  LoadTableData: '[Worklist] Load table data',
  StartLoadTableData: '[Worklist] Start load table data',
  LoadTableDataSuccess: '[Worklist] Load table data success',
  LoadTableDataFailure: '[Worklist] Load table data failure',
  ExportTable: '[Worklist] ExportTable',
  ExportTableSuccess: '[Worklist] ExportTableSuccess',
  ExportTableFailure: '[Worklist] ExportTableFailure',
}
type ActionsList = Record<string, string>;

export function makeActionsEnum(prefix: string): ActionsList {
  return Object.keys(SharedTableActions).reduce((list: ActionsList, actionKey: string): ActionsList => {
    const actionLabel = `${prefix} ${SharedTableActions[actionKey]}`;
    defineType(actionLabel);
    return {
      ...list,
      [actionKey]: actionLabel
    };
  }, {})
}
