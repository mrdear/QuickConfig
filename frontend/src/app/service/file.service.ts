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

  saveFile(filePath: string, fileContent: string, prevMd5: string) {
    let payload = {
      filePath: filePath,
      fileContent: fileContent,
      prevMd5: prevMd5,
    }

    return this.httpClient.post("/api/file/save", payload)
      .pipe(map(x => x as any));
  }

  /**
   * 遍历一层文件
   */
  walkFile(path: string) {
    let form = new FormData();
    form.set("filePath", path);

    return this.httpClient.post("/api/file/walk", form)
      .pipe(map(x => x as any[]));
  }

}
