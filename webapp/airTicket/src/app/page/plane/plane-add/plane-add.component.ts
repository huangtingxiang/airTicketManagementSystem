import {Component, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import {MatSnackBar} from '@angular/material';
import {ShipSpace} from '../../../entity/ShipSpace';

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

  constructor(private snackBar: MatSnackBar) {
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

  openSnackBar(described: string, price: number) {
    let message;
    if (this.primaryCtrl.value) {
      message = '成功添加主舱位:' + described + ',价格:' + price + '!';
    } else {
      message = '成功添加舱位:' + described + ',价格:' + price +      '！';
    }
    this.snackBar.open(message, '确认', {
      duration: 2000,
    });
    this.add(described, price, this.primaryCtrl.value);
  }

  remove(shipSpace: ShipSpace) {
    this.shipSpaces = this.shipSpaces.filter((data) => {
      return shipSpace !== data;
    });
  }

  add(described: string, price: number, primary: boolean) {
    this.shipSpaces.push(new ShipSpace(described, price, primary));
  }

}
