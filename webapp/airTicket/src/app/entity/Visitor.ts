// 乘客实体
import {TicketOrder} from './TicketOrder';

export class Visitor {

  id: number; // id

  name: string; // 名字

  idCard: string; // 身份证号码

  phoneNumber: string; // 手机号

  orders: Array<TicketOrder>; // 订单号
}
