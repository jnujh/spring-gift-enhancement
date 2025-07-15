package gift.repository;

import gift.domain.Wish;
import gift.domain.Member;
import gift.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishJpaRepository extends JpaRepository<Wish, Long> {

    // 중복 찜 여부 확인
    boolean existsByMemberAndProduct(Member member, Product product);

    // 사용자 ID 기준으로 찜 목록 조회
    List<Wish> findAllByMemberOrderByIdDesc(Member member);

    // Wish ID + 사용자 ID 기준 삭제
    void deleteByIdAndMemberId(Long id, Long memberId);

    // 사용자 ID 소유의 찜 항목 조회
    Optional<Wish> findByIdAndMemberId(Long id, Long memberId);
}
