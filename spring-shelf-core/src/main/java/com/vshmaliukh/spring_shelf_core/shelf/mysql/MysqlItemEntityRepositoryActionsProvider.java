package com.vshmaliukh.spring_shelf_core.shelf.mysql;

import com.vshmaliukh.spring_shelf_core.shelf.ItemEntityRepositoryActionsProvider;
import com.vshmaliukh.spring_shelf_core.shelf.mysql.repositories.MysqlBookRepository;
import com.vshmaliukh.spring_shelf_core.shelf.mysql.repositories.MysqlComicsRepository;
import com.vshmaliukh.spring_shelf_core.shelf.mysql.repositories.MysqlMagazineRepository;
import com.vshmaliukh.spring_shelf_core.shelf.mysql.repositories.MysqlNewspaperRepository;
import org.springframework.stereotype.Service;

@Service
public final class MysqlItemEntityRepositoryActionsProvider extends ItemEntityRepositoryActionsProvider {

    public MysqlItemEntityRepositoryActionsProvider(MysqlBookRepository mysqlBookRepository,
                                                    MysqlMagazineRepository mysqlMagazineRepository,
                                                    MysqlNewspaperRepository mysqlNewspaperRepository,
                                                    MysqlComicsRepository mysqlComicsRepository) {
        this.bookRepositoryActions = mysqlBookRepository;
        this.magazineRepositoryActions = mysqlMagazineRepository;
        this.newspaperRepositoryActions = mysqlNewspaperRepository;
        this.comicsRepositoryActions = mysqlComicsRepository;
    }

}
