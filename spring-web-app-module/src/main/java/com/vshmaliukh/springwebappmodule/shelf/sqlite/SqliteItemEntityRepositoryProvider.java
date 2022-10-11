package com.vshmaliukh.springwebappmodule.shelf.sqlite;

import com.vshmaliukh.springwebappmodule.shelf.ItemEntityRepositoryProvider;
import com.vshmaliukh.springwebappmodule.shelf.sqlite.repositories.SqliteBookRepository;
import com.vshmaliukh.springwebappmodule.shelf.sqlite.repositories.SqliteComicsRepository;
import com.vshmaliukh.springwebappmodule.shelf.sqlite.repositories.SqliteMagazineRepository;
import com.vshmaliukh.springwebappmodule.shelf.sqlite.repositories.SqliteNewspaperRepository;
import org.springframework.stereotype.Service;

@Service
public final class SqliteItemEntityRepositoryProvider extends ItemEntityRepositoryProvider {

    public SqliteItemEntityRepositoryProvider(SqliteBookRepository mysqlBookRepository,
                                              SqliteMagazineRepository mysqlMagazineRepository,
                                              SqliteNewspaperRepository mysqlNewspaperRepository,
                                              SqliteComicsRepository mysqlComicsRepository) {
        this.bookRepository = mysqlBookRepository;
        this.magazineRepository = mysqlMagazineRepository;
        this.newspaperRepository = mysqlNewspaperRepository;
        this.comicsRepository = mysqlComicsRepository;
    }

}
