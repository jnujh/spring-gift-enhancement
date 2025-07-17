# 미션 3. 상품 고도화

## 2단계 : 페이지네이션

---

### 1. 연관관계 리팩토링

Wish Entity 에서만 Product와 Member를 참조하던 기존 구조에서,  
Product와 Member Entity 에도 각각 List<Wish> 필드를 추가하여 양방향 연관관계를 구성했습니다.

- Member → Wish, Product → Wish 방향의 객체 탐색 가능
- Wish.create() 메서드 내부에서 양방향 관계를 명시적으로 연결

-> 객체 그래프 탐색이 원활하게 이루어질 수 있도록 리팩토링했습니다.

---

### 2. 페이지네이션 기능 적용

사용자의 위시 항목이 많아질 경우를 위시리스트 전체를 조회하는 건 적절하지 않을 수 있기 때문에 페이지네이션을 적용,  

Spring Data JPA 의 Pageable 기능을 활용하여 페이지네이션을 적용했습니다.

- Repository: Page<Wish> findByMemberId(Long memberId, Pageable pageable) 메서드 추가
- Service: 기존 List<Wish> 반환 방식을 Page<Wish>로 변경
- Controller: @PageableDefault 어노테이션을 이용해 GET 요청 시 페이지 정보 수신
- 응답은 Page<WishResponse> 형태로 전달되며, 페이징 정보가 함께 포함됨

-> 위시리스트 전체를 한 번에 조회하지 않고, 페이지 단위로 나눠 조회할 수 있도록 리팩토링했습니다.
