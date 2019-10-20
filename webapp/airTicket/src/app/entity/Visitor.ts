// 乘客实体
import {Order} from './Order';

export class Visitor {

  id: number; // id

  name: string; // 名字

  idCard: string; // 身份证号码

  phoneNumber: string; // 手机号

  orders: Array<Order>; // 订单号
}
