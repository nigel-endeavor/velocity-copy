import { ActionCreator, TypedAction } from '@ngrx/store/src/models';
import { MemoizedSelector } from '@ngrx/store';

type GeneratedActionCreator<T> = ActionCreator<string, (props: T) => TypedAction<string> & T>;
type GeneratedEmptyActionCreator = ActionCreator<string, () => TypedAction<string>>;
export interface GeneratedAction {
  loadTableData: GeneratedEmptyActionCreator,
  startLoadTableData: GeneratedActionCreator<{ filters: any }>,
  loadTableDataSuccess: GeneratedActionCreator<{ records: any, total: number }>,
  loadTableDataFailure: GeneratedEmptyActionCreator,
  exportTable: GeneratedActionCreator<{ fileName: string }>,
  exportTableSuccess: GeneratedEmptyActionCreator,
  exportTableFailure: GeneratedEmptyActionCreator,
}
export interface GeneratedSelectors {
  getTableData: MemoizedSelector<any, any>,
  getTableDataIsLoading: MemoizedSelector<any, boolean>,
  getTableDataBaked: MemoizedSelector<any, any>,
}

// todo: replace anys by generics
export interface BaseConfig {
  feature: string,
  errorMessage: string,
  readMethodName: string,
  destroyAction: GeneratedActionCreator<any>,
  getReadFilters: MemoizedSelector<any, any>,
  getColumns: MemoizedSelector<any, any>,
  service: any,
}

export interface SharedTableConfig extends BaseConfig {
  actions: GeneratedAction,
  selectors: GeneratedSelectors,
}


export interface SharedTableInternals {
  config: SharedTableConfig;
  actions: GeneratedAction;
  selectors: any;
}
