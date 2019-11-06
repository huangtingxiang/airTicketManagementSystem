import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {PlaneService} from '../../../core/service/plane.service';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {PlaneType} from '../../../entity/Plane';
import {ShipSpace} from '../../../entity/ShipSpace';
import {MatSnackBar, MatTableDataSource} from '@angular/material';
import {SeatConfig} from '../../../config/SeatConfig';
import {Seat} from '../../../entity/Seat';

@Component({
  selector: 'app-plane-edit',
  templateUrl: './plane-edit.component.html',
  styleUrls: ['./plane-edit.component.css']
})
export class PlaneEditComponent implements OnInit {
  selectColor = SeatConfig.notSelectColor;

  // 当前选中舱位
  selectShipSpace: ShipSpace;


  planeForm: FormGroup;

  primaryCtrl = new FormControl(false);

  // 座位表数据源
  seatDataSource: MatTableDataSource<[][]> = new MatTableDataSource<any>();

  // 颜色集合
  colors = SeatConfig.allColors;

  id: number;

  // 座位表显示总行数
  totalSign = [];

  // 座位表显示的行数
  seatDisplayedColumns = ['index'];

  planeType = [
    {value: 0, label: '大机型'},
    {value: 1, label: '中机型 '},
    {value: 2, label: '小机型'}
  ];

  constructor(private activatedRoute: ActivatedRoute,
              private snackBar: MatSnackBar,
              private fb: FormBuilder,
              private router: Router,
              private planeService: PlaneService) {
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
    // 获取数据
    this.activatedRoute.paramMap.subscribe((params: ParamMap) => {
      this.id = +params.get('id');
      this.planeService.getById(this.id)
        .subscribe((plane) => {
          this.planeForm.patchValue(plane);
        });
    });
  }

  // 点击舱位按钮时
  openSnackBar(described: string) {
    let message;
    if (this.primaryCtrl.value) {
      message = '成功添加主舱位:' + described + '!';
    } else {
      message = '成功添加舱位:' + described + '！';
    }
    this.snackBar.open(message, '确认', {
      duration: 2000,
    });
    this.add(described, this.primaryCtrl.value);
  }

  // 删除舱位
  remove(shipSpace: ShipSpace) {
    const shipSpaces = this.planeForm.get('shipSpaces');
    shipSpaces.setValue(shipSpaces.value.filter((data) => {
      return shipSpace !== data;
    }));
  }

  // 添加舱位
  add(described: string, primary: boolean) {
    this.planeForm.get('shipSpaces').value.push(new ShipSpace(described, primary));
  }

  // 生成座位表
  createSeat() {
    // 清空舱位座位
    this.planeForm.get('shipSpaces').value.forEach((shipSpace: ShipSpace) => {
      shipSpace.seats = [];
    });
    // 生成表格
    const total = SeatConfig.totalCol;
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

  update() {
    // 将所有座位保存至舱位中
    let totalSeat = 0;
    this.seatDataSource.data.forEach((seatData: any) => {
      seatData.forEach((seatDisplay) => {
        if (seatDisplay.value.shipSpace) {
          if (!seatDisplay.value.shipSpace.seats) {
            seatDisplay.value.shipSpace.seats = [];
          }
          seatDisplay.value.shipSpace.seats.push(seatDisplay.value);
          seatDisplay.value.shipSpace = null;
          totalSeat++;
        }
      });
    });
    this.planeForm.value.totalSeat = totalSeat;
    this.planeService.update(this.id, this.planeForm.value)
      .subscribe(() => {
        this.router.navigateByUrl('/plane');
      });
  }

}
