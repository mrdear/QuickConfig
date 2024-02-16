import {Injectable} from '@angular/core';
import {Subject} from "rxjs";
import {NzTreeNodeOptions} from "ng-zorro-antd/tree";

/**
 * 目录树点击行为
 */
@Injectable({
  providedIn: 'root'
})
export class TreeService {
  /**
   * 最后点击的节点
   */
  lastSubject = new Subject<NzTreeNodeOptions>();

  constructor() {
  }


  getActiveNode() {
    return this.lastSubject;
  }

  /**
   * 发送节点
   */
  nextActiveNode(node: NzTreeNodeOptions) {
    this.lastSubject.next(node)
  }

}
