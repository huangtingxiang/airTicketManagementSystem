import {NgModule} from '@angular/core';
import {MatGridListModule, MatListModule, MatToolbarModule} from '@angular/material';


@NgModule({
  declarations: [],
  imports: [
    MatGridListModule,
    MatListModule,
    MatToolbarModule
  ],
  exports: [
    MatGridListModule,
    MatListModule,
    MatToolbarModule
  ]
})
export class ShareModule {
}
