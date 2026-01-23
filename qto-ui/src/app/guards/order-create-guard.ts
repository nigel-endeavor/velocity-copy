import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot } from '@angular/router';
import { Permissions, SecurityUtilService } from '../services/security-util.service';

@Injectable()
export class OrderCreateGuard implements CanActivate {

  constructor(private securityUtils: SecurityUtilService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    return this.securityUtils.userHasPermission(Permissions.ORDER_CREATE);
  }
}
