import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AirlineCompanyIndexComponent} from './airline-company-index/airline-company-index.component';


const routes: Routes = [
  {path: '', component: AirlineCompanyIndexComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AirlineCompanyRoutingModule {
}
