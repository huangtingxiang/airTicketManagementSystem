// 订单实体

import {FlightManagement} from './FlightManagement';
import {Visitor} from './Visitor';

export class TicketOrder {

  id: number; // id

  orderStatus: number; // 订单状态

  createTime: Date; // 创建时间

  flightManagement: FlightManagement; // 对应航班

  visitor: Visitor; // 对应乘客
}
