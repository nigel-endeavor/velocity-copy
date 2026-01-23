import { Injectable } from "@angular/core";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { Store } from "@ngrx/store";
import { TaskManagerState } from "./task-manager.reducer";
import { LookupValueService } from "../../../services/lookup-value.service";
import { TaskGroupService } from "../../../services/task-group.service";
import { catchError, concatMap, map, of, switchMap, throwError } from "rxjs";
import * as actions from './task-manager.actions';
import { taskGroupTableActions } from "../configs/table.configs";
import { HttpErrorResponse } from "@angular/common/http";

@Injectable()
export class TaskManagerEffects {
  constructor(
    private actions$: Actions,
    private store: Store<TaskManagerState>,
    private lookupValueService: LookupValueService,
    private taskGroupService: TaskGroupService
  ) {}

  public loadLookupValues$ = createEffect(() => this.actions$.pipe(
    ofType(actions.loadLookupValuesByKey),
    switchMap(action => {
      return this.lookupValueService.find(action.lookupKey).pipe(
        map(values => actions.loadLookupValuesByKeySuccess({ key: action.key, values })),
        catchError(error => of(actions.loadLookupValuesByKeyFailure({ error })))
      )
    })
  ));

  public saveTaskGroup$ = createEffect(() => this.actions$.pipe(
    ofType(actions.saveTaskGroup),
    switchMap(action => {
      return this.taskGroupService.save(action.taskGroup).pipe(
        concatMap(() => [actions.saveTaskGroupSuccess(), taskGroupTableActions.loadTableData()]),
        catchError((error: HttpErrorResponse) => {
          // something happens to the selectedTaskGroup object somewhere, resetting it resolves this.
          this.store.dispatch(actions.setSelectedTaskGroup({ taskGroup: null }));
          this.store.dispatch(actions.setSelectedTaskGroup({ taskGroup: action.taskGroup }));

          return throwError(() => error); // Re-throw the error to let global error handler pick it up.
        })
      )
    })
  ));

  public deleteTaskGroup$ = createEffect(() => this.actions$.pipe(
    ofType(actions.deleteTaskGroup),
    switchMap(action => {
      return this.taskGroupService.delete(action.taskGroup).pipe(
        concatMap(() => [actions.deleteTaskGroupSuccess(), taskGroupTableActions.loadTableData()]),
        catchError((error: HttpErrorResponse) => {
          return throwError(() => error); // Re-throw the error to let global error handler pick it up.
        })
      )
    })
  ));

}
