import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {PlaneIndexComponent} from './plane-index/plane-index.component';
import {PlaneAddComponent} from './plane-add/plane-add.component';


const routes: Routes = [
  {path: '', component: PlaneIndexComponent},
  {path:  'add', component: PlaneAddComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PlaneRoutingModule {
}
