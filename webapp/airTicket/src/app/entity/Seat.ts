// 座位实体

import {ShipSpace} from './ShipSpace';

export class Seat {

  id: number;

  number: string; // 编号

  line: number; // 行数

  col: number; // 列数

  shipSpace: ShipSpace;

  constructor(id: number, number1: string, row: number, column: number) {
    this.id = id;
    this.number = number1;
    this.line = row;
    this.col = column;
  }
}
