import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-main-index',
  templateUrl: './main-index.component.html',
  styleUrls: ['./main-index.component.css']
})
export class MainIndexComponent implements OnInit {


  data: any;

  constructor() {
    this.data = {
      labels: ['城市', '机场', '航空', '飞机', '航班', '用户', '游客' ],
      datasets: [
        {
          label: '总数',
          backgroundColor: '#42A5F5',
          borderColor: '#1E88E5',
          data: [65, 59, 43, 80, 44, 34, 58]
        }
      ]
    };
  }

  ngOnInit(): void {
  }

}
