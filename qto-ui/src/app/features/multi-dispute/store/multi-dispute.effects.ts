import { Injectable } from "@angular/core";
import * as actions from "./multi-dispute.actions";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { Store } from "@ngrx/store";
import { DisputesService } from "src/app/services/disputes.service";
import { LookupValueService } from "src/app/services/lookup-value.service";
import { switchMap } from "rxjs";
import { SubjectInterface } from "src/app/models/subject.model";
import { SubjectService } from "src/app/services/subject.service";
import { MultiDisputeState } from "./multi-dispute.reducer";
import { LookupValue } from "src/app/models/lookup-value.model";
import { SecurityUtilService } from "src/app/services/security-util.service";
import { CompanyConfigPropertyService } from "src/app/services/company-config-property.service";
import { CompanyConfigProperty } from "src/app/models/company-config-property.model";

@Injectable()
export class MultiDisputeEffects {

  constructor(
    private actions$: Actions,
    private store: Store<MultiDisputeState>,
    private lookupValueService: LookupValueService,
    private disputeService: DisputesService,
    private subjectService: SubjectService,
    private securityUtils: SecurityUtilService,
    private companyConfigService: CompanyConfigPropertyService
  ) { }

  public loadDisputeTypes = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadDisputeTypes
    ),
    switchMap((action) => {
      return this.lookupValueService.find('DISPUTE_TYPE', undefined).pipe(
        switchMap((disputeTypes: LookupValue[]) => [actions.loadDisputeTypesSuccess({ disputeTypes })]),
      );
    })
  ));

  public loadDisputeAssignments = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadDisputeAssignments
    ),
    switchMap((action) => {
      let currentUser = this.securityUtils.getLoggedInUser().name || '';
      return this.subjectService.getSubjects(null, null, true).pipe(
        switchMap((disputeAssignments: SubjectInterface[]) => [actions.loadDisputeAssignmentsSuccess({ disputeAssignments: disputeAssignments.map((da: SubjectInterface) => da.displayName), currentUser })]),
      );
    })
  ));

  public loadInternalOnlyDefault = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadInternalOnlyDefault
    ),
    switchMap((action) => {
      return this.companyConfigService.getValue('DISPUTE_NOTE_INTERNAL_ONLY_DEFAULT').pipe(
        switchMap((internalOnlyDefault: CompanyConfigProperty) => {
          let value = false;
          if (internalOnlyDefault && internalOnlyDefault.value) {
            value = JSON.parse(internalOnlyDefault.value.toLowerCase());
          }
          return [actions.loadInternalOnlyDefaultSuccess({ internalOnlyDefault: value })];
        }),
      );
    })
  ));

  public onSubmitDispute = createEffect(() => this.actions$.pipe(
    ofType(
      actions.submitDispute
    ),
    switchMap((action) => {
      return this.disputeService.submitMultiDispute(action.serviceIds, action.dispute).pipe(
        switchMap(() => [actions.pageDestroyed()]),
      );
    })
  ));

}