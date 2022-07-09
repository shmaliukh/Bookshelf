package org.shmaliukhVlad.services.GsonService;

import org.shmaliukhVlad.bookshelf.Shelf;

public interface SaveReadTwoFiles {

    Shelf readShelfFromTwoFiles();
    void saveShelfInTwoFiles();
}
