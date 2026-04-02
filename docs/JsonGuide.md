---
layout: default.md
title: "JSON Guide"
pageNav: 3
---

# JSON Guide for GymContactsPro Data Files 📋

**JSON** (JavaScript Object Notation) is a lightweight data format used for storing and exchanging data. 
GymContactsPro uses JSON to store member information in the `addressbook.json` file.

This guide explains JSON in the **context of GymContactsPro**, helping you understand and edit it safely.

---

## Table of Contents

* [Empty Data File Structure](#empty-data-file-structure)
* [Adding the First Member](#adding-the-first-member)
* [Complete Example](#complete-example)
* [Field Ruless](#field-rules)
* [Common Mistakes to Avoid](#common-mistakes-to-avoid)
* [Validation](#validation)
* [Safety Tips](#safety-tips)

---

### Empty Data File Structure

An empty JSON data file with an empty list of members looks like this:

```json
{
  "persons" : [ ... list of members ... ]
}
```

<box type="info" seamless>

**Note:**
* The curly brackets `{}` at the beginning and end of the file is compulsory for JSON to work. 
* The `"persons"` header is compulsory and must be exactly as shown with double quotes `""` surrounding it. 
It tells JSON that it holds a list of members.
* The square brackets `[]` after the `"persons"` will only appear once in the entire file.
It is where the list of members will be stored.

</box>

---

### Adding the First Member

A JSON data file with a single member looks like this:

```json
{
  "persons" : [ {
    "name" : "Alex Yeoh",
    "phone" : "87438807",
    "email" : "alexyeoh@example.com",
    "address" : "Blk 30 Geylang Street 29, #06-40, 388066",
    "membershipId" : 1000,
    "membershipExpiryDate" : "2027-01-15"
  } ]
}
```

<box type="info" seamless>

**Note:**
* Each member is wrapped in curly brackets `{}`
* Within each member's `{}`, are their fields (name, phone, email, etc.) separated by commas `,`
* The name of fields is followed by a colon `:`, then the value of the field
* Notice that names and values of each field are enclosed in double quotes `""` except for the value for the `"membershipId"` field
  * This has to do with the fact that the value is a number and not a collection of characters. If you are interested in knowing more, see [Field Rules](#field-rules) for more details.

</box>


### Complete Example

A JSON data file with multiple members looks like:

```json
{
  "persons" : [ {
    "name" : "Alex Yeoh",
    "phone" : "87438807",
    "email" : "alexyeoh@example.com",
    "address" : "Blk 30 Geylang Street 29, #06-40, 388066",
    "membershipId" : 1000,
    "membershipExpiryDate" : "2027-01-15"
  }, {
    "name" : "Bernice Yu",
    "phone" : "99272758",
    "email" : "berniceyu@example.com",
    "address" : "Blk 30 Lorong 3 Serangoon Gardens, #07-18, 654321",
    "membershipId" : 1001,
    "membershipExpiryDate" : "2026-12-31"
  } ]
}
```
---

## Field Rules

Each member in the `"persons"` list contains the following fields:

| Field | Type | Description | Example |
|-------|------|-------------|---------|
| `name` | String | Full name of the member | `"Alex Yeoh"` |
| `phone` | String | 8-digit phone number | `"87438807"` |
| `email` | String | Email address | `"alexyeoh@example.com"` |
| `address` | String | Full address with postal code at the end | `"Blk 30 Geylang Street 29, #06-40, 388066"` |
| `membershipId` | Number | Unique 4-digit ID (no quotes) | `1000` |
| `membershipExpiryDate` | String | Expiry date in YYYY-MM-DD format | `"2027-01-15"` |

---

### Common Mistakes to Avoid 
```json
// ❌ WRONG - Missing quotes around string value
"name" : John Yeoh

// ❌ WRONG - Quotes around membershipId
"membershipId" : "1000"

// ❌ WRONG - Missing comma between members
{ "name" : "John" } { "name" : "Jane" }

// ❌ WRONG - Comma after last member in the list
{ "persons" : [ { "name" : "John" }, ] }

// ❌ WRONG - Wrong date format
"membershipExpiryDate" : "01-01-2027"
```

---

## Validation

After editing your `addressbook.json` file:

1. **Check bracket matching** - Every `{` must have a closing `}`, and every `[` must have a closing `]`
2. **Check quotes** - All strings must be enclosed in double quotes (except the value for `membershipId`)
3. **Check commas** - Ensure commas separate members, but don't appear after the last member
4. **Validate online** - You can paste your JSON at [jsonlint.com](https://www.jsonlint.com) to check for syntax errors

---

## Safety Tips

* **Always back up** your `addressbook.json` file before editing it, in case the application cannot load your edited file
* **Use a text editor** with proper formatting (e.g., Visual Studio Code, Notepad++)
* **Avoid programs** like Microsoft Word which may introduce unwanted formatting (though it is still possible to edit the file)
* **Test carefully** – After editing, open GymContactsPro to ensure it loads correctly

