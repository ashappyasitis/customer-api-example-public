# CUSTOMER API - EXAMPLE

# CONTENTS
1. [FB1 PROJECT INFO](#fb1-1-show-project-info)
2. [FB2-SERVER VERSIONING SYSTEM AND JAVA VERSION](#fb1-2-server-version-and-java-version)


cf) FB - feature branch
## [FB1-1] SHOW PROJECT INFO
* 리모트(REMOTE) 환경에서 서버가 부팅되면 인프라(INFRA.) 제약 때문에 확인이 쉽지 않은 경우가 있다.
  * 서버 부팅시 필요로 하는 정보를 미리 볼 수 있게 구성하는 것을 추천한다.

```text
>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
PROJECT_NAME: CUSTOMER-API
VERSION: v0.0.0
SEQUENCE: FB1-1
DESCRIPTION: SHOW PROJECT INFO
ACTIVE_PROFILE: local
SERVER_PORT: 15000
TIMEZONE: Asia/Seoul
>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
```
## [FB1-2] SERVER VERSION AND JAVA VERSION

### PROJECT INFO.
* JAVA17: Amazon Corretto 17

### Release Version
**VERSION**  `v1.2.456` with `TAG: v1.2.456`

`v1`: Major Version
`2`: milestone Version
`v1`: Minor Version

### Branch Version System
**SEQUENCE** `FB1-0`

BranchName: FB1-PROJECT-INFO (FB: FEATURE BRANCH)
example) FB1-0 ===> FB1-2 ===> FB1-3