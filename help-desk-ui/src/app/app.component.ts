import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MsalBroadcastService, MsalService } from '@azure/msal-angular';
import { SecurityUtilService } from './services/security-util.service';
import { filter, take } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, AfterViewInit {
  title = 'qto-help-desk';

  constructor(
    private authService: MsalService,
    private msalBroadCastService: MsalBroadcastService,
    private securityUtils: SecurityUtilService,
  ) {}

  ngOnInit() {
  }

  ngAfterViewInit(): void {

  }

}
