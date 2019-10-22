import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AirPortRoutingModule } from './air-port-routing.module';
import { AirPortIndexComponent } from './air-port-index/air-port-index.component';
import {ShareModule} from '../../share/share.module';
import { AirPortAddComponent } from './air-port-add/air-port-add.component';
import { AirPortEditComponent } from './air-port-edit/air-port-edit.component';


@NgModule({
  declarations: [AirPortIndexComponent, AirPortAddComponent, AirPortEditComponent],
  imports: [
    CommonModule,
    AirPortRoutingModule,
    ShareModule
  ]
})
export class AirPortModule { }
