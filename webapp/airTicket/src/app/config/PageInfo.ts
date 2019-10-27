import {Subject} from 'rxjs';
import {MatPaginatorIntl} from '@angular/material';
import {Pageable} from '../entity/norm/pageable';

// 覆盖默认的分页label
export class PageMessage extends MatPaginatorIntl {
  itemsPerPageLabel = '分页条数';

  lastPageLabel = '最后一页';

  nextPageLabel = '下一页';

  previousPageLabel = '上一页';

  firstPageLabel = '首页';

  changes = new Subject<void>();
}

export const PAGEABLE = new Pageable(10, 0);
