# 김영한님의 spring 스프링부트와 JPA 활용 1편, 2편 강의 정리
<br>
<hr>
<br>

## ✔️ jpashop directory
### 학습 범위 : 2-1-1 - 2-1-5
- 프로젝트 생성 및 환경 설정

- thymeleaf - jpa - h2 연동

- jpa 쿼리 로그 출력 > `p6spy`
<br>
<br>

### 학습 범위 : 2-2-1 - 2-3-2
- 엔티티 설계

- 엔티티 설계 시 주의점
  - 가급적 Setter 미사용
  - 모든 연관 관계는 지연 로딩(`LAZY`) 사용... 꼭!
  - `OneToOne`, `ManyToOne` 와 같이 `xToOne`인 경우 FetchType이 즉시로딩이므로 `LAZY`를 걸야줘야한다.
  - 컬렉션은 생성자에서 초기화 하지말고 필드에서 바로 초기화
<br>
<br>

### 학습 범위 : 2-4-1 - 2-4-3
- 회원 리포지토리 개발

- 회원 서비스 개발
<br>
<br>

### 학습 범위 : 2-5-1 - 2-5-3
- 상품 리포지토리 개발

- 상품 서비스 개발
<br>
<br>

### 학습 범위 : 2-6-1 - 2-6-5
- 주문, 주문상품 엔티티 개발

- 주문 리포지토리 개발

- 주문 서비스 개발

- 주문 기능 테스트

- 주문 검색 기능 개발 - JQPL 사용
<br>
<br>

### 학습 범위 : 2-7-1 - 2-7-10
- 홈 화면과 레이아웃

- 회원 등록 및 목록 조회

- 상품 등록, 목록, 수정(`em.merge`)

  준영속 엔티티 : 영속성 컨텍스트가 더는 관리하지 않는 엔티티 

- 준영속 엔티티를 수정하는 2가지 방법
  - 변경감지, 병합(merge)
 
<br>

- 변경 감지 : dirty checking

Ex
```java
@Transactional
void update(Item itemParam) { //itemParam: 파리미터로 넘어온 준영속 상태의 엔티티
 Item findItem = em.find(Item.class, itemParam.getId()); //같은 엔티티를 조회한다.
 findItem.setPrice(itemParam.getPrice()); //데이터를 수정한다.
}
```
> 조회된 findItem은 영속 상태의 데이터이고, 이를 set 하게 되면 변경감지가 일어나며<br>
트랜잭션 커밋에 의해 Flush가 되므로 최종적으로 db에 반영된다.<br>
별도의 em.persist를 호출하지 않아도 저장이 되는 것
<br>

- 병합 : `em.merge`
  - 변경 감지로 set으로 한땀한땀 설정하는 것을 한번에 해결
  - 변경 감지는 원하는 속성만 수정되지만, merge는 모든 속성이 변경된다. (위험!! : 병합 시 값이 없으면 `null` 세팅됨)
  - merge는 위험하기 때문에 웬만하면 변경감지를 사용한다.
<br>

- 주문, 주문 목록 조회(검색), 주문 취소
<br>
<br>

### 학습 범위 : 3-1-1 - 3-1-3
**➡️ 회원 등록**
- `RestController`

- DTO를 통한 데이터 transfer
  - 엔티티를 직접 사용하면 API 스펙 변경에 따른 위험성 증가
<br>

**➡️ 회원 수정**
- DTO를 통한 데이터 수정

- `@PathVariable`
<br>

**➡️ 회원 조회**
- stream api 및 dto를 통한 회원 조회

- `Result<T>` 제네릭 클래스로 결과값 리턴받기
<br>
<br>

### 학습 범위 : 3-2-1 - 3-3-4
- `Order`에 대한 간단한 api controller -> `x to one`에 대해서만 다룸

- API 최적화에 대한 강의 내역

- 샘플 데이터 생성
<br>

**➡️ 지연 로딩과 조회 성능 최적화**
- 원본 엔티티로 조회 시 연관 엔티티간 양방향 조회에 따른 무한 루프 발생
  - `@JSonIgnore`
  - `Hibernate5Module`
  - dto로 변환해서 반환
<br>

- 지연 로딩으로 인해 무한 루프가 발생한다고 해서 `EAGER` 설정을 통한 즉시로딩으로 바꾸면 안된다.

- 다른 곳에서 API 사용 시 필요없는 데이터까지 조회되어 성능 이슈가 발생하며, 불필요한 운영 데이터가 노출된다.

- LAZY 로딩에 따른 N + 1 문제

- fetch join을 이용한 최적화
<br>

**➡️ 쿼리 방식 선택 권장 순서**
1. 우선 엔티티를 DTO로 변환하는 방법 선택

2. 필요하면 fetch join으로 성능 최적화 -> 대부분의 성능 이슈 해결

3. 그래도 안되면 DTO로 직접 조회

4. 최후의 방법은 JPA가 제공하는 네이티브 SQL이나 스프링 JDBC Template을 사용해서 SQL 직접 사용
<br>
<br>

### 학습 범위 : 3-4-1 - 
- `Order`에 대한 상세 controller -> `x to many` 까지 다룸
<br>

**➡️ 컬렉션 조회 최적화**
- dto를 통한 조회

- fetch join + distinct
  - distinct가 db에서는 행이 완전 동일해야 제외하지만, jpa는 부모 엔티티가 같으면 제외해준다.
  - 일대다 + fetch join으로는 페이징 불가능 -> distinct로 제외시켜도 db에서는 행이 중복되기 때문
