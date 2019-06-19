import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../auth/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  name = 'name';
  password = 'password';

  constructor(private authenticationService: AuthenticationService, private router: Router) {}

  ngOnInit() {}

  onClick() {
    this.authenticationService.login(this.name, this.password).subscribe(event => {
      if (event != null) {
        localStorage.setItem('user', JSON.stringify(event));
      }
      this.router.navigate(['/programs']);
    });
  }
}
