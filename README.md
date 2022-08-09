# Practice_1_PragmatTech
SHELFBOOK PROJECT


## Task
> "Program: bookshelf"

The bookshelf can contain books and magazines, the user can perform the following actions with them:
* Add
* Delete
* Borrow from the consoleShelf
* Return to the consoleShelf
* Display a list of available books with the ability to sort by author, or by name, or by date of publication, or by number of pages
* Display a list of available journals with the ability to sort by title or number of pages

***Book*** properties:
* Name
* Author
* Date of publication
* Number of pages
* Note whether the book is borrowed from the consoleShelf

***Magazine*** properties:
* Name
* Number of pages
* Note whether the magazine is borrowed from the consoleShelf

Interaction with the user should take place through the consoleTerminal, menu items can be selected via menu item numbers.
Exit the program must be through a separate menu item.
Books and magazines should be saved in a file (number of files and format - optional)

## Realization
> Some notificatons

**Classes in project:**
* Main
* Termial
* _(abstract)_ Literature 
  * Book _extends_ Literature
  * Magazine _extends_ Literature
* _(interface)_ ActionsWithShelf 
* Shelf _implements_ ActionsWithShelf



Code is covered with tests

## State of realization
> To Do List

 - [x] **Add** new Literature object to Shelf
 - [x] **Delete**  Literature object by index from Shelf
 - [x] **Borrow**  Literature object by index from Shelf
 - [x] **Arrive**  Literature object by index back to Shelf
 - [x] **Print list of**  available **Books** sorted by parameter...
    - [x] Sorted by _'name'_ value
    - [x] Sorted by _'author'_ value
    - [x] Sorted by _'page number'_ value
    - [x] Sorted by _'date'_ value
 - [x] **Print list of** available **Magazines** sorted by parameter...
    - [x] Sorted by _'name'_ value
    - [x] Sorted by _'page number'_ value
 - [x] **Save** in file
 - [x] \(Optional) Deserialize
 - [x] \(Optional) Print current state of Shelf
 - [x] **Exit**
