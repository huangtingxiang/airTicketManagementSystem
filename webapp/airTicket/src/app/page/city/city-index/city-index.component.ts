import {Component, OnInit, ViewChild} from '@angular/core';
import {CityService} from '../../../core/service/city.service';
import {City} from '../../../entity/City';
import {MatPaginator, MatTableDataSource} from '@angular/material';
import {SelectionModel} from '@angular/cdk/collections';

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
  selection: SelectionModel<City>;

  // 分页dom
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(private cityService: CityService) {
  }

  ngOnInit() {
    // 获取所有的城市
    this.cityService.getAll()
      .subscribe((cities: City[]) => {
        this.dataSource = new MatTableDataSource<City>(cities);
        this.selection = new SelectionModel<City>(true, []);
        this.dataSource.paginator = this.paginator;
      });
    this.dataSource.filterPredicate = (data: City, filterValue: string) => {
      return data.name.toLowerCase().includes(filterValue);
    };
  }

  // 是否全部选中
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  // 主选击框点中
  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSource.data.forEach(row => this.selection.select(row));
  }

  // 依照城市名过滤
  filterByName(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }


}
