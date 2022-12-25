select (select count(*) from cd.members) as count, firstname, surname from cd.members
order by joindate