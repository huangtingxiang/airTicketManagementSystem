import {Component, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import {MatIconRegistry, MatSnackBar, MatTableDataSource} from '@angular/material';
import {ShipSpace} from '../../../entity/ShipSpace';
import {DomSanitizer} from '@angular/platform-browser';
import {Seat} from '../../../entity/Seat';

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

  // 初始化行数
  seatRow = 0;

  // 初始化列数
  seatCol = 0;

  // 座位表数据源
  seatDataSource: MatTableDataSource<any> = new MatTableDataSource<any>();

  // 飞机舱位
  shipSpaces: ShipSpace[];

  stateCtrl = new FormControl();

  primaryCtrl = new FormControl();

  filteredStates: Observable<State[]>;

  states: State[] = [
    {
      name: 'Arkansas',
      population: '2.978M',
      // https://commons.wikimedia.org/wiki/File:Flag_of_Arkansas.svg
      flag: 'https://upload.wikimedia.org/wikipedia/commons/9/9d/Flag_of_Arkansas.svg'
    },
    {
      name: 'California',
      population: '39.14M',
      // https://commons.wikimedia.org/wiki/File:Flag_of_California.svg
      flag: 'https://upload.wikimedia.org/wikipedia/commons/0/01/Flag_of_California.svg'
    },
    {
      name: 'Florida',
      population: '20.27M',
      // https://commons.wikimedia.org/wiki/File:Flag_of_Florida.svg
      flag: 'https://upload.wikimedia.org/wikipedia/commons/f/f7/Flag_of_Florida.svg'
    },
    {
      name: 'Texas',
      population: '27.47M',
      // https://commons.wikimedia.org/wiki/File:Flag_of_Texas.svg
      flag: 'https://upload.wikimedia.org/wikipedia/commons/f/f7/Flag_of_Texas.svg'
    }
  ];

  planeType = [
    {value: 0, label: '大机型'},
    {value: 1, label: '中机型 '},
    {value: 2, label: '小机型'}
  ];

  constructor(private snackBar: MatSnackBar, private iconRegistry: MatIconRegistry, private sanitizer: DomSanitizer) {
  }

  ngOnInit() {
    this.shipSpaces = [];
    this.shipSpaces.push(new ShipSpace('经济舱', 860, true));
    this.shipSpaces.push(new ShipSpace('头等舱', 900, false));
    console.log(this.shipSpaces);
    this.filteredStates = this.stateCtrl.valueChanges
      .pipe(
        startWith(''),
        map(state => state ? this._filterStates(state) : this.states.slice())
      );
  }

  private _filterStates(value: string): State[] {
    const filterValue = value.toLowerCase();

    return this.states.filter(state => state.name.toLowerCase().indexOf(filterValue) === 0);
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
    this.shipSpaces = this.shipSpaces.filter((data) => {
      return shipSpace !== data;
    });
  }

  // 添加舱位
  add(described: string, price: number, primary: boolean) {
    this.shipSpaces.push(new ShipSpace(described, price, primary));
  }

  // 生成座位表
  createSeat() {
    const total = ['A', 'B', 'D', 'E', 'F', 'G', 'H', 'I', 'J'];
    this.seatDisplayedColumns = ['index'];
    this.totalSign = [];
    const seatData = [];
    for (let i = 0; i < this.seatRow; i++) {
      this.totalSign.push(total[i]);
      this.seatDisplayedColumns.push(total[i]);
    }
    for (let i = 1; i <= this.seatCol; i++) {
      const data = [];
      this.totalSign.forEach((sign: string) => {
        const disSeat = {
          value: new Seat(0, i + sign),
          color: '#e2e2e2'
        };
        data.push(disSeat);
      });
      seatData.push(data);
    }
    this.seatDataSource = new MatTableDataSource<any>(seatData);
  }

  setSelectShipSpace(selectShipSpace: ShipSpace, color: string) {
    this.selectShipSpace = selectShipSpace;
    this.selectColor = color;
  }

  setShipSpaceToSeat(element: { value: Seat, color: string }) {
    element.color = this.selectColor;
  }

}
