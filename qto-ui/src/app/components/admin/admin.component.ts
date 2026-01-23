import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  azureAdGroups: any[] | null;

  constructor(
    private adminService: AdminService
  ) { }

  ngOnInit(): void {
  }

  getAzureAdGroups() {
    this.azureAdGroups = null;
    this.adminService.getAzureAdGroups().subscribe((res: any) => {
      this.azureAdGroups = res;
    });
  }

  refreshAzureAdGroups() {
    this.azureAdGroups = null;
    this.adminService.refreshAzureAdGroups().subscribe((res: any) => {
      this.azureAdGroups = res;
    });
  }
}
