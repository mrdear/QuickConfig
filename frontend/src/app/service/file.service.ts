import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FileService {

  constructor(protected httpClient: HttpClient) {
  }

  /**
   * 获取文件
   */
  getFile(path: string) {
    let form = new FormData();
    form.set("filePath", path);

    return this.httpClient.post("/api/file/get", form)
      .pipe(map(x => x as any));
  }

}
