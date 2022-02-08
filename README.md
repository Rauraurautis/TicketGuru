# TicketGuru

Tiimi:
* Jukka Silkosuo
* Jori Kemppi
* Jimi Kurko
* Miska Routio

## Johdanto

TicketGuru on lipunmyyntijärjestelmä joka on tarkoitettu lippujen myymiseen asiakkaan myyntipisteessä. TicketGurun tilaajana toimii **LippupalveluX**.
Järjestelmä toteutetaan Spring Boot-frameworkissä Javalla ja siinä käytetään -määrittelemätön SQL-tietokantaa.

Myyntijärjestelmä toteutetaan desktopympäristöön sekä lippujen tapahtumassa tarkistusta varten mobiiliin.

Lipunmyyntijärjestelmällä on tarkoitus:
* määritellä ja lisätä tapahtumia järjestelmään
* myydä lippuja tapahtumiin lipunmyyntipisteessä asiakkaille
* tulostaa lippuja myytäväksi tapahtuman ovella
* sisällyttää lippuihin tapahtuman ovella luettava QR-koodi, jolla merkitä ne käytetyiksi

## Järjestelmän määrittely

TicketGurun käyttäjäroolit:
* Admin; esihenkilö tms ylempi henkilö joka vastaa tapahtumien myynnistä
* Lipunmyyjä, asiakkaiden kanssa tekemisissä oleva myyjä joka myy tapahtumaliput
* Lipuntarkastaja; tapahtumassa oleva työntekijä joka tarkastaa ostetut liput ja myy tulostettuja lippuja

### Käyttäjätarinat käyttäjärooleittain

Yleistä
* Tietokannan (SQL) luominen projektille, mihin muualta järjestelmästä voidaan tehdä muutoksia.
* Tulostettujen lippujen pitäisi jokaisen sisältää uniikki koodi jonka avulla lipun ostanut asiakas pääsee tapahtumaan.
* Järjestelmällä pitäisi olla yksinkertainen kirjautumissysteemi jota kautta lipunmyyjät, adminit ja lipuntarkastajat pääsevät kirjautumaan järjestelmään.

Admin
* Admin-oikeudet omaavan henkilön pitäisi pystyä lisäämään järjestelmään tapahtumia ja määrittämään niille tapahtuman nimen, ajankohdan, lippujen määrän ja lippujen hinnan.
* Admin-oikeudet omaavan henkilön pitäisi pystyä muokkaamaan tai poistamaan järjestelmään lisättyjä tapahtumia (muuttamaan ajankohtaa, nimeä, lippujen hintoja)
* Admin-oikeudet omaavan henkilön tulisi päästä tarkastelemaan myyntiraporttia jokaisesta tapahtumasta, josta selviää kaikki tapahtumakohtaiset myynnit ja kaikki myyntitapahtumat yksityiskohtineen (aika, nro ja summa)

Lipunmyyjä
* Lipunmyyjän oikeudet omaavan henkilön pitäisi pystyä myymään X-määrän aikuisten ja/tai lasten lippuja asiakkaalle ja tulostamaan myyntiä vastaavan määrän lippuja.
* Lipunmyyjän oikeudet omaavan käyttäjän pitäisi pystyä tulostamaan kaikki jäljelle jääneet liput järjestelmästä ennakkomyynnin päätyttyä.

## Käyttöliittymä

![Käyttöliittymäkaavio tapahtumien luomisesta](assets/images/ui-kaavio-1.png)

Tapahtumaa luotaessa käyttäjä voi lisätä hintaryhmiä (esimerkiksi katsomon alueita), ja merkitä kuhunkin hintaryhmään, montako lippua kyseiseen hintaryhmään myydään. Lipputyyppejä lisätessä kukin hintaryhmä on omalla välilehdellään, ja hintaryhmän välilehdellä voi määritellä, mitä kukin lipputyyppi (esim. aikuinen, lapsi, opiskelija) maksaa kyseisessä hintaryhmässä.

![Käyttöliittymäkaavio lippujen myymisestä](assets/images/ui-kaavio-2.png)

"Lisää lipputyyppi" luo taulukkoon uuden rivin, johon käyttäjä voi pudotusvalikosta valita hintaryhmän ja lipputyypin.

![Käyttöliittymäkaavio myyntiraporteista](assets/images/ui-kaavio-3.png)