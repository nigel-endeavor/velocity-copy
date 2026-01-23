import { Injectable } from "@angular/core";
import { Actions } from "@ngrx/effects";
import { Store } from "@ngrx/store";
import { RelocateRecordState } from "./relocate-record.reducer";     

@Injectable()
export class RelocateRecordEffects {

  constructor(
    private actions$: Actions,
    private store: Store<RelocateRecordState>,
  ) {}

}