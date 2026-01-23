import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot } from '@angular/router';
import { Permissions, SecurityUtilService } from '../../services/security-util.service';

@Injectable()
export class ReadonlyGuard implements CanActivate {

  constructor(private securityUtilService: SecurityUtilService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const canEdit = this.securityUtilService.userHasPermission(Permissions.INVOICE_WRITE) || 
    this.securityUtilService.userHasPermission(Permissions.ORDER_WRITE) ||
    this.securityUtilService.userHasPermission(Permissions.INVENTORY_WRITE) ||
    this.securityUtilService.userHasPermission(Permissions.ORDER_WRITE_TERMINAL) ||
    this.securityUtilService.userHasPermission(Permissions.ADMIN) ||
    this.securityUtilService.userHasPermission(Permissions.TENANT_ADMIN) ||
    this.securityUtilService.userHasPermission(Permissions.LOOKUP_ADMIN);
    // return !(route.params['id'] === 'new' && canEdit);
    return (route.params['id'] === 'new' && canEdit) || (route.params['id'] !== 'new');
  }
}
