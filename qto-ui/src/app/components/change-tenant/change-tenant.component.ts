import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { CompanySearchCriteria } from 'src/app/models/company-search-criteria';
import { Company } from 'src/app/models/company.model';
import { PaginatedResult } from 'src/app/models/paginated-result.model';
import { CompanyService } from 'src/app/services/company.service';
import { SecurityUtilService } from 'src/app/services/security-util.service';
import { SubjectService } from 'src/app/services/subject.service';

@Component({
  selector: 'app-change-tenant',
  templateUrl: './change-tenant.component.html',
  styleUrls: ['./change-tenant.component.scss']
})
export class ChangeTenantComponent implements OnInit {

  displayName: string;
  selectedTenant: string;
  tenantNames: string[];

  @ViewChild('dialog') updateCompleteDialog: ElementRef;

  constructor(
    private subjectService: SubjectService,
    private companyService: CompanyService,
    private securityUtils: SecurityUtilService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Get the current user's display name
    this.displayName = this.securityUtils.getLoggedInUser().name || 'ERROR: No Name';

    // Search for Vertek Client companies, users with a standard tenant config should only have access to one.  This will be their assigned tenant.
    let companySearchCriteria = new CompanySearchCriteria();
    companySearchCriteria.type = 'Vertek Client';
    this.companyService.search(companySearchCriteria).subscribe((res: PaginatedResult<Company>) => {
      if (res.collection.length != 1) {
        throw new Error('User has access to ' + res.collection.length + ' Vertek Clients. Expected 1.');
      } else {
        this.selectedTenant = res.collection[0].name;
      }
    });

    // Get the list of all tenants
    this.subjectService.getTenantNames().subscribe((res: string[]) => {
      this.tenantNames = res;
    });
  }

  onUpdateTenantClicked(): void {
    this.subjectService.updateSelectedTenant(this.selectedTenant).subscribe(() => {
      // Reload the page after a 2 second delay
      setTimeout(() => {
        localStorage.removeItem('worklist');
        window.location.reload();
      }, 2000);
      this.updateCompleteDialog.nativeElement.showModal();
    });
  }
}
