:springboot-project-readme# Spring Boot Project 시작하기! with Soup

## Soup 프로젝트 최초 설정 가이드

### Intellij
`File` > `Setting` > `Build, Execution, Deployment` > `Build Tools` > `Gradle` > `Gradle JVM`  
자바 버전을 프로젝트 Java 버전과 맞춰주세요!

### Yaml 파일 설정
`resources/application.yml` 파일을 통해 RDBMS와 연결하기

```yaml
datasource:
  url: { { datasource-url } } # jdbc:mysql://localhost:3306
  username: { { datasource-username } } # username
  password: { { datasource-password } } # password
  driver-class-name: com.mysql.cj.jdbc.Driver # RDBMS Driver
```

`yaml` 파일에 `database` 환경 변수 설정을 통해 프로젝트에서 DB로 연결해 봐요!
- DB 연결을 하게 되면 프로젝트에서 `JPA`를 이용하여 database table 까지 생성해 준다고?


## Soup 좀 더 활용하기!
`global/common` 폴더의 `code`, `request`, `resposne` 안에 있는 클래스 파일을 프로젝트에서 적절히 활용해 봐요!

---
### Reference Documentation

For further reference, please consider the following sections:

- [Official Gradle documentation](https://docs.gradle.org)
- [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.5/gradle-plugin/reference/html/)
- [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.5/gradle-plugin/reference/html/#build-image)

### Additional Links

These additional references should also help you:

- [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
