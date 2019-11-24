import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {FlightManagement} from '../../entity/FlightManagement';
import {Observable} from 'rxjs';
import {Pageable} from '../../entity/norm/pageable';
import {Page} from '../../entity/norm/page';

@Injectable({
  providedIn: 'root'
})
export class FlightManagementService {

  baseUrl = 'api/flightManagement';

  constructor(private httpClient: HttpClient) {
  }

  // 保存
  save(flightManagement: FlightManagement): Observable<FlightManagement> {
    return this.httpClient.post<FlightManagement>(this.baseUrl, flightManagement);
  }

  //
  pageByStartingPlaceAndDestinationStartTime(startingPlaceId: number, destinationId: number, startTime: Date, pageable: Pageable): Observable<Page<FlightManagement>> {
    const params: any = {
      size: pageable.size.toString(),
      page: pageable.page.toString(),
    };
    if (startingPlaceId) {
      params.startingPlaceId = startingPlaceId.toString();
    }
    if (destinationId) {
      params.destinationId = destinationId.toString();
    }
    if (startTime) {
      params.startTime = startTime.getTime().toString();
    }
    return this.httpClient.get<Page<FlightManagement>>(this.baseUrl + '/page', {params});
  }

  // 通过id获取
  getById(id: number): Observable<FlightManagement> {
    return this.httpClient.get<FlightManagement>(this.baseUrl + '/' + id);
  }

  delete(id: number): Observable<any> {
    return this.httpClient.delete(this.baseUrl + '/' + id);
  }

  update(id: number, management: FlightManagement): Observable<FlightManagement> {
    return this.httpClient.put<FlightManagement>(this.baseUrl + '/' + id, management);
  }
}
