// 舱位实体

import {Seat} from './Seat';

export class ShipSpace {

  id: number;

  described: string; // 描述

  price: number; // 票价

  primary: boolean; // 是否为主舱位

  seats: Array<Seat>; // 座位集合

  constructor(described: string, price: number, primary: boolean) {
    this.described = described;
    this.price = price;
    this.primary = primary;
  }
}
