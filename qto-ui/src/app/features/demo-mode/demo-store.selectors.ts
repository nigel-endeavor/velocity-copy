import { createFeatureSelector, createSelector } from '@ngrx/store';

import * as reducer from './demo-store.reducer';

export const selectDemoStoreState
  = createFeatureSelector<reducer.DemoStoreState>(reducer.demoStoreFeatureKey);

export const isDemoModeEnabled = createSelector(selectDemoStoreState,
  (state: reducer.DemoStoreState) => state.enabled
);

export const isSlowdownEnabled = createSelector(selectDemoStoreState,
  (state: reducer.DemoStoreState) => state.slowdownEnabled
);

export const getSlowdownTime = createSelector(selectDemoStoreState,
  (state: reducer.DemoStoreState) => state.slowdown
);

