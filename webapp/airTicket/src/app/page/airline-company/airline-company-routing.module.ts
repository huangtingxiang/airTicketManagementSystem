import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AirlineCompanyIndexComponent} from './airline-company-index/airline-company-index.component';
import {AirlineCompanyAddComponent} from './airline-company-add/airline-company-add.component';
import {AirlineCompanyEditComponent} from './airline-company-edit/airline-company-edit.component';


const routes: Routes = [
  {path: '', component: AirlineCompanyIndexComponent},
  {path: 'add', component: AirlineCompanyAddComponent},
  {path: 'edit', component: AirlineCompanyEditComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AirlineCompanyRoutingModule {
}
