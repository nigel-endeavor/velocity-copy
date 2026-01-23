//Utility Functions

import { Observable, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

export function getCurrency(currencyString: string): number {
  if (currencyString == null) {
    return 0;
  } else {
    return Number(currencyString.replace(/[^0-9.-]+/g, ''));
  }
}

export function getPercent(percentString: string): number {
  if (percentString == null) {
    return 0;
  } else {
    return Number(percentString.replace(/[^0-9.-]+/g, ''));
  }
}

export function getStatusColor(status: string): string {
  switch (status) {
    case 'Pending Assignment':
    case 'Order Received':
      return '#AA67FF'; //purple
    case 'Service Complete':
    case 'Location Complete':
    case 'Order Complete':
    case 'Complete':
    case 'Dispute Closed':
    case 'Disconnect Complete':
      return '#2A9041'; //green
    case 'Activation Requested':
    case 'Activation Scheduled':
    case 'Installation Issue':
    case 'Schedule Date Confirmed':
    case 'Circuit Complete':
    case 'Pending Bill Review':
    case 'Billing Review Complete':
    case 'Provider Disconnect Order Complete':
    case 'Carrier Disconnect Order Complete': //this status is deprecated, but may still exist in the database
    case 'Billing Review Complete':
      return '#3871C1'; //dark blue
    case 'On Hold':
      return '#FF7152'; //orange
    case 'Partial Complete - Pending Re-Schedule':
      return '#FB9D19'; //orange
    case 'Incomplete - Pending Re-Schedule':
      return '#D73F49'; //orange
    case 'Service Cancelled':
    case 'Location Cancelled':
    case 'Order Cancelled':
    case 'Cancelled':
    case 'Change In Assignment':
    case 'Disconnect Cancelled':
      return '#565656'; //gray
    case 'Disconnect In Progress':
    case 'In Progress':
    default:
      return '#5DCADE'; //light blue
  }
}

export function getStateColor(state: boolean): string {
  if (state) {
    return '#2A9041'; //green
  } else {
    return '#565656'; //gray
  }
}

export function getTodayAsString(): string {
  return new Date().toISOString().split('T')[0]
}

export function getUserTimeZoneAbbreviation(): string {
  const date = new Date();
  const timeZoneAbbreviation = new Intl.DateTimeFormat([], {timeZoneName: 'short'}).formatToParts(date)
    .find(part => part.type === 'timeZoneName')!.value;
  return timeZoneAbbreviation;
}

export function addressToString(address1: string, address2: string, city: string, state: string, postalCode: string, country: string): string {
  let str = '';
    if (address1) {
      str += address1 + ' ';
    }
    if (address2) {
      str += '\n' + address2  + ' ';
    }
    if (city) {
      str += '\n' + city;
    }
    if (city && state) {
      str += ', ';
    }
    if (state) {
      str += state;
    }
    if (postalCode) {
      str += ' ' + postalCode;
    }
    return str;
}

export const CHART_COLOR_PALETTE = [
  '#AA67FF', //purple
  '#2A9041', //green
  '#3871C1', //dark blue
  '#FF7152', //orange
  '#FB9D19', //orange
  '#D73F49', //orange
  '#565656', //gray
  '#5DCADE' //light blue
];

export const ROYGBIV = [
  '#DD0000', //red
  '#FE6230', //orange
  '#FEF600', //yellow
  '#00BB00', //green
  '#009BFE', //blue
  '#000083', //indigo
  '#30009B', //violet
];

export function getBaseUrl(): string {
  const isDevOrUat = environment.appUrl.includes('platform-lb');
  let url = window.location.protocol + '//'
    + window.location.host
    + (isDevOrUat ? '/qto-ops' : '')
    + '/#'
    + environment.baseHref;
  return url;
}

export const CANADIAN_PROVINCES = ['NL', 'PE', 'NS', 'NB', 'QC', 'ON', 'MB', 'SK', 'AB', 'BC', 'YT', 'NT', 'NU'];

/**
 * An interface that requires ngOnDestroy
 */
export interface WithOnDestroy {
  ngOnDestroy?(): void;

  componentDestroy?: () => Observable<void>;

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  [key: string]: any;
}

/**
 * Decorator
 * Patch the component with unsubscribe behavior
 *
 * @param constructor - The component class constructor
 */
export function WithDestroy(constructor: WithOnDestroy): void {
  //@ts-ignore
  const originalDestroy = constructor.prototype.ngOnDestroy;

  //@ts-ignore
  constructor.prototype.componentDestroy = function () {
    this._destroy$ = this._destroy$ || new Subject<void>();
    return this._destroy$.asObservable();
  };

  //@ts-ignore
  constructor.prototype.ngOnDestroy = function () {
    if (typeof originalDestroy === 'function') {
      originalDestroy.apply(this);
    }

    if (this._destroy$) {
      this._destroy$.next();
      this._destroy$.complete();
    }
  };
}


/**
 * A pipe-able operator to unsubscribe during OnDestroy lifecycle event
 *
 * @param component - The component class (`this` context)
 * @returns The component wrapped in an Observable
 *
 * @example
 * source.pipe(untilComponentDestroyed(this)).subscribe...
 */
export function untilComponentDestroyed<C, T>(component: C & WithOnDestroy): (source: Observable<T>) => Observable<T> {
  if (typeof component.componentDestroy !== 'function') {
    throw Error('@WithDestroy decorator is not used');
  }

  return (source: Observable<T>): Observable<T> => source.pipe(takeUntil(component.componentDestroy!()));
}
