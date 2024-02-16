import {Component, OnInit} from '@angular/core';
import {NzCodeEditorComponent} from "ng-zorro-antd/code-editor";
import {FormsModule} from "@angular/forms";
import {NzRowDirective} from "ng-zorro-antd/grid";
import {NzLayoutComponent} from "ng-zorro-antd/layout";
import {NzInputDirective, NzInputGroupComponent} from "ng-zorro-antd/input";
import {NzButtonComponent} from "ng-zorro-antd/button";
import {NzIconDirective} from "ng-zorro-antd/icon";
import {FileService} from "../service/file.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {TreeService} from "../service/tree.service";
import {NzDescriptionsComponent, NzDescriptionsItemComponent} from "ng-zorro-antd/descriptions";
import {NgIf} from "@angular/common";
import {NzSpinComponent} from "ng-zorro-antd/spin";
import {NzDividerComponent} from "ng-zorro-antd/divider";

@Component({
  selector: 'app-editor',
  standalone: true,
  imports: [
    NzCodeEditorComponent,
    FormsModule,
    NzRowDirective,
    NzLayoutComponent,
    NzInputGroupComponent,
    NzInputDirective,
    NzButtonComponent,
    NzIconDirective,
    NzDescriptionsComponent,
    NzDescriptionsItemComponent,
    NgIf,
    NzSpinComponent,
    NzDividerComponent
  ],
  templateUrl: './editor.component.html',
  styleUrl: './editor.component.less'
})
export class EditorComponent implements OnInit {

  node: any = null;

  isLoading = false;

  content: string = '';

  md5: string = '';

  info: string = '';

  private fileTypeToMonacoLanguageMap: Map<string, string> = new Map([
    ['js', 'javascript'],
    ['ts', 'typescript'],
    ['jsx', 'typescript'],
    ['groovy', 'java'],
    ['md', 'markdown'],
    ['py', 'python'],
    ['rb', 'ruby'],
    ['sh', 'shell'],
    ['kt', 'kotlin'],
  ]);

  constructor(protected fileService: FileService,
              protected treeService: TreeService,
              protected message: NzMessageService) {
  }

  ngOnInit(): void {
    this.treeService.getActiveNode().subscribe(node => {
      this.node = null;
      this.node = node;
      this.getFile()
    })
  }


  getEditorConf(node: any): any {
    let suffix: string = 'ini';

    let title = node.title;
    let lastIndexOf = title.lastIndexOf('.');
    if (lastIndexOf > 0) {
      suffix = title.substring(lastIndexOf + 1).toLowerCase();
      if (this.fileTypeToMonacoLanguageMap.has(suffix)) {
        suffix = this.fileTypeToMonacoLanguageMap.get(suffix);
      }
    }

    return {
      language: suffix,
      autoIndent: 'None', // 控制编辑器在用户键入、粘贴、移动或缩进行时是否应自动调整缩进
      cursorBlinking: 'Solid', // 光标动画样式
      minimap: {
        enabled: false // 是否启用预览图
      },
      scrollBeyondLastLine: false, // 设置编辑器是否可以滚动到最后一行之后
      theme: 'vs-dark',
      renderLineHighlight: 'all',
    };
  }

  getFile() {
    this.content = '';
    this.md5 = '';

    if (!this.node.isText) {
      return;
    }

    this.isLoading = true;
    this.fileService.getFile(this.node!.key)
      .subscribe(resp => {
        this.content = resp.content;
        this.md5 = resp.md5;
        this.info = resp.info;
      }, () => {
      }, () => this.isLoading = false);
  }


  saveFile() {
    this.isLoading = true;
    this.fileService.saveFile(this.node.key, this.content, this.md5)
      .subscribe(resp => {
        this.message.success("success")

        this.content = resp.content;
        this.md5 = resp.md5;
        this.info = resp.info;
      }, () => {
      }, () => this.isLoading = false)
  }

}
