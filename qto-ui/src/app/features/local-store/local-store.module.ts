import { ModuleWithProviders, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { storageProvider } from './local-store.token';
import { LocalStoreService } from './local-store.service';

@NgModule({
  imports: [
    CommonModule,
  ],
  providers: [
    LocalStoreService
  ]
})
export class LocalStoreModule {
  static forRoot(): ModuleWithProviders<LocalStoreModule> {
    return {
      ngModule: LocalStoreModule,
      providers: [{
        provide: storageProvider,
        useValue: window.localStorage
      }]
    };
  }
}
