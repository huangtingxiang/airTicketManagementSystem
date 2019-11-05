import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {MainComponent} from './page/main/main.component';
import {MainIndexComponent} from './page/main-index/main-index.component';


const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    children: [
      {
        path: '',
        component: MainIndexComponent,
      },
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
      },
      {
        path: 'user-management',
        loadChildren: () => import('./page/user/user.module').then(mod => mod.UserModule)
      }
    ]
  },
  {
    path: 'login',
    loadChildren: () => import('./page/login/login.module').then(mod => mod.LoginModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
