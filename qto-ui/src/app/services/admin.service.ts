import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  path: string = environment.appUrl + '/admin';

  constructor(private http: HttpClient) { }

  getAzureAdGroups() {
    return this.http.get(this.path + '/azureAdGroups');
  }

  refreshAzureAdGroups() {
    return this.http.post(this.path + '/refreshAzureAdGroups', null);
  }
}
