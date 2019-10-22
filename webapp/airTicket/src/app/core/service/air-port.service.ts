import {Injectable} from '@angular/core';
import {CoreModule} from '../core.module';
import {AirPort} from '../../entity/AirPort';
import {Page} from '../../entity/norm/page';
import {Pageable} from '../../entity/norm/pageable';
import {Observable, of} from 'rxjs';
import {City} from '../../entity/City';

@Injectable({
  providedIn: CoreModule
})
export class AirPortService {

  constructor() {
  }

  // 获取所有的分页
  getAllByPage(pageable: Pageable): Observable<Page<AirPort>> {
    let airPorts: any[];
    airPorts = [];
    console.log(pageable);
    for (let i = 0; i < 50 / pageable.size; i++) {
      airPorts.push(new AirPort('airport' + i, 'null', new City(i, 'city' + i, 'pinyin' + i, false)));
    }
    const pageData = new Page<AirPort>();
    pageData.totalElements = 50;
    pageData.content = airPorts;
    return of<Page<AirPort>>(pageData);
  }
}
