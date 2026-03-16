import { Injectable } from '@angular/core';
import { AccountInfo } from '@azure/msal-browser';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Subject } from 'rxjs';

const DEMO_USER: AccountInfo = {
  homeAccountId: 'demo-user',
  environment: 'local',
  tenantId: 'demo-tenant',
  username: 'demo@local',
  localAccountId: 'demo-local',
  name: 'Demo User',
  idTokenClaims: { roles: ['*'], aud: 'demo', tid: 'demo-tenant' }
} as AccountInfo;

@Injectable({
  providedIn: 'root'
})
export class SecurityUtilService {

  about: any;
  aboutSubject: Subject<any> = new Subject<any>();

  constructor(
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
    return DEMO_USER;
  }

  userHasPermission(_permission: string): boolean {
    return true;
  }

  getBearerToken(): string {
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
