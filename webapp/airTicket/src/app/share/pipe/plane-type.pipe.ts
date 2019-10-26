import {Pipe, PipeTransform} from '@angular/core';
import {PlaneType} from '../../entity/Plane';

@Pipe({
  name: 'planeType'
})
export class PlaneTypePipe implements PipeTransform {

  transform(planeType: PlaneType): any {
    let message = '';
    switch (planeType) {
      case PlaneType.LARGE:
        message = '大机型';
        break;
      case PlaneType.SMALL:
        message = '小机型';
        break;
      case PlaneType.MEDIUM:
        message = '中机型';
        break;
    }
    return message;
  }

}
