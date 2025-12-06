import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ApiConfigService {
  private apiBaseUrl: string;

  constructor() {
    this.apiBaseUrl = this.getApiBaseUrl();
  }

  private getApiBaseUrl(): string {
    return '';
  }

  getApiUrl(endpoint: string): string {
    return `/api/${endpoint}`;
  }

  getBaseUrl(): string {
    return this.apiBaseUrl;
  }

  getWebSocketUrl(): string {
    const hostname = window.location.hostname;
    const port = 8081;
    const wsProtocol = window.location.protocol === 'https:' ? 'wss' : 'ws';

    if (hostname === 'localhost' || hostname === '127.0.0.1') {
      return `${wsProtocol}://localhost:${port}/ws-notifications`;
    } else {
      return `${wsProtocol}://${hostname}:${port}/ws-notifications`;
    }
  }
}
