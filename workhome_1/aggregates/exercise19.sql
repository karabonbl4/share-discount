select name, rank() over(order by sum(slots* case
								   when memid = 0 then guestcost
								  else membercost
								  end) desc) from cd.facilities
join cd.bookings on cd.bookings.facid = cd.facilities.facid
group by name
order by rank, name
limit 3