package gift.domain;

import gift.policy.EmailPolicy;
import gift.policy.PasswordPolicy;
import jakarta.persistence.*;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    protected Member() {
    }

    // 새로운 회원 생성 (정적 팩토리 메서드)
    public static Member create(String email, String password) {
        validateEmail(email);
        validatePassword(password);

        Member member = new Member();
        member.email = email;
        member.password = password;
        return member;
    }

    // DB 에서 조회된 데이터를 복원할 때 사용 (검증 생략)
    public static Member withId(Long id, String email, String password) {
        Member member = new Member();
        member.id = id;
        member.email = email;
        member.password = password;
        return member;
    }

    // 암호화된 비밀번호를 이용해 Member 생성
    public static Member withEncodedPassword(String email, String encodedPassword) {
        validateEmail(email);
        // 암호화된 비밀번호는 형식 검증 생략

        Member member = new Member();
        member.email = email;
        member.password = encodedPassword;
        return member;
    }

    // 이메일 유효성 검사
    private static void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("이메일은 필수입니다.");
        }
        if (!email.matches(EmailPolicy.EMAIL_REGEX)) {
            throw new IllegalArgumentException(EmailPolicy.EMAIL_RULE_MESSAGE);
        }
    }

    // 비밀번호 유효성 검사
    private static void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }
        if (!password.matches(PasswordPolicy.PASSWORD_REGEX)) {
            throw new IllegalArgumentException(PasswordPolicy.PASSWORD_RULE_MESSAGE);
        }
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}