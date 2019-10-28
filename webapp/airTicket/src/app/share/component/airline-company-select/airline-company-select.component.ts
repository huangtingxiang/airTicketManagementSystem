import {Component, Input, OnInit} from '@angular/core';
import {map, startWith} from 'rxjs/operators';
import {FormControl} from '@angular/forms';
import {AirlineCompanyService} from '../../../core/service/airline-company.service';
import {Pageable} from '../../../entity/norm/pageable';
import {AirlineCompany} from '../../../entity/AirlineCompany';
import {Page} from '../../../entity/norm/page';

@Component({
  selector: 'app-airline-company-select',
  templateUrl: './airline-company-select.component.html',
  styleUrls: ['./airline-company-select.component.css']
})
export class AirlineCompanySelectComponent implements OnInit {

  @Input('formCtrl')
  searchCtrl: FormControl;

  airlineCompanies: AirlineCompany[] = [];

  constructor(private airlineCompanyService: AirlineCompanyService) {
  }

  ngOnInit() {
    // 监听输入框变化
    this.searchCtrl.valueChanges
      .pipe(
        startWith('')
        // map(value => this._filterGroup(value))
      ).subscribe((value) => {
      this.airlineCompanyService.pageByName(value, new Pageable(20, 0))
        .subscribe((airlineCompanyPage: Page<AirlineCompany>) => {
          this.airlineCompanies = airlineCompanyPage.content;
        });
    });
  }

  displayFn(airlineCompany: AirlineCompany) {
    return airlineCompany ? airlineCompany.name : '';
  }

}
