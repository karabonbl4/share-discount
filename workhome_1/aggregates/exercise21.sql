select name, initialoutlay/((sum(slots* case
				when cd.bookings.memid = 0 then guestcost
				else membercost
				end)/3)- monthlymaintenance) as months from cd.facilities
join cd.bookings on cd.bookings.facid = cd.facilities.facid
group by cd.facilities.facid
order by name
