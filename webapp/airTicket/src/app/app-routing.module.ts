import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';


const routes: Routes = [
  {
    path: 'air-port',
    loadChildren: () => import('./page/air-port/air-port.module').then(mod => mod.AirPortModule)
  },
  {
    path: 'airline-company',
    loadChildren: () => import('./page/airline-company/airline-company.module').then(mod => mod.AirlineCompanyModule)
  },
  {
    path: 'city',
    loadChildren: () => import('./page/city/city.module').then(mod => mod.CityModule)
  },
  {
    path: 'flight-management',
    loadChildren: () => import('./page/flight-management/flight-management.module').then(mod => mod.FlightManagementModule)
  },
  {
    path: 'plane',
    loadChildren: () => import('./page/plane/plane.module').then(mod => mod.PlaneModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
