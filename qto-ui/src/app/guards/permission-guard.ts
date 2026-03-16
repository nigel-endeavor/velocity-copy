import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot } from '@angular/router';

@Injectable()
export class PermissionGuard implements CanActivate {

  canActivate(_route: ActivatedRouteSnapshot, _state: RouterStateSnapshot): boolean {
    return true;
  }
}
