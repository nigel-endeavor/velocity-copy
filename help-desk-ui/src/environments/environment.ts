// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  qtoUrl: 'http://127.0.0.1:7081/qto/api',
  wsUrl: 'ws://127.0.0.1:7081/qto',
  baseHref: '/help-desk',
  //Azure Config
  azureClientId: '77dd2c9c-5d15-46ad-98d9-039c62d8ef9a',
  azureAuthority: 'https://login.microsoftonline.com/119de762-6e78-4af0-a159-76b9a12af1a4',
  azureRedirectUri: 'http://localhost:4200/help-desk/'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
