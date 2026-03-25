---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# GymContactsPro User Guide

GymContactsPro is a desktop app for managing gym memberships, designed for gym managers who need **a fast and efficient way to organize member records**. It is optimized for use via a **Command Line Interface (CLI)** while still providing a clear and easy-to-read interface. If you are comfortable **typing commands and prefer keyboard-based workflows**, GymContactsPro can help you manage members, track membership validity, and handle daily membership tasks more quickly and accurately than manual methods or generic tools.

- [Quick start](#quick-start)
- [Features](#features)
  - [Adding a person: `add`](#adding-a-person-add)
  - [Listing all persons: `list`](#listing-all-persons-list)
  - [Deleting a person: `delete`](#deleting-a-person-delete)
  - [Editing a person: `edit`](#editing-a-person-edit)
  - [Locating person(s): `find`](#locating-person-s-find)
  - [Clearing all entries: `clear`](#clearing-all-entries-clear)
  - [Viewing help: `help`](#viewing-help-help)
  - [Exiting the program: `exit`](#exiting-the-program-exit)
  - [Saving the data](#saving-the-data)
  - [Editing the data file](#editing-the-data-file)
  - [Archiving data files: `coming in v2.0`](#archiving-data-files-coming-in-v20)
- [FAQ](#faq)
- [Known issues](#known-issues)
- [Command summary](#command-summary)

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar GymContactsPro.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)
Alternatively you could simply double click GymContactsPro.jar file.

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01, 138601 m/2026-11-12` : Adds a contact named `John Doe` to the Address Book.

   * `delete 1006` : Deletes the person with membership ID 1006 from the list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features 
<br>

### Notes about the command format:
<box type="info" seamless>

**Notes here apply to all features introduced below (where applicable),and will not be repeated**
<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [p/PHONE]` can be used as `n/John Doe p/92214584` or as `n/John Doe`.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE`, `p/PHONE n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
  </box>

### Viewing help : `help`

Shows a message explaining how to access the help page；you may press the "Esc" key to close this help window.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person : `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS m/EXPIRY_DATE`

<box type="tip" seamless>

**Tip:** Parameters can be in any order
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01, 138601 m/2026-12-11`
![Add person](images/AddPersonImages/add1.png) ![Add person](images/AddPersonImages/add2.png)

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit MEMBERSHIP_ID [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [m/EXPIRY_DATE]`

* Edits the person at the specified `MEMBERSHIP_ID`.
* At least one of the optional fields must be provided. 
* Multiple fields can be provided at once. The order of the fields does not matter.
* Existing values will be updated to the input values.


Example input:
*  `edit 1000 p/91234567 e/johndoe@example.com` <br> <br>
![command for 'edit 1000 p/91234567 e/johndoe@example.com'](images/editPhoneEmailCommand.png)

Example output:
* Updates the phone number and email of member at ID 1000 <br> <br>
![result for 'edit 1000 p/91234567 e/johndoe@example.com'](images/editPhoneEmailResult.png)


### Locating person(s) : `find`

Finds person(s) whose attributes contain any of the given keywords and, returns them as a list.

Format: `find PREFIX/KEYWORD [MORE_KEYWORDS]`

* Only 1 `PREFIX` is allowed in the command
    * Prefix `id/` finds by Membership ID.
    * Prefix `n/` finds by Name.
    * Prefix `p/` finds by Phone number.
    * Prefix `e/` finds by Email.
    * Prefix `a/` finds by Address(Postal Code).
    * Prefix `m/` finds by Membership Expiry Date.
* At least 1 `KEYWORD` must be provided.
  * Persons matching at least one keyword will be returned (i.e. `OR` search).<br>
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
  * The order of the keywords does not matter.<br>
  e.g. `Hans Bo` will match `Bo Hans`
* Only full keywords will be matched<br>
e.g. `Han` will not match `Hans`
* Keywords are case-insensitive.<br>
e.g `hans` will match `Hans`

Example input:
* `find n/bernice` <br>
  ![command for 'find n/bernice'](images/findBerniceCommand.png)

Example output:
* Returns `Bernice Yu` <br>
  ![result for 'find n/bernice'](images/findBerniceResult.png)

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete id/MEMBERSHIP_ID`

* Deletes the person with the specified `MEMBERSHIP_ID`.
* The MEMBERSHIP_ID refers to the Membership ID number shown in the displayed person list.

  Example input:
* `delete id/1000` <br>
  ![result for 'delete id/1000'](images/delete1000.png)

Example output:
* Returns `Deleted Person: Alex Yeoh; Phone: 87438807; Email: alexyeoh@example.com; Address: Blk 30 Geylang Street 29, #06-40; Membership ID: 1000; Membership Expiry Date: 2026-12-31` <br>
  ![result for 'delete id/1000'](images/delete1000result.png)


### Clearing all entries : `clear`

Clears all data of memberships after your confirmation.

Format: `clear`

* A warning page will pop up.
* The user is asked to confirm his/her command.
* “Y” means all the data will be deleted, “N” means clear command will be cancelled. Meanwhile, clicking the corresponding buttons has the same function.
* After confirmation, the pop up window will give feedback on his/her decision and close soon after.

Example input:
  ![result for 'clear'](images/clearInput.png)

Example output:
* If all the data has been successfully deleted, this window will display a message to inform you.\
  ![result for 'successfully delete'](images/successfullyDelete.png)
* If you cancel the clear command, this window will also notify you.
  ![result for 'cancel clear'](images/cancelDelete.png)
* Meanwhile, the product also shows information of clear results.
  ![result for 'success inf showed on app'](images/successInf.png)
  ![result for 'cancel inf showed on app'](images/cancelInf.png)

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>



--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE e/EMAIL a/ADDRESS m/EXPIRY_DATE` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 m/2026-12-31`
**List**   | `list`
**Delete** | `delete id/MEMBERSHIP_ID`<br> e.g., `delete 3`
**Edit**   | `edit id/MEMBERSHIP_ID [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [m/EXPIRY_DATE]`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find PREFIX/KEYWORD [MORE_KEYWORDS]`<br> e.g., `find n/James Jake`
**Help**   | `help`
**Clear**  | `clear`
**Exit**   | `exit`
