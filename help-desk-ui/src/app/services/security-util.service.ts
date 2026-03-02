import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Subject } from 'rxjs';

/** Stub user info when not using Microsoft auth. */
export interface StubUserInfo {
  name?: string;
}

@Injectable({
  providedIn: 'root'
})
export class SecurityUtilService {

  about: any;
  aboutSubject: Subject<any> = new Subject<any>();

  constructor(private httpClient: HttpClient) { }

  /** Stub: returns null when not using Microsoft auth. Replace with JWT/session auth later. */
  getLoggedInUser(): StubUserInfo | null {
    return null;
  }

  /** Stub: returns false. Replace with real permission check when auth is implemented. */
  userHasPermission(_permission: string): boolean {
    return false;
  }

  /** Stub: returns empty string. Replace with real token when auth is implemented. */
  getBearerToken(): string {
    return '';
  }

  getIsAdminUser(): boolean {
    return this.userHasPermission(Permissions.ADMIN);
  }

  getIsOrderReadUser(): boolean {
    return this.userHasPermission(Permissions.ORDER_READ);
  }

  getIsOrderWriteUser(): boolean {
    return this.userHasPermission(Permissions.ORDER_WRITE);
  }

  getIsOrderWriteTerminalUser(): boolean {
    return this.userHasPermission(Permissions.ORDER_WRITE_TERMINAL);
  }

  getIsInvoicingUser(): boolean {
    return this.userHasPermission(Permissions.INVOICING);
  }

  getIsInvoicingReadUser(): boolean {
    return this.userHasPermission(Permissions.INVOICE_READ);
  }

  getIsInvoicingWriteUser(): boolean {
    return this.userHasPermission(Permissions.INVOICE_WRITE);
  }

  getIsInventoryWriteUser(): boolean {
    return this.userHasPermission(Permissions.INVENTORY_WRITE);
  }

  getIsInventoryReadUser(): boolean {
    return this.userHasPermission(Permissions.INVENTORY_READ);
  }

  getIsFileImportUser(): boolean {
    return this.userHasPermission(Permissions.FILE_IMPORT);
  }
}

export enum Permissions {
  ADMIN = '*',
  INVOICING = 'invoicing',
  INVOICE_WRITE = 'invoice:write',
  INVOICE_READ = 'invoice:read',
  ORDER_WRITE_TERMINAL = 'order:write-terminal',
  ORDER_READ = 'order:read',
  ORDER_WRITE = 'order:write',
  INVENTORY_READ = 'inventory:read',
  INVENTORY_WRITE = 'inventory:write',
  FILE_IMPORT = 'file-import',
  TENANT_ADMIN = 'tenant-admin',
  LOOKUP_ADMIN = 'lookup-admin',
  CHANGE_TENANT = 'change-tenant'
}
