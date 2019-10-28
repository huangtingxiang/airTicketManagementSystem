import {Component, Input, OnInit} from '@angular/core';
import {MatIconRegistry} from '@angular/material';
import {DomSanitizer} from '@angular/platform-browser';

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

  constructor(iconRegistry: MatIconRegistry, sanitizer: DomSanitizer) {
    iconRegistry.addSvgIcon(
      'seat',
      sanitizer.bypassSecurityTrustResourceUrl('assets/icon/seat.svg'));
  }

  ngOnInit() {
  }
}
