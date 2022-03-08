INSERT INTO venue (venue_name, venue_address, venue_city) VALUES ('Sibeliustalo', 'Ankkurikatu 7, 15140', 'Lahti');
INSERT INTO venue (venue_name, venue_address, venue_city) VALUES ('Tampere-talo', 'Yliopistonkatu 55, 33100', 'Tampere');
INSERT INTO venue (venue_name, venue_address, venue_city) VALUES ('Nokia Areena', 'Sorinkatu 3, 33100', 'Tampere');
INSERT INTO venue (venue_name, venue_address, venue_city) VALUES ('Tavastia', 'Urho Kekkosen katu 4, 00100', 'Helsinki');
INSERT INTO event (event_title, event_description, number_of_tickets, venue_id) VALUES ('Lady Gaga Live', 'Lady Gaga, Monster Tour 2022', 3400, 2);
INSERT INTO event (event_title, event_description, number_of_tickets, venue_id) VALUES ('Fröbelin Palikat Live', 'Fröbelin Palikat, Never Stop The Madness', 250, 4);
INSERT INTO event (event_title, event_description, number_of_tickets, venue_id) VALUES ('Alice Cooper Live', 'Alice Cooper, Poison Concert One Night Only', 2800, 3);
INSERT INTO event (event_title, event_description, number_of_tickets, venue_id) VALUES ('Elvis Presley Live','Elvis, I Never Left Tour', 6000, 3);
INSERT INTO ticket_type (event_id, ticket_type_description, price) VALUES (1, 'Normaalilippu', 50);
INSERT INTO ticket_type (event_id, ticket_type_description, price) VALUES (1, 'Lastenlippu', 20);
INSERT INTO ticket_type (event_id, ticket_type_description, price) VALUES (2, 'Lastenlippu', 20);

