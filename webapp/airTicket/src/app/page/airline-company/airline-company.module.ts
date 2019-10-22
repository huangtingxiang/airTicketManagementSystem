import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AirlineCompanyRoutingModule} from './airline-company-routing.module';
import {AirlineCompanyIndexComponent} from './airline-company-index/airline-company-index.component';
import {ShareModule} from '../../share/share.module';
import { AirlineCompanyAddComponent } from './airline-company-add/airline-company-add.component';
import { AirlineCompanyEditComponent } from './airline-company-edit/airline-company-edit.component';


@NgModule({
  declarations: [AirlineCompanyIndexComponent, AirlineCompanyAddComponent, AirlineCompanyEditComponent],
  imports: [
    CommonModule,
    AirlineCompanyRoutingModule,
    ShareModule
  ]
})
export class AirlineCompanyModule {
}
