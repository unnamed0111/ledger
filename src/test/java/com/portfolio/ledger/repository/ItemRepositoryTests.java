package com.portfolio.ledger.repository;

import com.portfolio.ledger.domain.Box;
import com.portfolio.ledger.domain.Item;
import com.portfolio.ledger.domain.Person;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ItemRepositoryTests {
    @Autowired private ItemRepository   itemRepository;
    @Autowired private BoxRepository boxRepository;
    @Autowired private PersonRepository personRepository;

    @BeforeEach
    public void preRendering() {
        log.info("=================Item Repository=================");
    }

    @Test
    public void testInsertItem() {
        log.info("--------------------INSERT ITEM--------------------");

        Box box = Box.builder().bno(1).build();
        Person person = personRepository.getReferenceById("user1"); // 제일 이상적인 형태
//        Person person = Person.builder()
//                .username("user1")
//                .age(18)
//                .build();

        Item item = Item.builder()
                .weight(22.22)
                .box(box)
                .person(person)
                .build();

        itemRepository.save(item);
    }

    @Test
    public void testInsertBox() {
        log.info("--------------------INSERT PERSON--------------------");

        Box box = Box.builder().bno(1).build();

        Box result = boxRepository.save(box);
        log.info("BOX ID : " + result.getBno());
    }

    @Test
    public void testInsertPerson() {
        log.info("--------------------INSERT PERSON--------------------");
        Person person = Person.builder()
                .username("user1") // 아마 식별자(ID)값이 존재하기 때문에 DB에 있는걸로 판단해서 select가 실행될것임
                .age(27)
                .build();

        Person result = personRepository.save(person);
        log.info("PERSON ID : " + result.getUsername());
    }

    @Test
    public void testAll() {
        testInsertPerson();
        testInsertBox();
        testInsertItem();
    }
}
