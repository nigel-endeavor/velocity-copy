import { createReducer, on } from '@ngrx/store';
import * as actions from './demo-store.actions';

export const demoStoreFeatureKey = 'demoStoreState';

export interface DemoStoreState {
  enabled: boolean;
  slowdown: number;
  slowdownEnabled: boolean;
}

export const initialState: DemoStoreState = {
  enabled: false,
  slowdown: 0,
  slowdownEnabled: false,
};

export const demoStoreReducer = createReducer(
  initialState,
  on(actions.toggleDemoMode, state => {
    // reset all settings is demo mode is disabled
    const newState = !state.enabled;
    const clearStateIfDisabled = newState
      ? {}
      : {
        slowdown: 0,
        slowdownEnabled: false
      };

    return {
      ...state,
      enabled: newState,
      ...clearStateIfDisabled
    }
  }),
  on(actions.toggleSlowdown, state => ({
    ...state,
    slowdownEnabled: !state.slowdownEnabled,
  })),
  on(actions.slowdownSecondsChanged, (state, action) => ({
    ...state,
    slowdown: action.time,
  })),
);

