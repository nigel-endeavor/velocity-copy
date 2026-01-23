import { Injectable } from '@angular/core';
import { MsalService } from '@azure/msal-angular';
import { AccountInfo } from '@azure/msal-browser';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SecurityUtilService {

  about: any;
  aboutSubject: Subject<any> = new Subject<any>();

  constructor(
    private authService: MsalService,
    private httpClient: HttpClient
  ) { }

  loadAppAbout(): void {
    let url = environment.publicUrl + '/about.json';
    this.httpClient.get(url).subscribe((res: any) => {
      this.about = res;
      this.aboutSubject.next(this.about);
    });
  }

  getLoggedInUser(): AccountInfo {
    return this.authService.instance.getAllAccounts()[0];
  }

  userHasPermission(permission: string): boolean {
    return this.getLoggedInUser().idTokenClaims!.roles!.includes(permission) || this.getLoggedInUser().idTokenClaims!.roles!.includes(Permissions.ADMIN);
  }

  getBearerToken(): string {
    let localStorageKey = this.getLoggedInUser().homeAccountId
      + '-login.windows.net-accesstoken-'
      + this.getLoggedInUser().idTokenClaims!.aud
      + '-'
      + this.getLoggedInUser().idTokenClaims!.tid
      + '-api://' + environment.azureClientId + '/qto--';
    let accessToken = localStorage.getItem(localStorageKey);
    if (accessToken) {
      return JSON.parse(accessToken).secret;
    }
    return '';
  }

  // Admin
  getIsAdminUser(): boolean {
    return this.userHasPermission(Permissions.ADMIN);
  }

  // Order
  getIsOrderReadUser(): boolean {
    return this.userHasPermission(Permissions.ORDER_READ);
  }

  getIsOrderWriteUser(): boolean {
    return this.userHasPermission(Permissions.ORDER_WRITE);
  }

  getIsOrderWriteTerminalUser(): boolean {
    return this.userHasPermission(Permissions.ORDER_WRITE_TERMINAL);
  }

  // Invoicing 
  // Refactor to use read and write permissions
  getIsInvoicingUser(): boolean {
    return this.userHasPermission(Permissions.INVOICING);
  }

  getIsInvoicingReadUser(): boolean {
    return this.userHasPermission(Permissions.INVOICE_READ);
  }

  getIsInvoicingWriteUser(): boolean {
    return this.userHasPermission(Permissions.INVOICE_WRITE);
  }

  // Inventory
  getIsInventoryWriteUser(): boolean {
    return this.userHasPermission(Permissions.INVENTORY_WRITE);
  }

  getIsInventoryReadUser(): boolean {
    return this.userHasPermission(Permissions.INVENTORY_READ);
  }

  // File Import
  getIsFileImportUser(): boolean {
    return this.userHasPermission(Permissions.FILE_IMPORT);
  }

  // Tenant Admin
  getIsTenantAdminUser(): boolean {
    return this.userHasPermission(Permissions.TENANT_ADMIN);
  }
}

export enum Permissions {
  ADMIN = '*',
  INVOICING = 'invoicing',
  INVOICE_WRITE = 'invoice:write', // can finalize and unfinalize charges within Invoicing  
  INVOICE_READ = 'invoice:read', // can view the invoices and charges 
  ORDER_WRITE_TERMINAL = 'order:write-terminal',
  ORDER_READ = 'order:read',
  ORDER_WRITE = 'order:write',
  ORDER_CREATE = 'order:create',
  INVENTORY_READ = 'inventory:read',
  INVENTORY_WRITE = 'inventory:write',
  FILE_IMPORT = 'file-import',
  TENANT_ADMIN = 'tenant-admin',
  LOOKUP_ADMIN = 'lookup-admin',
  CHANGE_TENANT = 'change-tenant'
}
