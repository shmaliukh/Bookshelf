package com.vshmaliukh.spring_shelf_core.shelf.sqlite;

import com.vshmaliukh.spring_shelf_core.shelf.ItemEntityRepositoryActionsProvider;
import com.vshmaliukh.spring_shelf_core.shelf.sqlite.repositories.SqliteBookRepository;
import com.vshmaliukh.spring_shelf_core.shelf.sqlite.repositories.SqliteComicsRepository;
import com.vshmaliukh.spring_shelf_core.shelf.sqlite.repositories.SqliteMagazineRepository;
import com.vshmaliukh.spring_shelf_core.shelf.sqlite.repositories.SqliteNewspaperRepository;
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
