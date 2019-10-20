// 飞机实体

import {AirlineCompany} from './AirlineCompany';
import {ShipSpace} from './ShipSpace';

export class Plane {

  id: number; // id

  name: string; // 名称

  icon: string; // 图标

  planeType: number; // 飞机机型

  airlineCompany: AirlineCompany; // 所属公司

  shipSpaces: Array<ShipSpace>; // 舱位集合
}
