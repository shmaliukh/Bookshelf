package org.shmaliukhVlad.services.GsonService;

import org.shmaliukhVlad.bookshelf.Shelf;

public interface SaveReadOneFile {//TODO rename

    Shelf readShelfFromGsonFile();
    void saveShelfInGsonFile();


}
