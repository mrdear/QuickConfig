import {Component, OnInit} from '@angular/core';
import {NzIconDirective} from "ng-zorro-antd/icon";
import {NzSiderComponent} from "ng-zorro-antd/layout";
import {NzFormatEmitEvent, NzTreeComponent, NzTreeNode, NzTreeNodeOptions} from "ng-zorro-antd/tree";
import {FileService} from "../service/file.service";
import {ConfigService} from "../service/config.service";
import {TreeService} from "../service/tree.service";

@Component({
  selector: 'app-file-sider',
  standalone: true,
  imports: [
    NzTreeComponent,
    NzSiderComponent,
    NzIconDirective
  ],
  templateUrl: './file-sider.component.html',
  styleUrl: './file-sider.component.less'
})
export class FileSiderComponent implements OnInit {

  nodes: NzTreeNodeOptions[] = []
  // node map集合
  nodeMap: Map<string, NzTreeNodeOptions> = new Map<string, NzTreeNodeOptions>();

  constructor(protected fileService: FileService,
              protected treeService: TreeService,
              protected configService: ConfigService) {

  }

  ngOnInit(): void {
    this.configService.getConfig()
      .subscribe(config => {
        let path = config['quick-config.display.root-path'] || '/';
        console.log("start load path", path)

        this.fileService.walkFile(path)
          .subscribe(resp => {
            this.nodes = resp
              .filter(x => x.path !== path)
              .map(x => {
                let node = this.mapNode(x);
                this.nodeMap.set(node.key, node);
                return node;
              })
          })
      })
  }


  clickNode(event: NzFormatEmitEvent) {
    let node: NzTreeNode = event.node as NzTreeNode;

    let origin = node.origin;
    this.treeService.nextActiveNode(origin);
  }

  expandNode(event: NzFormatEmitEvent) {
    let node: NzTreeNode = event.node as NzTreeNode;

    if (event.eventName === 'expand' && node.isExpanded) {
      this.fileService.walkFile(node.key)
        .subscribe(resp => {
          let children = resp.map(x => this.mapNode(x))
            .filter(x => x.key !== node.key);

          node.clearChildren()
          node.addChildren(children);
        })
    }
  }

  private mapNode(x: any) {
    let node: NzTreeNodeOptions = {
      title: x.name,
      key: x.path,
      isLeaf: !x.hasChildren,
      expanded: false,
      children: [],
      icon: x.isDir ? '' : 'file',
      isText: x.isText,
      info: x.info,
      isDir: x.isDir,
    }
    return node;
  }

}
