import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISensorData } from 'app/shared/model/devicedataservice/sensor-data.model';

type EntityResponseType = HttpResponse<ISensorData>;
type EntityArrayResponseType = HttpResponse<ISensorData[]>;

@Injectable({ providedIn: 'root' })
export class SensorDataService {
  public resourceUrl = SERVER_API_URL + 'services/devicedataservice/api/sensor-data';

  constructor(protected http: HttpClient) {}

  create(sensorData: ISensorData): Observable<EntityResponseType> {
    return this.http.post<ISensorData>(this.resourceUrl, sensorData, { observe: 'response' });
  }

  update(sensorData: ISensorData): Observable<EntityResponseType> {
    return this.http.put<ISensorData>(this.resourceUrl, sensorData, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ISensorData>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISensorData[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
