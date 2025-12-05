import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { CartService } from '../../services/cart.service';
import { OrderService } from '../../services/order.service';
import { PaymentService } from '../../services/payment.service';
import { CouponService } from '../../services/coupon.service';
import { AddressService, Address } from '../../services/address.service';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss']
})
export class CheckoutComponent implements OnInit {
  address: Address = {
    fullName: '',
    phone: '',
    line1: '',
    line2: '',
    city: '',
    state: '',
    pincode: ''
  };
  
  savedAddresses: Address[] = [];
  selectedAddressId: number | null = null;
  showAddressForm = false;
  saveThisAddress = false;
  
  paymentMethod = '';
  upiId = '';
  orderItems: any[] = [];
  subtotal = 0;
  deliveryFee = 30;
  tax = 0;
  total = 0;
  isProcessing = false;
  order: any = null;
  couponCode = '';
  couponDiscount = 0;
  couponApplied = false;
  couponMessage = '';

  constructor(
    private cartService: CartService,
    private orderService: OrderService,
    private paymentService: PaymentService,
    private couponService: CouponService,
    private addressService: AddressService,
    private router: Router
  ) {}

  ngOnInit() {
    this.cartService.cartItems$.subscribe(items => {
      this.orderItems = items;
      this.calculateTotals();
    });
    
    this.addressService.addresses$.subscribe(addresses => {
      this.savedAddresses = addresses;
      if (addresses.length > 0 && !this.selectedAddressId) {
        const defaultAddr = this.addressService.getDefaultAddress();
        if (defaultAddr) {
          this.selectAddress(defaultAddr.id!);
        }
      }
    });
  }

  calculateTotals() {
    this.subtotal = this.orderItems.reduce((sum, item) => sum + (item.price * item.quantity), 0);
    this.tax = Math.round(this.subtotal * 0.05);
    this.total = this.subtotal + this.deliveryFee + this.tax - this.couponDiscount;
  }

  applyCoupon() {
    if (!this.couponCode.trim()) {
      this.couponMessage = 'Please enter a coupon code';
      return;
    }

    console.log('Applying coupon:', this.couponCode, 'Amount:', this.subtotal);
    
    this.couponService.validateCoupon(this.couponCode, this.subtotal).subscribe({
      next: (response) => {
        console.log('Coupon response:', response);
        if (response.valid) {
          this.couponDiscount = response.discount;
          this.couponApplied = true;
          this.couponMessage = `Coupon applied! Discount: ₹${response.discount}`;
          this.calculateTotals();
        } else {
          this.couponMessage = response.message || 'Invalid coupon code';
          this.couponApplied = false;
          this.couponDiscount = 0;
        }
      },
      error: (error) => {
        console.error('Coupon error:', error);
        this.couponMessage = error.error?.message || 'Error validating coupon';
        this.couponApplied = false;
        this.couponDiscount = 0;
      }
    });
  }

  removeCoupon() {
    this.couponCode = '';
    this.couponDiscount = 0;
    this.couponApplied = false;
    this.couponMessage = '';
    this.calculateTotals();
  }
  
  selectAddress(addressId: number) {
    this.selectedAddressId = addressId;
    const selected = this.savedAddresses.find(addr => addr.id === addressId);
    if (selected) {
      this.address = { ...selected };
      this.showAddressForm = false;
    }
  }
  
  useNewAddress() {
    this.selectedAddressId = null;
    this.address = {
      fullName: '',
      phone: '',
      line1: '',
      line2: '',
      city: '',
      state: '',
      pincode: ''
    };
    this.showAddressForm = true;
  }
  
  saveAddress() {
    if (this.saveThisAddress && this.address.fullName && this.address.phone && this.address.line1) {
      this.addressService.saveAddress(this.address).subscribe({
        next: () => {
          this.addressService.loadAddresses();
          this.saveThisAddress = false;
        },
        error: (error) => console.error('Error saving address:', error)
      });
    }
  }

  placeOrder() {
    if (!this.paymentMethod || this.paymentMethod.trim() === '') {
      alert('Please select a payment method');
      return;
    }

    if (!this.address.fullName || !this.address.phone || !this.address.line1) {
      alert('Please fill in all required address fields');
      return;
    }

    if (this.paymentMethod === 'upi' && !this.upiId) {
      alert('Please enter your UPI ID');
      return;
    }

    if (this.orderItems.length === 0) {
      alert('Your cart is empty');
      return;
    }

    this.isProcessing = true;
    
    // Save address if requested
    this.saveAddress();
    
    const fullAddress = `${this.address.line1}, ${this.address.line2}, ${this.address.city}, ${this.address.state} ${this.address.pincode}`;
    
    this.orderService.createOrder(fullAddress, this.address.phone).subscribe({
      next: (response) => {
        this.order = response.order;
        this.processPayment();
      },
      error: (error) => {
        console.error('Error creating order:', error);
        alert('Error creating order. Please try again.');
        this.isProcessing = false;
      }
    });
  }

  processPayment() {
    if (!this.order) return;
    this.completeOrder(0);
  }

  completeOrder(paymentId: number) {
    const clearResult = this.cartService.clearCart();
    if (clearResult instanceof Observable) {
      clearResult.subscribe({
        next: () => {
          alert(`Order placed successfully! Order ID: ${this.order.id}`);
          this.router.navigate(['/orders']);
          this.isProcessing = false;
        },
        error: () => {
          alert(`Order placed successfully! Order ID: ${this.order.id}`);
          this.router.navigate(['/orders']);
          this.isProcessing = false;
        }
      });
    } else {
      alert(`Order placed successfully! Order ID: ${this.order.id}`);
      this.router.navigate(['/orders']);
      this.isProcessing = false;
    }
  }
}
