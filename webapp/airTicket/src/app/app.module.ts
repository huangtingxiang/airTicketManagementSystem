import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ShareModule} from './share/share.module';
import {AppRoutingModule} from './app-routing.module';
import {CoreModule} from './core/core.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatDatepickerModule, MatSnackBarModule} from '@angular/material';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {BaseInterceptor} from './config/interceptor/base-interceptor';
import { MainComponent } from './page/main/main.component';
import { MainIndexComponent } from './page/main-index/main-index.component';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    MainIndexComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ShareModule,
    AppRoutingModule,
    CoreModule,
    FormsModule,
    ReactiveFormsModule,
    MatSnackBarModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: BaseInterceptor, multi: true},
    MatDatepickerModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
