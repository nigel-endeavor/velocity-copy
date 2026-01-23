import { NgModule } from "@angular/core";
import { MultiDisputeComponent } from "./multi-dispute.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { initialState, multiDisputeFeatureKey, multiDisputeReducer } from "./store/multi-dispute.reducer";
import { EffectsModule } from "@ngrx/effects";
import { CommonModule } from "@angular/common";
import { MatCheckboxModule } from "@angular/material/checkbox";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { MatListModule } from "@angular/material/list";
import { MatSelectModule } from "@angular/material/select";
import { StoreModule } from "@ngrx/store";
import { MultiDisputeEffects } from "./store/multi-dispute.effects";
import { VtkCurrencyPipe } from "src/app/pipes/vtk-currency.pipe";

@NgModule({
  declarations: [
    MultiDisputeComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    StoreModule.forFeature(multiDisputeFeatureKey, multiDisputeReducer, {
      initialState
    }),
    EffectsModule.forFeature([MultiDisputeEffects]),
    ReactiveFormsModule,
    MatSelectModule,
    MatInputModule,
    MatFormFieldModule,
    MatIconModule,
    MatDatepickerModule,
    MatCheckboxModule,
    MatListModule,
    VtkCurrencyPipe
  ]
})
export class MultiDisputeModule { }