# CUSTOMER API - EXAMPLE

# CONTENTS
1. [FB1-1 PROJECT INFO](#fb1-1-show-project-info)
2. [FB1-2-SERVER VERSIONING SYSTEM AND JAVA VERSION](#fb1-2-server-version-and-java-version)
3. [FB2-HEALTH API AND TEST CODE CONVENTION](#fb2-health-api-and-test-code-convention)


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

## [FB2] HEALTH API AND TEST CODE CONVENTION

### HEALTH API
* response: 200 OK
  * Health API 주요 기능: 서버 동작 확인
  * 그래서 200 OK 만으로도 충분함
* 그 외
  * 서버 버전: 서버에 배포된 버전 확인
  * 시간 정보: 서버 시간과 Timezone을 확인 할 수 있음
    * 국내 서비스: LocalDateTime (yyyy-MM-dd HH:mm:dd)
    * 글로벌 서비스: ZonedDateTime (yyyy-MM-dd HH:mm:ddZ)

`주의`: health API는 보통 인증 및 인가 과정을 거치지 않기 때문에 서버의 주요 정보가 노출되지 않게 해야 함

```text
{
    "message": "서버 상태 조회가 완료되었습니다.",
    "data": {
        "projectName": "CUSTOMER-API",
        "version": "v0.0.0",
        "description": "SERVER INFO."
    },
    "responseAt": "2023-10-03 05:27:20+0900"
}
```


### TEST CODE
**BDD: Behavior Driven Development**
* Given: 주어진 값 설정
* When: 테스트 수행 조건
* Then: 테스트 완료 후 조건 확인

```java
    @Test
    @DisplayName("Health API 호출 - 서버 상태 정상일 때 - Health Response 확인")
    public void getHealth_whenServerIsValid_receiveHealthResponse(){
        // given: 주어진 값 설정
        // when: 테스트 수행 조건
        // then: 테스트 완료 후 조건 확인 
    }
```