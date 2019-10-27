import {Component, OnInit, ViewChild} from '@angular/core';
import {CityService} from '../../../core/service/city.service';
import {City} from '../../../entity/City';
import {MatPaginator, MatTableDataSource} from '@angular/material';
import {SelectionModel} from '@angular/cdk/collections';
import {PAGEABLE} from '../../../config/PageInfo';

@Component({
  selector: 'app-city-index',
  templateUrl: './city-index.component.html',
  styleUrls: ['./city-index.component.css']
})
export class CityIndexComponent implements OnInit {

  // 显示的列
  displayedColumns: string[] = ['select', 'id', 'name', 'pinyin', 'primary', 'operation'];

  // 数据源
  dataSource: MatTableDataSource<City>;

  // 选中列
  selection: SelectionModel<City> = new SelectionModel<City>(true, []);

  // 分页dom
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  pageable = {
    page: PAGEABLE.page,
    size: PAGEABLE.size
  };

  constructor(private cityService: CityService) {
  }

  ngOnInit() {
    // 获取所有的城市
    this.cityService.getAll()
      .subscribe((cities: City[]) => {
        this.dataSource = new MatTableDataSource<City>(cities);
        this.dataSource.paginator = this.paginator;
        this.dataSource.filterPredicate = (data: City, filterValue: string) => {
          return data.name.toLowerCase().includes(filterValue.toLowerCase())
            || data.pinyin.toLowerCase().includes(filterValue.toLowerCase());
        };
      });
  }

  // 是否全部选中
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource._pageData(this.dataSource.data).length;
    return numSelected === numRows;
  }

  // 主选击框点中
  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSource._pageData(this.dataSource.data).forEach(row => this.selection.select(row));
  }

  // 依照城市名过滤
  filterByName(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  // 设为热门城市
  setToPrimaried(city: City) {
    this.cityService.setToPrimaried(city.id)
      .subscribe(() => {
        city.primaried = true;
      });
  }

  // 不设为热门城市
  setNotToPrimaried(city: City) {
    this.cityService.setToNotPrimaried(city.id)
      .subscribe(() => {
        city.primaried = false;
      });
  }

  // 全设为热门城市
  setAllToPrimaried() {
    this.cityService.setAllToPrimaried(this.selection.selected.map(city => city.id))
      .subscribe(() => {
        this.selection.selected.forEach((city => city.primaried = true));
      });
  }

  // 取消设为热门城市
  setAllToNotPrimaried() {
    this.cityService.setAllToNotPrimaried(this.selection.selected.map(city => city.id))
      .subscribe(() => {
        this.selection.selected.forEach((city => city.primaried = false));
      });
  }

  delete(id: number) {
    this.cityService.delete(id)
      .subscribe(() => {
        this.dataSource.data = this.dataSource.data.filter((city => city.id !== id));
      });
  }
}
