package com.vshmaliukh.springbootshelfcore.shelf.mysql;

import com.vshmaliukh.springbootshelfcore.shelf.mysql.repositories.MysqlBookRepository;
import com.vshmaliukh.springbootshelfcore.shelf.mysql.repositories.MysqlComicsRepository;
import com.vshmaliukh.springbootshelfcore.shelf.mysql.repositories.MysqlMagazineRepository;
import com.vshmaliukh.springbootshelfcore.shelf.mysql.repositories.MysqlNewspaperRepository;
import com.vshmaliukh.springbootshelfcore.shelf.ItemEntityRepositoryActionsProvider;
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
