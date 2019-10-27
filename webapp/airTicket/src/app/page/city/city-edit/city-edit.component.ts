import {Component, OnInit} from '@angular/core';
import {CityService} from '../../../core/service/city.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-city-edit',
  templateUrl: './city-edit.component.html',
  styleUrls: ['./city-edit.component.css']
})
export class CityEditComponent implements OnInit {

  cityFormGroup: FormGroup;

  id: number;

  constructor(private cityService: CityService,
              private fb: FormBuilder,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.cityFormGroup = this.fb.group({
      name: [null, Validators.required],
      pinyin: [null, Validators.required],
      primaried: [true, Validators.required]
    });
    this.activatedRoute.paramMap.subscribe((paramMap: ParamMap) => {
      this.id = +paramMap.get('id');
      this.cityService.getById(this.id)
        .subscribe((city) => {
          this.cityFormGroup.patchValue(city);
        });
    });
  }

  update() {
    this.cityService.update(this.id, this.cityFormGroup.value)
      .subscribe(() => {
        this.router.navigateByUrl('/city');
      });
  }

}
