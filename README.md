# Where The Hack- "7-Inventory"

---
I'm very forgetful so I planned to build an inventory app to keep track of my stuff in Java as a personal project. 
It has a class "Item" which contains information about the kept item, 
and a class "Place" which extends "Item" and it has an extra attribute that it can contain a list of "Item". 
So an "Item" can be kept at a "Place", and a "Place" can also be kept at another "Place" (maybe a bigger place in reality). 
It turned out that I realized it's a very simplified folder system LOL.

---

### What will the application do?

`7-Inventory` allows users to 

1. **create** 
   1. `Item` in the current `place`   
   with a `name`, `createdDate` and optional information:

      - `importantDate` &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;
      // such as expiry/effective date...

      - `degreeOfImportance` // importance level of an item
     
      - `keyWords` &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

   3. `Place` &nbsp;&nbsp;&nbsp;
   // can keep items, and is also an item itself

[//]: # ()
[//]: # (   3. `ListOfObjects` &nbsp; )

[//]: # (   // can keep items or places    )

[//]: # (      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;)

[//]: # (      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;)

[//]: # (      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;)

[//]: # (      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;)

[//]: # (      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;)

[//]: # (   // meaning can be `listOfItems` or `listOfPlaces`)


[//]: # (   3. a `place` to a `listOfPlaces`)

2. **delete**
   1. an `item` from the current `place`
   2. a `place` from the current `place`

3. **access**
   1. an `item` in the current `place`
   2. a `place` in the current `place`

4. **exit**
   1. an `item`
   2. a `place`

[//]: # (5. `find`)

[//]: # (    1. an `item` based on `name` or `createdDate`   )

[//]: # (   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;)

[//]: # (   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;)

[//]: # (   // return the `place` where it is kept if found)

[//]: # (6. `tryFind`)

[//]: # (   1. try to find an `item`/`place`by comparing it )

[//]: # (   to its **related information**, and return the `place` )

[//]: # (   or a path to the `item`)

[//]: # (   \- &#40;if item is kept in a place, )

[//]: # (   and the place is kept in another place...&#41;)

[//]: # (7. `getAll`)

[//]: # (   1. `item` &nbsp;&nbsp;&nbsp;)

[//]: # (   in a `place`)

[//]: # (   2. `place` in the system and all `item` kept in these `place`)

[//]: # ()
[//]: # (8. `getTimeline`)

[//]: # (   1. return a timeline &#40;important dates&#41; of all `item` kept)

[//]: # (      - e.g. expiry dates of food in the fridge)

---

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (| class        | method and description | return type |)

[//]: # (| ------------ | ---------------------- | ----------- |)

[//]: # (| Place        | add&#40;Item item&#41;         | void        |)

[//]: # (| Place        | remove&#40;Item item&#41;      | void        |)

[//]: # (| Place        | find&#40;String name&#41;      | int         |)

[//]: # (| Place        | find&#40;LocalDate date&#41;   | int         |)

[//]: # (| Place        | getAll&#40;&#41;               | String      |)

[//]: # (| Place        | getTimeline&#40;&#41;          | String      |)

[//]: # (| ListOfObject | getEverything&#40;&#41;        | String      |)

[//]: # (| ListOfObject | getEveryTimeline&#40;&#41;     | String      |)

---

### Who will use it?

Everyone who wants to manage belongings.

---

### Why is this project of interest to you?

- It's fun :)

---

### User Story

As a user, I want to be able to
- _create multiple `place` at Top Level_
- _access a `place` at Top Level_
- _create multiple `place`/`item` inside the current `place`_
- _access a `place`/`item` inside a `place`_
- _delete the current `place`/`item`_
- _go back to previous `place`_
- _go back to Top Level_
- _see all `item` in a `place`_
- _save my inventory automatically when quit_
- _reload my previous inventory_

[//]: # (- _get a timeline for all `item` in all `place`_)
[//]: # (- _find an `item` by its name_)
[//]: # (- _find an `item` by fuzzy search_)

[//]: # ()
[//]: # (### Instructions for Grader)

[//]: # ()
[//]: # (- You can generate the first required event by clicking `Create a new Place` button)

[//]: # (- You can generate the second required event by clicking  `Show  All Important Dates` button)

[//]: # (- You can locate my visual component at the start of the app)

[//]: # (- You can save the state of my application by clicking `Save File` button)

[//]: # (- You can reload the state of my application by `Load File` button)

---

[//]: # (### Phase 4: Task 2)

[//]: # ()
[//]: # (**Sample:**)

[//]: # (```)

[//]: # (Event log cleared.)

[//]: # (Added item-"create a place  to delete" to place-"place7 aug 9".)

[//]: # (Added item-"item to  delete" to place-"create a place  to delete".)

[//]: # (Added item-"another place" to place-"create a place  to delete".)

[//]: # (Displayed timeline.)

[//]: # (Deleted item-"another place" from place-"create a place  to delete".)

[//]: # (Deleted item-"create a place  to delete" from place-"place7 aug 9".)

[//]: # (Added item-"item1" to place-"place7 aug 9".)

[//]: # (Deleted item-"item1" from place-"place7 aug 9".)

[//]: # (```)

[//]: # ()
[//]: # (---)

[//]: # (### Phase 4: Task 3)

[//]: # ()
[//]: # (I will use a composite pattern between `Place` and `Item`.)