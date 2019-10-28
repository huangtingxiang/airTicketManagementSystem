import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {AirPortService} from '../../../core/service/air-port.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-air-port-add',
  templateUrl: './air-port-add.component.html',
  styleUrls: ['./air-port-add.component.css']
})
export class AirPortAddComponent implements OnInit {

  airPortForm: FormGroup;

  constructor(private fb: FormBuilder,
              private router: Router,
              private airPortService: AirPortService) {
  }

  ngOnInit() {
    this.airPortForm = this.fb.group({
      name: ['', Validators.required],
      city: [null, Validators.required],
      icon: [null]
    });
  }


  save() {
    this.airPortService.save(this.airPortForm.value)
      .subscribe(() => {
        this.router.navigateByUrl('air-port');
      });
  }

}

