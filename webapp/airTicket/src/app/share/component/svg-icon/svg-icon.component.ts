import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-svg-icon',
  templateUrl: './svg-icon.component.html',
  styleUrls: ['./svg-icon.component.css']
})
export class SvgIconComponent implements OnInit {

  @Input()
  iconName: string;

  @Input()
  color: string;

  constructor() {
  }

  ngOnInit() {

  }
}