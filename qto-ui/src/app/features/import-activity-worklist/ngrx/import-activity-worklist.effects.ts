import { Injectable } from "@angular/core";
import * as actions from "./import-activity-worklist.actions";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { switchMap, map, catchError, of } from "rxjs";
import { SubjectInterface } from "src/app/models/subject.model";
import { SubjectService } from "src/app/services/subject.service";

@Injectable()
export class ImportActivityWorklistEffects {

  constructor(
    private actions$: Actions,
    private subjectService: SubjectService,
  ) {}

  public onLoadSubjects$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadSubjects
    ),
    switchMap((action) => {
      return this.subjectService.getSubjects().pipe(
        map((subjects: SubjectInterface[]) => actions.loadSubjectsSuccess({ subjects }))
      )
    })
  ));
}