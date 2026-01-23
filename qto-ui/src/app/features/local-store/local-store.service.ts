import { Inject, Injectable } from '@angular/core';
import { storageProvider } from './local-store.token';

@Injectable({
  providedIn: 'root'
})
export class LocalStoreService {

  constructor(@Inject(storageProvider) private localStorage: Storage) {
  }

  setItem(key: string, value: string): void {
    try {
      this.localStorage.setItem(key, value);
    } catch (e) {

    }
  }

  getItem(key: string): string | undefined {
    try {
      return this.localStorage.getItem(key) || undefined;
    } catch (e) {
      return undefined;
    }
  }

  removeItem(key: string): void {
    try {
      this.localStorage.removeItem(key);
    } catch (e) {
    }
  }
}
