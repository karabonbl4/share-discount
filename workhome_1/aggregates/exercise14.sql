select surname, firstname, cd.members.memid, min(starttime) as starttime from cd.members
join cd.bookings on cd.bookings.memid = cd.members.memid
where starttime > '2012-09-01'
group by cd.members.memid
order by cd.members.memid