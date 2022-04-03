CREATE TABLE "Venue" (
  "venueId" SERIAL PRIMARY KEY,
  "venueName" varchar,
  "venueAddress" timestamp,
  "venueCity" varchar
);

CREATE TABLE "Event" (
  "eventId" SERIAL PRIMARY KEY,
  "eventDescription" varchar,
  "venueId" integer,
  "numberOfTickets" integer,
  "date" datetime
);

CREATE TABLE "TicketType" (
  "ticketTypeId" SERIAL PRIMARY KEY,
  "eventId" integer,
  "ticketTypeDescription" varchar,
  "price" float
);

CREATE TABLE "Ticket" (
  "ticketId" SERIAL PRIMARY KEY,
  "ticketTypeId" integer,
  "ticketCode" int,
  "ticketUsed" boolean,
  "salesEventId" integer,
  "finalPrice" float
);

CREATE TABLE "SalesEvent" (
  "salesEventId" SERIAL PRIMARY KEY,
  "dateOfSale" datetime
);

ALTER TABLE "Event" ADD FOREIGN KEY ("venueId") REFERENCES "Venue" ("venueId");

ALTER TABLE "TicketType" ADD FOREIGN KEY ("eventId") REFERENCES "Event" ("eventId");

ALTER TABLE "Ticket" ADD FOREIGN KEY ("ticketTypeId") REFERENCES "TicketType" ("ticketTypeId");

ALTER TABLE "Ticket" ADD FOREIGN KEY ("salesEventId") REFERENCES "SalesEvent" ("salesEventId");
