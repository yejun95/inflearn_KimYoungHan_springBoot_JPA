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

### 학습 범위 : 3-1-1 - 
**➡️ 회원 등록**
- `RestController`

- DTO를 통한 데이터 transfer
  - 엔티티를 직접 사용하면 API 스펙 변경에 따른 위험성 증가
<br>
<br>

**➡️ 회원 수정**
- DTO를 통한 데이터 수정

- `@PathVariable`
