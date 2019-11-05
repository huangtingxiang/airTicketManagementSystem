import {Component, OnInit} from '@angular/core';
import {UserService} from '../../../core/service/user.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login-index',
  templateUrl: './login-index.component.html',
  styleUrls: ['./login-index.component.css']
})
export class LoginIndexComponent implements OnInit {

  userForm: FormGroup;

  constructor(private userService: UserService,
              private router: Router,
              private fb: FormBuilder) {
  }

  ngOnInit() {
    this.userForm = this.fb.group({
      userName: ['', Validators.required],
      passWord: ['', Validators.required]
    });
  }

  login() {
    this.userService.login(this.userForm.value)
      .subscribe(() => {
        this.router.navigateByUrl('/');
      });
  }

}
