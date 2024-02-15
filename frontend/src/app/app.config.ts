import {ApplicationConfig, importProvidersFrom} from '@angular/core';
import {provideRouter} from '@angular/router';
import {IconDefinition} from '@ant-design/icons-angular';
import {NzIconModule} from 'ng-zorro-antd/icon';
import {SearchOutline} from '@ant-design/icons-angular/icons';

import {routes} from './app.routes';
import {provideHttpClient} from "@angular/common/http";

const icons: IconDefinition[] = [SearchOutline];

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes), provideHttpClient(), importProvidersFrom(NzIconModule.forRoot(icons))]
};
