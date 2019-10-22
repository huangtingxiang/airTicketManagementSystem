// 机场表

import {City} from './City';

export class AirPort {

  id: number; // id

  icon: string; // 图标

  name: string;

  city: City; // 所属城市

  constructor(name: string, icon: string, city: City) {
    this.icon = icon;
    this.name = name;
    this.city = city;
  }

}
