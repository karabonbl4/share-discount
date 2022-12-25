select cd.facilities.facid, name, round(sum(slots*0.5), 2) as "Total hours" from cd.facilities
join cd.bookings on cd.bookings.facid = cd.facilities.facid
group by cd.facilities.facid
order by cd.facilities.facid