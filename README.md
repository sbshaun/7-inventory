# Where The Hack- "7-Inventory"

---

### What will the application do?

`7-Inventory` allows users to 

1. `create` 
   1. `Item` and **related information** such as
      - `name`
      
      - `createdDate` 

      - `importantDate` &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;
      // such as expiry/effective date

      - `degreeOfImportance` // importance level of an item.
     
      - `keyWords` &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      // related to the item for easy lookup

   2. `Place` &nbsp;&nbsp;&nbsp;
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

2. `add`
   
   1. an `item`  to a `place`
   
   2. a `place` to a `place`
   
[//]: # (   3. a `place` to a `listOfPlaces`)

4. `remove`
   1. an `item` from a `place`
   2. a `place`

5. `find`
    1. an `item` based on **name** or **createdDate**   
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   // return the `place` where it is kept if found

[//]: # (6. `tryFind`)

[//]: # (   1. try to find an `item`/`place`by comparing it )

[//]: # (   to its **related information**, and return the `place` )

[//]: # (   or a path to the `item`)

[//]: # (   \- &#40;if item is kept in a place, )

[//]: # (   and the place is kept in another place...&#41;)

7. `getAll`
   1. `item` &nbsp;&nbsp;&nbsp;
   in a `place`
   2. `place` in the system and all `item` kept in these `place`

8. `getTimeline`
   1. return a timeline (important dates) of all `item` kept
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
- _create/remove an `place`_
- _add an `item` to a `place`_
- _add a `place` to a `place`_
- _find an `item` by its name_
- _get all `item` in a `place`_
- _get a timeline for all `item` in all `place`_

[//]: # (- _find an `item` by fuzzy search_)

---
