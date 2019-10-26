import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatPaginatorIntl} from '@angular/material';
import {PageMessage} from '../config/PageInfo';
import {HttpClientModule} from '@angular/common/http';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    HttpClientModule
  ],
  providers: [{provide: MatPaginatorIntl, useClass: PageMessage}] // 全局配置分页label
})
export class CoreModule {
}
