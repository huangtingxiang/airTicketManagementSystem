import {Component, OnInit} from '@angular/core';
import {MatDialog, MatTableDataSource, PageEvent} from '@angular/material';
import {Pageable} from '../../../entity/norm/pageable';
import {PAGEABLE} from '../../../config/PageInfo';
import {AirPort} from '../../../entity/AirPort';
import {AirPortService} from '../../../core/service/air-port.service';
import {Page} from '../../../entity/norm/page';
import {FormControl} from '@angular/forms';
import {debounceTime, distinctUntilChanged} from 'rxjs/operators';
import {HttpErrorResponse} from '@angular/common/http';
import {ErrorDialogComponent} from '../../../share/component/error-dialog/error-dialog.component';

@Component({
  selector: 'app-air-port-index',
  templateUrl: './air-port-index.component.html',
  styleUrls: ['./air-port-index.component.css']
})
export class AirPortIndexComponent implements OnInit {

  // 显示的列
  displayedColumns: string[] = ['index', 'name', 'city', 'operation'];

  pageable: Pageable = new Pageable(PAGEABLE.size, PAGEABLE.page);

  dataSource: MatTableDataSource<AirPort> = new MatTableDataSource<AirPort>([]);

  totalElements: number;

  searchCtrl: FormControl = new FormControl('');

  constructor(private airPortService: AirPortService,
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

  // 重新加载分页数据
  reload() {
    this.airPortService.pageByName(this.searchCtrl.value, this.pageable)
      .subscribe((pageData: Page<AirPort>) => {
        this.dataSource.data = pageData.content;
        this.totalElements = pageData.totalElements;
      });
  }

  // 分页改变时
  pageChange(event: PageEvent) {
    this.pageable.page = event.pageIndex;
    this.pageable.size = event.pageSize;
    this.reload();
  }

  // 删除
  delete(id: number) {
    this.airPortService.delete(id)
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
        const dialogRef = this.dialog.open(ErrorDialogComponent, {
          width: '500px',
          data: {title, content}
        });
      });
  }
}
