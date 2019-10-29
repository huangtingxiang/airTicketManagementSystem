import {Component, OnInit} from '@angular/core';
import {MatDialog, MatTableDataSource, PageEvent} from '@angular/material';
import {Plane} from '../../../entity/Plane';
import {Pageable} from '../../../entity/norm/pageable';
import {PAGEABLE} from '../../../config/PageInfo';
import {PlaneService} from '../../../core/service/plane.service';
import {Page} from '../../../entity/norm/page';
import {AirPort} from '../../../entity/AirPort';
import {FormControl} from '@angular/forms';
import {debounceTime, distinctUntilChanged} from 'rxjs/operators';
import {ErrorDialogComponent} from '../../../share/component/error-dialog/error-dialog.component';
import {ShipSpace} from '../../../entity/ShipSpace';
import {PlaneSeatComponent} from '../../../share/component/plane-seat/plane-seat.component';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-plane-index',
  templateUrl: './plane-index.component.html',
  styleUrls: ['./plane-index.component.css']
})
export class PlaneIndexComponent implements OnInit {

  searchCtrl: FormControl = new FormControl('');

  dataSource: MatTableDataSource<Plane> = new MatTableDataSource<Plane>([]);

  totalElements: number;

  // 显示的列
  displayedColumns: string[] = ['index', 'name', 'planeType', 'airlineCompany', 'shipSpaces', 'seats', 'operation'];

  pageable: Pageable = new Pageable(PAGEABLE.size, PAGEABLE.page);

  constructor(private planeService: PlaneService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.reload();
    // 输入框变化时加载分页数据
    this.searchCtrl.valueChanges.pipe(
      debounceTime(300),
      distinctUntilChanged(),
    ).subscribe((data) => {
      this.reload();
    });
  }

  // 当分页改变时
  pageChange(event: PageEvent) {
    this.pageable.page = event.pageIndex;
    this.pageable.size = event.pageSize;
    this.reload();
  }

  // 重新加载数据
  reload() {
    this.planeService.pageByName(this.searchCtrl.value, this.pageable)
      .subscribe((pageData: Page<Plane>) => {
        this.dataSource.data = pageData.content;
        this.totalElements = pageData.totalElements;
      });
  }

  // 显示座位组件
  showSeat(plane: Plane) {
    const dialogRef = this.dialog.open(PlaneSeatComponent, {
      width: '1000px',
      height: '500px',
      panelClass: 'plane-seat',
      data: {shipSpaces: plane.shipSpaces, totalRow: plane.totalRow, totalCol: plane.totalCol}
    });
  }

  // 删除
  delete(id: number) {
    this.planeService.delete(id)
      .subscribe(() => {
        this.dataSource.data = this.dataSource.data.filter((city => city.id !== id));
      }, (response: HttpErrorResponse) => {
        const title = '删除失败';
        let content;
        if (response.status === 409) {
          content = '存在约束关系，不可删除';
        } else {
          content = '服务器错误';
        }
        this.dialog.open(ErrorDialogComponent, {
          width: '500px',
          data: {title, content}
        });
      });
  }

}
