import {Component, OnInit} from '@angular/core';
import {UserService} from '../../../core/service/user.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {ErrorDialogComponent} from '../../../share/component/error-dialog/error-dialog.component';
import {MatDialog} from '@angular/material';

@Component({
  selector: 'app-login-index',
  templateUrl: './login-index.component.html',
  styleUrls: ['./login-index.component.css']
})
export class LoginIndexComponent implements OnInit {

  userForm: FormGroup;

  constructor(private userService: UserService,
              private dialog: MatDialog,
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
      }, (response) => {
        const title = '登陆失败';
        const content = response.error.errorMessage;
        this.dialog.open(ErrorDialogComponent, {
          width: '500px',
          data: {title, content}
        });
      });
  }

}
