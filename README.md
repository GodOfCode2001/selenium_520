## Option 1:
### 下载Java
### 配置Java
### 下载Gradle
### 配置Gradle
### cd tests/mytest
### gradle test

## Option 2:
### Step 1:
Copy gradle project into **tests/** folder.

### Step 2:
``` 
docker compose up
docker exec -it [container name] bash
```
### Step 3:
```cd tests/gradle_project_name```

### Step 4:
```gradle test```
