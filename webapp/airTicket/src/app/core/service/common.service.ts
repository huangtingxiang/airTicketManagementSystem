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
}
