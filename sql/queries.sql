-- The club is adding a new facility - a spa.
insert into cd.facilities (
  facid, name, membercost, guestcost,
  initialoutlay, monthlymaintenance
)
values
  (9, 'Spa', 20, 30, 100000, 800);

-- automatically generate the value for the next facid, rather than specifying it as a constant.
insert into cd.facilities (
  facid, name, membercost, guestcost,
  initialoutlay, monthlymaintenance
)
select
  (
    select
      max(facid)
    from
      cd.facilities
  )+ 1,
  'Spa',
  20,
  30,
  100000,
  800;

-- update initialoutlay
update
  cd.facilities
set
  initialoutlay = 10000
where
  facid = 1;

-- update with calculation
update
  cd.facilities facs
set
  membercost = facs2.membercost * 1.1,
  guestcost = facs2.guestcost * 1.1
from
  (
    select
      *
    from
      cd.facilities
    where
      facid = 0
  ) facs2
where
  facs.facid = 1;

-- delete all bookings from the cd.bookings table
delete from cd.bookings;

--  remove member 37, who has never made a booking (delete condition)
delete from
  cd.members
where
  memid = 37;

-- list of facilities that charge a fee to members, and that fee is less than 1/50th of the monthly maintenance cost
select
  facid,
  name,
  membercost,
  monthlymaintenance
from
  cd.facilities
where
  membercost > 0
  and (
    membercost < monthlymaintenance / 50.0
  );

-- produce a list of all facilities with the word 'Tennis' in their name
select
  *
from
  cd.facilities
where
  name like '%Tennis%';

--  retrieve the details of facilities with ID 1 and 5
select
  *
from
  cd.facilities
where
  facid in (1, 5);

--  list of members who joined after the start of September 2012
select
  memid,
  surname,
  firstname,
  joindate
from
  cd.members
where
  joindate >= '2012-09-01';

--  combined list of all surnames and all facility names
select
  surname
from
  cd.members
union
select
  name
from
  cd.facilities;

-- list of the start times for bookings by members named 'David Farrell'
select
  bks.starttime
from
  cd.bookings bks
  inner join cd.members mems on mems.memid = bks.memid
where
  mems.firstname = 'David'
  and mems.surname = 'Farrell';

-- list of the start times for bookings for tennis courts, for the date '2012-09-21'
select
  bks.starttime
from
  cd.bookings bks
  inner join cd.members mems on mems.memid = bks.memid
where
  mems.firstname = 'David'
  and mems.surname = 'Farrell';

-- list of all members, including the individual who recommended them (if any)
select
  mems.firstname as memfname,
  mems.surname as memsname,
  recs.firstname as recfname,
  recs.surname as recsname
from
  cd.members mems
  left outer join cd.members recs on recs.memid = mems.recommendedby
order by
  memsname,
  memfname;

--  list of all members who have recommended another member?
select
  distinct recs.firstname as firstname,
  recs.surname as surname
from
  cd.members mems
  inner join cd.members recs on recs.memid = mems.recommendedby
order by
  surname,
  firstname;

-- list of all members, including the individual who recommended them (if any)
select
  distinct mems.firstname || ' ' || mems.surname as member,
  (
    select
      recs.firstname || ' ' || recs.surname as recommender
    from
      cd.members recs
    where
      recs.memid = mems.recommendedby
  )
from
  cd.members mems
order by
  member;

-- count of the number of recommendations each member has made. Order by member ID.
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

--List the total slots booked per facility
select
  facid,
  sum(slots) as "Total Slots"
from
  cd.bookings
group by
  facid
order by
  facid;

-- List the total slots booked per facility in a given month
select
  facid,
  sum(slots) as "Total Slots"
from
  cd.bookings
where
  starttime >= '2012-09-01'
  and starttime < '2012-10-01'
group by
  facid
order by
  sum(slots);

-- List the total slots booked per facility per month

select
  facid,
  extract(
    month
    from
      starttime
  ) as month,
  sum(slots) as "Total Slots"
from
  cd.bookings
where
  extract(
    year
    from
      starttime
  ) = 2012
group by
  facid,
  month
order by
  facid,
  month;

-- Find the count of members who have made at least one booking
select
  count(distinct memid)
from
  cd.bookings

-- List each member's first booking after September 1st 2012
select
  mems.surname,
  mems.firstname,
  mems.memid,
  min(bks.starttime) as starttime
from
  cd.bookings bks
  inner join cd.members mems on mems.memid = bks.memid
where
  starttime >= '2012-09-01'
group by
  mems.surname,
  mems.firstname,
  mems.memid
order by
  mems.memid;

-- Produce a list of member names, with each row containing the total member count
select
  count(*) over(),
  firstname,
  surname
from
  cd.members
order by
  joindate

-- Produce a numbered list of members
select
  row_number() over(
    order by
      joindate
  ),
  firstname,
  surname
from
  cd.members
order by
  joindate

-- Output the facility id that has the highest number of slots booked, again
select
  facid,
  total
from
  (
    select
      facid,
      sum(slots) total,
      rank() over (
        order by
          sum(slots) desc
      ) rank
    from
      cd.bookings
    group by
      facid
  ) as ranked
where
  rank = 1

-- Format the names of members
select
  surname || ', ' || firstname as name
from
  cd.members

-- Find telephone numbers with parentheses
select
  memid,
  telephone
from
  cd.members
where
  telephone similar to '%[()]%';

-- Count the number of members whose surname starts with each letter of the alphabet
select
  substr (mems.surname, 1, 1) as letter,
  count(*) as count
from
  cd.members mems
group by
  letter
order by
  letter
