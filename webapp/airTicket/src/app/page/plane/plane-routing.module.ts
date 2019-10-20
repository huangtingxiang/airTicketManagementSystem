import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {PlaneIndexComponent} from './plane-index/plane-index.component';


const routes: Routes = [
  {path: '', component: PlaneIndexComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PlaneRoutingModule {
}
