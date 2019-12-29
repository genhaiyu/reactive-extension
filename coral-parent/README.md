# 统一 pom 管理

## coral-parent
`coral-parent`，统一并简化依赖，避免重复 jar .

### 1. 统一 parent 配置
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.yugh.coral</groupId>
            <artifactId>parent</artifactId>
            <version>${project.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### 2. 组件坐标
`groupId`：
```text
org.yugh.coral
```

`artifactId`：
```text
coral-parent
coral-core
coral-boot

```

### 3. snapshots/releases

```xml
<distributionManagement>
    <repository>
        <id>releases</id>
        <name>Nexus Repository</name>
        <url>xx/nexus/content/repositories/releases/</url>
    </repository>
    <snapshotRepository>
        <id>snapshots</id>
        <name>Nexus snapshots Repository</name>
        <url>xx/nexus/content/repositories/snapshots/</url>
    </snapshotRepository>
</distributionManagement>
```