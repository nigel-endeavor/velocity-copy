import { Component } from '@angular/core';
import { ServiceEquipmentService } from '../../../services/service-equipment.service';
import { getStatusColor } from '../../../utilities';
import { Store } from '@ngrx/store';
import { SecurityUtilService } from '../../../services/security-util.service';
import { ActivatedRoute } from '@angular/router';
import { ServiceEquipment } from '../../../models/service-equipment-model';
import { AbstractTableComponent } from '../../../features/abstract-table/abstract-table.component';


@Component({
  selector: 'app-service-equipment-table',
  templateUrl: '../../../features/abstract-table/abstract-table.component.html',
  styleUrls: ['../../../features/abstract-table/abstract-table.component.scss']
})
export class ServiceEquipmentTableComponent extends AbstractTableComponent<ServiceEquipment> {
  override persistFilters = true;
  override cardPageCount = 2;
  override allowLimitToggle = true;

  isInventory: boolean;

  constructor(
    private serviceEquipmentService: ServiceEquipmentService,
    public override store: Store,
    public override securityUtils: SecurityUtilService,
    private route: ActivatedRoute
  ) {
    super(store, securityUtils);
  }

  override getModelService() {
    return this.serviceEquipmentService;
  }

  override getStatusColor(row: any) {
    if (row && row.decommission) {
      return '#565656';
    } else if (row && (row.serialNumber || row.macAddress)) {
      return '#3871C1';
    } else {
      return '#AA67FF';
    }
  }
}
