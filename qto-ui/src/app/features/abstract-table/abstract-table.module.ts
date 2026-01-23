import { Inject, Injectable, Injector, NgModule, Optional} from '@angular/core';
import { CommonModule } from '@angular/common';
import { AbstractTableComponent } from './abstract-table.component';
import { DropdownModule } from '../../components/dropdown/dropdown.module';
import { MatLegacyCheckboxModule as MatCheckboxModule } from '@angular/material/legacy-checkbox';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LocalStoreModule } from '../local-store/local-store.module';
import { ReducerManager, Store } from '@ngrx/store';
import { Actions, EffectSources } from '@ngrx/effects';
import { MatLegacyDialogModule as MatDialogModule }  from '@angular/material/legacy-dialog';
import { MultiEditModule } from '../multi-edit/multi-edit.module';
import { MatLegacyProgressSpinnerModule as MatProgressSpinnerModule } from '@angular/material/legacy-progress-spinner';
import { MatIconModule } from '@angular/material/icon';
import { generateReducer } from '../shared-table/ngrx/shared-table.reducer';
import { SharedTableEffectsConfigToken, SharedTableEffectsToken } from '../shared-table/tokens';
import { SharedTableConfig } from '../shared-table/interfaces';
import { SharedTableEffects } from '../shared-table/ngrx/shared-table.effects';
import { Observable } from 'rxjs';
import { NestedPropertyPipe } from '../../components/pipes/nested-property.pipe';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatTableModule } from '@angular/material/table';
import { NewMacdModule } from '../macds/new-macd/new-macd.module';
import { MultiDisputeModule } from '../multi-dispute/multi-dispute.module';

@Injectable()
class Instantiator {
  private alreadyProcessedConfigs: Record<string, boolean> = {};

  constructor(@Inject(SharedTableEffectsConfigToken)
              @Optional() private config: {
                config: SharedTableConfig
              }[],
              private injector: Injector) {
  }

  create() {
    this.config.forEach(c => {
      if (!this.alreadyProcessedConfigs[c.config.feature]) {
        this.alreadyProcessedConfigs[c.config.feature] = true;
        const {
          config,
        } = c;
        const inj = Injector.create([{
            provide: SharedTableEffectsConfigToken,
            useValue: config
          }, {
            provide: SharedTableEffectsToken,
            useFactory: (actions$: Actions,
                         store: Store,
                         service: {
                           [key: string]: (request: any) => Observable<any>
                         },
                         config: SharedTableConfig,
            ) => {
              return new SharedTableEffects(
                actions$,
                store,
                service,
                config,
              );
            },
            deps: [
              Actions,
              Store,
              config.service,
              SharedTableEffectsConfigToken
            ],
          }],
          this.injector);

        const manager = inj.get(ReducerManager);
        const effects = inj.get(SharedTableEffectsToken);
        const sources = inj.get(EffectSources);

        // hack for same effects class instantiation prevention
        // eslint-disable-next-line @typescript-eslint/no-empty-function
        (effects as any).__proto__ = function () {
        };
        manager.addReducer(config.feature, generateReducer(config.actions)());
        sources.addEffects(effects);
      }
    });
  }
}

@NgModule({
  declarations: [
    AbstractTableComponent,
    NestedPropertyPipe
  ],
  exports: [
    AbstractTableComponent,
    NestedPropertyPipe
  ],
  imports: [
    CommonModule,
    MatCheckboxModule,
    FormsModule,
    ReactiveFormsModule,
    MatProgressSpinnerModule,
    LocalStoreModule,
    DropdownModule,
    MatDialogModule,
    MultiEditModule,
    MatIconModule,
    DragDropModule,
    MatTableModule,
    NewMacdModule,
    MultiDisputeModule
  ]
})
export class AbstractTableModule {
  constructor(
    private instantiator: Instantiator,
  ) {
    instantiator.create();
  }

  static forRoot(config: SharedTableConfig) {
    return {
      ngModule: AbstractTableModule,
      providers: [
        {
          provide: SharedTableEffectsConfigToken,
          useValue: {
            config,
          },
          multi: true,
        },
        Instantiator,
      ],
    }
  }
}
