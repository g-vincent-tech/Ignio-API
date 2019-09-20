export interface ISensorData {
  id?: string;
  deviceId?: string;
  temperature?: number;
  co_ppm?: number;
  lp_gas_ppm?: number;
  particle_ppm?: number;
  epoch?: string;
}

export class SensorData implements ISensorData {
  constructor(
    public id?: string,
    public deviceId?: string,
    public temperature?: number,
    public co_ppm?: number,
    public lp_gas_ppm?: number,
    public particle_ppm?: number,
    public epoch?: string
  ) {}
}
