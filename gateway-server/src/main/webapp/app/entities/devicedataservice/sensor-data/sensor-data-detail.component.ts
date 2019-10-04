import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISensorData } from 'app/shared/model/devicedataservice/sensor-data.model';

@Component({
  selector: 'jhi-sensor-data-detail',
  templateUrl: './sensor-data-detail.component.html'
})
export class SensorDataDetailComponent implements OnInit {
  sensorData: ISensorData;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sensorData }) => {
      this.sensorData = sensorData;
    });
  }

  previousState() {
    window.history.back();
  }
}
