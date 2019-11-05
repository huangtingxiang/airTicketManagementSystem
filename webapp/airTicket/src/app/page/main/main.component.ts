import {Component, OnInit} from '@angular/core';
import {UserService} from '../../core/service/user.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  constructor(private userService: UserService,
              private router: Router) {
  }

  ngOnInit() {
  }

  logout() {
    this.userService.logout()
      .subscribe(() => {
        this.router.navigateByUrl('/login');
      });
  }

}
