import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MultiEditComponent } from './components/multi-edit.component';
import { MatInputModule } from "@angular/material/input";
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatExpansionModule } from '@angular/material/expansion';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatSelectModule } from "@angular/material/select";
import { MatIconModule } from '@angular/material/icon';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { MultieEditEffects } from './store/multie-edit.effects';
import { multieEditFeatureKey, multieEditReducer, initialState } from './store/multie-edit.reducer';
import { DropdownModule } from '../../components/dropdown/dropdown.module';

@NgModule({
  declarations: [
    MultiEditComponent
  ],
    imports: [
        CommonModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        MatExpansionModule,
        MatDatepickerModule,
        MatSelectModule,
        MatIconModule,
        StoreModule.forFeature(multieEditFeatureKey, multieEditReducer, {
            initialState
        }),
        EffectsModule.forFeature([MultieEditEffects]),
        DropdownModule,
    ]
})
export class MultiEditModule { }
