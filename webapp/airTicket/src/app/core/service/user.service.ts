import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CoreModule} from '../core.module';
import {User} from '../../entity/User';
import {Observable} from 'rxjs';
import {Page} from '../../entity/norm/page';
import {Pageable} from '../../entity/norm/pageable';

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

  // 保存管理员
  saveRoot(user: User): Observable<User> {
    return this.httClient.post<User>(this.baseUrl + '/root', user);
  }

  getAllByUserName(username: string, pageable: Pageable): Observable<Page<User>> {
    const params = {
      username,
      page: pageable.page.toString(),
      size: pageable.size.toString()
    };
    return this.httClient.get<Page<User>>(this.baseUrl + '/getAllByUserName', {params});
  }

  // 删除
  delete(id: number): Observable<any> {
    return this.httClient.delete(this.baseUrl + '/' + id);
  }

  // 重启
  reboot(id: number): Observable<any> {
    return this.httClient.put(this.baseUrl + '/reboot/' + id, {});
  }

  getById(id: number): Observable<User> {
    return this.httClient.get<User>(this.baseUrl + '/' + id);
  }

  resetPassword(id: number, passWord: string): Observable<any> {
    return this.httClient.put(`${this.baseUrl}/resetPassword/${id}`, {passWord});
  }
}
