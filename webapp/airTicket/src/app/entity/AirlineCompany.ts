// 航空公司

import {City} from './City';

export class AirlineCompany {

  id: number; // id

  name: string; // 名称

  icon: string; // 图标

  city: City; // 所属城市

  constructor(id: number, name: string, icon: string, city: City) {
    this.id = id;
    this.name = name;
    this.icon = icon;
    this.city = city;
  }
}
