import {Injectable} from '@angular/core';
import {CoreModule} from '../core.module';
import {Observable, of} from 'rxjs';
import {City} from '../../entity/City';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: CoreModule
})
export class CityService {

  baseUrl = 'api/city';

  constructor(private httpClient: HttpClient) {
  }

  // 保存城市
  save(city: City): Observable<City> {
    return this.httpClient.post<City>(this.baseUrl, city);
  }

  // 通过id获取城市
  getById(id: number): Observable<City> {
    return this.httpClient.get<City>(this.baseUrl + '/' + id);
  }

  // 更新
  update(id: number, city: City): Observable<City> {
    return this.httpClient.put<City>(this.baseUrl + '/' + id, city);
  }

  // 删除
  delete(id: number): Observable<any> {
    return this.httpClient.delete(this.baseUrl + '/' + id);
  }

  // 获取所有城市
  getAll(): Observable<City[]> {
    return this.httpClient.get<City[]>(this.baseUrl);
  }

  // 设为热门城市
  setToPrimaried(id: number): Observable<any> {
    return this.httpClient.put(this.baseUrl + '/toPrimaried/' + id, {});
  }

  // 取消热门城市
  setToNotPrimaried(id: number): Observable<any> {
    return this.httpClient.put(this.baseUrl + '/notToPrimaried/' + id, {});
  }

  // 全部设为热门城市
  setAllToPrimaried(ids: Array<number>): Observable<any> {
    return this.httpClient.post(this.baseUrl + '/allToPrimaried', ids);
  }

  // 全部取消设为热门城市
  setAllToNotPrimaried(ids: Array<number>): Observable<any> {
    return this.httpClient.post(this.baseUrl + '/allNotToPrimaried', ids);
  }

}
