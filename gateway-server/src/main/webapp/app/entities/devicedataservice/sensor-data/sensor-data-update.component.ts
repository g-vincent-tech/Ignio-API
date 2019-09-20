import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISensorData, SensorData } from 'app/shared/model/devicedataservice/sensor-data.model';
import { SensorDataService } from './sensor-data.service';

@Component({
  selector: 'jhi-sensor-data-update',
  templateUrl: './sensor-data-update.component.html'
})
export class SensorDataUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    deviceId: [null, [Validators.required]],
    temperature: [null, [Validators.required]],
    co_ppm: [null, [Validators.required]],
    lp_gas_ppm: [null, [Validators.required]],
    particle_ppm: [null, [Validators.required]],
    epoch: [null, [Validators.required]]
  });

  constructor(protected sensorDataService: SensorDataService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sensorData }) => {
      this.updateForm(sensorData);
    });
  }

  updateForm(sensorData: ISensorData) {
    this.editForm.patchValue({
      id: sensorData.id,
      deviceId: sensorData.deviceId,
      temperature: sensorData.temperature,
      co_ppm: sensorData.co_ppm,
      lp_gas_ppm: sensorData.lp_gas_ppm,
      particle_ppm: sensorData.particle_ppm,
      epoch: sensorData.epoch
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sensorData = this.createFromForm();
    if (sensorData.id !== undefined) {
      this.subscribeToSaveResponse(this.sensorDataService.update(sensorData));
    } else {
      this.subscribeToSaveResponse(this.sensorDataService.create(sensorData));
    }
  }

  private createFromForm(): ISensorData {
    return {
      ...new SensorData(),
      id: this.editForm.get(['id']).value,
      deviceId: this.editForm.get(['deviceId']).value,
      temperature: this.editForm.get(['temperature']).value,
      co_ppm: this.editForm.get(['co_ppm']).value,
      lp_gas_ppm: this.editForm.get(['lp_gas_ppm']).value,
      particle_ppm: this.editForm.get(['particle_ppm']).value,
      epoch: this.editForm.get(['epoch']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISensorData>>) {
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
