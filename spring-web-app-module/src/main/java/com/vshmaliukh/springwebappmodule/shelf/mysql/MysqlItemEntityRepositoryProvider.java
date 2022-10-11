package com.vshmaliukh.springwebappmodule.shelf.mysql;

import com.vshmaliukh.springwebappmodule.shelf.ItemEntityRepositoryProvider;
import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.MysqlBookRepository;
import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.MysqlComicsRepository;
import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.MysqlMagazineRepository;
import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.MysqlNewspaperRepository;
import org.springframework.stereotype.Service;

@Service
public final class MysqlItemEntityRepositoryProvider extends ItemEntityRepositoryProvider {

    public MysqlItemEntityRepositoryProvider(MysqlBookRepository mysqlBookRepository,
                                             MysqlMagazineRepository mysqlMagazineRepository,
                                             MysqlNewspaperRepository mysqlNewspaperRepository,
                                             MysqlComicsRepository mysqlComicsRepository) {
        this.bookRepository = mysqlBookRepository;
        this.magazineRepository = mysqlMagazineRepository;
        this.newspaperRepository = mysqlNewspaperRepository;
        this.comicsRepository = mysqlComicsRepository;
    }

}
