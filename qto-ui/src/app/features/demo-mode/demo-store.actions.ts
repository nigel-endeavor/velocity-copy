import { createAction, props } from '@ngrx/store';

export const toggleDemoMode = createAction(
  '[DemoStore] Toggle Demo Mode'
);

export const toggleSlowdown = createAction(
  '[DemoStore] Slowdown Toggled'
);

export const slowdownSecondsChanged = createAction(
  '[DemoStore] Slowdown Seconds Changed',
  props<{ time: number }>()
);
