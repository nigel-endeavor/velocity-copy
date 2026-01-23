import { Injectable } from '@angular/core';

import { Actions } from '@ngrx/effects';
import { Store } from '@ngrx/store';
import { of } from 'rxjs';

import { InvoiceChargesState } from './invoice-charges.reducer';
import { InvoiceChargeService } from '../../../../services/invoice-charge.service';

@Injectable()
export class InvoiceChargeEffects {

  constructor(
    private actions$: Actions,
    private store: Store<InvoiceChargesState>,
    private invoiceChargeService: InvoiceChargeService
  ) {}
}
