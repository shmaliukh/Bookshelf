package com.vshmaliukh.springwebappmodule.shelf.mysql;

import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.MysqlBookRepository;
import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.MysqlComicsRepository;
import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.MysqlMagazineRepository;
import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.MysqlNewspaperRepository;
import com.vshmaliukh.springwebappmodule.shelf.ItemEntityRepositoryActionsProvider;
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
