import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApplicationWrapperComponent } from './application-wrapper.component';
import { RouterModule } from '@angular/router';
import { DropdownModule } from "../components/dropdown/dropdown.module";
import {MatLegacyMenuModule as MatMenuModule} from '@angular/material/legacy-menu';
import {MatIconModule} from '@angular/material/icon';
import {MatBadgeModule} from '@angular/material/badge';
import {MatLegacyListModule as MatListModule} from '@angular/material/legacy-list';
import {MatLegacyCheckboxModule as MatCheckboxModule} from '@angular/material/legacy-checkbox';
import { FormsModule } from '@angular/forms';
import { RelativeTimePipe } from '../components/pipes/relative-time.pipe';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatLegacyDialogModule } from '@angular/material/legacy-dialog';

// @ts-ignore
@NgModule({
  declarations: [
    ApplicationWrapperComponent,
    RelativeTimePipe
  ],
  imports: [
    CommonModule,
    RouterModule,
    DropdownModule,
    MatMenuModule,
    MatIconModule,
    MatBadgeModule,
    MatListModule,
    MatCheckboxModule,
    FormsModule,
    MatTooltipModule,
    MatLegacyDialogModule
  ],
  exports: [
    ApplicationWrapperComponent
  ]
})

export class ApplicationWrapperModule { }
