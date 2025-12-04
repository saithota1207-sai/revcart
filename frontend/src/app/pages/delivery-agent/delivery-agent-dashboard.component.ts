import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { DeliveryService } from '../../services/delivery.service';
import { OrderService } from '../../services/order.service';

@Component({
  selector: 'app-delivery-agent-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './delivery-agent-dashboard.component.html',
  styleUrls: ['./delivery-agent-dashboard.component.scss']
})
export class DeliveryAgentDashboardComponent implements OnInit {
  agent: any = null;
  orders: any[] = [];
  filteredOrders: any[] = [];
  statusFilter = '';
  isLoading = true;

  constructor(
    private deliveryService: DeliveryService,
    private orderService: OrderService,
    private router: Router
  ) {}

  ngOnInit() {
    const agentData = localStorage.getItem('deliveryAgent');
    if (!agentData) {
      this.router.navigate(['/delivery-agent/login']);
      return;
    }

    this.agent = JSON.parse(agentData);
    this.loadOrders();
  }

  loadOrders() {
    this.isLoading = true;
    this.deliveryService.getDeliveryOrders().subscribe({
      next: (orders: any[]) => {
        this.orders = orders;
        this.filterOrders();
        this.isLoading = false;
      },
      error: (error: any) => {
        console.error('Error loading orders:', error);
        this.isLoading = false;
      }
    });
  }

  filterOrders() {
    if (this.statusFilter) {
      this.filteredOrders = this.orders.filter(o => o.status === this.statusFilter);
    } else {
      this.filteredOrders = this.orders;
    }
  }

  updateOrderStatus(orderId: number, newStatus: string) {
    this.deliveryService.updateDeliveryStatus(orderId, newStatus).subscribe({
      next: () => {
        alert('Order status updated');
        this.loadOrders();
      },
      error: (error) => {
        alert('Error updating status');
      }
    });
  }

  updateAgentStatus(newStatus: string) {
    console.log('Updating agent status:', this.agent, 'New status:', newStatus);
    if (!this.agent || !this.agent.id) {
      alert('Agent ID not found. Please login again.');
      return;
    }
    
    this.deliveryService.updateAgentStatus(this.agent.id, newStatus).subscribe({
      next: () => {
        this.agent.status = newStatus;
        localStorage.setItem('deliveryAgent', JSON.stringify(this.agent));
        alert('Status updated');
      },
      error: (error) => {
        console.error('Error updating status:', error);
        alert('Error updating status: ' + (error.error?.message || error.message));
      }
    });
  }

  logout() {
    localStorage.removeItem('deliveryAgent');
    this.router.navigate(['/delivery-agent/login']);
  }

  getStatusColor(status: string): string {
    switch (status) {
      case 'DELIVERED': return 'success';
      case 'SHIPPED': return 'warning';
      case 'CONFIRMED':
      case 'PROCESSING': return 'info';
      default: return 'secondary';
    }
  }
}
