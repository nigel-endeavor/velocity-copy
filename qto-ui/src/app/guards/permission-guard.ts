import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot } from '@angular/router';
import { MsalBroadcastService, MsalService } from '@azure/msal-angular';
import { InteractionStatus } from '@azure/msal-browser';
import { filter } from 'rxjs';
import { SecurityUtilService } from '../services/security-util.service';

@Injectable()
export class PermissionGuard implements CanActivate {

  constructor(private securityUtils: SecurityUtilService, private authService: MsalService, private msalBroadcastService: MsalBroadcastService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (this.isUser()) {
      return true;
    } else {
      //force another login, if one is not in progress, to refresh the users token
      this.msalBroadcastService.inProgress$
      .pipe(
        filter((status: InteractionStatus) => status === InteractionStatus.None),
      )
      .subscribe(() => {
        this.authService.loginRedirect();
      });
      throw Error('The current user has no assigned roles. Please contact support.');
    }
  }

  //verifies that the logged in user has a roles object in their token
  isUser(): boolean {
    if (!this.securityUtils.getLoggedInUser()) {//if no user, fall back to MsalGuard
      return true;
    }
    if (this.securityUtils.getLoggedInUser().idTokenClaims?.roles) {
      return true;
    }
    return false;
  }
}
