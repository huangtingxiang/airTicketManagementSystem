import { Injectable } from '@angular/core';
import {CoreModule} from '../core.module';
import {Pageable} from '../../entity/norm/pageable';
import {Observable, of} from 'rxjs';
import {Page} from '../../entity/norm/page';
import {City} from '../../entity/City';
import {AirlineCompany} from '../../entity/AirlineCompany';

@Injectable({
  providedIn: CoreModule
})
export class AirlineCompanyService {

  constructor() { }

  // 获取所有的分页
  getAllByPage(pageable: Pageable): Observable<Page<AirlineCompany>> {
    const airlineCompanies = [];
    for (let i = 1; i <= pageable.size; i++) {
      airlineCompanies.push(new AirlineCompany( i , 'company' + i, null, new City(i, 'city' + i, 'pinyin' + i, false)));
    }
    const pageData = new Page<AirlineCompany>();
    pageData.totalElements = 50;
    console.log(airlineCompanies);
    pageData.content = airlineCompanies;
    return of<Page<AirlineCompany>>(pageData);
  }
}
