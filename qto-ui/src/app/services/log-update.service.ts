import { ApplicationRef, Injectable } from '@angular/core';
import { SwUpdate } from '@angular/service-worker';
import { interval } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class LogUpdateService {

  // constructor(appRef: ApplicationRef, updates: SwUpdate) {
  //   updates.versionUpdates.subscribe(evt => {
  //     switch (evt.type) {
  //       case 'VERSION_DETECTED':
  //         console.log(`Downloading new app version: ${evt.version.hash}`);
  //         break;
  //       case 'VERSION_READY':
  //         console.log(`Current app version: ${evt.currentVersion.hash}`);
  //         console.log(`New app version ready for use: ${evt.latestVersion.hash}`);
  //         Object.entries(localStorage).forEach(item => {
  //           if (item[0].includes('State') || item[0].includes('Worklist')) {
  //             localStorage.removeItem(item[0]);
  //           }
  //         });

  //         break;
  //       case 'VERSION_INSTALLATION_FAILED':
  //         console.log(`Failed to install app version '${evt.version.hash}': ${evt.error}`);
  //         break;
  //     }
  //   });

  //   // Allow the app to stabilize first, before starting
  //   // polling for updates with `interval()`.
  //   //

  //   const everyTwoHours$ = interval(2 * 60 * 60 * 1000);


  //   everyTwoHours$.subscribe(async () => {
  //     try {
  //       const updateFound = await updates.checkForUpdate();
  //       console.log(updateFound ? 'A new version is available.' : 'Already on the latest version.');
  //       if (updateFound) {
  //         document.location.reload();
  //       }
  //     } catch (err) {
  //       console.error('Failed to check for updates:', err);
  //     }
  //   });
  // }
}
