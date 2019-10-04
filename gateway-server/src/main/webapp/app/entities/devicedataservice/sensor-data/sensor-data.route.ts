import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SensorData } from 'app/shared/model/devicedataservice/sensor-data.model';
import { SensorDataService } from './sensor-data.service';
import { SensorDataComponent } from './sensor-data.component';
import { SensorDataDetailComponent } from './sensor-data-detail.component';
import { SensorDataUpdateComponent } from './sensor-data-update.component';
import { SensorDataDeletePopupComponent } from './sensor-data-delete-dialog.component';
import { ISensorData } from 'app/shared/model/devicedataservice/sensor-data.model';

@Injectable({ providedIn: 'root' })
export class SensorDataResolve implements Resolve<ISensorData> {
  constructor(private service: SensorDataService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISensorData> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SensorData>) => response.ok),
        map((sensorData: HttpResponse<SensorData>) => sensorData.body)
      );
    }
    return of(new SensorData());
  }
}

export const sensorDataRoute: Routes = [
  {
    path: '',
    component: SensorDataComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'SensorData'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SensorDataDetailComponent,
    resolve: {
      sensorData: SensorDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SensorData'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SensorDataUpdateComponent,
    resolve: {
      sensorData: SensorDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SensorData'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SensorDataUpdateComponent,
    resolve: {
      sensorData: SensorDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SensorData'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sensorDataPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SensorDataDeletePopupComponent,
    resolve: {
      sensorData: SensorDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SensorData'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
