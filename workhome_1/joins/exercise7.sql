select distinct concat(fst.firstname, ' ', fst.surname) as member,
    (select concat(scd.firstname, ' ', scd.surname) as recommender from cd.members scd
    where fst.recommendedby = scd.memid)
from cd.members fst
order by member