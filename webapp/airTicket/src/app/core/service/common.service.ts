import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CoreModule} from '../core.module';

@Injectable({
  providedIn: CoreModule
})
export class CommonService {

  baseUrl = 'api/common';

  constructor(private httpClient: HttpClient) {
  }

  uploadImage(file: File): Observable<string> {
    const formData: FormData = new FormData();

    formData.append('file', file);

    return this.httpClient.post<string>(this.baseUrl + '/upload', formData);
  }

  // 查找实体是否存在数组 存在则 返回true
  findEntityInArray(array: Entity[], e: Entity): boolean {
    if (array.length === 0) {
      return false;
    } else {
      return array.find((entity) => {
        return entity.id === e.id;
      }) ? true : false;
    }
  }
}

export interface Entity {
  id: number;
}
