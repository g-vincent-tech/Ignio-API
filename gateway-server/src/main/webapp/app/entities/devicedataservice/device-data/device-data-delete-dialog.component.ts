import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeviceData } from 'app/shared/model/devicedataservice/device-data.model';
import { DeviceDataService } from './device-data.service';

@Component({
  selector: 'jhi-device-data-delete-dialog',
  templateUrl: './device-data-delete-dialog.component.html'
})
export class DeviceDataDeleteDialogComponent {
  deviceData: IDeviceData;

  constructor(
    protected deviceDataService: DeviceDataService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.deviceDataService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'deviceDataListModification',
        content: 'Deleted an deviceData'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-device-data-delete-popup',
  template: ''
})
export class DeviceDataDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ deviceData }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DeviceDataDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.deviceData = deviceData;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/device-data', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/device-data', { outlets: { popup: null } }]);
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
