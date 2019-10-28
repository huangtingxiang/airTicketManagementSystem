import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AirlineCompanyService} from '../../../core/service/airline-company.service';
import {Router} from '@angular/router';


@Component({
  selector: 'app-airline-company-add',
  templateUrl: './airline-company-add.component.html',
  styleUrls: ['./airline-company-add.component.css']
})
export class AirlineCompanyAddComponent implements OnInit {

  airlineCompanyForm: FormGroup;

  constructor(private fb: FormBuilder,
              private router: Router,
              private airlineCompanyService: AirlineCompanyService) {
  }

  ngOnInit() {
    this.airlineCompanyForm = this.fb.group({
      name: ['', Validators.required],
      city: [null, Validators.required]
    });
  }

  save() {
    this.airlineCompanyService.save(this.airlineCompanyForm.value)
      .subscribe(() => {
        this.router.navigateByUrl('airline-company');
      });
  }

}
