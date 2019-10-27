import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {CityIndexComponent} from './city-index/city-index.component';
import {CityAddComponent} from './city-add/city-add.component';
import {CityEditComponent} from './city-edit/city-edit.component';


const routes: Routes = [
  {path: '', component: CityIndexComponent},
  {path: 'add', component: CityAddComponent},
  {path: 'edit/:id', component: CityEditComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CityRoutingModule {
}
