import { Injectable } from "@angular/core";
import { Actions } from "@ngrx/effects";
import { Store } from "@ngrx/store";
import { LinkLocationState } from "./link-location.reducer";


@Injectable()
export class LinkLocationEffects {

  constructor(
    private actions$: Actions,
    private store: Store<LinkLocationState>,
  ) {}

}