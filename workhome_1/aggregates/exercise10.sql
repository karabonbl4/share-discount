select cd.facilities.name, sum(slots* case
							   when memid = 0 then cd.facilities.guestcost
							   else cd.facilities.membercost
							   end) as revenue
	   from cd.bookings
join cd.facilities on cd.facilities.facid = cd.bookings.facid
group by cd.facilities.name
having sum(slots* case
		   when memid = 0 then cd.facilities.guestcost
		   else cd.facilities.membercost
		   end) < 1000
order by revenue