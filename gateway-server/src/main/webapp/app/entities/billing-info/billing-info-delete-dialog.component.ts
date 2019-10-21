import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBillingInfo } from 'app/shared/model/billing-info.model';
import { BillingInfoService } from './billing-info.service';

@Component({
  selector: 'jhi-billing-info-delete-dialog',
  templateUrl: './billing-info-delete-dialog.component.html'
})
export class BillingInfoDeleteDialogComponent {
  billingInfo: IBillingInfo;

  constructor(
    protected billingInfoService: BillingInfoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.billingInfoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'billingInfoListModification',
        content: 'Deleted an billingInfo'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-billing-info-delete-popup',
  template: ''
})
export class BillingInfoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ billingInfo }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BillingInfoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.billingInfo = billingInfo;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/billing-info', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/billing-info', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
