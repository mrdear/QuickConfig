# QuickConfig

QuickConfig is a Docker project based on native Java. Its purpose is to provide online editing capabilities and
configuration synchronization capabilities (TODO) for devices like NAS that are not convenient for editing text files.

After QuickConfig is launched, you can select files in a specified directory, and the system will automatically load the
content for editing and saving on a web page.

![QuickConfig](https://res.mrdear.cn/upic/mLsbwX.png)

The following image shows resource usage.

![Resource Usage](https://res.mrdear.cn/upic/ZTTtn5.png)

## Installation

1. Execute `docker push quding/quickconfig:lastest`.
2. The default web port is `8080`.
3. The default system configuration is `/work/config`.
4. `docker run -p 8080:8080 -v you-nas-conf:/you-fonc quding/quickconfig:lastest`
5. Access: http://your-nas-ip:8080

## Configuration Explanation

```properties
# Root directory displayed on the homepage, default is /
quick-config.display.root-path=/
# Whether to show hidden files, default is false
quick-config.display.hide-file.show=false
# Whether to show empty directories, default is false
quick-config.display.empty-dir.show=false
```

## Technology Stack

- Frontend: [Angular](https://github.com/angular/angular)
- Backend: [Quarkus](https://github.com/quarkusio/quarkus) 