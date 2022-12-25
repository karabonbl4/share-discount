select distinct scd.firstname, scd.surname from cd.members fst
join cd.members scd
on fst.recommendedby = scd.memid
order by surname, firstname