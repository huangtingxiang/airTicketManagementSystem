import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {CityIndexComponent} from './city-index/city-index.component';


const routes: Routes = [
  {path: '', component: CityIndexComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CityRoutingModule {
}
