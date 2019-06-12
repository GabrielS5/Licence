import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../auth/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  loggedIn: boolean;

  constructor(private authenticationService: AuthenticationService, private router: Router) {
    this.loggedIn = localStorage.getItem('user') != null;
  }

  ngOnInit() {}

  onLogout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }
}
