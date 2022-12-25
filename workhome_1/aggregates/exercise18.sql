select firstname, surname, round((sum(slots)*0.5), -1) as hours, rank() over(order by round((sum(slots)*0.5), -1) desc) from cd.members
join cd.bookings on cd.bookings.memid = cd.members.memid
group by cd.members.memid
order by rank, surname, firstname