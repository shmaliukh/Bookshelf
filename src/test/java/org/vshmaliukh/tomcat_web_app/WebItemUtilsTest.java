package org.vshmaliukh.tomcat_web_app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.vshmaliukh.services.menus.GeneratedMenuForAdding;
import org.vshmaliukh.services.menus.GeneratedMenuForSorting;
import org.vshmaliukh.services.menus.menu_items.MenuItemClassType;
import org.vshmaliukh.tomcat_web_app.utils.HtmlUtil;
import org.vshmaliukh.tomcat_web_app.utils.WebUtils;
import org.vshmaliukh.tomcat_web_app.utils.UrlUtil;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.vshmaliukh.ShelfWebApp.*;
import static org.vshmaliukh.tomcat_web_app.servlets.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.tomcat_web_app.servlets.LogInServlet.USER_NAME;

class WebItemUtilsTest {

    @ParameterizedTest
    @MethodSource("provideValueToTestGenerateBaseURLString")
    void testGenerateBaseURLString(String servletTitle, Map<String, String> userAtr, String expectedURLStr) {
        assertEquals(expectedURLStr, UrlUtil.generateBaseURLString(servletTitle, userAtr));
    }

    private static Stream<Arguments> provideValueToTestGenerateBaseURLString() {
        Map<String, String> map1 = new HashMap<>();
        map1.put(USER_NAME, "USER_NAME");
        map1.put(TYPE_OF_WORK_WITH_FILES, "1");
        return Stream.of(
                Arguments.of(
                        DELETE_ITEM_TITLE,
                        map1,
                        "/delete_item?user_name=USER_NAME&type_of_work_with_files=1"
                ),
                Arguments.of(
                        MAIN_MENU_TITLE,
                        map1,
                        "/main_menu?user_name=USER_NAME&type_of_work_with_files=1"
                ),
                Arguments.of(
                        LOG_IN_TITLE,
                        map1,
                        "/log_in?user_name=USER_NAME&type_of_work_with_files=1"
                ),
                Arguments.of(
                        "",
                        Collections.singletonMap("", ""),
                        "?user_name&type_of_work_with_files"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideValueToTestGenerateBaseURLBuilder")
    void testGenerateBaseURL(String servletTitle, Map<String, String> userAtr, String expectedURLStr) {
        assertEquals(expectedURLStr,
                UrlUtil.generateBaseURLBuilder(servletTitle, userAtr)
                        .addParameter("parameterV", "V").toString());
    }

    private static Stream<Arguments> provideValueToTestGenerateBaseURLBuilder() {
        Map<String, String> map1 = new HashMap<>();
        map1.put(USER_NAME, "USER_NAME");
        map1.put(TYPE_OF_WORK_WITH_FILES, "1");
        map1.put(ITEMS_SORTING_MENU_TITLE, "1");
        return Stream.of(
                Arguments.of(
                        DELETE_ITEM_TITLE,
                        map1,
                        "/delete_item?user_name=USER_NAME&type_of_work_with_files=1&parameterV=V"
                ),
                Arguments.of(
                        MAIN_MENU_TITLE,
                        map1,
                        "/main_menu?user_name=USER_NAME&type_of_work_with_files=1&parameterV=V"
                ),
                Arguments.of(
                        LOG_IN_TITLE,
                        map1,
                        "/log_in?user_name=USER_NAME&type_of_work_with_files=1&parameterV=V"
                ),
                Arguments.of(
                        "",
                        Collections.singletonMap("", ""),
                        "?user_name&type_of_work_with_files&parameterV=V"
                )
        );
    }

    @Test
    void testFormHTMLButton_null() {
        assertEquals("<button onclick=\"window.location.href='null';\"> null</button> \n",
                HtmlUtil.formHTMLButton(null, null));
    }

    @Test
    void testFormHTMLButton_str() {
        assertEquals("<button onclick=\"window.location.href='log_in';\"> log_in</button> \n",
                HtmlUtil.formHTMLButton(LOG_IN_TITLE, LOG_IN_TITLE));
    }

    @Test
    void testFormHTMLButton_baseURLString() {
        assertEquals("<button onclick=\"window.location.href='/log_in?user_name&type_of_work_with_files';\"> log_in</button> \n",
                HtmlUtil.formHTMLButton(
                        UrlUtil.generateBaseURLString(LOG_IN_TITLE, Collections.singletonMap("k", "v"))
                        , LOG_IN_TITLE));
    }

    @Test
    void testGenerateFormHTMLStart() {
        assertEquals("<form action = \"/log_in?user_name&type_of_work_with_files\" method = \"POST\">\n",
                HtmlUtil.generateFormHTMLStart(Collections.singletonMap("k", "v"), LOG_IN_TITLE));
    }

    @Test
    void testGenerateMenuItemsFormHTML_adding() {
        assertEquals("<form action = \"/log_in?user_name&type_of_work_with_files\" method = \"POST\">\n" +
                        "<input type=\"radio\" id=\"1\"\n" +
                        "     name=\"menu_item_index\"      value=\"1\">\n" +
                        "    <label for=\"1\">Add new Book item to Shelf</label>\n" +
                        "<br>\n" +
                        "<input type=\"radio\" id=\"2\"\n" +
                        "     name=\"menu_item_index\"      value=\"2\">\n" +
                        "    <label for=\"2\">Add random Book item to Shelf</label>\n" +
                        "<br>\n" +
                        "<input type=\"radio\" id=\"3\"\n" +
                        "     name=\"menu_item_index\"      value=\"3\">\n" +
                        "    <label for=\"3\">Add new Comics item to Shelf</label>\n" +
                        "<br>\n" +
                        "<input type=\"radio\" id=\"4\"\n" +
                        "     name=\"menu_item_index\"      value=\"4\">\n" +
                        "    <label for=\"4\">Add random Comics item to Shelf</label>\n" +
                        "<br>\n" +
                        "<input type=\"radio\" id=\"5\"\n" +
                        "     name=\"menu_item_index\"      value=\"5\">\n" +
                        "    <label for=\"5\">Add new Magazine item to Shelf</label>\n" +
                        "<br>\n" +
                        "<input type=\"radio\" id=\"6\"\n" +
                        "     name=\"menu_item_index\"      value=\"6\">\n" +
                        "    <label for=\"6\">Add random Magazine item to Shelf</label>\n" +
                        "<br>\n" +
                        "<input type=\"radio\" id=\"7\"\n" +
                        "     name=\"menu_item_index\"      value=\"7\">\n" +
                        "    <label for=\"7\">Add new Newspaper item to Shelf</label>\n" +
                        "<br>\n" +
                        "<input type=\"radio\" id=\"8\"\n" +
                        "     name=\"menu_item_index\"      value=\"8\">\n" +
                        "    <label for=\"8\">Add random Newspaper item to Shelf</label>\n" +
                        "<br>\n" +
                        "<input type = \"submit\" value = \"Submit\" />\n" +
                        "</form>",
                HtmlUtil.generateMenuItemsFormHTML(Collections.singletonMap("k", "v"), LOG_IN_TITLE, new GeneratedMenuForAdding()));
    }

    @Test
    void testGenerateMenuItemsFormHTML_sorting() {
        assertEquals("<form action = \"/log_in?user_name&type_of_work_with_files\" method = \"POST\">\n" +
                        "<input type=\"radio\" id=\"1\"\n" +
                        "     name=\"menu_item_index\"      value=\"1\">\n" +
                        "    <label for=\"1\">Sort Book items by value...</label>\n" +
                        "<br>\n" +
                        "<input type=\"radio\" id=\"2\"\n" +
                        "     name=\"menu_item_index\"      value=\"2\">\n" +
                        "    <label for=\"2\">Sort Comics items by value...</label>\n" +
                        "<br>\n" +
                        "<input type=\"radio\" id=\"3\"\n" +
                        "     name=\"menu_item_index\"      value=\"3\">\n" +
                        "    <label for=\"3\">Sort Magazine items by value...</label>\n" +
                        "<br>\n" +
                        "<input type=\"radio\" id=\"4\"\n" +
                        "     name=\"menu_item_index\"      value=\"4\">\n" +
                        "    <label for=\"4\">Sort Newspaper items by value...</label>\n" +
                        "<br>\n" +
                        "<input type = \"submit\" value = \"Submit\" />\n" +
                        "</form>",
                HtmlUtil.generateMenuItemsFormHTML(Collections.singletonMap("k", "v"), LOG_IN_TITLE, new GeneratedMenuForSorting()));
    }

    @Test
    void testGenerateMenuItemRadio() {
        List<MenuItemClassType> menuItems = new GeneratedMenuForSorting().getMenuItems();
        for (int i = 0; i < menuItems.size(); i++) {
            assertEquals("<input type=\"radio\" id=\"" + menuItems.get(i).getIndex() + "\"\n" +
                    "     name=\"menu_item_index\"      value=\"" + menuItems.get(i).getIndex() + "\">\n" +
                    "    <label for=\"" + menuItems.get(i).getIndex() + "\">" + menuItems.get(i).getStr() + "</label>\n" +
                    "<br>\n",
                    WebUtils.generateMenuItemRadio(menuItems.get(i)));
        }
    }
}