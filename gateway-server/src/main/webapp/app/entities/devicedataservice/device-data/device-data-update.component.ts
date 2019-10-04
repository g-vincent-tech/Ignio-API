import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDeviceData, DeviceData } from 'app/shared/model/devicedataservice/device-data.model';
import { DeviceDataService } from './device-data.service';

@Component({
  selector: 'jhi-device-data-update',
  templateUrl: './device-data-update.component.html'
})
export class DeviceDataUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    deviceId: [null, [Validators.required]],
    epoch: [null, [Validators.required]]
  });

  constructor(protected deviceDataService: DeviceDataService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ deviceData }) => {
      this.updateForm(deviceData);
    });
  }

  updateForm(deviceData: IDeviceData) {
    this.editForm.patchValue({
      id: deviceData.id,
      deviceId: deviceData.deviceId,
      epoch: deviceData.epoch
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const deviceData = this.createFromForm();
    if (deviceData.id !== undefined) {
      this.subscribeToSaveResponse(this.deviceDataService.update(deviceData));
    } else {
      this.subscribeToSaveResponse(this.deviceDataService.create(deviceData));
    }
  }

  private createFromForm(): IDeviceData {
    return {
      ...new DeviceData(),
      id: this.editForm.get(['id']).value,
      deviceId: this.editForm.get(['deviceId']).value,
      epoch: this.editForm.get(['epoch']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeviceData>>) {
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
