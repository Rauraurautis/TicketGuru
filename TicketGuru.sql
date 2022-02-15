CREATE TABLE "Venues" (
  "venueId" SERIAL PRIMARY KEY,
  "venueName" varchar,
  "venueAddress" timestamp,
  "venueCity" varchar
);

CREATE TABLE "Events" (
  "eventId" SERIAL PRIMARY KEY,
  "eventDescription" varchar,
  "venueId" integer,
  "numberOfTickets" integer,
  "date" datetime
);

CREATE TABLE "TicketTypes" (
  "ticketTypeId" SERIAL PRIMARY KEY,
  "eventId" integer,
  "ticketTypeDescription" varchar,
  "price" float
);

CREATE TABLE "Tickets" (
  "ticketId" SERIAL PRIMARY KEY,
  "ticketTypeId" integer,
  "ticketCode" int,
  "ticketUsed" boolean
);

CREATE TABLE "TicketSales" (
  "ticketSaleId" SERIAL PRIMARY KEY,
  "salesEventId" integer,
  "ticketId" integer
);

CREATE TABLE "SalesEvents" (
  "salesEventId" SERIAL PRIMARY KEY,
  "dateOfSale" datetime
);

ALTER TABLE "Events" ADD FOREIGN KEY ("venueId") REFERENCES "Venues" ("venueId");

ALTER TABLE "TicketTypes" ADD FOREIGN KEY ("eventId") REFERENCES "Events" ("eventId");

ALTER TABLE "Tickets" ADD FOREIGN KEY ("ticketTypeId") REFERENCES "TicketTypes" ("ticketTypeId");

ALTER TABLE "Tickets" ADD FOREIGN KEY ("ticketId") REFERENCES "TicketSales" ("ticketId");

ALTER TABLE "TicketSales" ADD FOREIGN KEY ("salesEventId") REFERENCES "SalesEvents" ("salesEventId");
