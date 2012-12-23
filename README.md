Product Backlog
===
Product Backlog is a project management web application which includes most important agile concepts as: **kanban**, **product backlog**, **burndown chart** and is extremely easy to use by project team members. Overhead of using this tool is insignificant for a development team. 

This project is in progress. See the items below:

TODO Items
---
- ~~Add new items~~
- ~~Close and open items by double click~~
- ~~Add burndown chart~~
- ~~burn down chart scale x axis from 0~~
- ~~Add grawl for notifying user~~
- ~~Add numbers to items (product items count starts from 1)~~
- ~~Change accordian to something else because it automatically closes~~
- Add timestamp column (lastModified) to Item for sorting purposes
- Add timezone configuration option for burndown chart
- When start typing automatically focus to 'New item' inputbox
- When typing long name widen the text field of  'New item'
- Add priority to issues
- Add user capabilities: 
 - user registration and login
 - ~~product manager user role to create product~~ and assign new roles
 - ~~add login with google~~
 - contributor access
 - readonly access
- Product can be: private (only for logged in users with readonly access), public
- First dblclick assigns to me, second dblclick closes issue
- On closed items context menu:
 - open assigned to me
 - open unassigned
 - change name
- On open items context menu:
 - close item (duplicates second dblclick action)
 - assign to me (duplicates first dblclick action)
 - assign to: choose person from list
 - set unassigned

- Add note (details) to item:
 - option in context menu ('Add note') that opens dialog box to add some note 
 - notes cannot be deleted but can be edited by owner
 - indicate that item has details as (active) icon on list (which displays notes in dialog window)
 - separate table ItemNote

- Add sorting options:
 - first assigned to me
 - sort by number
 - sort by assignee
 - latest first
 - unassigned first
 - sort by priority

- Add tagging functionality:
 - tagging can be by releases

- Release functionality:
 - product manager specifies release dates and items between those dates creates release notes automatically 

- Project selection through url string (i.e. http://host.com/product1)
