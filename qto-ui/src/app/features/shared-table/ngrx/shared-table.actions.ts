import { createAction, props } from '@ngrx/store';
import { GeneratedAction } from '../interfaces';
import { makeActionsEnum } from '../helper';

export function generateActions(prefix: string): GeneratedAction {
  const actions = makeActionsEnum(prefix);

  const loadTableData = createAction(
    actions['LoadTableData'],
  );

  const startLoadTableData = createAction(
    actions['StartLoadTableData'],
    props<{ filters: any }>(),
  );

  const loadTableDataSuccess = createAction(
    actions['LoadTableDataSuccess'],
    props<{ records: any, total: number }>(),
  );

  const loadTableDataFailure = createAction(actions['LoadTableDataFailure']);

  const exportTable = createAction(
    actions['ExportTable'],
    props<{ fileName: string }>()
  );

  const exportTableSuccess = createAction(
    actions['ExportTableSuccess'],
  );

  const exportTableFailure = createAction(
    actions['ExportTableFailure'],
  );

  return {
    loadTableData,
    startLoadTableData,
    loadTableDataSuccess,
    loadTableDataFailure,
    exportTable,
    exportTableSuccess,
    exportTableFailure,
  }
}
