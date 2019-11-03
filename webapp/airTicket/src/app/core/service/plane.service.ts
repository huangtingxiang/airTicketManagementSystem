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
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: CoreModule
})
export class PlaneService {

  baseUrl = 'api/plane';

  constructor(private httpClient: HttpClient) {
  }

  // 更新
  update(id: number, plane: Plane): Observable<Plane> {
    return this.httpClient.put<Plane>(this.baseUrl + '/' + id, plane);
  }

  // 通过名称分页
  pageByName(name: string, pageable: Pageable): Observable<Page<Plane>> {
    const params = {
      page: pageable.page.toString(),
      size: pageable.size.toString(),
      name
    };
    return this.httpClient.get<Page<Plane>>(this.baseUrl + '/pageByName', {params});
  }

  // 保存
  save(plane: Plane): Observable<Plane> {
    return this.httpClient.post<Plane>(this.baseUrl, plane);
  }

  // 通过id获取
  getById(id: number): Observable<Plane> {
    return this.httpClient.get<Plane>(this.baseUrl + '/' + id);
  }

  // 删除
  delete(id: number): Observable<any> {
    return this.httpClient.delete(this.baseUrl + '/' + id);
  }

  // 通过航空公司获取
  getByAirlineCompany(id: number): Observable<Plane[]> {
    return this.httpClient.get<Plane[]>(`${this.baseUrl}/getByAirlineCompany/${id}`);
  }
}
