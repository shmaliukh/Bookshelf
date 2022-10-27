package org.vshmaliukh.services.save_read_services;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.services.save_read_services.sql_handler.UserContainer;

@Slf4j
@NoArgsConstructor
public abstract class AbstractSaveReadService implements SaveReadItems {

    @Setter
    @Getter
    protected String userName;
    @Getter
    @Setter
    protected UserContainer userContainer;

    protected AbstractSaveReadService(String userName){
        this.userName = userName;
    }

    public static void informAboutErr(String userName, String problemMessage, Exception exception) {
        log.error("[User]: name: '" + userName + "'"
                + " // [FilesHandler]: " + problemMessage
                + " // [Exception]: " + exception.getMessage());
    }

}
