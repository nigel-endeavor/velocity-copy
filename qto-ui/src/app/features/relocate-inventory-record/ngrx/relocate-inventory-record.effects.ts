import { Injectable } from "@angular/core";
import { Actions } from "@ngrx/effects";
import { Store } from "@ngrx/store";
import { RelocateInventoryRecordState } from "./relocate-inventory-record.reducer";     

@Injectable()
export class RelocateInventoryRecordEffects {

  constructor(
    private actions$: Actions,
    private store: Store<RelocateInventoryRecordState>,
  ) {}

}