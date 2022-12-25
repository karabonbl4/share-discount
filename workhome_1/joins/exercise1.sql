select starttime from cd.bookings
join cd.members on cd.members.memid = cd.bookings.memid
where cd.members.firstname='David' and cd.members.surname='Farrell'