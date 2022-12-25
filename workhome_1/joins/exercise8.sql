select member, facility, cost from (select concat(firstname, ' ', surname) as member,
									cd.facilities.name as facility,
		case
    when cd.members.memid = 0 then cd.bookings.slots * cd.facilities.guestcost
	else cd.bookings.slots * cd.facilities.membercost
	end as cost
from cd.members
join cd.bookings on cd.members.memid = cd.bookings.memid
join cd.facilities on cd.bookings.facid = cd.facilities.facid
where cd.bookings.starttime >= '2012-09-14' and cd.bookings.starttime < '2012-09-15') as foo
where cost > 30
order by cost desc