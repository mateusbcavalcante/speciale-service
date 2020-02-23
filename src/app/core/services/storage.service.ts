import { Injectable } from '@angular/core';

@Injectable()
export class StorageService {

  constructor() {
  }

  clear() {
    localStorage.clear();
  }

  removeItem(key: string) {
    localStorage.removeItem(key);
  }

  setJson(key: string, value: any) {
    localStorage.setItem(key, JSON.stringify(value));
  }

  getJson(key: string, defaultValue?: any): any {
    const value = localStorage.getItem(key);
    return !!value ? JSON.parse(value) : defaultValue;
  }

}
