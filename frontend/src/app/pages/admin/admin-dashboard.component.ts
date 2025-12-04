import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { OrderService } from '../../services/order.service';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.scss']
})
export class AdminDashboardComponent implements OnInit {
  orders: any[] = [];
  products: any[] = [];
  stats = {
    totalOrders: 0,
    totalProducts: 0,
    totalRevenue: 0,
    pendingOrders: 0
  };
  selectedTab = 'orders';
  isLoading = true;

  constructor(
    private orderService: OrderService,
    private productService: ProductService
  ) {}

  ngOnInit() {
    this.loadDashboardData();
  }

  loadDashboardData() {
    this.isLoading = true;
    
    this.orderService.getAllOrders().subscribe({
      next: (orders: any[]) => {
        this.orders = orders;
        this.calculateStats();
        this.isLoading = false;
      },
      error: () => {
        this.orderService.getUserOrders().subscribe({
          next: (orders: any[]) => {
            this.orders = orders;
            this.calculateStats();
            this.isLoading = false;
          },
          error: () => this.isLoading = false
        });
      }
    });

    this.productService.getAllProducts().subscribe({
      next: (products: any[]) => {
        this.products = products;
        this.stats.totalProducts = products.length;
      },
      error: () => {}
    });
  }

  calculateStats() {
    this.stats.totalOrders = this.orders.length;
    this.stats.totalRevenue = this.orders.reduce((sum, order) => sum + (order.totalAmount || 0), 0);
    this.stats.pendingOrders = this.orders.filter(o => o.status === 'PENDING').length;
  }

  updateOrderStatus(orderId: number, event: Event) {
    const status = (event.target as HTMLSelectElement).value;
    if (!status) return;
    this.orderService.updateOrderStatus(orderId, status).subscribe({
      next: () => {
        alert('Order status updated');
        this.loadDashboardData();
      },
      error: () => alert('Error updating order status')
    });
  }

  deleteProduct(productId: number) {
    if (confirm('Are you sure you want to delete this product?')) {
      this.productService.deleteProduct(productId).subscribe({
        next: () => {
          alert('Product deleted');
          this.loadDashboardData();
        },
        error: () => alert('Error deleting product')
      });
    }
  }

  getStatusColor(status: string): string {
    const colors: { [key: string]: string } = {
      'PENDING': 'warning',
      'CONFIRMED': 'info',
      'PROCESSING': 'info',
      'SHIPPED': 'primary',
      'DELIVERED': 'success',
      'CANCELLED': 'danger'
    };
    return colors[status] || 'secondary';
  }
}
