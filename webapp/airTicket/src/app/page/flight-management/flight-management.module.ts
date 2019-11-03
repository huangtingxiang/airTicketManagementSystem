import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {FlightManagementRoutingModule} from './flight-management-routing.module';
import {FlightManagementIndexComponent} from './flight-management-index/flight-management-index.component';
import {FlightManagementAddComponent} from './flight-management-add/flight-management-add.component';
import {ShareModule} from '../../share/share.module';
import { FlightManagementEditComponent } from './flight-management-edit/flight-management-edit.component';


@NgModule({
  declarations: [FlightManagementIndexComponent, FlightManagementAddComponent, FlightManagementEditComponent],
  imports: [
    CommonModule,
    ShareModule,
    FlightManagementRoutingModule
  ]
})
export class FlightManagementModule {
}
