import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISensorData } from 'app/shared/model/devicedataservice/sensor-data.model';
import { SensorDataService } from './sensor-data.service';

@Component({
  selector: 'jhi-sensor-data-delete-dialog',
  templateUrl: './sensor-data-delete-dialog.component.html'
})
export class SensorDataDeleteDialogComponent {
  sensorData: ISensorData;

  constructor(
    protected sensorDataService: SensorDataService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.sensorDataService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sensorDataListModification',
        content: 'Deleted an sensorData'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sensor-data-delete-popup',
  template: ''
})
export class SensorDataDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sensorData }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SensorDataDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sensorData = sensorData;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sensor-data', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sensor-data', { outlets: { popup: null } }]);
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
