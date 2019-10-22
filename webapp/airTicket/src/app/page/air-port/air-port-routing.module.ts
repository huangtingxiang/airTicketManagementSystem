import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AirPortIndexComponent} from './air-port-index/air-port-index.component';
import {AirPortAddComponent} from './air-port-add/air-port-add.component';
import {AirPortEditComponent} from './air-port-edit/air-port-edit.component';


const routes: Routes = [
  {path: '', component: AirPortIndexComponent},
  {path: 'add', component: AirPortAddComponent},
  {path: 'edit', component: AirPortEditComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AirPortRoutingModule {
}
