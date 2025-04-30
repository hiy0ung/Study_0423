package com.study.study3.library.config;

import com.study.study3.library.model.Book;
import com.study.study3.library.model.Loan;
import com.study.study3.library.model.Member;
import com.study.study3.library.repository.BookRepository;
import com.study.study3.library.repository.LoanRepository;
import com.study.study3.library.repository.MemberRepository;
import lombok.Builder;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.Arrays;

// 실행되고 AOP 를 제외한 가장 먼저 등록되는 bean
@Configuration
public class LibraryConfig {

    private static final Logger logger = LoggerFactory.getLogger(LibraryConfig.class);

    @Bean
    @Profile("dev")
    public String initDevData(
            BookRepository bookRepository,
            MemberRepository memberRepository,
            LoanRepository loanRepository) {
        // 샘플 회원 데이터 생성
        Member member1 = new Member("John Doe", "john@example.com", "1234567890");
        Member member2 = new Member("Jane Smith", "jane@example.com", "9876543210");

        memberRepository.save(member1);
        memberRepository.save(member2);

        // 샘플 도서 데이터 생성
        Book book1 = new Book(
                "Clean Code",
                "Robert C. Martin",
                "9780132350884",
                "A handbook of agile software craftsmanship",
                Arrays.asList("Programming", "Software Engineering"),
                2008
        );

        Book book2 = new Book(
                "Design Patterns",
                "Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides",
                "9780201633610",
                "Elements of Reusable Object-Oriented Software",
                Arrays.asList("Programming", "Software Design"),
                1994
        );

        Book book3 = new Book(
                "The Pragmatic Programmer",
                "Andrew Hunt, David Thomas",
                "9780201616224",
                "From Journeyman to Master",
                Arrays.asList("Programming", "Professional Development"),
                1999
        );

        Book book4 = new Book(
                "Effective Java",
                "Joshua Bloch",
                "9780134685991",
                "Best practices for the Java platform",
                Arrays.asList("Java", "Programming"),
                2018
        );

        Book book5 = new Book(
                "Domain-Driven Design",
                "Eric Evans",
                "9780321125217",
                "Tackling Complexity in the Heart of Software",
                Arrays.asList("Software Design", "Architecture"),
                2003
        );

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);
        bookRepository.save(book5);

        // 샘플 대출 데이터 생성
        Loan loan1 = new Loan(book1, member1);
        Loan loan2 = new Loan(book2, member2);

        // 과거 대출 생성 (이미 반납됨)
        Loan pastLoan = new Loan(book3, member1);
        pastLoan.setLoanDate(LocalDateTime.now().minusDays(30));
        pastLoan.setDueDate(LocalDateTime.now().minusDays(16));
        pastLoan.returnBook();

        // 연체된 대출 생성
        Loan overdueLoan = new Loan(book4, member2);
        overdueLoan.setLoanDate(LocalDateTime.now().minusDays(30));
        overdueLoan.setDueDate(LocalDateTime.now().minusDays(16));

        loanRepository.save(loan1);
        loanRepository.save(loan2);
        loanRepository.save(pastLoan);
        loanRepository.save(overdueLoan);

        // 대출 정보를 회원에 연결
        member1.addLoan(loan1);
        member1.addLoan(pastLoan);
        member2.addLoan(loan2);
        member2.addLoan(overdueLoan);

        memberRepository.save(member1);
        memberRepository.save(member2);

        logger.info("Sample data initialization completed");
        return "Sample data initialized successfully";
    }

    /**
     * 로깅 메시지 형식을 정의하는 빈
     *
     * @Bean 어노테이션을 통해 Spring 빈으로 등록됩니다.
     */
    @Bean
    public String logPattern() {
        return "[%-5level] %d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %c{1} - %msg%n";
    }

    /**
     * 도서관 설정 정보를 담은 빈
     */
    @Bean
    public LibraryProperties libraryProperties() {
        return LibraryProperties.builder()
                .name("Spring Boot Library")
                .maxLoanDays(14)
                .maxLoansPerMember(5)
                .allowRenewals(true)
                .maxRenewals(2)
                .build();
    }

    /**
     * 도서관 설정 정보를 담는 내부 클래스
     */
    @Getter
    @Builder
    public static class LibraryProperties {
        private String name;
        private int maxLoanDays;
        private int maxLoansPerMember;
        private boolean allowRenewals;
        private int maxRenewals;
    }
}
