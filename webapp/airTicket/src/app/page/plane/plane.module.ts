import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PlaneRoutingModule } from './plane-routing.module';
import { PlaneIndexComponent } from './plane-index/plane-index.component';


@NgModule({
  declarations: [PlaneIndexComponent],
  imports: [
    CommonModule,
    PlaneRoutingModule
  ]
})
export class PlaneModule { }
