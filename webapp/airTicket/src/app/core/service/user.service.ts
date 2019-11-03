import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CoreModule} from '../core.module';
import {User} from '../../entity/User';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: CoreModule
})
export class UserService {

  baseUrl = 'api/user';

  constructor(private httClient: HttpClient) {
  }

  login(user: User): Observable<any> {
    return this.httClient.post(this.baseUrl + '/login', user);
  }
}
