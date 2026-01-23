import { ErrorHandler, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HelpDeskComponent } from './components/help-desk/help-desk.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { APP_BASE_HREF, CommonModule } from '@angular/common';
import { MSAL_INSTANCE, MsalInterceptor, MsalModule, MsalRedirectComponent } from '@azure/msal-angular';
// import { GlobalErrorHandler } from './services/global-error-handler';
import { environment } from '../environments/environment';
import { InteractionType, PublicClientApplication} from '@azure/msal-browser';
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
    MsalModule.forRoot(new PublicClientApplication({
      auth: {
          clientId: environment.azureClientId,
          authority: environment.azureAuthority,
          redirectUri: environment.azureRedirectUri,
          postLogoutRedirectUri: window.location.protocol + '//' + window.location.host + environment.baseHref
      },
      cache: {
          cacheLocation: 'localStorage',
      }
  }), {
      interactionType: InteractionType.Redirect
  }, {
      interactionType: InteractionType.Redirect,
      protectedResourceMap: new Map([
          [environment.qtoUrl + '/*', ['api://' + environment.azureClientId + '/qto']]
      ]),
  }),
  AttachmentsComponent,
  CommonModule,
  BrowserAnimationsModule,
  MatCommonModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: MsalInterceptor, multi: true },
    { provide: APP_BASE_HREF, useValue: environment.baseHref },
  ],
  bootstrap: [AppComponent, MsalRedirectComponent]
})
export class AppModule { }
