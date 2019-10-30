import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {FlightManagementIndexComponent} from './flight-management-index/flight-management-index.component';
import {FlightManagementAddComponent} from './flight-management-add/flight-management-add.component';


const routes: Routes = [
  {path: '', component: FlightManagementIndexComponent},
  {path: 'add', component: FlightManagementAddComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FlightManagementRoutingModule {
}
