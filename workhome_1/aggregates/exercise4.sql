select facid, sum(slots) as "Total slots" from cd.bookings
group by facid
order by facid