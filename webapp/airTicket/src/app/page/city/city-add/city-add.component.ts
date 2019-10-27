import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CityService} from '../../../core/service/city.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-city-add',
  templateUrl: './city-add.component.html',
  styleUrls: ['./city-add.component.css']
})
export class CityAddComponent implements OnInit {

  cityForm: FormGroup;

  constructor(private fb: FormBuilder,
              private cityService: CityService,
              private router: Router) {
  }

  ngOnInit() {
    this.cityForm = this.fb.group({
      name: ['', Validators.required],
      pinyin: ['', Validators.required],
      primaried: [true, Validators.required]
    });
  }

  save() {
    this.cityService.save(this.cityForm.value)
      .subscribe(() => {
        this.router.navigateByUrl('/city');
      });
  }

}
