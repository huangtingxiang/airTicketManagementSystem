import {Component, Input, OnInit} from '@angular/core';
import {CityService} from '../../../core/service/city.service';
import {City} from '../../../entity/City';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import {FormControl} from '@angular/forms';

@Component({
  selector: 'app-city-select',
  templateUrl: './city-select.component.html',
  styleUrls: ['./city-select.component.css']
})
export class CitySelectComponent implements OnInit {

  data = {
    A: [],
    B: [],
    C: [],
    D: [],
    E: [],
    F: [],
    G: [],
    H: [],
    I: [],
    J: [],
    K: [],
    L: [],
    M: [],
    N: [],
    O: [],
    P: [],
    Q: [],
    R: [],
    S: [],
    T: [],
    U: [],
    V: [],
    W: [],
    X: [],
    Y: [],
    Z: []
  };

  // 表单控件 用于监听输入框变化
  @Input()
  cityCtr: FormControl;

  // 城市数组数据
  cityGroup: CityGroup[] = [];

  // 可观察数据 表示根据输入框的变化更改城市列表
  cityGroupOptions: Observable<CityGroup[]>;

  constructor(private cityService: CityService) {
  }

  ngOnInit() {
    //  获取所有城市并设置相应的数据结构
    this.cityService.getAll()
      .subscribe((cities: City[]) => {
        cities.forEach((city: City) => {
          this.data[city.pinyin.charAt(0).toUpperCase()].push(city);
        });
        Object.keys(this.data).forEach((key) => {
          this.cityGroup.push({label: key, value: this.data[key]});
        });
      });
    // 监听输入框变化
    this.cityGroupOptions = this.cityCtr.valueChanges
      .pipe(
        startWith(''),
        map(value => this._filterGroup(value))
      );
  }

  displayFn(city: City) {
    return city ? city.name : '';
  }

  private _filterGroup(value: string): CityGroup[] {
    if (value) {
      return this.cityGroup
        .map(group => ({label: group.label, value: this.filter(group.value, value)}))
        .filter(group => group.value.length > 0);
    }

    return this.cityGroup;
  }

  private filter(cities: City[], value: string) {
    return cities.filter(city => city.pinyin.toLowerCase().includes(value) || city.name.toLowerCase().includes(value));
  }

}

interface CityGroup {
  label: string;
  value: City[];
}
