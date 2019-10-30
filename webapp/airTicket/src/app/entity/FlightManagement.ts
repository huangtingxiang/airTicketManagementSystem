// 航班安排

import {City} from './City';
import {AirPort} from './AirPort';
import {Plane} from './Plane';
import {TicketOrder} from './TicketOrder';
import {TicketPrice} from './TicketPrice';

export class FlightManagement {

  id: number;

  startTime: Date; // 起始时间

  arrivalTime: Date; // 到达时间

  startingPlace: City; // 起始地

  destination: City; // 目的地

  startingAirPort: AirPort;  // 起始机场

  destinationAirPort: AirPort; // 到达机场

  plane: Plane; // 飞机

  orders: Array<TicketOrder>; // 订单集合

  ticketPrices: Array<TicketPrice>; // 订单价钱
}
