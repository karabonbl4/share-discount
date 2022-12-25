select cd.bookings.starttime, cd.facilities.name
join cd.bookings on cd.bookings.facid = cd.facilities.facid
where starttime >= '2012-09-21' and starttime < '2012-09-22' and cd.facilities.name like '%Tennis Court%'