import {Component, OnInit} from '@angular/core';
import {MatDialog, MatTableDataSource, PageEvent} from '@angular/material';
import {User} from '../../../entity/User';
import {debounceTime, distinctUntilChanged, startWith} from 'rxjs/operators';
import {FormControl} from '@angular/forms';
import {Pageable} from '../../../entity/norm/pageable';
import {PAGEABLE} from '../../../config/PageInfo';
import {Page} from '../../../entity/norm/page';
import {Plane} from '../../../entity/Plane';
import {UserService} from '../../../core/service/user.service';
import {ErrorDialogComponent} from '../../../share/component/error-dialog/error-dialog.component';

@Component({
  selector: 'app-user-index',
  templateUrl: './user-index.component.html',
  styleUrls: ['./user-index.component.css']
})
export class UserIndexComponent implements OnInit {

  dataSource: MatTableDataSource<User> = new MatTableDataSource<User>([]);

  searchCtrl: FormControl = new FormControl('');

  totalElements: number;

  pageable: Pageable = new Pageable(PAGEABLE.size, PAGEABLE.page);

  displayedColumns: string[] = ['index', 'username', 'password', 'role', 'rebootOrDelete', 'operation'];

  constructor(private userService: UserService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    // 输入框变化时加载分页数据
    this.searchCtrl.valueChanges.pipe(
      startWith(''),
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
    this.userService.getAllByUserName(this.searchCtrl.value, this.pageable)
      .subscribe((pageData: Page<User>) => {
        this.dataSource.data = pageData.content;
        this.totalElements = pageData.totalElements;
      });
  }

  delete(user: User) {
    this.userService.delete(user.id)
      .subscribe(() => {
        user.status = false;
        const title = '成功';
        const content = '成功冻结' + user.userName + '用户';
        this.dialog.open(ErrorDialogComponent, {
          width: '500px',
          data: {title, content}
        });
      });
  }

  reboot(user: User) {
    this.userService.reboot(user.id)
      .subscribe(() => {
        user.status = true;
        const title = '成功';
        const content = '成功启用' + user.userName + '用户';
        this.dialog.open(ErrorDialogComponent, {
          width: '500px',
          data: {title, content}
        });
      });
  }

}
