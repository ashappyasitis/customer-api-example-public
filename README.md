# CUSTOMER API - EXAMPLE

# CONTENTS
1. [FB1-1 PROJECT INFO](#fb1-1-show-project-info)
2. [FB1-2-SERVER VERSIONING SYSTEM AND JAVA VERSION](#fb1-2-server-version-and-java-version)
3. [FB2-HEALTH API AND TEST CODE CONVENTION](#fb2-health-api-and-test-code-convention)
4. [FB3-Setting up your local MySQL DB and Mybatis](#fb3-setting-up-your-local-mysql-db-and-mybatis)
5. [FB4-Validation Settings](#fb4-validation-settings)
6. [FB5-CURD-API](#fb5-crud-apis)
7. [FB6-LOGGING](#fb6-logging)
8. [FB7-Global Exception Handler](#fb7-global-exception-handler)
9. [FB8-Add notification](#fb8-add-notification)


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

## [FB3] Setting up your local MySQL DB and Mybatis
### Create a local DB
> docker run --name mysql_3306 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=pw_mysql -e TZ=Asia/Seoul --restart=always -d mysql:latest

* 33306 port 에 MySQL을 Docker로 생성한다.
* ID: root
* PW: 33306
```shell
docker run --name mysql_33306 -p 33306:3306 -e MYSQL_ROOT_PASSWORD=33306 -e TZ=Asia/Seoul --restart=always -d mysql:latest
```
### DotEnv 사용
* `.env.example` 파일을 이용해서 `.env` 파일 생성
* Program 실행시에는 `.env` 파일을 사용함
* [주의사항] git 에는 `.env.example`만 올리고 다른 `.env` 파일은 올리지 않도록 주의 한다.

```dotenv
SERVER_PORT=15000
DB_NAME=mysql
DB_HOST=localhost
DB_PORT=33306
DB_USER=root
DB_PASSWORD=33306
DB_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
CP_NAME=cp-customer-local
```

### DB Schema 및 Table 생성
`sql/customer_setup.sql` 파일 참조

## [FB4] Validation Settings
Spring Boot의 Validation 라이브러리는 유효성을 검사할 때 매우 많이 사용되는 라이브러리이다.
이 라이브러리의 단점은 유효성 검사 결과가 랜덤(Random)하게 나온다는 것이다.

#### `GroupSequence` 를 이용하면 원하는 Group 순서로 Validation을 실시해서 Message를 받을 수 있다.
> * @NotNull/@NotBlank -> @Size -> @Min/@Max -> @Pattern

## [FB5] CRUD APIs
* tb_customer 테이블은 단순 예제를 위한 것 (`크게 의미 없음`) 
* APIs
  * GET (검색 및 페이징): ~/api/v1/customers
  * GET (고객사 상세 조회): ~/api/v1/customer
  * POST (고객사 생성): ~/api/v1/customer
  * PUT (고객사 정보 수정): ~/api/v1/customer
  * DELETE (고객사 삭제): ~/api/v1/customer

> 참고: 일반적으로는 아래와 같이 많이 쓰이지만 개인적으로 취향 으로 인해 위의 방식을 적용 함
>  * GET (검색 및 페이징): ~/api/v1/customers
>  * GET (고객사 상세 조회): ~/api/v1/customers/customerCode
>  * POST (고객사 생성): ~/api/v1/customers
>  * PUT (고객사 정보 수정): ~/api/v1/customers
>  * DELETE (고객사 삭제): ~/api/v1/customers

## [FB6] LOGGING
로그는 전략적인 관점에서 접근을 해야 한다.

* 목표1: 로그는 최소화 하면서 디버깅에는 문제 없게 만들기
  * 단, 결재 및 정산과 관련된 경우에는 로그를 상세하게 남기는 것이 전략이 될 수 있다.
* 목표2: 외부 시스템과 연동하는 경우 논쟁을 위해 Filter 단 Logging이 필요 함
  * 내부 시스템간 연동을 하는 경우에는 Filter 단 로깅이 필요 없을 수 있다.
* 목표3: 필요한 내용만 찍을 수 있게 하기
  * Filter단 에서는 Hacking tool 과 같은 의미 없는 Logging도 찍힐 수 있음
  * Interceptor단 에서는 Controller에서 제공된 내용을 url을 기반으로 로깅 할 수 있음
  * health api 처럼 주기적으로 자주 호출되는 API는 로깅 대상에서 제외하는 것이 좋음

> * Filter: AccessLogFilter
> * Interceptor: LogInterceptor

###  ContentCachingRequestWrapper, ContentCachingResponseWrapper
* HttpServletRequest의 Body는 getInputStream()을 통해서 한 번만 값을 읽어 올 수 있다. 그래서 ContentCachingRequestWrapper 와 같은 Wrapper를 사용한다.
  * 주의해야 할 점: Body의 값을 Memory에 Caching 해야 하기 때문에 벌크로 데이터를 주고 받을 때에는 메모리를 충분히 확보해야 함
* 현 예제에서는 Filter에서 ContentCachingRequestWrapper로 변경해서 내려주고 있기 때문에 Interceptor에서 문제 없이 사용가능 함
  * `HttpServletRequest` -> `new ContentCachingRequestWrapper`(At Filter) ->  `ContentCachingRequestWrapper`(Interceptor)

## [FB7] Global Exception Handler
**잘못된 에러 메시지**
에러 메시지에 불필요한 내용이 많으면 가독성이 떨어진다. 또한 해킹의 실마리가 될 수 있다.
그러므로 에러 발생 시 항상 가공되고 가독성 있는 메시지를 보낼 수 있도록 하는 것을 추천한다.

#### 잘못된 에러 메시지
```json
{
    "timestamp": "2023-09-30T02:03:42.172+00:00",
    "status": 500,
    "error": "Internal Server Error",
    "trace": "java.lang.NullPointerException: Cannot invoke \"me.kevin.customerapi.model.entity.Customer.getCustomerCode()\" because \"customer\" is null\r\n\tat me.kevin.customerapi.mapper.service.CustomerServiceMapper.toCustomerResponse(CustomerServiceMapper.java:101)\r\n\tat me.kevin.customerapi.mapper.service.CustomerServiceMapper.toReadCustomerResponse(CustomerServiceMapper.java:72)\r\n\tat me.kevin.customerapi.service.CustomerService.readCustomer(CustomerService.java:36)\r\n\tat java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n\tat java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)\r\n\tat java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n\tat java.base/java.lang.reflect.Method.invoke(Method.java:568)\r\n\tat org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:343)\r\n\tat org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:196)\r\n\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\r\n\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:751)\r\n\tat org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:123)\r\n\tat org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:391)\r\n\tat org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:119)\r\n\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184)\r\n\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:751)\r\n\tat org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:703)\r\n\tat me.kevin.customerapi.service.CustomerService$$SpringCGLIB$$0.readCustomer(<generated>)\r\n\tat me.kevin.customerapi.controller.CustomerApiController.readCustomer(CustomerApiController.java:31)\r\n\tat java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n\tat java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)\r\n\tat java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n\tat java.base/java.lang.reflect.Method.invoke(Method.java:568)\r\n\tat org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\r\n\tat org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:118)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:884)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:797)\r\n\tat org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\r\n\tat org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1081)\r\n\tat org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:974)\r\n\tat org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1011)\r\n\tat org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:903)\r\n\tat jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)\r\n\tat org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:885)\r\n\tat jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:205)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149)\r\n\tat org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:51)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149)\r\n\tat org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149)\r\n\tat org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149)\r\n\tat org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149)\r\n\tat org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:167)\r\n\tat org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90)\r\n\tat org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:482)\r\n\tat org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:115)\r\n\tat org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:93)\r\n\tat org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)\r\n\tat org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:341)\r\n\tat org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:391)\r\n\tat org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63)\r\n\tat org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:894)\r\n\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1740)\r\n\tat org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52)\r\n\tat org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1191)\r\n\tat org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659)\r\n\tat org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\r\n\tat java.base/java.lang.Thread.run(Thread.java:833)\r\n",
    "message": "Cannot invoke \"me.kevin.customerapi.model.entity.Customer.getCustomerCode()\" because \"customer\" is null",
    "path": "/api/v1/customer"
}
```

#### 의미있는 에러 메시지
```json
{
    "message": "잘못된 요청 파라미터가 존재합니다. 다시 확인하고 요청해 주세요.",
    "url": "/api/v1/customers",
    "errorAt": "2023-10-01 05:19:02",
    "errors": [
        {
            "id": "companyCode",
            "message": "필수 입력값입니다."
        }
    ]
}
```

## [FB8] Add notification
### 신 기능 추가 
* 고객사 생성, 수정 및 삭제 시 알림기능 추가

