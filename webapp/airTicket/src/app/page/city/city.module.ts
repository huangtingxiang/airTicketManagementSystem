import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CityRoutingModule } from './city-routing.module';
import { CityIndexComponent } from './city-index/city-index.component';


@NgModule({
  declarations: [CityIndexComponent],
  imports: [
    CommonModule,
    CityRoutingModule
  ]
})
export class CityModule { }
