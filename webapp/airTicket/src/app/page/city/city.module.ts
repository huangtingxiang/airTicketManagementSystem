import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {CityRoutingModule} from './city-routing.module';
import {CityIndexComponent} from './city-index/city-index.component';
import {ShareModule} from '../../share/share.module';
import { CityAddComponent } from './city-add/city-add.component';
import { CityEditComponent } from './city-edit/city-edit.component';


@NgModule({
  declarations: [CityIndexComponent, CityAddComponent, CityEditComponent],
  imports: [
    CommonModule,
    CityRoutingModule,
    ShareModule
  ]
})
export class CityModule {
}
