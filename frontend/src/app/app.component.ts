import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {NzContentComponent, NzFooterComponent, NzLayoutComponent} from "ng-zorro-antd/layout";
import {NzIconDirective} from "ng-zorro-antd/icon";
import {FileSiderComponent} from "./file-sider/file-sider.component";
import {ConfigService} from "./service/config.service";
import {NzSpinComponent} from "ng-zorro-antd/spin";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NzFooterComponent, NzLayoutComponent, NzIconDirective, NzContentComponent, FileSiderComponent, NzSpinComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.less'
})
export class AppComponent {

  isLoading = true;

  constructor(protected configService: ConfigService) {
    this.configService.refreshConfig()
      .subscribe(resp => {
        if (Object.keys(resp).length > 0) {
          this.isLoading = false;
        }
      })
  }

}
