drop table if exists utilityfunction;
drop table if exists user;
create table user(
userID VARCHAR(50) primary key,
userPassword VARCHAR(50),
userName VARCHAR(50),
gender INTEGER,
age INTEGER,
isValid integer
);
insert into user values('310104200101036099','123456','张大哥',1,20,1);
insert into user values('310104200101036080','123456','王大姐',1,21,1);

drop table if exists bankAccount;
create table bankAccount(
accountID VARCHAR(50) primary key,
userID VARCHAR(50),
openDate datetime,
openMoney numeric(10,2),
accountPassword VARCHAR(50),
balance numeric(10,2),
isReportLoss integer
);
insert into bankAccount values('123456789','310104200101036099','2020-1-1',0.00,'123456',0.00,0);
insert into bankAccount values('113456789','310104200101036080','2020-1-1',0.00,'123456',0.00,0);


drop table if exists utilityAccount;
create table utilityAccount(
accountID VARCHAR(50),
functionID integer,
userID VARCHAR(50),
utilityBalance numeric(10,2),
primary key(accountID, functionID)
);
insert into utilityAccount values('000001',4,'310104200101036099',0.00);
insert into utilityAccount values('000002',5,'310104200101036080',0.00);


drop table if exists conciergeInfo;
create table conciergeInfo(
conciergeID VARCHAR(50) primary key,
bookTime datetime,
conciergeName VARCHAR(50),
gender integer,
age integer,
conciergeLanguage VARCHAR(50),
personalStatement VARCHAR(50)
);
insert into conciergeInfo values('001','2020-3-3','朱先生',1,33,'中国','人美心善');

drop table if exists userUnity;
create table userUnity(
userID VARCHAR(50) primary key,
unityID integer,
authorization integer,
unityRelationship VARCHAR(50)
);
insert into userUnity values('310104200101036099','3',0,'dad');

drop table if exists userConcierge;
create table userConcierge(
userID VARCHAR(50),
conciergeID VARCHAR(50),
primary key(userID, conciergeID)
);
insert into userConcierge values('310104200101036099','001');

drop table if exists assistanceRequest;
create table assistanceRequest(
assistanceID VARCHAR(50),
userID VARCHAR(50),
accountID VARCHAR(50),
payeeAccountID VARCHAR(50),
functionID integer,
amount NUMERIC(10,2),
assistanceDate datetime,
isFinished INTEGER,
primary key(assistanceID)
);
insert into assistanceRequest values(1,'310104200101036099','123456789','0',1,999.99,'2021-1-1',0);
insert into assistanceRequest values(2,'310104200101036099','123456789','0',2,999.99,'2021-1-3',0);

drop table if exists  systemFunction;
create table systemFunction(
functionID integer primary key,
functionName VARCHAR(50),
functionDescription VARCHAR(50),
isUtility integer
);
insert into systemFunction values(1,'转账','用于账户之间的转账',0);
insert into systemFunction values(2,'还款','用于信用卡账户的还款',0);
insert into systemFunction values(3,'账户查询','用于查询账户的余额与账户状态',0);
insert into systemFunction values(4,'水费支付','用于支付水费',1);
insert into systemFunction values(5,'电费支付','用于支付电费',1);
insert into systemFunction values(6,'煤气费支付','用于支付煤气费',1);

drop table if exists tradeRecord;
create table tradeRecord(
recordID VARCHAR(50) primary key,
accountID VARCHAR(50),
functionID integer,
tradeAmount numeric(10,2),
tradeTime datetime,
payeeAccountID VARCHAR(50)
);
insert into tradeRecord values('202111233101042001','123456789',1,999.99,'2021-1-3','113456789');


/*
alter table tradeRecord add foreign key(accountID) references bankAccount(accountID);
alter table tradeRecord add foreign key (functionID) references systemFunction(functionID);
alter table assistanceRequest add foreign key(userID) references user(userID);
alter table assistanceRequest add foreign key(accountID) references bankAccount(accountID);
alter table assistanceRequest add foreign key(functionID) references systemFunction(functionID);
alter table assistanceRequest add foreign key(utilityID) references utilityFunction(utilityID);
alter table userConcierge add foreign key(userID) references user(userID);
alter table userConcierge add foreign key(conciergeID) references conciergeInfo(conciergeID);
alter table userUnity add foreign key(userID) references user(userID);
alter table utilityAccount add foreign key(accountID) references bankAccount(accountID);
alter table utilityAccount add foreign key(utilityID) references utilityFunction(utilityID);
*/

