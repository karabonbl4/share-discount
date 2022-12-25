select name, case
			 when sum(slots* case
			          when memid = 0 then guestcost
			          else membercost
			          end) > 14000 then 'high'
			 when sum(slots* case
			          when memid = 0 then guestcost
			          else membercost
			          end) > 1000 then 'average'
			 else 'low'
			 end as revenue
	   from cd.facilities
join cd.bookings on cd.bookings.facid = cd.facilities.facid
group by cd.bookings.facid, name
order by sum(slots* case
			          when memid = 0 then guestcost
			          else membercost
			          end) desc