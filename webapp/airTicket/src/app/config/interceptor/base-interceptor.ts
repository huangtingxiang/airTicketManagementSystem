import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpHeaderResponse, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError, mergeMap, tap} from 'rxjs/operators';
import {Router} from '@angular/router';

@Injectable()
export class BaseInterceptor implements HttpInterceptor {

  constructor(private router: Router) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      mergeMap((event: any) => {
        return of(event);
      }),
      catchError((error: HttpErrorResponse) => {
        return this.handleHttpException(error);
      })
    );
  }

  private handleHttpException(error: HttpErrorResponse): Observable<HttpErrorResponse> {
    switch (error.status) {
      case 401:
        if (this.router.url !== '/login') {
          // 未登录，跳转到登录页
          this.router.navigateByUrl('/login');
        }
        break;
      case 403:
        break;
      case 404:
        break;
    }
    // 最终将异常抛出来，便于组件个性化处理
    throw error;
  }
}
