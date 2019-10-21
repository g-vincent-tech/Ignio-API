import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BillingInfo } from 'app/shared/model/billing-info.model';
import { BillingInfoService } from './billing-info.service';
import { BillingInfoComponent } from './billing-info.component';
import { BillingInfoDetailComponent } from './billing-info-detail.component';
import { BillingInfoUpdateComponent } from './billing-info-update.component';
import { BillingInfoDeletePopupComponent } from './billing-info-delete-dialog.component';
import { IBillingInfo } from 'app/shared/model/billing-info.model';

@Injectable({ providedIn: 'root' })
export class BillingInfoResolve implements Resolve<IBillingInfo> {
  constructor(private service: BillingInfoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBillingInfo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<BillingInfo>) => response.ok),
        map((billingInfo: HttpResponse<BillingInfo>) => billingInfo.body)
      );
    }
    return of(new BillingInfo());
  }
}

export const billingInfoRoute: Routes = [
  {
    path: '',
    component: BillingInfoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'BillingInfos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BillingInfoDetailComponent,
    resolve: {
      billingInfo: BillingInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BillingInfos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BillingInfoUpdateComponent,
    resolve: {
      billingInfo: BillingInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BillingInfos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BillingInfoUpdateComponent,
    resolve: {
      billingInfo: BillingInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BillingInfos'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const billingInfoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BillingInfoDeletePopupComponent,
    resolve: {
      billingInfo: BillingInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BillingInfos'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
