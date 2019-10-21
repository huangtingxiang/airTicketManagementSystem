import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatPaginatorIntl} from '@angular/material';
import {PageMessage} from '../config/PageInfo';


@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  providers: [{provide: MatPaginatorIntl, useClass: PageMessage}] // 全局配置分页label
})
export class CoreModule {
}
