import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { ApiConfigService } from './api-config.service';

@Injectable({
  providedIn: 'root'
})
export class WishlistService {
  private apiUrl: string;
  private wishlistItems = new BehaviorSubject<any[]>([]);
  public wishlistItems$ = this.wishlistItems.asObservable();

  constructor(private http: HttpClient, private authService: AuthService, private apiConfig: ApiConfigService) {
    this.apiUrl = this.apiConfig.getApiUrl('wishlist');
    this.loadWishlist();
  }

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  loadWishlist(): void {
    if (this.authService.isAuthenticated()) {
      this.http.get<any>(this.apiUrl, { headers: this.getHeaders() }).subscribe({
        next: (response) => {
          this.wishlistItems.next(response.products || []);
        },
        error: (error) => console.error('Error loading wishlist:', error)
      });
    }
  }

  addToWishlist(productId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/add/${productId}`, {}, { headers: this.getHeaders() });
  }

  removeFromWishlist(productId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/remove/${productId}`, { headers: this.getHeaders() });
  }

  isInWishlist(productId: number): boolean {
    return this.wishlistItems.value.some(item => item.id === productId);
  }

  getWishlistCount(): number {
    return this.wishlistItems.value.length;
  }
}
