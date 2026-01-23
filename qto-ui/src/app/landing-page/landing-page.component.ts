import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SecurityUtilService } from '../services/security-util.service';
import { CompanyConfigPropertyService } from '../services/company-config-property.service';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.scss']
})
export class LandingPageComponent implements OnInit {

  welcomeText: string = '';
  newQuoteLink: string = 'https://cw.connectbase.com/#/login';

  constructor(
    public securityUtils: SecurityUtilService,
    private router: Router,
    private companyConfigPropertyService: CompanyConfigPropertyService
  ) { }

  ngOnInit(): void {
    if (this.securityUtils.getLoggedInUser()) {
      this.welcomeText = 'Welcome back, ' + this.securityUtils.getLoggedInUser().name + '.';
    } else {
      this.welcomeText = 'Welcome.';
    }

    this.companyConfigPropertyService.getValue('NEW_QUOTE_URL').subscribe((config: any) => {
      if (config) {
        this.newQuoteLink = config.value;
      }
    });
  }

  onManageLocationsClicked(): void {
    if (localStorage.getItem('worklist')) {
      this.router.navigate(['/' + localStorage.getItem('worklist')]);
    } else {
      this.router.navigate(['/locations']);
    }
  }

  onNewQuoteClicked(): void {
    window.open(this.newQuoteLink, '_blank');
  }
}
