import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {
  NzContentComponent,
  NzFooterComponent,
  NzHeaderComponent,
  NzLayoutComponent,
  NzSiderComponent
} from "ng-zorro-antd/layout";
import {NzMenuDirective, NzMenuItemComponent} from "ng-zorro-antd/menu";
import {NzIconDirective} from "ng-zorro-antd/icon";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NzFooterComponent, NzLayoutComponent, NzSiderComponent, NzMenuDirective, NzMenuItemComponent, NzIconDirective, NzHeaderComponent, NzContentComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.less'
})
export class AppComponent {
  title = 'frontend';
}
