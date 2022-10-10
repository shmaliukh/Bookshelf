package com.vshmaliukh.springwebappmodule.shelf;

import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.*;
import com.vshmaliukh.springwebappmodule.shelf.repository_services.ActionsWithItemEntity;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vshmaliukh.shelf.literature_items.book_item.Book;
import org.vshmaliukh.shelf.literature_items.comics_item.Comics;
import org.vshmaliukh.shelf.literature_items.magazine_item.Magazine;
import org.vshmaliukh.shelf.literature_items.newspaper_item.Newspaper;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@NoArgsConstructor
public final class ItemEntityRepositoryProvider {

    MysqlUserRepository mysqlUserRepository;
    MysqlBookRepository mysqlBookRepository;
    MysqlMagazineRepository mysqlMagazineRepository;
    MysqlNewspaperRepository mysqlNewspaperRepository;
    MysqlComicsRepository mysqlComicsRepository;

    private Map<Class, ActionsWithItemEntity> mysqlItemRepositoryMap = new ConcurrentHashMap<>();

    @PostConstruct
    private void initClassTypeRepositoryMap() {
        mysqlItemRepositoryMap.put(Book.class, mysqlBookRepository);
        mysqlItemRepositoryMap.put(Magazine.class, mysqlMagazineRepository);
        mysqlItemRepositoryMap.put(Comics.class, mysqlComicsRepository);
        mysqlItemRepositoryMap.put(Newspaper.class, mysqlNewspaperRepository);
    }


    public ActionsWithItemEntity getMysqlRepositoryByClassType(Class itemClassType) {
        return mysqlItemRepositoryMap.getOrDefault(itemClassType, null);
    }

    public List<ActionsWithItemEntity> getMysqlRepositoryList() {
        return new ArrayList<>(mysqlItemRepositoryMap.values());
    }

    @Autowired
    public void setMysqlUserRepository(MysqlUserRepository mysqlUserRepository) {
        this.mysqlUserRepository = mysqlUserRepository;
    }

    @Autowired
    public void setMysqlComicsRepository(MysqlComicsRepository mysqlComicsRepository) {
        this.mysqlComicsRepository = mysqlComicsRepository;
    }

    @Autowired
    public void setMysqlBookRepository(MysqlBookRepository mysqlBookRepository) {
        this.mysqlBookRepository = mysqlBookRepository;
    }

    @Autowired
    public void setMysqlMagazineRepository(MysqlMagazineRepository mysqlMagazineRepository) {
        this.mysqlMagazineRepository = mysqlMagazineRepository;
    }

    @Autowired
    public void setMysqlNewspaperRepository(MysqlNewspaperRepository mysqlNewspaperRepository) {
        this.mysqlNewspaperRepository = mysqlNewspaperRepository;
    }
}
