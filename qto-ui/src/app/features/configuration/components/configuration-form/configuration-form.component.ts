import { Component, OnInit, ViewChild } from '@angular/core';
import { Store } from '@ngrx/store';
import { Company } from 'src/app/models/company.model';
import { CompanyConfigPropertyService } from 'src/app/services/company-config-property.service';
import { getSelectedTenant } from '../../ngrx/configuration.selectors';
import { SecurityUtilService, Permissions } from 'src/app/services/security-util.service';
import { CompanyConfigProperty } from 'src/app/models/company-config-property.model';
import { setSelectedTab } from '../../ngrx/configuration.actions';
import { NgForm } from '@angular/forms';
import { SubjectService } from 'src/app/services/subject.service';
import { LookupValueService } from "../../../../services/lookup-value.service";

@Component({
  selector: 'app-configuration-form',
  templateUrl: './configuration-form.component.html',
  styleUrls: ['./configuration-form.component.scss']
})
export class ConfigurationFormComponent implements OnInit {

  @ViewChild('configForm') configForm: NgForm;
  configProperty: CompanyConfigProperty;
  isLoading = false;
  public selectedTenant$ = this.store.select<Company | null>(getSelectedTenant);
  public editingInfo = false;
  modifiableProperties: any[];
  editEnabled: boolean = this.securityUtils.userHasPermission(Permissions.TENANT_ADMIN);
  editingConfigInfo: boolean = false;
  visible: boolean = true;
  changeVisible:boolean = true;
  subjects: string[] = [];
  serviceOpts: string[];

  constructor(
    public companyConfigService: CompanyConfigPropertyService,
    private store: Store,
    private securityUtils: SecurityUtilService,
    private subjectService: SubjectService,
    private lookupValueService: LookupValueService
  ) { }

    ngOnInit(): void {
      this.store.dispatch(setSelectedTab({ tab: 'Configuration' }));
      this.selectedTenant$.subscribe((selectedTenant) => {
        if (selectedTenant) {
          this.companyConfigService.getModifiableProperties(selectedTenant.id).subscribe((res) => {
            this.modifiableProperties = res;
          });
        }
      });
      this.subjectService.getSubjects().subscribe((res) => {
        this.subjects = res.map((subject) => subject.emailAddress);
      });
      this.lookupValueService.getValues('ALL_SERVICE_TYPES', null).subscribe((res: string[]) => {
        this.serviceOpts = res;
      });
    }

  onSaveConfigClicked(config: any): void {
    if (this.configForm.controls[config.key] && !this.configForm.controls[config.key].valid) {
      throw new Error('Invalid value for configuration property: ' + config.key);
    }
    this.configProperty = config;
    config.editing = false;
    this.isLoading = true;
    this.companyConfigService.save(this.configProperty).subscribe(res => {
      this.isLoading = false;
      this.selectedTenant$.subscribe((selectedTenant) => {
        if (selectedTenant) {
          this.companyConfigService.getModifiableProperties(selectedTenant.id).subscribe((res) => {
            this.modifiableProperties = res;
          });
        }
      });
    });
  }

  onEditConfigClicked(config: any): void {
    config.editing = !config.editing;
  }

  formatKeyName(key: string): string {
    const words = key.split('_').map((word) => {
      if (['LOE', 'URL'].includes(word)) {
        return word;
      }
      return word.charAt(0).toUpperCase() + word.slice(1).toLowerCase();
    });
    return words.join(' ');
  }

  viewPassword (config: any) {
    config.visible = !config.visible;
  }
}
