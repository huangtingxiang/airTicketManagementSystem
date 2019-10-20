// 用户实体
import {Visitor} from './Visitor';

export class User {

  id: number; // id

  userName: string; // 用户名

  passWord: string; // 密码

  visitor: Visitor; // 旅客信息
}
