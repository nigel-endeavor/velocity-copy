import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatSelectModule } from "@angular/material/select";
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from "@angular/material/form-field";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatListModule } from '@angular/material/list';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { NewMacdComponent } from './new-macd.component';
import { StoreModule } from '@ngrx/store';
import { initialState, newMacdFeatureKey, newMacdReducer } from './store/new-macd.reducer';
import { EffectsModule } from '@ngrx/effects';
import { NewMacdEffects } from './store/new-macd.effects';
import { AttachmentsComponent } from 'src/app/components/attachments/attachments.component';
import { LoadingSpinnerComponent } from 'src/app/components/loading-spinner/loading-spinner.component';

@NgModule({
  declarations: [
    NewMacdComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    StoreModule.forFeature(newMacdFeatureKey, newMacdReducer, {
      initialState
    }),
    EffectsModule.forFeature([NewMacdEffects]),
    ReactiveFormsModule,
    DragDropModule,
    MatSelectModule,
    MatCardModule,
    MatInputModule,
    MatFormFieldModule,
    MatIconModule,
    MatDatepickerModule,
    MatCheckboxModule,
    MatListModule,
    AttachmentsComponent,
    LoadingSpinnerComponent
  ]
})
export class NewMacdModule { }
