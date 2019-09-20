import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDevice } from 'app/shared/model/devicedataservice/device.model';

type EntityResponseType = HttpResponse<IDevice>;
type EntityArrayResponseType = HttpResponse<IDevice[]>;

@Injectable({ providedIn: 'root' })
export class DeviceService {
  public resourceUrl = SERVER_API_URL + 'services/devicedataservice/api/devices';

  constructor(protected http: HttpClient) {}

  create(device: IDevice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(device);
    return this.http
      .post<IDevice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(device: IDevice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(device);
    return this.http
      .put<IDevice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IDevice>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDevice[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(device: IDevice): IDevice {
    const copy: IDevice = Object.assign({}, device, {
      created: device.created != null && device.created.isValid() ? device.created.format(DATE_FORMAT) : null,
      purchased: device.purchased != null && device.purchased.isValid() ? device.purchased.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.created = res.body.created != null ? moment(res.body.created) : null;
      res.body.purchased = res.body.purchased != null ? moment(res.body.purchased) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((device: IDevice) => {
        device.created = device.created != null ? moment(device.created) : null;
        device.purchased = device.purchased != null ? moment(device.purchased) : null;
      });
    }
    return res;
  }
}
