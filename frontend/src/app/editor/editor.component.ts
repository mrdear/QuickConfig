import {Component} from '@angular/core';
import {NzCodeEditorComponent} from "ng-zorro-antd/code-editor";
import {FormsModule} from "@angular/forms";
import {NzRowDirective} from "ng-zorro-antd/grid";
import {NzLayoutComponent} from "ng-zorro-antd/layout";
import {NzInputDirective, NzInputGroupComponent} from "ng-zorro-antd/input";
import {NzButtonComponent} from "ng-zorro-antd/button";
import {NzIconDirective} from "ng-zorro-antd/icon";
import {FileService} from "../service/file.service";
import {NzMessageService} from "ng-zorro-antd/message";

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
    NzIconDirective
  ],
  templateUrl: './editor.component.html',
  styleUrl: './editor.component.less'
})
export class EditorComponent {

  path: string = '';

  content: string = '';


  constructor(protected fileService: FileService,
              protected message: NzMessageService) {
  }

  getEditorConf(): any {
    return {
      language: 'typescript',
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
    if (!this.path || !this.path.startsWith("/")) {
      this.message.error("file path should start with /")
      return;
    }

    this.fileService.getFile(this.path)
      .subscribe(resp => {
        this.content = resp.content;
      });
  }
}
