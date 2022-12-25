select distinct concat(firstname, ' ', surname) as member, cd.facilities.name as facility from cd.members
join cd.bookings on cd.members.memid = cd.bookings.memid
join cd.facilities on  cd.facilities.facid = cd.bookings.facid
where cd.facilities.name like '%Tennis Court%'
order by member, facility