import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AirPortRoutingModule } from './air-port-routing.module';
import { AirPortIndexComponent } from './air-port-index/air-port-index.component';


@NgModule({
  declarations: [AirPortIndexComponent],
  imports: [
    CommonModule,
    AirPortRoutingModule
  ]
})
export class AirPortModule { }
