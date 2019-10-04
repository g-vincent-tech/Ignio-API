import { Moment } from 'moment';

export interface IDevice {
  id?: string;
  deviceId?: string;
  ownerId?: string;
  created?: Moment;
  purchased?: Moment;
}

export class Device implements IDevice {
  constructor(public id?: string, public deviceId?: string, public ownerId?: string, public created?: Moment, public purchased?: Moment) {}
}
