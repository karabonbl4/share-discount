select fst.firstname as memfname, fst.surname as memsname, scd.firstname as recfname, scd.surname as recsname from cd.members fst
left join cd.members scd
on scd.memid = fst.recommendedby
order by fst.surname, fst.firstname