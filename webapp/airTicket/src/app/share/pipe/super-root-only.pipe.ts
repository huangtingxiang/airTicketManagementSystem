import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'superRootOnly'
})
export class SuperRootOnlyPipe implements PipeTransform {

  transform(value: any): boolean {
    if (value && value.toString() === '0') {
      return true;
    } else {
      return false;
    }
  }

}
