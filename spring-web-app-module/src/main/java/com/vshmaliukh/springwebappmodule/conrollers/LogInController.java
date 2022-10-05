package com.vshmaliukh.springwebappmodule.conrollers;

import com.vshmaliukh.springwebappmodule.UserDataModelForJson;
import com.vshmaliukh.springwebappmodule.shelf.entities.BookEntity;
import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.MysqlBookRepository;
import com.vshmaliukh.springwebappmodule.shelf.mysql.services.MysqlBookService;
import com.vshmaliukh.springwebappmodule.shelf.sqlite.repositories.SqliteBookRepository;
import com.vshmaliukh.springwebappmodule.utils.ControllerUtils;
import com.vshmaliukh.springwebappmodule.utils.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.vshmaliukh.Constants.*;

@Controller
public class LogInController {

    private final MysqlBookRepository mysqlBookRepository;
    private final SqliteBookRepository sqliteBookRepository;

    private final MysqlBookService mysqlBookService;

    public LogInController(MysqlBookRepository mysqlBookRepository, SqliteBookRepository sqliteBookRepository, MysqlBookService mysqlBookService) {
        this.mysqlBookRepository = mysqlBookRepository;
        this.sqliteBookRepository = sqliteBookRepository;
        this.mysqlBookService = mysqlBookService;
    }

    @GetMapping(value = {"/", "/" + LOG_IN_TITLE})
    ModelAndView doGet(@CookieValue(defaultValue = "") String userName,
                       @CookieValue(defaultValue = "") String typeOfWork,
                       ModelMap modelMap) {

        justTestMysqlAndSqliteWork();

        modelMap.addAttribute(USER_NAME, userName);
        ControllerUtils.formRadioButtonsToChooseTypeOfWork(typeOfWork, modelMap);
        return new ModelAndView(LOG_IN_TITLE, modelMap);
    }

    private void justTestMysqlAndSqliteWork() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setAuthor("!!!!");
        bookEntity.setName("!!!!");
        bookEntity.setDateOfIssue("2022-09-14");
        bookEntity.setPages(1111);
        bookEntity.setBorrowed(false);
        bookEntity.setUserId(1);

        mysqlBookService.addBook(bookEntity);
        mysqlBookService.deleteBook(bookEntity);
        mysqlBookService.deleteBook(bookEntity);
        mysqlBookService.addBook(bookEntity);

//        mysqlBookRepository.save(bookEntity);
//        sqliteBookRepository.save(bookEntity);

        List<BookEntity> mySqlBookList = mysqlBookRepository.findAll();
        System.out.println("mySqlMagazineRepository.findAll() => " + mySqlBookList);
//        List<BookEntity> sqliteBooList = sqliteBookRepository.findAll();
//        System.out.println("sqliteMagazineRepository.findAll() => " + sqliteBooList);
    }

    @PostMapping("/" + LOG_IN_TITLE)
    String doPost(@RequestBody UserDataModelForJson userModel,
                  HttpServletResponse response) {
        String userName = userModel.getUserName();
        String typeOfWork = userModel.getTypeOfWorkAsStr();

        CookieUtil.addCookie(USER_NAME, userName, response);
        CookieUtil.addCookie(TYPE_OF_WORK_WITH_FILES, typeOfWork, response);
        return "redirect:/" + MAIN_MENU_TITLE;
    }

}
