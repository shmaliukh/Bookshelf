package org.vshmaliukh.services.GsonService;

import org.vshmaliukh.bookshelf.Shelf;

public interface SaveReadTwoFiles {

    Shelf readShelfFromTwoFiles();
    void saveShelfInTwoFiles();
}
