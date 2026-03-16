import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HelpDeskComponent } from './components/help-desk/help-desk.component';
import { HttpClientModule } from '@angular/common/http';
import { APP_BASE_HREF, CommonModule } from '@angular/common';
import { environment } from '../environments/environment';
import { AttachmentsComponent } from './components/attachments/attachments.component';
import { FormsModule } from '@angular/forms';
import { DialogComponent } from './components/dialog/dialog.component';
import { MatDialogModule} from '@angular/material/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCommonModule } from '@angular/material/core';

@NgModule({
  declarations: [
    AppComponent,
    HelpDeskComponent,
    DialogComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    MatDialogModule,
    AttachmentsComponent,
    CommonModule,
    BrowserAnimationsModule,
    MatCommonModule
  ],
  providers: [
    { provide: APP_BASE_HREF, useValue: environment.baseHref },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
