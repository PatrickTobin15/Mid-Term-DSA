# Patient Management System - DSA Midterm Sprint (Solo)

This is my console based Patient Management System for the midterm sprint. It's
split into two main parts like the assignment asks: a Patient Waiting Queue and
a Patient History System, both tied together with one a Scanner based menu in
`Main.java`.

## How it's organized

```
src/main/java/com/patientms/
  Patient.java          -> the entity for a patient in the waiting queue
  PatientQueue.java      -> Part 1: custom Queue (singly linked list)
  PatientRecord.java     -> the entity for a single history record
  PatientHistoryDLL.java -> Part 2: custom Doubly Linked List
  Main.java              -> the menu that ties everything together

src/test/java/com/patientms/
  PatientQueueTest.java
  PatientHistoryDLLTest.java
```

I built both the Queue and the Doubly Linked List myself using my own `Node`
classes instead of using `java.util.LinkedList`, since the point of this
assignment is to actually show I understand how these data structures work
under the hood, not just call a built in class.

## How to run it

This is a Maven project.

```
mvn compile exec:java
```

or just compile and run manually:

```
mvn compile
java -cp target/classes com.patientms.Main
```

## How to run the tests

```
mvn test
```

## What each test does

**PatientQueueTest**
- `testAddAndRemoveMaintainsFifoOrder` - adds 3 patients and checks that the
  first one added is the first one served, proving the queue actually behaves
  FIFO.
- `testRemoveFromEmptyQueueReturnsNullInsteadOfCrashing` - calls
  `removePatient()` on a brand new empty queue and checks it returns `null`
  instead of throwing a NullPointerException, since the assignment specifically
  says to handle empty queue situations safely.
- `testInsertAtPositionZeroForEmergencyCase` - adds 2 normal patients, then
  inserts an emergency patient at position 0 and checks they get served first.
- `testInsertAtMiddlePosition` - checks that inserting at position 1 (not just
  the very front) actually lands the patient in the right spot in line.
- `testQueueSizeUpdatesCorrectlyAsPatientsAreAddedAndRemoved` - makes sure the
  internal size counter goes up and down correctly as patients are added and
  removed.

**PatientHistoryDLLTest**
- `testNavigateForwardThenBackward` - starts at the oldest record, moves
  forward one, then back one, and checks we land back where we started.
- `testDisplayNewestAndOldest` - checks that jumping straight to newest/oldest
  actually grabs the head and tail records.
- `testCannotNavigatePastTheTail` - stands on the newest record and tries to
  call `next()` anyway, checking it doesn't move past the tail.
- `testCannotNavigatePastTheHead` - same idea but for `previous()` at the
  oldest record.
- `testEmptyHistoryIsHandledSafely` - checks that every navigation method
  returns `null` instead of crashing when there are no records at all.

## Documentation Questions (Part 5)

**Why was a Queue appropriate for the waiting room?**

A hospital waiting room works exactly like a real life line which is a first come first serve. 
A Queue models that directly: you add people to the back and take people off the front, so the 
order they get served in naturally matches the order that they had arrived in. If I'd used something
like a Stack or a plain list I would've had to write some extra logic just to force
the FIFO behavior that a Queue already gives me for free.

**Explain the FIFO principle.**

FIFO stands for First In, First Out. It just means the first item you put into
the structure is the first one that comes back out. Whoever gets added to the
queue first is sitting at the front, and every new person joins at the back of
the line behind them, so nobody can "cut" ahead of someone who's already been
waiting longer (unless it's an emergency insert, which is a deliberate
exception, not the queue breaking its own rule).

**Explain what could happen if a stack was used instead of a queue in Part 1.**

A Stack is LIFO (Last In, First Out), so the most recently added patient would
be the next one served, not the patient who's been waiting the longest. In
practice that means whoever walks in most recently could get seen first, while
patients who arrived hours earlier keep getting pushed further and further
back every time someone new walks in. That's basically the opposite of how a
real waiting room should work, and patients could end up waiting indefinitely.

**Suggest ways that you could improve this system (Theory).**

A few ideas:
- Add priority levels (like a real triage system) so patients with more
  urgent conditions automatically move ahead of less urgent ones, instead of
  relying on me manually calling `insertAtPosition()`.
- Store everything in an actual database instead of in-memory, so the queue
  and history survive a restart of the program.
- Add estimated wait times based on how many people are ahead of a patient.
- Add input validation so you can't add a patient with a blank ID or name.
- Turn the emergency insert into its own priority queue sorted by severity
  level instead of a manual position number.

**Why is a Doubly Linked List appropriate for patient history?**

The requirement is to navigate both forward AND backward through a patient's
history, and see the newest and oldest record at any given time. A Doubly Linked
List is built exactly for that, every node keeps a pointer to both the node
before it and the node after it, so I can move in either direction from
wherever I'm currently standing without needing to start over from the
beginning. A singly linked list only has `next` pointers, so once you move
forward you can't go back without restarting the traversal from the head which can be a pain.

**What would happen in the DLL if you forgot to update the next and previous
pointers when manipulating the DLL?**

The list would basically break. If I forgot to update `next`, the list could
end up looking like it stops early, skips over an entire record, or even
creates an infinite loop if a node accidentally points back to itself or an
earlier node. If I forgot to update `prev`, navigating backward could either
throw a NullPointerException, land on the wrong node, or get completely stuck
because the previous pointer still points to the old neighbor instead of the
newly inserted one. Basically the two directions would fall out of sync with
each other, and the list would report a different order depending on whether
you're reading it forward or backward.

**Explain why a Doubly Linked List works in this case compared to an Array or
ArrayList.**

An Array/ArrayList is index-based, so navigating forward and backward is
technically possible (just `index + 1` / `index - 1`), but a Doubly Linked
List fits this use case more naturally for a couple of reasons:
- Inserting or removing a record from an ArrayList in the middle means
  shifting every element after it, which gets more expensive the bigger the
  history gets. A DLL just re-points a couple of node pointers, which is
  constant time regardless of list size.
- The DLL directly represents "where am I standing right now" through the
  `current` pointer and its `next`/`prev` links, which maps really well onto
  the idea of literally walking through someone's visit history one record
  at a time, rather than me having to manually track and bounds-check an
  integer index into an array.
