package com.vshmaliukh.springwebappmodule.shelf;

import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.MysqlBookRepository;
import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.MysqlComicsRepository;
import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.MysqlMagazineRepository;
import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.MysqlNewspaperRepository;
import com.vshmaliukh.springwebappmodule.shelf.repository_services.ActionsWithItemEntity;
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
public final class ItemEntityRepositoryProvider {

    final MysqlBookRepository mysqlBookRepository;
    final MysqlMagazineRepository mysqlMagazineRepository;
    final MysqlNewspaperRepository mysqlNewspaperRepository;
    final MysqlComicsRepository mysqlComicsRepository;

    private Map<Class, ActionsWithItemEntity> mysqlItemRepositoryMap = new ConcurrentHashMap<>();

    public ItemEntityRepositoryProvider(MysqlBookRepository mysqlBookRepository,
                                        MysqlMagazineRepository mysqlMagazineRepository,
                                        MysqlNewspaperRepository mysqlNewspaperRepository,
                                        MysqlComicsRepository mysqlComicsRepository) {
        this.mysqlBookRepository = mysqlBookRepository;
        this.mysqlMagazineRepository = mysqlMagazineRepository;
        this.mysqlNewspaperRepository = mysqlNewspaperRepository;
        this.mysqlComicsRepository = mysqlComicsRepository;
    }

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

}
