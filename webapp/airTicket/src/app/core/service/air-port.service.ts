import {Injectable} from '@angular/core';
import {CoreModule} from '../core.module';
import {AirPort} from '../../entity/AirPort';
import {Page} from '../../entity/norm/page';
import {Pageable} from '../../entity/norm/pageable';
import {Observable, of} from 'rxjs';
import {City} from '../../entity/City';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: CoreModule
})
export class AirPortService {

  baseUrl = 'api/airPort';

  constructor(private httpClient: HttpClient) {
  }

  // 获取所有的分页
  pageByName(name: string, pageable: Pageable): Observable<Page<AirPort>> {
    const params = {
      page: pageable.page.toString(),
      size: pageable.size.toString(),
      name
    };
    return this.httpClient.get<Page<AirPort>>(this.baseUrl + '/pageByName', {params});
  }

  // 通过id获取
  getById(id: number): Observable<AirPort> {
    return this.httpClient.get<AirPort>(this.baseUrl + '/' + id);
  }

  // 保存
  save(airPort: AirPort): Observable<AirPort> {
    return this.httpClient.post<AirPort>(this.baseUrl, airPort);
  }

  // 更新
  update(id: number, airPort: AirPort): Observable<AirPort> {
    return this.httpClient.put<AirPort>(this.baseUrl + '/' + id, airPort);
  }

  // 删除
  delete(id: number): Observable<any> {
    return this.httpClient.delete(this.baseUrl + '/' + id);
  }
}
