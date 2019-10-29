import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {PlaneIndexComponent} from './plane-index/plane-index.component';
import {PlaneAddComponent} from './plane-add/plane-add.component';
import {PlaneEditComponent} from './plane-edit/plane-edit.component';


const routes: Routes = [
  {path: '', component: PlaneIndexComponent},
  {path: 'add', component: PlaneAddComponent},
  {path: 'edit/:id', component: PlaneEditComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PlaneRoutingModule {
}
