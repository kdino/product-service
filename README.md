# product-service

### About

브랜드와 상품 정보를 관리하는 서비스 입니다. 아래의 REST endpoint들을 제공합니다.
| Method | Endpoint | Description |
|-------------|----------------------------------|----------------------------------|
| POST | /products | 상품을 추가합니다. |
| GET | /products/{id} | 상품 정보를 조회합니다.|
| PUT | /products/{id} | 상품 정보를 업데이트합니다.|
| DELETE | /products/{id} | 상품을 삭제합니다.|
| GET | /brands | 모든 브랜드 정보를 조회합니다.|
| POST | /brands | 브랜드를 추가합니다.|
| PUT | /brands/{id} | 브랜드 정보를 업데이트합니다.|
| DELETE | /brands/{id} | 브랜드를 삭제합니다.|
| GET | /products/cheapest/combination | 가장 저렴한 제품 조합 정보를 조회합니다.|
| GET | /products/cheapest/brand | 모든 카테고리의 가격 합이 가장 저렴한 브랜드의 제품 정보를 조회합니다.|
| GET | /products/category/{category} | 특정 카테고리의 최대/최저 가격 제품 정보를 조회합니다.|

[<img src="https://run.pstmn.io/button.svg" alt="Run In Postman" style="width: 128px; height: 32px;">](https://www.postman.com/kdino/ki-workspace/collection/0r7y04e/product-service?action=share&creator=2680338)

### Tech Stack

* kotlin
* ktor
* exposed
* H2
* arrow-effect
* kotest

### Structure

프로젝트의 구조는 layered architecture를 따릅니다.
1개의 메인 프로젝트 안에 4개의 서브 프로젝트가 존재하며 각 프로젝트의 목록과 역할은 아래와 같습니다.

* `boot` : Dependency injection(DI)를 담당하고 서버를 실행시킵니다. 서브 프로젝트로 분리된 각각의 레이어에 존재하는 클래스들은 모두 `boot` project에서 조립됩니다.
* `presentation` : REST 인터페이스를 구현합니다. Serializable한 객체를 받아 도메인 객체로 변환시켜 service들을 호출합니다.
* `domain` : 일반적인 layered architecture의 `application` 계층과 `domain`를 합쳐, 하나의 `domain` 프로젝트로 구성했습니다.
  Service는 사용자의 use case를 구현하는 함수가 모여있는 class입니다. Service는 repository 객체를 이용해 use case를 구현합니다.
  Domain service와 domain 객체는 비즈니스 로직을 담고있는 클래스입니다. Repository는 DB로의 CRUD를 담당하며 구현채는 `intrastructure`에 위치합니다.
* `infrastructure` : 기술적 의존도가 높은 구현체들이 위치하는 layer입니다. 주로 DB operation을 위한 `repository`의 구현체들이 존재합니다.

### 환경변수 (미설정 시, 기본값 사용)

```bash
# DB 커넥션 풀 사이즈 설정
EXPORT CONF_DATABASE_POOL_SIZE=<DB CONNECTION POOL SIZE>
# API를 제공할 포트 설정
EXPORT CONF_HOST_PORT=<API HOST PORT>
```

### Usage

```bash
# 서버실행
./gradlew run 
# 전체 테스트 코드 실행
./gradlew test
```

### Error Codes

| Error Code | Description       |
|------------|-------------------|
| 2001       | 매칭되는 카테고리가 없습니다.  |
| 2002       | 브랜드 이름이 이미 존재합니다. |
| 4001       | 브랜드를 찾을 수 없습니다.   |
| 4002       | 상품을 찾을 수 없습니다.    |
| 5000       | 서버 에러             |

### 추가 필요한 예외처리
* 상품을 삭제할 때, 카테고리의 마지막 상품인 경우 실패 처리
* 브랜드를 삭제할 때, 속해있는 상품들도 함께 삭제 처리
