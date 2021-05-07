CREATE TABLE BankAppUser (
	id 		   serial not null primary key,
	"name"	   varchar not null,
	email	   varchar not null,
	join_date  date
);
// changes made to the table
ALTER TABLE public.bankappcustomer RENAME TO "BankAppUser";
ALTER TABLE public."BankAppUser" DROP COLUMN member_id;

Select email from "BankAppUser";
insert into "BankAppUser" ("name","email") 
values 					('Andrew','testingemail');


alter table public."BankAppUser" add column "password" varchar;
alter table public."BankAppUser" alter column "join_date" type timestamptz;
update public."BankAppUser" set join_date = null where "name" = 'andrewStockton';
update public."BankAppUser" set join_date = clock_timestamp() where "name" = 'andrewStockton';
update public."BankAppUser" set "password" = 'admin' where "name" = 'andrewStockton';

Select email from "BankAppUser";
insert into "BankAppUser" ("name","email","join_date","password") 
values 					('Andrew','testingemail',pg_catalog.clock_timestamp(), 'testPassowrd');


CREATE TABLE BankAppAccount (
	id 			 	 serial not null primary key,
	"name" 		     varchar not null,
	primary_account  char(1) not null default 'Y',
	balance 		 float,
	constraint fk_customer_id foreign key(id) references BankAppCustomer(id)
);
ALTER TABLE public.bankappaccount RENAME TO "BankAppAccount";

alter table public."BankAppAccount" add column customer_id int not null;
         |         |         |         |         |         |
alter table "BankAppAccount"
add constraint customer_id
foreign key (customer_id) references public."BankAppUser"(id);




Select email from public."BankAppUser" where email = 'testingemail';

ALTER TABLE public."BankAppAccount" RENAME COLUMN customer_id TO fk_customer_id;


insert into "BankAppAccount" ("name", "balance", "fk_customer_id") 
values ('Checking', 0 , (select id from "BankAppUser" where "email" = 'testingemail'));

insert into "BankAppAccount" ("name", "balance", "fk_customer_id") 
values ('Savings', 0 , (select id from "BankAppUser" where "email" = 'testingemail'));


Select name from "BankAppAccount" where (fk_customer_id = 5 and name = 'Checking');
Select name from "BankAppAccount" where (fk_customer_id = 5 and name = 'Checking') 	


CREATE TABLE "BankAppTransaction" (
	id 			serial not null primary key,
	trans_date 	date not null,
	description varchar not null,
	amount		float not null,
	trans_type  char(1) default 'D',
	account_id  int not null
);

Select name,id from "BankAppAccount" where fk_customer_id = 5 and name = 'Checking' or name = 'Savings';

insert into "BankAppTransaction" ("trans_date", "description", "amount", "trans_type", "account_id") 
values (pg_catalog.clock_timestamp(), 	--trans date
		'entered description',			--entered description
		100,							--entered amount
		'D',							--deposit
		(select id from "BankAppAccount" where "id" = 20)); --entered account

select balance from "BankAppAccount" where id = 20; -- gets the balance perform addition

update "BankAppAccount" set "balance" = 100 where "id" = 20;

insert into "BankAppAccount" ("balance") values 100 where "id" = 20;


Select id,name,email,join_date,password from "BankAppUser" where email = 'asdf';

Select name,id from "BankAppAccount" where (fk_customer_id = 6 and name = 'Checking');
Select name,id from "BankAppAccount" where (fk_customer_id = 6 and name = 'Savings');
