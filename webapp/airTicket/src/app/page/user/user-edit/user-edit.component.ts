import {Component, OnInit} from '@angular/core';
import {UserService} from '../../../core/service/user.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {User} from '../../../entity/User';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Location} from '@angular/common';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent implements OnInit {

  id: number;

  password: FormControl;

  user: User;

  constructor(private userService: UserService,
              private fb: FormBuilder,
              private router: Router,
              private location: Location,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.password = this.fb.control({}, [Validators.required]);
    this.activatedRoute.paramMap.subscribe((params: ParamMap) => {
      this.id = +params.get('id');
      this.userService.getById(this.id)
        .subscribe((user: User) => {
          this.user = user;
          this.password.setValue(user.passWord);
        });
    });
  }

  back() {
    this.location.back();
  }

  resetPassword() {
    this.userService.resetPassword(this.id, this.password.value)
      .subscribe(() => {
        this.router.navigateByUrl('/user-management');
      });
  }

}
