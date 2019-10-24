// 座位实体

export class Seat {

  id: number;

  number: string; // 编号

  row: number; // 行数

  column: number; // 列数

  constructor(id: number, number1: string, row: number, column: number) {
    this.id = id;
    this.number = number1;
    this.row = row;
    this.column = column;
  }
}
