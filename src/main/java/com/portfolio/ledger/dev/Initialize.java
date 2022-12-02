package com.portfolio.ledger.dev;
//
//import com.portfolio.ledger.domain.Account;
//import com.portfolio.ledger.domain.Reply;
//import com.portfolio.ledger.dto.AccountDTO;
//import com.portfolio.ledger.repository.ReplyRepository;
//import com.portfolio.ledger.service.AccountService;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.util.stream.IntStream;
//
//// 해당 클래스는 테스트 용도로 사용 (초기에 인메모리 DB에 샘플 데이터 생성 용도)
//@Log4j2
//public class Initialize implements ApplicationRunner {
//    @Autowired
//    private AccountService accountService;
//
//    @Autowired
//    private ReplyRepository replyRepository;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        log.info(".......................INITIALIZE SAMPLE DATA.......................");
//
//        sampleAccountInsert();
//        sampleReplyInsert();
//    }
//
//    private void sampleAccountInsert() {
//        IntStream.rangeClosed(1, 100).forEach(i -> {
//            int     amount  = (int)(Math.random() * 100) + 1;
//            double  price   = (Math.random() * 10000) + 100; // 100 원 이상
//
//            AccountDTO accountDTO = AccountDTO.builder()
//                    .date(LocalDate.now().minusDays(100 - i))
//                    .title("Sample " + i)
//                    .content("Sample content " + i)
//                    .amount(amount)
//                    .price(price)
//                    .snp(Math.random() < 0.5)
//                    .writer("user" + i % 10)
//                    .build();
//
//            accountService.register(accountDTO);
//        });
//    }
//
//    public void sampleReplyInsert() {
//        log.info("............................REPLY INSERT............................");
//
//        IntStream.rangeClosed(1, 20).forEach(i -> {
//            Account account = Account.builder().ano(101 - (long) i).build();
//
//            int number = (int)(Math.random() * 4) + 1;
//
//            for (int j = 0; j < number; j++) {
//                Reply reply = Reply.builder()
//                        .account(account)
//                        .content("유저" + (i % 4) + "댓글...." + j + 1)
//                        .writer("User" + (i % 4))
//                        .build();
//
//                replyRepository.save(reply);
//            }
//        });
//    }
//}

import com.portfolio.ledger.domain.Member;
import com.portfolio.ledger.domain.MemberRole;
import com.portfolio.ledger.repository.MemberRepository;
import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.IntStream;

@Log4j2
@Component
public class Initialize implements ApplicationRunner {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createLoginTable();
        createUsers();
    }
    private void createLoginTable() throws Exception {
        log.info("............CREATE TABLE PERSISTENT FOR LOGINS............");

        @Cleanup Connection connection = dataSource.getConnection();

        Statement statement = connection.createStatement();
        String sql =    "CREATE TABLE persistent_logins (" +
                            "username VARCHAR(64) NOT NULL ," +
                            "series VARCHAR(64) PRIMARY KEY ," +
                            "token VARCHAR(64) NOT NULL ," +
                            "last_used TIMESTAMP NOT NULL" +
                        ")";

        statement.execute(sql);
    }

    private void createUsers() {
        log.info("..........................INSERT SAMPLE..........................");

        IntStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder()
                    .mid("user" + i)
                    .mpw(passwordEncoder.encode("0000"))
                    .email("user" + i + "@email" + i + ".com")
                    .build();

            member.addRole(MemberRole.USER);

            if(i >= 6) {
                member.addRole(MemberRole.ADMIN);
            }

            memberRepository.save(member);
        });
    }
}