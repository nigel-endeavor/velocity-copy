import { Component } from '@angular/core';
import { AbstractMilestonesComponent } from 'src/app/components/milestones/abstract-milestones.component';
import { LocationMilestoneInstance } from 'src/app/models/milestone-instance.model';
import { Milestone } from 'src/app/models/milestone.model';
import { LocationMilestoneInstanceService } from 'src/app/services/location-milestone-instance.service';
import { MilestoneDisplaySetService } from 'src/app/services/milestone-display-set.service';
import { SecurityUtilService } from 'src/app/services/security-util.service';
import { FormsModule } from '@angular/forms';
import { CommonModule, DatePipe } from '@angular/common';
import { NoteComponent } from '../note/note.component';
import { JeopsComponent } from '../jeops/jeops.component';
import { OrderEditService } from '../../order-detail-page/order-edit.service';
import { NgxDaterangepickerMd } from 'ngx-daterangepicker-material';
import { MatDividerModule } from '@angular/material/divider';
import { DatepickerModule } from '../datepicker/datepicker.module';
import { DatetimepickerModule } from '../datetimepicker/datetimepicker.module';
import { MatLegacyProgressSpinnerModule as MatProgressSpinnerModule } from '@angular/material/legacy-progress-spinner';
import { Store } from '@ngrx/store';
import { MatLegacyDialog } from '@angular/material/legacy-dialog';
import { JeopService } from 'src/app/services/jeop.service';

@Component({
  standalone: true,
  selector: 'app-location-milestones',
  templateUrl: './abstract-milestones.component.html',
  styleUrls: ['./abstract-milestones.component.scss'],
  imports: [
    CommonModule,
    NoteComponent,
    JeopsComponent,
    DatePipe,
    FormsModule,
    NgxDaterangepickerMd,
    MatDividerModule,
    DatepickerModule,
    DatetimepickerModule,
    MatProgressSpinnerModule,
  ]
})
export class LocationMilestonesComponent extends AbstractMilestonesComponent<LocationMilestoneInstance> {

  override displayGroup: string = 'LOCATION_MILESTONE';

  constructor(private milestoneDisplaySetService: MilestoneDisplaySetService,
              public oes: OrderEditService,
              private locationMilestoneInstanceService: LocationMilestoneInstanceService,
              public securityUtils: SecurityUtilService,
              public store: Store,
              private dialogService: MatLegacyDialog,
              private jeopService: JeopService
  ) {
    super(milestoneDisplaySetService, oes, securityUtils, store, dialogService, jeopService);
  }

  override getMilestoneInstanceService(): LocationMilestoneInstanceService {
    return this.locationMilestoneInstanceService;
  }

  override getNewInstance(milestone: Milestone): LocationMilestoneInstance {
    let instance = new LocationMilestoneInstance();
    instance.locationId = this.parentId;
    instance.milestone = milestone;
    return instance;
  }

  override getMilestoneInstanceClass() {
    return LocationMilestoneInstance;
  }
}
