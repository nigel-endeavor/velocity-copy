import { BaseConfig, SharedTableInternals } from './interfaces';
import { generateActions } from './ngrx/shared-table.actions';
import { SharedTableInitialState } from './ngrx/shared-table.reducer';
import { createGenericTabSelectors } from './ngrx/shared-table.selectors';

export function createTableInternals<TableGeneric>(cfg: BaseConfig): SharedTableInternals {
  const actions = generateActions(cfg.feature);
  const selectors = createGenericTabSelectors<TableGeneric, SharedTableInitialState<TableGeneric>>(
    cfg.feature,
  );
  const config = {
    feature: cfg.feature,
    errorMessage: cfg.errorMessage,
    readMethodName: cfg.readMethodName,
    destroyAction: cfg.destroyAction,
    getReadFilters: cfg.getReadFilters,
    getColumns: cfg.getColumns,
    service: cfg.service,
    actions,
    selectors,
  };

  return {
    actions,
    selectors,
    config
  }
}

