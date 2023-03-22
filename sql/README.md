
# Introduction
The project is about learning SQL(Structured Query Language) by performing several queries.
Using CRUD operations (create,read,update and delete) are used for persistent storage. 
# SQL Quries

###### Table Setup (DDL)
###### Members Table
```sql
CREATE TABLE cd.members (
memid Integer NOT NULL,
surname CHARACTER VARYING(200) NOT NULL,
firstname CHARACTER VARYING(200) NOT NULL,
address CHARACTER VARYING(300) NOT NULL,
zipcode Integer NOT NULL,
telephone CHARACTER VARYING(20) NOT NULL,
recommendedby Integer,
joindate timestamp NOT NULL,
CONSTRAINT PK_members_memid PRIMARY KEY(memid),
CONSTRAINT FK_members_recommendedby FOREIGN KEY(recommendedby)
REFERENCES cd.members(memid) ON
DELETE
SET
NULL
);
```

###### Bookings Table
```sql 
CREATE TABLE cd.bookings (
faceid Integer NOT NULL,
memid Integer NOT NULL,
starttime timestamp NOT NULL,
slots Integer NOT NULL,
CONSTRAINT PK_bookings_faceid PRIMARY KEY(faceid),
CONSTRAINT FK_bookings_memid FOREIGN KEY(memid)
REFERENCES cd.members(memid)
);
```

###### Facilities Table
```sql
CREATE TABLE cd.facilities (
faceid Integer NOT NULL,
name CHARACTER VARYING(100) NOT NULL,
membercost NUMERIC NOT NULL,
guestcost NUMERIC NOT NULL,
initialoutlay NUMERIC NOT NULL,
monthlymaintenance NUMERIC NOT NULL,
CONSTRAINT PK_facilities_faceid PRIMARY KEY(faceid)
);
```

###### Question 1: Show all members

```sql
select
	*
from
	cd.members;
```

###### Questions 2: The club is adding a new facility - a spa.
```sql
insert
	into
	cd.facilities
values
(9,
'Spa',
20,
30,
100000,
800);
```
###### Questions 3: Automatically generate the value for the next facid, rather than specifying it as a constant.
```sql 
insert
	into
	cd.facilities
    (facid,
	name,
	membercost,
	guestcost,
	initialoutlay,
	monthlymaintenance)
    select
	(
	select
		max(facid)
	from
		cd.facilities)+ 1,
	'Spa',
	20,
	30,
	100000,
	800;
```
###### Questions 4: Alter the data to fix an error.
```sql
update
	cd.facilities
set
	initialoutlay = 10000
where
	facid = 1;
```
###### Questions 5: Alter the price of the second tennis court so that it costs 10% more than the first one.
```sql
update
	cd.facilities
set
	membercost = (
	select
		(membercost * 0.1) + membercost
	from
		cd.facilities
	where
		facid = 0),
	guestcost = (
	select
		(guestcost * 0.1) + guestcost
	from
		cd.facilities
	where
		facid = 0)
where
	facid = 1;
```
###### Questions 6: Delete all bookings from the cd.bookings table.
```sql 
delete
from
	cd.bookings;
```
###### Questions 7: Remove member 37, who has never made a booking, from our database. How can we
```sql 
delete
from
	cd.members
where
	memid = 37;
```
###### Questions 8: Produce a list of facilities that charge a fee to members, and that fee is less than 1/50th of the monthly maintenance cost? Return the facid, facility name, member cost, and monthly maintenance of the facilities in question.
```sql 
select
	facid,
	name,
	membercost,
	monthlymaintenance
from
	cd.facilities
where
	membercost < (0.02 * monthlymaintenance)
	and membercost != 0;
```
###### Questions 9: Produce a list of all facilities with the word 'Tennis' in their name?
```sql 
select
	*
from
	cd.facilities
where
	name like '%Tennis%';
```
###### Questions 10: Retrieve the details of facilities with ID 1 and 5? Try to do it without using the OR operator.
```sql 
select
	*
from
	cd.facilities
where
	facid in (1, 5);
```
###### Questions 11: Produce a list of members who joined after the start of September 2012? Return the memid, surname, firstname, and joindate of the members in question.
```sql 
select
	memid,
	surname,
	firstname,
	joindate
from
	cd.members
where
	joindate > '2012-09-01';
```
###### Questions 12: Combine a list of all surnames and all facility names. Yes, this is a contrived example :-). Produce that list!
```sql 
select
	surname
from
	cd.members
union
select
	name
from
	cd.facilities;
```
###### Questions 13: Produce a list of the start times for bookings by members named 'David Farrell'?
```sql
select
	starttime
from
	cd.bookings b
join cd.members m on
	b.memid = m.memid
where
	firstname = 'David'
	and surname = 'Farrell'; 
```
###### Questions 14: Produce a list of the start times for bookings for tennis courts, for the date '2012-09-21'? Return a list of start time and facility name pairings, ordered by the time.
```sql 
select
	b.starttime as start,
	f.name
from
	cd.bookings b
join cd.facilities f
on
	b.facid = f.facid
where
	starttime >= '2012-09-21'
	and starttime <= '2012-09-22'
	and f.name like '%Tennis Court%'
order by
	starttime;
```
###### Questions 15: Output a list of all members, including the individual who recommended them (if any)? Ensure that results are ordered by (surname, firstname).
```sql 
select
	m.firstname as memfname,
	m.surname as memsname,
	r.firstname as recfname,
	r.surname as recsname
from
	cd.members m
left outer join cd.members r
on
	r.memid = m.recommendedby
order by
	memsname asc,
	memfname asc;
```

###### Questions 16: Output a list of all members who have recommended another member? Ensure that there are no duplicates in the list, and that results are ordered by (surname, firstname).
```sql
select
	distinct r.firstname as firstname,
	r.surname as surname
from
	cd.members m
inner join cd.members r
on
	r.memid = m.recommendedby
order by
	surname asc,
	firstname asc; 
```
###### Questions 17: Output a list of all members, including the individual who recommended them (if any), without using any joins?
```sql 
select
	distinct m.firstname || ' ' || m.surname as member,
	(
	select
		r.firstname || ' ' || r.surname as recommender
	from
		cd.members r
	where
		r.memid = m.recommendedby)
from
	cd.members m
order by
	member;
```
###### Questions 18: Produce a count of the number of recommendations each member has made. Order by member ID.
```sql
select
	recommendedby,
	count(*)
from
	cd.members
where
	recommendedby is not null
group by
	recommendedby
order by
	recommendedby;
```
###### Questions 19: Produce a list of the total number of slots booked per facility. For now, just produce an output table consisting of facility id and slots, sorted by facility id.
```sql 
select
	facid,
	SUM(slots)
from
	cd.bookings
group by
	facid
order by
	facid;
```
###### Questions 20: Produce a list of the total number of slots booked per facility in the month of September 2012. Produce an output table consisting of facility id and slots, sorted by the number of slots.
```sql 
 select
	facid,
	SUM(slots) as "Total Slots"
from
	cd.bookings
where
	starttime >= '2012-09-01'
	and starttime < '2012-10-01'
group by
	facid
order by
	SUM(slots);
```
###### Questions 21: Produce a list of the total number of slots booked per facility per month in the year of 2012. Produce an output table consisting of facility id and slots, sorted by the id and month.
```sql 
select
	facid,
	extract(month
from
	starttime) as month,
	SUM(slots) as "Total Slots"
from
	cd.bookings
where
	extract(year
from
	starttime) = 2012
group by
	facid,
	month
order by
	facid,
	month;
```
###### Questions 22: Find the total number of members (including guests) who have made at least one booking.
```sql 
select
	COUNT(distinct memid)
from
	cd.bookings;
```
###### Questions 23: Produce a list of each member name, id, and their first booking after September 1st 2012. Order by member ID.
```sql 
select
	m.surname,
	m.firstname,
	m.memid,
	MIN(b.starttime)
from
	cd.bookings b
join cd.members m
 on
	b.memid = m.memid
where
	b.starttime >= '2012-09-01'
group by
	m.surname,
	m.firstname,
	m.memid
order by
	m.memid;
```
###### Questions 24: Produce a list of member names, with each row containing the total member count. Order by join date, and include guest members.
```sql 
 select
	COUNT(*) over(),
	firstname,
	surname
from
	cd.members;
```
###### Questions 25: Produce a monotonically increasing numbered list of members (including guests), ordered by their date of joining. Remember that member IDs are not guaranteed to be sequential.
```sql 
select
	row_number() over(),
	firstname,
	surname
from
	cd.members
order by
	joindate;
```
###### Questions 26: Output the facility id that has the highest number of slots booked. Ensure that in the event of a tie, all tieing results get output.
```sql 
select
	facid,
	total
from
	(
	select
		facid,
		sum(slots) total,
		rank() over (
		order by sum(slots) desc) rank
	from
		cd.bookings
	group by
		facid
	) as ranked
where
	rank = 1
```
###### Questions 27: Output the names of all members, formatted as 'Surname, Firstname'
```sql 
select
	surname || ', ' || firstname as name
from
	cd.members;
```
###### Questions 28: find all the telephone numbers that contain parentheses, returning the member ID and telephone number sorted by member ID.
```sql 
select
	memid,
	telephone
from
	cd.members
where
	telephone like '(%)%';
```
###### Questions 29: produce a count of how many members you have whose surname starts with each letter of the alphabet. Sort by the letter, and don't worry about printing out a letter if the count is 0.
```sql 
select
	substr (m.surname,
	1,
	1) as letter,
	count(*)
from
	cd.members m
group by
	letter
order by
	letter;
```
