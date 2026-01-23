import { Injectable } from "@angular/core";
import { Actions, concatLatestFrom, createEffect, ofType } from "@ngrx/effects";
import { select, Store } from "@ngrx/store";
import { LookupValueService } from "../../../services/lookup-value.service";
import { SubjectService } from "../../../services/subject.service";
import { CompanyService } from "../../../services/company.service";
import { ServiceCyberViewService } from "../../../services/service-cyber-view.service";
import { ServiceCyberMultiEditService } from "../../../services/service-cyber-multiedit.service";
import * as actions from './service-cyber-worklist.actions';
import { ServiceCyberWorklistState } from "./service-cyber-worklist.reducer";
import { catchError, mergeMap, switchMap, withLatestFrom } from "rxjs/operators";
import { filter, map, of } from "rxjs";
import { getParamsByKey, getSelectedItemsIds, getFilters } from "./service-cyber-worklist.selectors";
import { SubjectInterface } from "../../../models/subject.model";
import { PaginatedResult } from "../../../models/paginated-result.model";
import { LookupValue } from "../../../models/lookup-value.model";
import { ServiceCyberView } from "../../../models/service-cyber-view.model";
import { serviceCyberTableActions } from "../configs/table.config";
import { CompanySearchCriteria } from "../../../models/company-search-criteria";
import { Company } from "../../../models/company.model";
import { clearStore } from "../../multi-edit/store/multie-edit.actions";


@Injectable()
export class ServiceCyberWorklistEffects
{
  constructor(
    private actions$: Actions,
    private store: Store<ServiceCyberWorklistState>,
    private lookupValueService: LookupValueService,
    private subjectService: SubjectService,
    private companyService: CompanyService,
    private serviceCyberWorklistService: ServiceCyberViewService,
    private serviceCyberMultieditService: ServiceCyberMultiEditService
    ) {}

  public onUpdateLocation$ = createEffect(() => this.actions$.pipe(
      ofType(actions.updateServiceCyber),
      switchMap((action) => {

        return this.serviceCyberWorklistService.save(action.serviceCyberView).pipe(
          switchMap((service: ServiceCyberView) => [
            serviceCyberTableActions.loadTableData(),
            actions.setUpdatedItem({ updatedItem: service })
          ]),
          catchError(error => of(actions.updateServiceCyberFailure(error)))
        )
      })
    )
  );

  public onLoadProvisioners$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadDropdownContent,
      actions.updateParams
    ),
    concatLatestFrom((action) => [
      this.store.pipe(select(getParamsByKey(action.filterKey))),
    ]),
    filter(([action, _]) => action.optionKey === 'provisioners'),
    switchMap(([action, filters]) => {
      return this.subjectService.getSubjects(action.orderId, filters).pipe(
        map((subjects: SubjectInterface[]) => actions.loadProvisionersSuccess({ subjects })),
        catchError(error => of(actions.loadProvisionersFailure(error)))
      )
    })
  ));

    public onLoadi90ProjectManagers$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadDropdownContent,
      actions.updateParams
    ),
    concatLatestFrom((action) => [
      this.store.pipe(select(getParamsByKey(action.filterKey))),
    ]),
    filter(([action, _]) => action.optionKey === 'i90ProjectManagers'),
    switchMap(([action, filters]) => {
      return this.subjectService.getSubjects(action.orderId, filters).pipe(
        map((subjects: SubjectInterface[]) => actions.loadi90ProjectManagersSuccess({ subjects })),
        catchError(error => of(actions.load90ProjectManagersFailure(error)))
      )
    })
  ));

  public onLoadProviders$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadDropdownContent,
      actions.updateParams
    ),
    concatLatestFrom((action) => [
      this.store.pipe(select(getParamsByKey(action.filterKey))),
    ]),
    filter(([action, _]) => action.optionKey === 'providers'),
    switchMap(([action, filters]) => {
      return this.lookupValueService.findFull('PROVIDER', undefined, filters).pipe(
        map((options: PaginatedResult<LookupValue>) => actions.loadDropdownContentSuccess({
          options, filterKey: action.filterKey, optionKey: action.optionKey
        })),
        catchError(error => of(actions.loadLookupValuesByKeyFailure(error)))
      )
    })
  ));

  public onLoadCustomersContent$ = createEffect(() => this.actions$.pipe(
      ofType(
        actions.loadDropdownContent,
        actions.updateParams
      ),
      concatLatestFrom((action) => [
        this.store.pipe(select(getParamsByKey(action.filterKey))),
      ]),
    filter(([action, params]) => action.optionKey === 'masterCustomers' ||  action.optionKey === 'customers'),
    mergeMap(([action, params]) => {
        let companyCriteria = new CompanySearchCriteria();
        companyCriteria.type = params.type || '';
        companyCriteria.name = params.name;
        companyCriteria.limit = params.limit;
        companyCriteria.offset = params.offset;
        return this.companyService.search(companyCriteria).pipe(
          map((customers: PaginatedResult<Company>) => actions.loadDropdownContentSuccess({ options: customers, filterKey: action.filterKey, optionKey: action.optionKey })),
          catchError(error => of(actions.loadDropdownContentFailure(error)))
        )
      })
    )
  );

  public onLoadLookupValue$ = createEffect(() => this.actions$.pipe(
    ofType(actions.loadLookupValuesByKey),
    mergeMap((action) => {
      return this.lookupValueService.find(action.lookupKey).pipe(
        map(item => {
          return item
        }),
        map((result: LookupValue[]) => actions.loadLookupValuesByKeySuccess({
          key: action.key,
          values: result
        })),
        catchError(error => of(actions.loadLookupValuesByKeyFailure(error)))
      )
      })
    )
  );

  public onMultiEditSuccess = createEffect(() => this.actions$.pipe(
    ofType(
      actions.sendMultieEditSuccess
    ),
    // @ts-ignore
    mergeMap(() => {
      return [
        clearStore()
      ];
    })
  ));



  public onSendMuliedit$ = createEffect(() => this.actions$.pipe(
    ofType(actions.sendMultieEdit),
    withLatestFrom(this.store.pipe(select(getSelectedItemsIds))),
    mergeMap(([action, selectedIds]) => {
      const payload: {
        ids: number[],
        fieldValues: Record<string, string | number>,
        milestones: {
          code: string,
          date: number | null
        }[]
      } = {
        ids: selectedIds,
        fieldValues: {},
        milestones: []
      }
      Object.keys(action.formResult).forEach(key => {
        if (action.milestoneCodes.includes(key)) {
          if (action.formResult[key]) {
            const date = new Date(new Date(action.formResult[key]).setHours(0, 0, 0, 0));
            payload.milestones.push({
              code: key,
              date: date.getTime()
            });
          } else {
            payload.milestones.push({
              code: key,
              date: null
            });
          }
        } else if (!action.milestoneCodes.includes(key)) {
          if (key.includes('Date')) {
            payload.fieldValues[key] = new Date(new Date(action.formResult[key]).setHours(0, 0, 0, 0)).getTime();
            // @ts-ignore
          } else if (action.formResult[key]?.indexOf && (action.formResult[key].indexOf('}') > 0)) {
            // @ts-ignore
            const item = JSON.parse(action.formResult[key])
            payload.fieldValues[key] = item.value || item.id;
          } else {
            payload.fieldValues[key] = action.formResult[key];
          }
        }
      });
      return this.serviceCyberMultieditService.sendMultiEdit(payload).pipe(
        switchMap((res) => {
          this.serviceCyberMultieditService.onMultiEdit.emit();
          return [actions.sendMultieEditSuccess()]
        }))
    })
  ));
}
