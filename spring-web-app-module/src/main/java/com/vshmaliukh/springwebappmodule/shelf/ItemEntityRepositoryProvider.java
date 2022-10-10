package com.vshmaliukh.springwebappmodule.shelf;

import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.*;
import com.vshmaliukh.springwebappmodule.shelf.repository_services.ActionsWithItemEntity;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.vshmaliukh.shelf.literature_items.book_item.Book;
import org.vshmaliukh.shelf.literature_items.comics_item.Comics;
import org.vshmaliukh.shelf.literature_items.magazine_item.Magazine;
import org.vshmaliukh.shelf.literature_items.newspaper_item.Newspaper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@NoArgsConstructor
public final class ItemEntityRepositoryProvider {

    private static final Map<Class, ActionsWithItemEntity> mysqlItemRepositoryMap = new ConcurrentHashMap<>();

    MysqlUserRepository mysqlUserRepository;
    MysqlBookRepository mysqlBookRepository;
    MysqlMagazineRepository mysqlMagazineRepository;
    MysqlNewspaperRepository mysqlNewspaperRepository;
    MysqlComicsRepository mysqlComicsRepository;

    private ItemEntityRepositoryProvider(MysqlBookRepository mysqlBookRepository,
                                         MysqlUserRepository mysqlUserRepository,
                                         MysqlMagazineRepository mysqlMagazineRepository,
                                         MysqlNewspaperRepository mysqlNewspaperRepository,
                                         MysqlComicsRepository mysqlComicsRepository) {
        this.mysqlBookRepository = mysqlBookRepository;
        this.mysqlUserRepository = mysqlUserRepository;
        this.mysqlMagazineRepository = mysqlMagazineRepository;
        this.mysqlNewspaperRepository = mysqlNewspaperRepository;
        this.mysqlComicsRepository = mysqlComicsRepository;

        initClassTypeRepositoryMap(mysqlBookRepository, mysqlMagazineRepository, mysqlNewspaperRepository, mysqlComicsRepository);
    }

    private void initClassTypeRepositoryMap(MysqlBookRepository mysqlBookRepository, MysqlMagazineRepository mysqlMagazineRepository, MysqlNewspaperRepository mysqlNewspaperRepository, MysqlComicsRepository mysqlComicsRepository) {
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

}
