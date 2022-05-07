# Yksikkö- ja integraatiotestit

## **Event**

### EventServiceTest

Event-serviceen liittyvät testit. Ennen kaikkia testejä initialisoidaan uusi **EventService**-olio, jota hyödynnetään testeissä.

---

### **addingEventAddsEvents**

**Kuvaus**

Testi testaa toimiiko tapahtumien lisäys järjestelmään

**Askeleet**

1. Luodaan uusi Event-olio
2. Lisätään olio servicen kautta järjestelmään

**Odotettu tulos**

Testin pitäisi palauttaa sama olio kuin järjestelmään lisätty olio

---

### **updatingEventUpdatesEvent**

**Kuvaus**

Testi testaa päivittääkö tapahtuman päivitys tapahtumaa

**Askeleet**

1. Haetaan event-repositorystä event-olio
2. Asetetaan event-olioon uusi title
3. Päivitetään haettu olio servicen avulla

**Odotettu tulos**

Testin palauttaman olion title pitäisi olla sama kuin muutetun eventin olion title

---

### **addingTicketTypeAddsTicketType**

**Kuvaus**

Testi testaa lisääkö ticketTypen lisäys ticketTypen järjestelmään

**Askeleet**

1. Haetaan event-repositorystä event-olio
2. Luodaan uusi TicketType-olio
3. Eventille lisätään serviceen luotu TicketType-olio

**Odotettu tulos**

Testin pitäisi palauttaa siinä luotu TicketType

---

### **updatingTicketTypeUpdatesTicketType**

**Kuvaus**

Testi testaa päivittääkö TicketTypen päivitys TicketTypen onnistuneesti

**Askeleet**

1. Haetaan event-repositorystä event-olio
2. Haetaan haettuun event-olioon liitetty TicketType-lista
3. Haetaan listasta yksi TicketType
4. Vaihetaan haetun TicketTypen kuvaus
5. Päivitetään servicen avulla TicketType

**Odotettu tulos**

Testin palauttaman TicketTypen kuvaus pitäisi vastaa testissä luotua kuvausta

---

## **SalesEvent**

### SalesEventRepositoryTest

SalesEventRepositoryyn liittyvät yksikkötestit. Ennen jokaista testiä luodaan uusi **SalesService**-olio.

---

### **itShouldReturnSalesEventsWhenSearchedByEvent**

**Kuvaus**

Testi testaa palauttaako SalesEventRepositorystä eventin perusteella hakeminen kaikki eventtiin liittyvät myyntitapahtumat

**Askeleet**

1. Luodaan uusi TicketType-olio
2. Tallennetaan TicketType-olio järjestelmään
3. Luodaan kaksi uutta SalesObject-oliota
4. Luodaan lista molemmista SalesObject-olioista
5. Luodaan kaksi myyntitapahtumaa hyödyntäen listaa
6. Haetaan SalesEventRepositorystä kaikki myyntitapahtumat ID 1 perusteella

**Odotettu tulos**

Testin palauttaman listan pituus pitäisi olla 2, joka vastaisi testissä luodun listan pituutta

---

### **SalesServiceTest**

SalesServiceen liittyvät integraatiotestit Ennen jokaista testiä luodaan uusi **SalesService**-olio.

---

### **creatingSaleCreatesSale**

**Kuvaus**

Testi testaa luoko uuden myyntitapahtuman luominen uuden myyntitapahtuman järjestelmään

**Askeleet**

1. Etsitään yksi TicketType järjestelmästä
2. Luodaan kaksi uutta SalesObjectia hyödyntäen haettua TicketTypeä
3. Luodaan lista molemmista SalesObject-olioista
4. Luodaan myynti hyödyntäen SalesServicen createSale-metodia

**Odotettu tulos**

Testin palauttaman luokan pitäisi vastaa SalesEvent-luokkaa

---


### **creatingSaleWithTooManyTicketsFailsToCreateSale**

**Kuvaus**

Testi testaa epäonnistuuko myynnin luominen jos lippujen määrä ylittää tapahtuman maksimilippumäärän

**Askeleet**

1. Etsitään yksi TicketType järjestelmästä
2. Luodaan kaksi uutta SalesObjectia hyödyntäen haettua TicketTypeä, toiseen TicketTypeen 4000 lippua
3. Luodaan lista molemmista SalesObject-olioista
4. Luodaan myynti hyödyntäen SalesServicen createSale-metodia

**Odotettu tulos**

Testin palauttaman virheen luokan pitäisi vastata TooManyTicketsException-luokkaa

---

## **Ticket**

### TicketRepositoryTest

TicketRepositoryyn liittyvät yksikkötestit.

---

### **itShouldReturnListWhenFoundByTicketType**

**Kuvaus**

Testi testaa palauttaako järjestelmä listan lipuista kun niitä haetaan TicketTypen perusteella

**Askeleet**

1. Etsitään yksi TicketType järjestelmästä
2. Luodaan uusi lippu hyödyntäen haettua TicketTypeä
3. Tallennetaan uusi lippu järjestelmään
4. Haetaan lista lippuja TicketTypen perusteella
5. Luodaan lista jossa on pelkästään aikaisemmin luotu lippu

**Odotettu tulos**

TicketTypen perusteella haetun listan pitäisi vastata lipusta luotua listaa

---

### **itShouldReturnEmptyListWhenSearchedWithTicketTypeNotUsed**

**Kuvaus**

Testi testaa palauttaako järjestelmä tyhjän listan kun haetaan TicketTypen perusteella jota ei ole käytetty

**Askeleet**

1. Etsitään yksi TicketType järjestelmästä
2. Luodaan uusi lippu hyödyntäen haettua TicketTypeä
3. Tallennetaan uusi lippu järjestelmään
4. Haetaan toinen TicketType-järjestelmästä
4. Haetaan lista lippuja toisen TicketTypen perusteella
5. Luodaan lista jossa ei ole mitään

**Odotettu tulos**

Toisen TicketTypen perusteella haetun listan pitäisi vastata luotua tyhjää listaa

---

### **itShouldReturnTicketWhenSearchedWithTicketCode**

**Kuvaus**

Testi testaa palauttaako järjestelmä lipun kun sitä hakee lippukoodin perusteella

**Askeleet**

1. Etsitään yksi TicketType järjestelmästä
2. Luodaan uusi lippu hyödyntäen haettua TicketTypeä
3. Tallennetaan uusi lippu järjestelmään, jossa ticket-koodi "asd"
4. Haetaan järjestelmästä lippu koodin "asd" preusteella

**Odotettu tulos**

Järjestelmästä haetun lipun pitäisi vastata testissä luotua lippua

---

### **itShouldReturnTicketTypesWhenSearchedWithEvent**

**Kuvaus**

Testi testaa palauttaako järjestelmä kaikki TicketTypet jotka tiettyyn eventtiin kuuluu

**Askeleet**

1. Etsitään yksi Event-olio
2. Haetaan TicketTypeRepositoriosta TicketTypet eventin perusteella

**Odotettu tulos**

Haun palauttaman listan pituus pitäisi olla 2 (nämä on aikaisemmin laitettu järjestelmään sql-tiedostoa hyödyntäen)

### TicketServiceTest

TicketServiceen liittyvät integraatiotestit. Ennen kaikkia testejä luodaan uudet **SalesService** ja **TicketService**-oliot. Järjestelmään tallennetaan uusi TicketType ja uusi myyntitapahtuma joka koostuu kahdesta SalesObject-oliosta.

---

### **gettingTicketsByEventIdReturnsTickets**

**Kuvaus**

Testi testaa palauttaako järjestelmä listan tapahtuman lipuista eventId:n perusteella

**Askeleet**

1. Haetaan järjestelmästä lista lippuja ID:n 1 perusteella

**Odotettu tulos**

Haetun listan pituus pitäisi olla 8, vastaen ennen testiä luodussa metodia luotujen lippujen määrää

---

### **gettingSingleTicketByTicketCodeReturnsTicket**

**Kuvaus**

Testi testaa palauttaako järjestelmä yhden lipun kun haetaan sitä TicketCoden perusteella

**Askeleet**

1. Etsitään yksi Ticket-olio järjestelmästä
2. Otetaan koodi talteen oliosta
3. Haetaan lippu järjestelmästä koodia hyödyntäen

**Odotettu tulos**

Haetun lipun ID:n pitäisi olla sama kuin testissä haetun Ticket-olion ID

---

### **settingTicketUsedSetsItUsed**

**Kuvaus**

Testi testaa laittaako järjestelmä lipun käytetyksi

**Askeleet**

1. Etsitään yksi Ticket-olio järjestelmästä
2. Laitetaan Ticket-olio käytetyksi

**Odotettu tulos**

Metodin palauttaman lipun pitäisi olla muuttunut false:sta true:ksi

---














