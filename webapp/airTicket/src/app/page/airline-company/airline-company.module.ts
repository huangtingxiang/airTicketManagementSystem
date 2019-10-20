import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AirlineCompanyRoutingModule } from './airline-company-routing.module';
import { AirlineCompanyIndexComponent } from './airline-company-index/airline-company-index.component';


@NgModule({
  declarations: [AirlineCompanyIndexComponent],
  imports: [
    CommonModule,
    AirlineCompanyRoutingModule
  ]
})
export class AirlineCompanyModule { }
