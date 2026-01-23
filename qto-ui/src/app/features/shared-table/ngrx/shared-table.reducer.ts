import { createReducer, on } from '@ngrx/store';
import { GeneratedAction } from '../interfaces';

export interface SharedTableInitialState<Data> {
  tableData: Data | null;
  isDataLoading: boolean;
  total: number;
}

export const generateInitialSharedTableState = <Data>(): SharedTableInitialState<Data> => ({
  tableData: null,
  isDataLoading: true,
  total: 0
});

export function generateReducer<Data>(
  actions: GeneratedAction,
  initialState: SharedTableInitialState<Data> = generateInitialSharedTableState<Data>()) {

  return () => {
    return createReducer(
      {...initialState},

      on(
        actions.startLoadTableData,
        (state => ({
          ...state,
          isDataLoading: true,
        })),
      ),
      on(
        actions.loadTableDataSuccess,
        (state, { records, total }) => ({
          ...state,
          tableData: records.map((item: any[]) => ({...item, selected: false})),
          total: total,
          isDataLoading: false,
        }),
      ),
      on(
        actions.loadTableDataFailure,
        state => ({
          ...state,
          isDataLoading: false,
        }),
      ),
      on(
        actions.exportTable,
        state => ({
          ...state,
          isDataLoading: true,
        })
      ),
      on(
        actions.exportTableSuccess,
        actions.exportTableFailure,
        state => ({
          ...state,
          isDataLoading: false,
        })
      ),
    )}
}
