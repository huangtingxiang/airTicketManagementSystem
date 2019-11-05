// 用户实体
import {Visitor} from './Visitor';

export class User {

  id: number; // id

  userName: string; // 用户名

  passWord: string; // 密码

  role: number;

  visitor: Visitor; // 旅客信息

  constructor(userName: string, passWord: string) {
    this.userName = userName;
    this.passWord = passWord;
  }
}
