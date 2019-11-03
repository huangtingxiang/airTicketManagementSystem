import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {FlightManagementIndexComponent} from './flight-management-index/flight-management-index.component';
import {FlightManagementAddComponent} from './flight-management-add/flight-management-add.component';
import {FlightManagementEditComponent} from './flight-management-edit/flight-management-edit.component';


const routes: Routes = [
  {path: '', component: FlightManagementIndexComponent},
  {path: 'add', component: FlightManagementAddComponent},
  {path: 'edit/:id', component: FlightManagementEditComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FlightManagementRoutingModule {
}
