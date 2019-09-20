import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDeviceData } from 'app/shared/model/devicedataservice/device-data.model';

type EntityResponseType = HttpResponse<IDeviceData>;
type EntityArrayResponseType = HttpResponse<IDeviceData[]>;

@Injectable({ providedIn: 'root' })
export class DeviceDataService {
  public resourceUrl = SERVER_API_URL + 'services/devicedataservice/api/device-data';

  constructor(protected http: HttpClient) {}

  create(deviceData: IDeviceData): Observable<EntityResponseType> {
    return this.http.post<IDeviceData>(this.resourceUrl, deviceData, { observe: 'response' });
  }

  update(deviceData: IDeviceData): Observable<EntityResponseType> {
    return this.http.put<IDeviceData>(this.resourceUrl, deviceData, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IDeviceData>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDeviceData[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
