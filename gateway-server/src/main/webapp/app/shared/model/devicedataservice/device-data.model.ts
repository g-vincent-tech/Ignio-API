import { ISensorData } from 'app/shared/model/devicedataservice/sensor-data.model';

export interface IDeviceData {
  id?: string;
  deviceId?: string;
  epoch?: string;
  sensorData?: ISensorData[];
}

export class DeviceData implements IDeviceData {
  constructor(public id?: string, public deviceId?: string, public epoch?: string, public sensorData?: ISensorData[]) {}
}
