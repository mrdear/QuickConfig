import {ApplicationConfig, importProvidersFrom} from '@angular/core';
import {provideRouter} from '@angular/router';
import {IconDefinition} from '@ant-design/icons-angular';
import {NzIconModule} from 'ng-zorro-antd/icon';
import {
  FileExcelOutline,
  FileOutline,
  FileTextOutline,
  FolderOpenOutline,
  FolderOutline,
  ProfileOutline,
  SearchOutline
} from '@ant-design/icons-angular/icons';

import {routes} from './app.routes';
import {provideHttpClient} from "@angular/common/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

const icons: IconDefinition[] = [SearchOutline, FolderOutline, FolderOpenOutline, FileOutline, FileExcelOutline, ProfileOutline, FileTextOutline];

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes), provideHttpClient(), importProvidersFrom(NzIconModule.forRoot(icons), BrowserAnimationsModule)]
};
