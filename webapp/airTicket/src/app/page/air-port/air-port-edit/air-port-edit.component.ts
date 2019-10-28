import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AirPortService} from '../../../core/service/air-port.service';
import {ActivatedRoute, Router} from '@angular/router';
import {AirPort} from '../../../entity/AirPort';

@Component({
  selector: 'app-air-port-edit',
  templateUrl: './air-port-edit.component.html',
  styleUrls: ['./air-port-edit.component.css']
})
export class AirPortEditComponent implements OnInit {

  id: number;

  airPortForm: FormGroup;

  constructor(private fb: FormBuilder,
              private activeRouter: ActivatedRoute,
              private router: Router,
              private airPortService: AirPortService) {
  }

  ngOnInit() {
    this.activeRouter.paramMap.subscribe((params) => {
      this.id = +params.get('id');
      this.airPortService.getById(this.id)
        .subscribe((airPort: AirPort) => {
          this.airPortForm.patchValue(airPort);
        });
    });
    this.airPortForm = this.fb.group({
      name: ['', Validators.required],
      city: [null, Validators.required],
      icon: [null]
    });
  }

  update() {
    this.airPortService.update(this.id, this.airPortForm.value)
      .subscribe(() => {
        this.router.navigateByUrl('/air-port');
      });
  }

}
