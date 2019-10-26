import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PlaneRoutingModule } from './plane-routing.module';
import { PlaneIndexComponent } from './plane-index/plane-index.component';
import { PlaneAddComponent } from './plane-add/plane-add.component';
import {ShareModule} from '../../share/share.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';


@NgModule({
  declarations: [PlaneIndexComponent, PlaneAddComponent],
  imports: [
    CommonModule,
    PlaneRoutingModule,
    ShareModule
  ]
})
export class PlaneModule { }
