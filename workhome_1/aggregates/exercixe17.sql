select facid, sum(slots) as total from cd.bookings
group by facid
order by total desc
limit 1
