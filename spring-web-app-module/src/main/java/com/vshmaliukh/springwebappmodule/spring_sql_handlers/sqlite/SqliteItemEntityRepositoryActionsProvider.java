package com.vshmaliukh.springwebappmodule.spring_sql_handlers.sqlite;

import com.vshmaliukh.springwebappmodule.spring_sql_handlers.ItemEntityRepositoryActionsProvider;
import com.vshmaliukh.springwebappmodule.spring_sql_handlers.sqlite.repositories.SqliteBookRepository;
import com.vshmaliukh.springwebappmodule.spring_sql_handlers.sqlite.repositories.SqliteComicsRepository;
import com.vshmaliukh.springwebappmodule.spring_sql_handlers.sqlite.repositories.SqliteMagazineRepository;
import com.vshmaliukh.springwebappmodule.spring_sql_handlers.sqlite.repositories.SqliteNewspaperRepository;
import org.springframework.stereotype.Service;

@Service
public final class SqliteItemEntityRepositoryActionsProvider extends ItemEntityRepositoryActionsProvider {

    public SqliteItemEntityRepositoryActionsProvider(SqliteBookRepository sqliteBookRepository,
                                                     SqliteMagazineRepository sqliteMagazineRepository,
                                                     SqliteNewspaperRepository sqliteNewspaperRepository,
                                                     SqliteComicsRepository sqliteComicsRepository) {
        this.bookRepositoryActions = sqliteBookRepository;
        this.magazineRepositoryActions = sqliteMagazineRepository;
        this.newspaperRepositoryActions = sqliteNewspaperRepository;
        this.comicsRepositoryActions = sqliteComicsRepository;
    }

}
