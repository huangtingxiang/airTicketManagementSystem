import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AirPortIndexComponent} from './air-port-index/air-port-index.component';


const routes: Routes = [
  {path: '', component: AirPortIndexComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AirPortRoutingModule {
}
