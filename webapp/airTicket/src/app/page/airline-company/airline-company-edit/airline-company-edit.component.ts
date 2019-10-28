import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {AirlineCompanyService} from '../../../core/service/airline-company.service';
import {AirlineCompany} from '../../../entity/AirlineCompany';

@Component({
  selector: 'app-airline-company-edit',
  templateUrl: './airline-company-edit.component.html',
  styleUrls: ['./airline-company-edit.component.css']
})
export class AirlineCompanyEditComponent implements OnInit {

  airlineCompanyForm: FormGroup;

  id: number;

  constructor(private fb: FormBuilder,
              private router: Router,
              private airlineCompanyService: AirlineCompanyService,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.airlineCompanyForm = this.fb.group({
      name: ['', Validators.required],
      city: [null, Validators.required]
    });
    this.activatedRoute.paramMap.subscribe((params: ParamMap) => {
      this.id = +params.get('id');
      this.airlineCompanyService.getById(this.id)
        .subscribe((airlineCompany: AirlineCompany) => {
          this.airlineCompanyForm.patchValue(airlineCompany);
        });
    });
  }

  update() {
    this.airlineCompanyService.update(this.id, this.airlineCompanyForm.value)
      .subscribe(() => {
        this.router.navigateByUrl('/airline-company');
      });
  }

}
