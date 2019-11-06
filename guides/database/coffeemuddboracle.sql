CREATE TABLE CMVFS (
	CMFNAM char (255),
	CMDTYP int ,
	CMMODD int,
	CMWHOM char (50) NULL,
	CMDATA CLOB NULL
);

ALTER TABLE CMVFS
	ADD 
	( 
		PRIMARY KEY (CMFNAM)
	);

CREATE TABLE CMCHAB (
	CMUSERID char (50) NULL ,
	CMABID char (50) NULL ,
	CMABPF int NULL ,
	CMABTX CLOB NULL
);

ALTER TABLE CMCHAB
	ADD 
	( 
		PRIMARY KEY (CMUSERID,CMABID)
	);

CREATE TABLE CMSTAT (
	CMSTRT int,
	CMENDT int,
	CMDATA CLOB NULL
);

ALTER TABLE CMSTAT
	ADD 
	( 
		PRIMARY KEY (CMSTRT)
	);
	
CREATE TABLE CMPOLL (
	CMNAME char (100) ,
	CMBYNM char (100) NULL ,
	CMSUBJ char (255) NULL ,
	CMDESC CLOB NULL ,
	CMOPTN CLOB NULL ,
	CMFLAG int NULL ,
	CMQUAL char (255) NULL ,
	CMRESL CLOB NULL,
	CMEXPI int NULL
);

ALTER TABLE CMPOLL
	ADD 
	( 
		PRIMARY KEY (CMNAME)
	);
	
CREATE TABLE CMCHAR (
    CMCHID char (50),
	CMUSERID char (50),
	CMPASS char (50) NULL ,
	CMCLAS char (250) NULL ,
	CMSTRE int NULL ,
	CMRACE char (50) NULL ,
	CMDEXT int NULL ,
	CMCONS int NULL ,
	CMGEND char (50) NULL ,
	CMWISD int NULL ,
	CMINTE int NULL ,
	CMCHAR int NULL ,
	CMHITP int NULL ,
	CMLEVL char (50) NULL ,
	CMMANA int NULL ,
	CMMOVE int NULL ,
	CMDESC CLOB NULL ,
	CMALIG int NULL ,
	CMEXPE int NULL ,
	CMEXLV int NULL ,
	CMWORS char (50) NULL ,
	CMPRAC int NULL ,
	CMTRAI int NULL ,
	CMAGEH int NULL ,
	CMGOLD int NULL ,
	CMWIMP int NULL ,
	CMQUES int NULL ,
	CMROID char (100) NULL ,
	CMDATE char (50) NULL ,
	CMCHAN int NULL ,
	CMATTA int NULL ,
	CMAMOR int NULL ,
	CMDAMG int NULL ,
	CMBTMP int NULL ,
	CMLEIG char (50) NULL ,
	CMHEIT int NULL ,
	CMWEIT int NULL ,
	CMPRPT char (250) NULL,
	CMCOLR char (100) NULL,
	CMLSIP char (100) NULL,
	CMEMAL char (255) NULL,
	CMPFIL CLOB NULL,
	CMSAVE char (150) NULL,
	CMMXML CLOB NULL
);

ALTER TABLE CMCHAR
	ADD 
	( 
		PRIMARY KEY (CMUSERID)
	);

CREATE TABLE CMCHFO (
	CMUSERID char (50) NULL ,
	CMFONM int NULL ,
	CMFOID char (50) NULL ,
	CMFOTX CLOB NULL ,
	CMFOLV int NULL ,
	CMFOAB int NULL 
);

ALTER TABLE CMCHFO
	ADD 
	( 
		PRIMARY KEY (CMUSERID,CMFONM)
	);

CREATE TABLE CMCHCL (
	CMUSERID char (50) NULL ,
	CMCLAN char (100) NULL ,
	CMCLRO int NULL,
	CMCLSTS char (100) NULL
);

ALTER TABLE CMCHCL
	ADD 
	( 
		PRIMARY KEY (CMUSERID,CMCLAN)
	);

CREATE TABLE CMCHIT (
	CMUSERID char (50) NULL ,
	CMITNM char (100) NULL ,
	CMITID char (50) NULL ,
	CMITTX CLOB NULL ,
	CMITLO char (100) NULL ,
	CMITWO int NULL ,
	CMITUR int NULL ,
	CMITLV int NULL ,
	CMITAB int NULL ,
	CMHEIT int NULL
);

ALTER TABLE CMCHIT
	ADD 
	( 
		PRIMARY KEY (CMUSERID,CMITNM)
	);

CREATE TABLE CMROCH (
	CMROID char (50) NULL ,
	CMCHNM char (100) NULL ,
	CMCHID char (50) NULL ,
	CMCHTX CLOB NULL ,
	CMCHLV int NULL ,
	CMCHAB int NULL ,
	CMCHRE int NULL ,
	CMCHRI char (100) NULL
);

ALTER TABLE CMROCH 
	ADD 
	( 
		PRIMARY KEY (CMROID,CMCHNM)
	);

CREATE TABLE CMROEX (
	CMROID char (50) NULL ,
	CMDIRE int NULL ,
	CMEXID char (50) NULL ,
	CMEXTX CLOB NULL ,
	CMNRID char (50) NULL 
);

ALTER TABLE CMROEX 
	ADD 
	( 
		PRIMARY KEY (CMROID,CMDIRE)
	);

CREATE TABLE CMROIT (
	CMROID char (50) NULL ,
	CMITNM char (100) NULL ,
	CMITID char (50) NULL ,
	CMITLO char (100) NULL ,
	CMITTX CLOB NULL ,
	CMITRE int NULL ,
	CMITUR int NULL ,
	CMITLV int NULL ,
	CMITAB int NULL ,
	CMHEIT int NULL
);

ALTER TABLE CMROIT 
	ADD 
	( 
		PRIMARY KEY (CMROID,CMITNM)
	);

CREATE TABLE CMROOM (
	CMROID char (50) NULL ,
	CMLOID char (50) NULL ,
	CMAREA char (50) NULL ,
	CMDESC1 char (255) NULL ,
	CMDESC2 CLOB NULL ,
	CMROTX CLOB NULL 
);

ALTER TABLE CMROOM 
	ADD 
	( 
		PRIMARY KEY (CMROID)
	);


CREATE TABLE CMQUESTS (
	CMQUESID char (250) NULL ,
	CMQUTYPE char (50) NULL ,
	CMQFLAGS int NULL ,
	CMQSCRPT CLOB NULL ,
	CMQWINNS CLOB NULL
);

ALTER TABLE CMQUESTS 
	ADD 
	( 
		PRIMARY KEY (CMQUESID)
	);


CREATE TABLE CMAREA (
	CMAREA char (50) ,
	CMTYPE char (50) ,
	CMCLIM int NULL ,
	CMSUBS char (100) NULL ,
	CMDESC CLOB NULL ,
	CMROTX CLOB NULL ,
	CMTECH int NULL
);

ALTER TABLE CMAREA 
	ADD 
	( 
		PRIMARY KEY (CMAREA)
	);

CREATE TABLE CMJRNL (
	CMJKEY char (75) ,
	CMJRNL char (50) NULL ,
	CMFROM char (50) NULL ,
	CMDATE char (50) NULL ,
	CMTONM char (50) NULL ,
	CMSUBJ char (100) NULL ,
	CMPART char (75) NULL ,
	CMATTR int NULL,
	CMDATA char (255) NULL ,
	CMUPTM number(20) NULL,
	CMIMGP char (50) NULL,
	CMVIEW int NULL,
	CMREPL int NULL,
	CMMSGT CLOB NULL 
);

ALTER TABLE CMJRNL 
	ADD 
	( 
		PRIMARY KEY (CMJKEY)
	);

CREATE INDEX CMJRNLNAME on CMJRNL (CMJRNL);
CREATE INDEX CMJRNLCMPART on CMJRNL (CMPART);
CREATE INDEX CMJRNLCMTONM on CMJRNL (CMTONM);
CREATE INDEX CMJRNLCMUPTM on CMJRNL (CMUPTM);

CREATE TABLE CMCLAN (
	CMCLID char (100) ,
	CMTYPE int ,
	CMDESC CLOB NULL ,
	CMACPT char (255) NULL ,
	CMPOLI CLOB NULL ,
	CMRCLL char (50) NULL ,
	CMDNAT char (50) NULL ,
	CMSTAT int NULL ,
	CMMORG char (50) NULL ,
	CMTROP int NULL
);

ALTER TABLE CMCLAN 
	ADD 
	( 
		PRIMARY KEY (CMCLID)
	);

CREATE TABLE CMPDAT (
	CMPLID char (100) ,
	CMSECT char (100) ,
	CMPKEY char (100) ,
	CMPDAT CLOB NULL 
);

ALTER TABLE CMPDAT 
	ADD 
	( 
		PRIMARY KEY (CMPLID,CMSECT,CMPKEY)
	);

CREATE TABLE CMGRAC (
	CMRCID char (250) ,
	CMRDAT CLOB NULL ,
	CMRCDT int NULL
);

ALTER TABLE CMGRAC 
	ADD 
	( 
		PRIMARY KEY (CMRCID)
	);
	
CREATE TABLE CMCCAC (
	CMCCID char (50) ,
	CMCDAT CLOB NULL 
);

ALTER TABLE CMCCAC 
	ADD 
	( 
		PRIMARY KEY (CMCCID)
	);

CREATE TABLE CMGAAC (
	CMGAID char (50) ,
	CMGAAT CLOB NULL , 
	CMGACL char (50) NULL 
);

ALTER TABLE CMGAAC 
	ADD 
	( 
		PRIMARY KEY (CMGAID)
	);

CREATE TABLE CMACCT (
	CMANAM char (50),
	CMPASS char (50),
	CMCHRS CLOB NULL,
	CMAXML CLOB NULL
);

ALTER TABLE CMACCT 
	ADD 
	( 
		PRIMARY KEY (CMANAM)
	);

CREATE TABLE CMBKLG (
	CMNAME char (50),
	CMINDX int,
	CMDATE number(20) NULL,
	CMDATA CLOB NULL
);

ALTER TABLE CMBKLG
	ADD 
	( 
		PRIMARY KEY (CMNAME,CMINDX)
	);

CREATE TABLE CMCLIT (
	CMCLID char (100) ,
	CMITNM char (100) ,
	CMITID char (50) NULL ,
	CMITTX CLOB NULL ,
	CMITLO char (100) NULL ,
	CMITWO int NULL ,
	CMITUR int NULL ,
	CMITLV int NULL ,
	CMITAB int NULL ,
	CMHEIT int NULL
);

ALTER TABLE CMCLIT
	ADD 
	( 
		PRIMARY KEY (CMCLID,CMITNM)
	);

