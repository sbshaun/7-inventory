# 7-Inventory

---

### What will the application do?

`7-Inventory` allows users to 

1. `create` 
   1. an `item` and **related information** such as

      - `addedDate` &nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      // self-evident.

      - `importantDate` &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;
      // such as expiry/effective date

      - `degreeOfImportance` // importance level of an item.
     
      - `keyWords` &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      // related to the item for easy lookup
   
   2. a `place` &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp; 
   // to keep items

   3. a `listOfPlaces` &nbsp;&nbsp;&nbsp; 
   // self-evident

2. `add`
   
   1. `item` &nbsp; to a `place`
   
   2. `place` to a `listOfPlaces`

3. `remove`
   1. `item` &nbsp;&nbsp;from a `place`
   2. `place` from a `listOfPlaces`

4. `find`
    1. the `place` &nbsp;&nbsp;&nbsp;&nbsp;
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
   // where an `item` &nbsp;is kept
    2. the `listOfPlace` 
   // where a &nbsp;&nbsp;`place` is kept 

5. `tryFind`
   1. try to find an `item`/`place`by comparing it 
   to its **related information**, and return the `place` 
   or a path to the `item`
   \- (if item is kept in a place, 
   and the place is kept in another place...)

6. `getAll`
   1. `item` &nbsp;&nbsp;&nbsp;
   in a `place`
   2. `place` &nbsp;&nbsp;in a `listOfPlaces`

7. `getTimeline`
   1. return a timeline (important dates) of all items
      - e.g. expiry dates of food in the fridge

---

### Who will use it?

Everyone who is willing to put more 
effort in **managing belongings**.

---

### Why is this project of interest to you?

- Because it is FUNNN as it makes more sense 
because funnn is more **fun** :)
- And it is also something that 
I **once thought about** but don't know how to realize.
- **Learning** new concepts and 
**implementing** them **are interesting** per se.

---

### User Story

As a user, I want to be able to
- _create/remove an `item`_
- _add an `item` to a `place`_
- _add a `place` to another `place`_
- _add a `place` to a `listOfPlaces`_
- _find an `item` by its name_
- _find an `item` by fuzzy search_
- _get all `item` in a `place`/`listOfPlaces`_
- _get a timeline for all `item`_

---
