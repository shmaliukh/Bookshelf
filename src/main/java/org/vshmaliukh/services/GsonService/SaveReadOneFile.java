package org.vshmaliukh.services.GsonService;

import org.vshmaliukh.bookshelf.Shelf;

public interface SaveReadOneFile {//TODO rename

    Shelf readShelfFromGsonFile();
    void saveShelfInGsonFile();


}
