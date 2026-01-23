import { Injectable } from "@angular/core";
import { Actions, concatLatestFrom, createEffect, ofType } from "@ngrx/effects";
import { Store, select } from "@ngrx/store";
import { DisputesService } from "src/app/services/disputes.service";
import { LookupValueService } from "src/app/services/lookup-value.service";
import { DisputeMeta, DisputesState } from "./disputes.reducer";
import * as actions from './disputes.actions';
import * as selectors from './disputes.selectors';
import { HttpErrorResponse } from "@angular/common/http";
import { switchMap, catchError, of, map } from "rxjs";
import { disputesTableActions } from "../configs/table.config";
import { SubjectService } from "src/app/services/subject.service";
import { SubjectInterface } from "src/app/models/subject.model";

@Injectable()
export class DisputesEffects {

  constructor(
    private actions$: Actions,
    private store: Store<DisputesState>,
    private disputesService: DisputesService,
    private lookupValueService: LookupValueService,
    private subjectService: SubjectService
  ) {}

  public saveDispute = createEffect(() => this.actions$.pipe(
    ofType(
      actions.saveDispute
    ),
    switchMap((action) => {
      return this.disputesService.save(action.dispute).pipe(
        switchMap((dispute: any) => [actions.saveDisputeSuccess({ dispute }), disputesTableActions.loadTableData(), actions.loadMetaData()]),
      );
    })
  ))

  public saveDisputeMilestone = createEffect(() => this.actions$.pipe(
    ofType(
      actions.saveDisputeMilestone
    ),
    switchMap((action) => {
      return this.disputesService.save(action.dispute).pipe(
        switchMap((dispute: any) => [actions.saveDisputeMilestoneSuccess({ dispute }), disputesTableActions.loadTableData(), actions.loadMetaData()]),
      );
    })
  ))

  public loadDisputeTypes = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadDisputeTypes
    ),
    switchMap((action) => {
      return this.lookupValueService.getValues('DISPUTE_TYPE', action.companyId).pipe(
        switchMap((disputeTypes: any) => [actions.loadDisputeTypesSuccess({ disputeTypes })]),
      );
    })
  ));

  public onLoadMetaData$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadMetaData,
      actions.updateFilters,
      actions.clearFilters
    ),
    concatLatestFrom(() => [
      this.store.pipe(select(selectors.getFilters)),
    ]),
    switchMap(([_, searchCriteria]) => {
      return this.disputesService.getDisputeMeta(searchCriteria).pipe(
        map((meta: DisputeMeta) => {
          return actions.loadMetaDataSuccess(meta)
        }),
        catchError(error => of(actions.loadMetaDataFailure(error)))
      )
    })
    )
  );

  public onLoadDisputeAssignments$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadDisputeAssignments
    ),
    switchMap(() => {
      return this.subjectService.getSubjects(null, null, true).pipe(
        switchMap((disputeAssignments: SubjectInterface[]) => [actions.loadDisputeAssignmentsSuccess({ disputeAssignments: disputeAssignments.map((da: SubjectInterface) => da.displayName)})]),
      );
    })
  ))
}