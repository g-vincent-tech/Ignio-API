import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IDevice, Device } from 'app/shared/model/devicedataservice/device.model';
import { DeviceService } from './device.service';

@Component({
  selector: 'jhi-device-update',
  templateUrl: './device-update.component.html'
})
export class DeviceUpdateComponent implements OnInit {
  isSaving: boolean;
  createdDp: any;
  purchasedDp: any;

  editForm = this.fb.group({
    id: [],
    deviceId: [null, [Validators.required]],
    ownerId: [],
    created: [],
    purchased: []
  });

  constructor(protected deviceService: DeviceService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ device }) => {
      this.updateForm(device);
    });
  }

  updateForm(device: IDevice) {
    this.editForm.patchValue({
      id: device.id,
      deviceId: device.deviceId,
      ownerId: device.ownerId,
      created: device.created,
      purchased: device.purchased
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const device = this.createFromForm();
    if (device.id !== undefined) {
      this.subscribeToSaveResponse(this.deviceService.update(device));
    } else {
      this.subscribeToSaveResponse(this.deviceService.create(device));
    }
  }

  private createFromForm(): IDevice {
    return {
      ...new Device(),
      id: this.editForm.get(['id']).value,
      deviceId: this.editForm.get(['deviceId']).value,
      ownerId: this.editForm.get(['ownerId']).value,
      created: this.editForm.get(['created']).value,
      purchased: this.editForm.get(['purchased']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDevice>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
