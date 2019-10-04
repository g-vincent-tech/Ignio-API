import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeviceData } from 'app/shared/model/devicedataservice/device-data.model';

@Component({
  selector: 'jhi-device-data-detail',
  templateUrl: './device-data-detail.component.html'
})
export class DeviceDataDetailComponent implements OnInit {
  deviceData: IDeviceData;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ deviceData }) => {
      this.deviceData = deviceData;
    });
  }

  previousState() {
    window.history.back();
  }
}
