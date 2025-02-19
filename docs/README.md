# Darwin User Guide

![Screenshot of Darwin chatbot](Ui.png)

Darwin is a chatbot. That's it. But Darwin never gives up.

## Adding tasks: `todo`, `deadline`, `event`

Adds the given task to the list.

Format for Todo: `todo DESCRIPTION`

Format for Deadline: `deadline DESCRIPTION /by DEADLINE_DATE`

Format for Event: `event DESCRIPTION /from START_DATE /to END_DATE`

- Dates must follow the YYYY-MM-DD format.

Example output for `todo Study`:
```
Got it. I've added this task:
  [T][] Study
Now you have 3 tasks in the list.
```

## Marking tasks: `mark`

Marks the task at given task number as completed.

Format: `mark TASK_NUMBER`

- TASK_NUMBER must be a positive integer.

Example output for `mark 2`:
```
Nice! I've marked this task as done:
  [D][X] Finish IP (by: Feb 21 2025)
```

## Unmarking tasks: `unmark`

Unmarks the task at given task number.

Format: `unmark TASK_NUMBER`

- TASK_NUMBER must be a positive integer.

Example output for `unmark 1`:
```
OK, I've marked this task as not done yet:
  [E][ ] Recess week (from: Feb 24 2025 to: Mar 2 2025)  
```

## Deleting tasks: `delete`

Deletes the task at given task number.

Format: `delete TASK_NUMBER`

- TASK_NUMBER must be a positive integer.

Example output for `delete 1`:
```
Noted. I've removed this task:
  [E][ ] Recess week (from: Feb 24 2025 to: Mar 2 2025)
Now you have 2 tasks in the list.
```

## Finding tasks: `find`

Finds all tasks that contain the given keyword.

Format: `find KEYWORD`

- KEYWORD must be a string.
- KEYWORD is case-sensitive.

Example output for `find Study`:
```
Here are the matching tasks in your list:
1.[T][] Study
```

## Listing tasks: `list`

Lists all existing tasks.

Format: `list`

Example output:
```
Here are the tasks in your list:
1.[D][X] Finish IP (by: Feb 21 2025)
2.[T][] Study
```

## Exit: `bye`

Exits Darwin chatbot.

Format: `bye`

Output:
```
Bye. Hope to see you again soon!
```