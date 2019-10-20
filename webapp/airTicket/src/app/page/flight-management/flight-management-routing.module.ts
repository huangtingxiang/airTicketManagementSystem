import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {FlightManagementIndexComponent} from './flight-management-index/flight-management-index.component';


const routes: Routes = [
  {path: '', component: FlightManagementIndexComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FlightManagementRoutingModule {
}
