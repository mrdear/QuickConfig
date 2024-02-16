import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, filter, map} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  configSubject = new BehaviorSubject<any>({});

  constructor(protected httpClient: HttpClient) {
  }

  getConfig() {
    return this.configSubject.pipe(filter(x => Object.keys(x).length > 0));
  }

  /**
   * 获取文件
   */
  refreshConfig() {
    this.httpClient.get("/api/config")
      .pipe(map(x => x as any))
      .subscribe(resp => {
        this.configSubject.next(resp);
      })
    return this.configSubject;
  }

}
