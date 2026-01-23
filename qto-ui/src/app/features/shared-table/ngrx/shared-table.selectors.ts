import { createFeatureSelector, createSelector } from '@ngrx/store';

import { SharedTableInitialState } from './shared-table.reducer';

export function createGenericTabSelectors<U, T extends SharedTableInitialState<U>>(
  feature: string
) {
  const stateSelector = createFeatureSelector<T>(feature);

  const getTableData = createSelector(stateSelector, state => state.tableData);
  const getTableTotal = createSelector(stateSelector, state => state.total);

  const getTableDataIsLoading = createSelector(stateSelector, state => {
    return state.isDataLoading
  });
  const getTableDataBaked = createSelector(stateSelector, state => state.isDataLoading);

  return {
    getTableData,
    getTableTotal,
    getTableDataIsLoading,
    getTableDataBaked
  };
}
