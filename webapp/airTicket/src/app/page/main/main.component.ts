import {Component, OnInit} from '@angular/core';
import {UserService} from '../../core/service/user.service';
import {Router} from '@angular/router';
import {User} from '../../entity/User';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  loginUser: User;

  constructor(private userService: UserService,
              private router: Router) {
  }

  ngOnInit() {
    this.userService.currentLoginUser()
      .subscribe((user) => {
        this.loginUser = user;
      });
  }

  logout() {
    this.userService.logout()
      .subscribe(() => {
        this.router.navigateByUrl('/login');
      });
  }

}
