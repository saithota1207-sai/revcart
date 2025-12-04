import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  activeTab = 'profile';
  profilePicturePreview: string | null = null;
  isUpdating = false;
  
  user = {
    firstName: '',
    lastName: '',
    email: '',
    phone: '',
    profilePicture: null as string | null
  };
  
  addresses = [
    {
      type: 'Home',
      line1: '123 Main Street',
      line2: 'Apartment 4B',
      city: 'Mumbai',
      state: 'Maharashtra',
      pincode: '400001',
      phone: '+91 9876543210'
    },
    {
      type: 'Office',
      line1: '456 Business Park',
      line2: 'Floor 5',
      city: 'Mumbai',
      state: 'Maharashtra',
      pincode: '400002',
      phone: '+91 9876543210'
    }
  ];
  
  orders = [
    { id: '12345', date: '2024-01-15', items: 5, total: 450, status: 'Delivered' },
    { id: '12346', date: '2024-01-10', items: 3, total: 280, status: 'Out for Delivery' },
    { id: '12347', date: '2024-01-05', items: 7, total: 620, status: 'Delivered' }
  ];
  
  settings = {
    emailNotifications: true,
    smsNotifications: false,
    shareData: true
  };

  constructor(
    private authService: AuthService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.loadUserData();
  }

  loadUserData(): void {
    const currentUser = this.authService.getCurrentUser();
    if (currentUser) {
      this.user.firstName = currentUser.firstName || '';
      this.user.lastName = currentUser.lastName || '';
      this.user.email = currentUser.email || '';
      this.user.phone = currentUser.phone || '';
      this.user.profilePicture = currentUser.profilePicture || null;
      this.profilePicturePreview = currentUser.profilePicture;
    }
  }

  onProfilePictureSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.profilePicturePreview = e.target.result;
        this.user.profilePicture = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  }

  updateProfile(): void {
    this.isUpdating = true;
    const updateData = {
      firstName: this.user.firstName,
      lastName: this.user.lastName,
      phone: this.user.phone,
      profilePicture: this.user.profilePicture
    };

    this.userService.updateUserProfile(updateData).subscribe({
      next: (response) => {
        const updatedUser = this.authService.getCurrentUser();
        updatedUser.firstName = this.user.firstName;
        updatedUser.lastName = this.user.lastName;
        updatedUser.phone = this.user.phone;
        updatedUser.profilePicture = this.user.profilePicture;
        this.authService.setCurrentUser(updatedUser, this.authService.getToken()!);
        alert('Profile updated successfully!');
        this.isUpdating = false;
      },
      error: (error) => {
        console.error('Error updating profile:', error);
        alert('Failed to update profile');
        this.isUpdating = false;
      }
    });
  }
  
  getStatusColor(status: string): string {
    switch (status) {
      case 'Delivered': return 'success';
      case 'Out for Delivery': return 'warning';
      case 'Processing': return 'info';
      default: return 'secondary';
    }
  }
}
