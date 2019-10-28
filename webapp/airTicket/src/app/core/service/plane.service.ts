import {Injectable} from '@angular/core';
import {AirPort} from '../../entity/AirPort';
import {City} from '../../entity/City';
import {Page} from '../../entity/norm/page';
import {Observable, of} from 'rxjs';
import {Pageable} from '../../entity/norm/pageable';
import {Plane, PlaneType} from '../../entity/Plane';
import {AirlineCompany} from '../../entity/AirlineCompany';
import {ShipSpace} from '../../entity/ShipSpace';
import {CoreModule} from '../core.module';

@Injectable({
  providedIn: CoreModule
})
export class PlaneService {

  constructor() {
  }

  getAllByPage(pageable: Pageable): Observable<Page<Plane>> {
    let planes: any[];
    planes = [];
    for (let i = 0; i < 50 / pageable.size; i++) {
      planes.push(new Plane(i, 'palneName' + i, 'NULL', PlaneType.SMALL,
        new AirlineCompany(i, 'AirlineCompany' + i, 'NULL', new City(i, 'city' + i, 'pinyin', false))
        , [new ShipSpace('ShipSpace' + i, 1000, false), new ShipSpace('ShipSpace' + i, 1000, false)]));
    }
    const pageData = new Page<Plane>();
    pageData.totalElements = 50;
    pageData.content = planes;
    return of<Page<Plane>>(pageData);
  }
}
