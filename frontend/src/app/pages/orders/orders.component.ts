import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { OrderService } from '../../services/order.service';

@Component({
  selector: 'app-orders',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.scss']
})
export class OrdersComponent implements OnInit {
  orders: any[] = [];
  filteredOrders: any[] = [];
  statusFilter = '';
  isLoading = true;

  constructor(private orderService: OrderService) {}

  ngOnInit() {
    this.loadOrders();
  }

  loadOrders() {
    this.isLoading = true;
    this.orderService.getUserOrders().subscribe({
      next: (orders) => {
        if (orders && Array.isArray(orders)) {
          this.orders = orders.map((order: any) => ({
            id: order.id,
            date: order.orderDate,
            status: order.status,
            total: order.totalAmount,
            items: order.orderItems?.map((item: any) => ({
              name: item.product?.name || 'Product',
              quantity: item.quantity,
              price: item.price,
              image: item.product?.image || ''
            })) || [],
            timeline: {
              confirmed: ['CONFIRMED', 'PROCESSING', 'SHIPPED', 'DELIVERED'].includes(order.status),
              packed: ['PROCESSING', 'SHIPPED', 'DELIVERED'].includes(order.status),
              shipped: ['SHIPPED', 'DELIVERED'].includes(order.status),
              delivered: order.status === 'DELIVERED'
            }
          }));
        } else {
          this.orders = [];
        }
        this.filterOrders();
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading orders:', error);
        this.orders = [];
        this.filterOrders();
        this.isLoading = false;
      }
    });
  }

  filterOrders() {
    if (this.statusFilter) {
      this.filteredOrders = this.orders.filter(order => order.status === this.statusFilter);
    } else {
      this.filteredOrders = this.orders;
    }
  }

  getStatusColor(status: string): string {
    switch (status) {
      case 'DELIVERED': return 'success';
      case 'SHIPPED': return 'warning';
      case 'CONFIRMED':
      case 'PROCESSING': return 'info';
      case 'CANCELLED': return 'danger';
      case 'PENDING': return 'secondary';
      default: return 'secondary';
    }
  }

  getStatusLabel(status: string): string {
    const labels: { [key: string]: string } = {
      'PENDING': 'Pending',
      'CONFIRMED': 'Confirmed',
      'PROCESSING': 'Processing',
      'SHIPPED': 'Out for Delivery',
      'DELIVERED': 'Delivered',
      'CANCELLED': 'Cancelled'
    };
    return labels[status] || status;
  }

  canCancel(status: string): boolean {
    return ['PENDING', 'CONFIRMED', 'PROCESSING'].includes(status);
  }

  cancelOrder(orderId: number) {
    if (confirm('Are you sure you want to cancel this order?')) {
      this.orderService.cancelOrder(orderId).subscribe({
        next: () => {
          alert('Order cancelled successfully');
          this.loadOrders();
        },
        error: (error) => {
          console.error('Error cancelling order:', error);
          alert('Error cancelling order');
        }
      });
    }
  }
}
