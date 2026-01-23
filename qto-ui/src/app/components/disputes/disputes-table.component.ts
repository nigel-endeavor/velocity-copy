import { Component } from "@angular/core";
import { Store, select} from "@ngrx/store";
import { AbstractTableComponent } from "src/app/features/abstract-table/abstract-table.component";
import { CommonColumn } from "src/app/interfaces/columns.interface";
import { DisputeSearchCriteria } from "src/app/models/dispute-search-criteria.model";
import { Dispute } from "src/app/models/dispute.model";
import { DisputesService } from "src/app/services/disputes.service";
import { SecurityUtilService } from "src/app/services/security-util.service";
import { DISPUTE_COLUMNS } from "./data/disputes-table-columns.consts";
import { getStatusColor } from "../../utilities";
import { editEnabled} from 'src/app/order-detail-page/ngrx/order-details.selectors';

@Component({
  selector: 'app-disputes-table',
  templateUrl: '../../features/abstract-table/abstract-table.component.html',
  styleUrls: ['../../features/abstract-table/abstract-table.component.scss']
})
export class DisputesTableComponent extends AbstractTableComponent<Dispute> {
  public override editEnabled$ = this.store.pipe(select(editEnabled));
  override searchCriteria: DisputeSearchCriteria = new DisputeSearchCriteria();
  override columns: CommonColumn[] = DISPUTE_COLUMNS;

  constructor(
    private disputeService: DisputesService,
    public override store: Store,
    public override securityUtils: SecurityUtilService
  ) {
    super(store, securityUtils);
  }

  override getModelService() {
    return this.disputeService;
  }

  override getStatusColor(row: any) {
    return getStatusColor(row.disputeStatus)
  }

}
