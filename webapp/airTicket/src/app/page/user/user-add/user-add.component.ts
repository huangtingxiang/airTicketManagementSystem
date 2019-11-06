import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../../core/service/user.service';
import {Router} from '@angular/router';
import {MatDialog} from '@angular/material';
import {HttpErrorResponse} from '@angular/common/http';
import {ErrorDialogComponent} from '../../../share/component/error-dialog/error-dialog.component';

@Component({
  selector: 'app-user-add',
  templateUrl: './user-add.component.html',
  styleUrls: ['./user-add.component.css']
})
export class UserAddComponent implements OnInit {

  userForm: FormGroup;

  constructor(private fb: FormBuilder,
              private router: Router,
              private dialog: MatDialog,
              private userService: UserService) {
  }

  ngOnInit() {
    this.userForm = this.fb.group({
      userName: ['', Validators.required],
      passWord: ['', Validators.required]
    });
  }

  save() {
    this.userService.saveRoot(this.userForm.value)
      .subscribe(() => {
        this.router.navigateByUrl('/user-management');
      }, (response) => {
        const title = '增加失败';
        let content;
        if (response.status === 409) {
          content = '用户名已存在';
        } else {
          content = '服务器错误';
        }
        this.dialog.open(ErrorDialogComponent, {
          width: '500px',
          data: {title, content}
        });
      });
  }

}
