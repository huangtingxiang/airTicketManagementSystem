import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl} from '@angular/forms';
@Component({
  selector: 'app-air-port-add',
  templateUrl: './air-port-add.component.html',
  styleUrls: ['./air-port-add.component.css']
})
export class AirPortAddComponent implements OnInit {

  test: FormControl = new FormControl('');

  constructor(private formBuilder: FormBuilder) {
  }

  ngOnInit() {

  }


  save() {
    console.log(this.test.value);
  }


}

