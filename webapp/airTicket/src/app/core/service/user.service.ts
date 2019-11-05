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

  // 登陆
  login(user: User): Observable<User> {
    return this.httClient.post<User>(this.baseUrl + '/login', user);
  }

  // 注销
  logout(): Observable<any> {
    return this.httClient.put(this.baseUrl + '/logout', {});
  }
}
