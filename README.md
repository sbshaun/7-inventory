# 7-Inventory

---

### What will the application do?

`7-Inventory` allows users to 

1. `create` 
   1. `Item` and **related information** such as

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

   2. `Place` &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp; 
   // to keep items

   3. `ListOfObjects` &nbsp; 
   // can keep items or places    
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   // meaning can be `listOfItems` or `listOfPlaces`

4. `add`
   
   1. an `item` &nbsp; to a `place`
   
   2. a `place` to a `listOfPlaces`

5. `remove`
   1. an `item` &nbsp;&nbsp;from a `place`
   2. a `place` from a `listOfPlaces`

6. `find`
    1. the `place` &nbsp;&nbsp;&nbsp;&nbsp;
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
   // where an `item` &nbsp;is kept
    2. the `place` &nbsp;&nbsp;&nbsp;
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       // where a &nbsp;&nbsp;`place` is located 

7. `tryFind`
   1. try to find an `item`/`place`by comparing it 
   to its **related information**, and return the `place` 
   or a path to the `item`
   \- (if item is kept in a place, 
   and the place is kept in another place...)

8. `getAll`
   1. `item` &nbsp;&nbsp;&nbsp;
   in a `place`
   2. `place` &nbsp;&nbsp;in a `listOfPlaces`

9. `getTimeline`
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
- _find an `item` by its name_
- _find an `item` by fuzzy search_
- _get all `item` in a `place`_
- _get a timeline for all `item` in all `place`_

---
