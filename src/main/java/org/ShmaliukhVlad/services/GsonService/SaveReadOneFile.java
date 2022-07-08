package org.ShmaliukhVlad.services.GsonService;

import org.ShmaliukhVlad.bookshelf.Shelf;

public interface SaveReadOneFile {//TODO rename

    Shelf readShelfFromGsonFile();
    void saveShelfInGsonFile();


}
