import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import {MatIconRegistry, MatSnackBar, MatTableDataSource} from '@angular/material';
import {ShipSpace} from '../../../entity/ShipSpace';
import {DomSanitizer} from '@angular/platform-browser';
import {Seat} from '../../../entity/Seat';
import {PlaneType} from '../../../entity/Plane';
import {PlaneService} from '../../../core/service/plane.service';

export interface State {
  flag: string;
  name: string;
  population: string;
}


@Component({
  selector: 'app-plane-add',
  templateUrl: './plane-add.component.html',
  styleUrls: ['./plane-add.component.css']
})
export class PlaneAddComponent implements OnInit {

  // 表单组
  planeForm: FormGroup;

  // 座位表显示的行数
  seatDisplayedColumns = ['index'];

  // 颜色集合
  colors = [
    '#d81e06',
    '#f4ea2a',
    '#1afa29',
    '#1296db',
    '#13227a',
    '#d4237a'
  ];

  selectColor = '#e2e2e2';

  // 当前选中舱位
  selectShipSpace: ShipSpace;

  // 座位表显示总行数
  totalSign = [];

  // 座位表数据源
  seatDataSource: MatTableDataSource<[][]> = new MatTableDataSource<any>();

  primaryCtrl = new FormControl(false);

  planeType = [
    {value: 0, label: '大机型'},
    {value: 1, label: '中机型 '},
    {value: 2, label: '小机型'}
  ];

  constructor(private snackBar: MatSnackBar,
              private planeService: PlaneService,
              private fb: FormBuilder) {
  }

  ngOnInit() {
    // 初始化表单组
    this.planeForm = this.fb.group({
      name: ['', Validators.required],
      icon: [null],
      planeType: [PlaneType.SMALL],
      totalRow: [null, Validators.required],
      totalCol: [null, Validators.required],
      airlineCompany: [null, Validators.required],
      shipSpaces: [null]
    });
    // 初始化舱位数据
    this.planeForm.get('shipSpaces').setValue([
      new ShipSpace('经济舱', 860, true),
      new ShipSpace('头等舱', 900, false)
    ]);
  }

  // 点击舱位按钮时
  openSnackBar(described: string, price: number) {
    let message;
    if (this.primaryCtrl.value) {
      message = '成功添加主舱位:' + described + ',价格:' + price + '!';
    } else {
      message = '成功添加舱位:' + described + ',价格:' + price + '！';
    }
    this.snackBar.open(message, '确认', {
      duration: 2000,
    });
    this.add(described, price, this.primaryCtrl.value);
  }

  // 删除舱位
  remove(shipSpace: ShipSpace) {
    const shipSpaces = this.planeForm.get('shipSpaces');
    shipSpaces.setValue(shipSpaces.value.filter((data) => {
      return shipSpace !== data;
    }));
  }

  // 添加舱位
  add(described: string, price: number, primary: boolean) {
    this.planeForm.get('shipSpaces').value.push(new ShipSpace(described, price, primary));
  }

  // 生成座位表
  createSeat() {
    // 清空舱位座位
    this.planeForm.get('shipSpaces').value.forEach((shipSpace: ShipSpace) => {
      shipSpace.seats = [];
    });
    // 生成表格
    const total = ['A', 'B', 'D', 'E', 'F', 'G', 'H', 'I', 'J'];
    this.seatDisplayedColumns = ['index'];
    this.totalSign = [];
    const seatData = [];
    for (let i = 0; i < this.planeForm.get('totalCol').value; i++) {
      this.totalSign.push(total[i]);
      this.seatDisplayedColumns.push(total[i]);
    }
    // 创建座位数据
    for (let i = 1; i <= this.planeForm.get('totalRow').value; i++) {
      const data = [];
      this.totalSign.forEach((sign: string, index: number) => {
        const disSeat = {
          value: new Seat(null, i + sign, i, index + 1),
          color: '#e2e2e2'
        };
        data.push(disSeat);
      });
      seatData.push(data);
    }
    this.seatDataSource.data = seatData;
  }

  // 当前选中舱位
  setSelectShipSpace(selectShipSpace: ShipSpace, color: string) {
    this.selectShipSpace = selectShipSpace;
    this.selectColor = color;
  }

  // 设置选中座位
  setShipSpaceToSeat(element: { value: Seat, color: string }) {
    element.color = this.selectColor;
    element.value.shipSpace = this.selectShipSpace;
  }

  // 选中所有行
  selectAllRow(row: number) {
    this.seatDataSource.data[row].forEach((element: any) => {
      this.setShipSpaceToSeat(element);
    });
  }

  // 选中所有列
  selectAllCol(col: number) {
    this.seatDataSource.data.forEach((cols: any) => {
      this.setShipSpaceToSeat(cols[col]);
    });
  }

  save() {
    // 将所有座位保存至舱位中
    this.seatDataSource.data.forEach((seatData: any) => {
      seatData.forEach((seatDisplay) => {
        if (seatDisplay.value.shipSpace) {
          if (!seatDisplay.value.shipSpace.seats) {
            seatDisplay.value.shipSpace.seats = [];
          }
          seatDisplay.value.shipSpace.seats.push(seatDisplay.value);
          seatDisplay.value.shipSpace = null;
        }
      });
    });
    this.planeService.save(this.planeForm.value)
      .subscribe((plane) => {
        console.log(plane);
      });
  }

}
