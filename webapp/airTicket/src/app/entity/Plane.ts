// 飞机实体

import {AirlineCompany} from './AirlineCompany';
import {ShipSpace} from './ShipSpace';

export class Plane {

  id: number; // id

  name: string; // 名称

  icon: string; // 图标

  planeType: PlaneType; // 飞机机型

  airlineCompany: AirlineCompany; // 所属公司

  shipSpaces: Array<ShipSpace>; // 舱位集合

  // 总行数
  totalRow: number;

  // 总列数
  totalCol: number;

  constructor(id: number, name: string, icon: string, planeType: PlaneType, airlineCompany: AirlineCompany, shipSpaces: Array<ShipSpace>) {
    this.id = id;
    this.name = name;
    this.icon = icon;
    this.planeType = planeType;
    this.airlineCompany = airlineCompany;
    this.shipSpaces = shipSpaces;
  }
}

export enum PlaneType {
  LARGE, MEDIUM, SMALL
}
