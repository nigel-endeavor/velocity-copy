import { CommonColumn } from "src/app/interfaces/columns.interface";
import { IMPORT_WORKLIST_COLUMNS } from "../data/import-worklist-columns.consts";
import { createReducer, on } from "@ngrx/store";
import * as actions from "./import-activity-worklist.actions";
import { SubjectInterface } from "src/app/models/subject.model";

export const importActivityWorklistFeatureKey = 'importActivityWorklist';

export interface ImportActivityWorklistState {
  filters: {
    offset: number;
    limit: number;
    sortDir: string;
    sortField: string;
    format: string;
    fields: string;
    headers: string;
  },
  isLoading: boolean,
  columns: CommonColumn[],
  subjects: SubjectInterface[]
};

export const initialState: ImportActivityWorklistState = {
  filters: {
    offset: 0,
    limit: 25,
    sortDir: '',
    sortField: '',
    format: '',
    fields: '',
    headers: ''
  },
  isLoading: false,
  columns: IMPORT_WORKLIST_COLUMNS,
  subjects: []
};

export const importActivityWorklistReducer = createReducer(
  initialState,

  on(actions.updateSort, (state, action) => {
    let resSort;
    if (state.filters.sortDir === action.sort.dir && state.filters.sortField === action.sort.col) {
      resSort = {
        sortDir: '',
        sortField: ''
      }
    } else {
      resSort = {
        sortDir: action.sort.dir,
        sortField: action.sort.col
      }
    }
    return {
      ...state,
      filters: {
        ...state.filters,
        ...resSort
      }
    }
  }),

  on(actions.updateFilters, (state, action) => {
    return {
      ...state,
      filters: {
        ...state.filters,
        [action.key]: action.value,
        ...(action.key !== 'offset' && { offset: 0 }),
      }
    }
  }),

  on(actions.clearFilters, (state) => {
    return {
      ...state,
      filters: {
        ...initialState.filters
      }
    }
  }),

  on(actions.toggleColumn, (state, action) => {
    const newColumns = [...state.columns].map(col => {
      return {
        ...col,
        hidden: col.propertyName === action.columnName ? !col.hidden : col.hidden
      }
    })
    return {
      ...state,
      columns: newColumns
    }
  }),

  on(actions.loadSubjectsSuccess, (state, action) => {
    return {
      ...state,
      subjects: action.subjects
    }
  })
);
