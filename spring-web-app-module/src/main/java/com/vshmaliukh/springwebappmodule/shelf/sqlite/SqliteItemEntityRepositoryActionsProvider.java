package com.vshmaliukh.springwebappmodule.shelf.sqlite;

import com.vshmaliukh.springwebappmodule.shelf.ItemEntityRepositoryActionsProvider;
import com.vshmaliukh.springwebappmodule.shelf.sqlite.repositories.SqliteBookRepository;
import com.vshmaliukh.springwebappmodule.shelf.sqlite.repositories.SqliteComicsRepository;
import com.vshmaliukh.springwebappmodule.shelf.sqlite.repositories.SqliteMagazineRepository;
import com.vshmaliukh.springwebappmodule.shelf.sqlite.repositories.SqliteNewspaperRepository;
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
