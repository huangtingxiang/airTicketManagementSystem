import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef, MatTableDataSource} from '@angular/material';
import {ShipSpace} from '../../../entity/ShipSpace';
import {SeatConfig} from '../../../config/SeatConfig';
import {Seat} from '../../../entity/Seat';

@Component({
  selector: 'app-plane-seat',
  templateUrl: './plane-seat.component.html',
  styleUrls: ['./plane-seat.component.css']
})
export class PlaneSeatComponent implements OnInit {

  // 座位表显示的行数
  seatDisplayedColumns = ['index'];

  // 座位表显示总行数
  totalSign = [];

  // 显示的舱位
  disPlayShipSpaces: Array<{ color: string, shipSpace: ShipSpace }> = [];

  // 座位表数据源
  seatDataSource: MatTableDataSource<[][]> = new MatTableDataSource<any>();

  constructor(public dialogRef: MatDialogRef<PlaneSeatComponent>,
              @Inject(MAT_DIALOG_DATA) public data: PlaneSeatData) {
  }

  ngOnInit() {
    // 生成表格
    const total = SeatConfig.totalCol;
    this.seatDisplayedColumns = ['index'];
    this.totalSign = [];
    for (let i = 0; i < this.data.totalCol; i++) {
      this.totalSign.push(total[i]);
      this.seatDisplayedColumns.push(total[i]);
    }
    // 创建座位数据
    const seatData = new Array(this.data.totalRow);
    for (let i = 0; i < this.data.totalRow; i++) {
      seatData[i] = new Array(this.data.totalCol);
    }
    this.data.shipSpaces.forEach((shipSpace: ShipSpace, index: number) => {
      this.disPlayShipSpaces.push({
        color: SeatConfig.allColors[index],
        shipSpace
      });
      shipSpace.seats.forEach((seat: Seat) => {
        seatData[seat.line - 1][seat.col - 1] = {
          color: SeatConfig.allColors[index],
          value: seat
        };
      });
    });
    this.seatDataSource.data = seatData;
  }


  onNoClick() {
    this.dialogRef.close();
  }
}

export interface PlaneSeatData {
  shipSpaces: ShipSpace[];
  totalRow: number;
  totalCol: number;
}
