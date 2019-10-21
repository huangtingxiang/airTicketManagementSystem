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
  displayedColumns: string[] = ['select', 'id', 'name', 'pinyin', 'primary'];

  dataSource: MatTableDataSource<City>;

  selection: SelectionModel<City>;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(private cityService: CityService) {
  }

  ngOnInit() {
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

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSource.data.forEach(row => this.selection.select(row));
  }

  filterByName(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }


}
