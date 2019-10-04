import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DeviceData } from 'app/shared/model/devicedataservice/device-data.model';
import { DeviceDataService } from './device-data.service';
import { DeviceDataComponent } from './device-data.component';
import { DeviceDataDetailComponent } from './device-data-detail.component';
import { DeviceDataUpdateComponent } from './device-data-update.component';
import { DeviceDataDeletePopupComponent } from './device-data-delete-dialog.component';
import { IDeviceData } from 'app/shared/model/devicedataservice/device-data.model';

@Injectable({ providedIn: 'root' })
export class DeviceDataResolve implements Resolve<IDeviceData> {
  constructor(private service: DeviceDataService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDeviceData> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DeviceData>) => response.ok),
        map((deviceData: HttpResponse<DeviceData>) => deviceData.body)
      );
    }
    return of(new DeviceData());
  }
}

export const deviceDataRoute: Routes = [
  {
    path: '',
    component: DeviceDataComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'DeviceData'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DeviceDataDetailComponent,
    resolve: {
      deviceData: DeviceDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DeviceData'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DeviceDataUpdateComponent,
    resolve: {
      deviceData: DeviceDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DeviceData'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DeviceDataUpdateComponent,
    resolve: {
      deviceData: DeviceDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DeviceData'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const deviceDataPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DeviceDataDeletePopupComponent,
    resolve: {
      deviceData: DeviceDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DeviceData'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
