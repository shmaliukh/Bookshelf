package org.ShmaliukhVlad.services.GsonService;

import org.ShmaliukhVlad.bookshelf.Shelf;

public interface SaveReadTwoFiles {

    Shelf readShelfFromTwoFiles();
    void saveShelfInTwoFiles();
}
