package gift.repository;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class WishJpaRepositoryTest {

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private WishJpaRepository wishJpaRepository;

    @Test
    @DisplayName("Wish를 저장할 수 있다")
    void save() {
        // given
        Member member = memberJpaRepository.save(Member.create("user@example.com", "Password123!"));
        Product product = productJpaRepository.save(Product.create("선물", 1000, "gift.jpg"));

        // when
        Wish wish = wishJpaRepository.save(Wish.create(member, product));

        // then
        assertThat(wish.getId()).isNotNull();
        assertThat(wish.getMember().getId()).isEqualTo(member.getId());
        assertThat(wish.getProduct().getId()).isEqualTo(product.getId());
    }

    @Test
    @DisplayName("중복 찜 여부를 확인할 수 있다")
    void existsByMemberAndProduct() {
        // given
        Member member = memberJpaRepository.save(Member.create("user@example.com", "Password123!"));
        Product product = productJpaRepository.save(Product.create("선물", 1000, "gift.jpg"));
        wishJpaRepository.save(Wish.create(member, product));

        // when
        boolean exists = wishJpaRepository.existsByMemberAndProduct(member, product);

        // then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("사용자의 모든 찜 항목을 ID 기준 내림차순으로 조회할 수 있다")
    void findAllByMemberOrderByIdDesc() {
        // given
        Member member = memberJpaRepository.save(Member.create("user@example.com", "Password123!"));
        Product product1 = productJpaRepository.save(Product.create("A", 1000, "a.jpg"));
        Product product2 = productJpaRepository.save(Product.create("B", 2000, "b.jpg"));

        wishJpaRepository.save(Wish.create(member, product1));
        wishJpaRepository.save(Wish.create(member, product2));

        // when
        List<Wish> wishes = wishJpaRepository.findAllByMemberOrderByIdDesc(member);

        // then
        assertThat(wishes).hasSize(2);
        assertThat(wishes.get(0).getProduct().getName()).isEqualTo("B");
        assertThat(wishes.get(1).getProduct().getName()).isEqualTo("A");
    }

    @Test
    @DisplayName("사용자의 찜 항목을 안전하게 삭제할 수 있다")
    void deleteByIdAndMemberId() {
        // given
        Member member = memberJpaRepository.save(Member.create("user@example.com", "Password123!"));
        Product product = productJpaRepository.save(Product.create("선물", 1000, "gift.jpg"));
        Wish wish = wishJpaRepository.save(Wish.create(member, product));

        // when
        wishJpaRepository.deleteByIdAndMemberId(wish.getId(), member.getId());

        // then
        boolean exists = wishJpaRepository.existsById(wish.getId());
        assertThat(exists).isFalse();
    }
}
