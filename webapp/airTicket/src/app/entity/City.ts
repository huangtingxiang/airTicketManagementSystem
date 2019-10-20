// 城市实体

import {AirPort} from './AirPort';

export class City {
  id: number; // id

  name: string; // 名称

  pinyin: string; // 拼音

  primary: boolean; // 是否热门城市

  airPorts: Array<AirPort>; // 机场集合
}
