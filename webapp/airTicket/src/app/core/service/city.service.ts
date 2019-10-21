import {Injectable} from '@angular/core';
import {CoreModule} from '../core.module';
import {Observable, of} from 'rxjs';
import {City} from '../../entity/City';

@Injectable({
  providedIn: CoreModule
})
export class CityService {

  constructor() {
  }

  // 获取所有城市
  getAll(): Observable<City[]> {
    const cities = new Array<City>();
    for (let i = 1; i < 50; i++) {
      cities.push(new City(i, 'city' + i, 'pinyin' + i, false));
    }
    return of<City[]>(cities);
  }

}
