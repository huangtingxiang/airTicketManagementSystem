import {Injectable} from '@angular/core';
import {CoreModule} from '../core.module';
import {Pageable} from '../../entity/norm/pageable';
import {Observable, of} from 'rxjs';
import {Page} from '../../entity/norm/page';
import {City} from '../../entity/City';
import {AirlineCompany} from '../../entity/AirlineCompany';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: CoreModule
})
export class AirlineCompanyService {

  baseUrl = 'api/airlineCompany';

  constructor(private httpClient: HttpClient) {
  }

  // 获取所有的分页
  getAllByPage(pageable: Pageable): Observable<Page<AirlineCompany>> {
    const airlineCompanies = [];
    for (let i = 1; i <= pageable.size; i++) {
      airlineCompanies.push(new AirlineCompany(i, 'company' + i, null, new City(i, 'city' + i, 'pinyin' + i, false)));
    }
    const pageData = new Page<AirlineCompany>();
    pageData.totalElements = 50;
    console.log(airlineCompanies);
    pageData.content = airlineCompanies;
    return of<Page<AirlineCompany>>(pageData);
  }

  // 通过名称获取分页
  pageByName(name: string, pageable: Pageable): Observable<Page<AirlineCompany>> {
    const params = {
      page: pageable.page.toString(),
      size: pageable.size.toString(),
      name
    };
    return this.httpClient.get<Page<AirlineCompany>>(this.baseUrl + '/getByName', {params});
  }

  // 保存
  save(airlineCompany: AirlineCompany): Observable<AirlineCompany> {
    return this.httpClient.post<AirlineCompany>(this.baseUrl, airlineCompany);
  }

  // 删除
  delete(id: number): Observable<any> {
    return this.httpClient.delete(this.baseUrl + '/' + id);
  }

  // 通过id获取
  getById(id: number): Observable<AirlineCompany> {
    return this.httpClient.get<AirlineCompany>(this.baseUrl + '/' + id);
  }

  // 更新
  update(id: number, airlineCompany: AirlineCompany): Observable<AirlineCompany> {
    return this.httpClient.put<AirlineCompany>(this.baseUrl + '/' + id, airlineCompany);
  }
}
