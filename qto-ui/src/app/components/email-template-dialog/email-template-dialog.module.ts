import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatSelectModule } from "@angular/material/select";
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from "@angular/material/form-field";
import { EmailTemplateDialogComponent } from './email-template-dialog.component';
import { EmailTemplateManagementFormComponent } from './email-template-management-form/email-template-management-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { StoreModule } from '@ngrx/store';
import { emailTemplateDialogFeatureKey, emailTemplateDialogReducer, initialState } from './store/email-template-dialog.reducer';
import { EmailTempolateDialogEffects } from './store/email-template-dialog.effects';
import { EffectsModule } from '@ngrx/effects';

@NgModule({
  declarations: [
    EmailTemplateDialogComponent,
    EmailTemplateManagementFormComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatCardModule,
    MatInputModule,
    MatFormFieldModule,
    MatIconModule,
    StoreModule.forFeature(emailTemplateDialogFeatureKey, emailTemplateDialogReducer, {
      initialState
    }),
    EffectsModule.forFeature([EmailTempolateDialogEffects])
  ]
})
export class EmailTemplateDialogModule { }
