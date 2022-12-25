create table cd.members (
memid int primary key not null,
surname varchar(200) not null,
firstname varchar(200) not null,
addres varchar (300) not null,
zipcode int not null,
telephone varchar(20),
recommendedby int null,
joindate timestamp default NOW(),
foreign key (recommendedby) references members(memid)
);

create table cd.facilities (
facid int primary key not null,
name varchar(100) not null,
membercost numeric not null,
guestcost numeric not null,
initialoutlay numeric not null,
monthlymaintenance numeric not null
);

create table cd.bookings (
bookid int primary key not null,
facid int not null,
memid int not null,
starttime timestamp not null,
slots int not null,
foreign key (facid) references cd.facilities(facid),
foreign key (memid) references cd.members(memid)
);