# 依赖问题排查指南

## 问题：Unresolved dependency: 'org.springframework.cloud:spring-cloud-starter:jar:2.0.0.RELEASE'

### 验证依赖是否真的有问题

Maven 命令行编译已经成功，说明依赖实际上是可用的。如果 IDE 显示错误，通常是 IDE 缓存问题。

### 解决方案

#### 1. IntelliJ IDEA

1. **刷新 Maven 项目**
   - 右键点击项目根目录
   - 选择 `Maven` -> `Reload Project`
   - 或者点击 Maven 工具窗口的刷新按钮

2. **重新导入 Maven 项目**
   - `File` -> `Invalidate Caches / Restart...`
   - 选择 `Invalidate and Restart`

3. **手动更新依赖**
   - 打开终端，运行：
   ```bash
   ./mvnw clean install -DskipTests
   ```
   - 然后在 IDEA 中：`File` -> `Sync Project with Gradle Files` (如果有) 或重新导入

#### 2. Eclipse

1. **更新 Maven 项目**
   - 右键点击项目
   - 选择 `Maven` -> `Update Project...`
   - 勾选 `Force Update of Snapshots/Releases`
   - 点击 `OK`

2. **清理并重新构建**
   - `Project` -> `Clean...`
   - 选择项目，点击 `Clean`

#### 3. VS Code / Cursor

1. **重新加载窗口**
   - `Cmd+Shift+P` (Mac) 或 `Ctrl+Shift+P` (Windows/Linux)
   - 输入 `Reload Window`
   - 选择 `Developer: Reload Window`

2. **手动运行 Maven 命令**
   ```bash
   ./mvnw dependency:resolve
   ```

### 验证依赖已正确解析

运行以下命令验证依赖：

```bash
./mvnw dependency:tree -pl my-debug-module | grep spring-cloud-starter
```

应该看到：
```
org.springframework.cloud:spring-cloud-starter:jar:2.0.0.RELEASE:compile
```

### 如果问题仍然存在

1. **检查 Maven 设置**
   - 确保 Maven 可以访问 Spring 仓库
   - 检查 `~/.m2/settings.xml` 中的仓库配置

2. **清理本地仓库缓存**
   ```bash
   rm -rf ~/.m2/repository/org/springframework/cloud/spring-cloud-starter
   ./mvnw clean install -U
   ```
   `-U` 参数强制更新依赖

3. **检查网络连接**
   - 确保可以访问 Maven 中央仓库或配置的镜像仓库

### 依赖版本管理

`spring-cloud-starter` 的版本由 `spring-cloud-commons-dependencies` 管理，版本为 `2.0.0.RELEASE`，这是通过父 POM 的 `dependencyManagement` 自动管理的，不需要显式指定版本。

