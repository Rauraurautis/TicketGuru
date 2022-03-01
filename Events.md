# API/Events

## Kaikkien tapahtumien listaus

Listaa kaikki tietokannassa olevat tapahtumat.

**URL** : `/api/events`

**METHOD** : `GET`

### Onnistunut response

**Code** : `200 OK`

**Esimerkki**

```json
[
{"eventID":1,"eventDescription":"Lady Gaga, Monster Tour","numberOfTickets":3400,"date":null,"eventVenue":null,"ticketTypes":[]},
{"eventID":2,"eventDescription":"Fröbelin Palikat, Never Stop The Madness","numberOfTickets":250,"date":null,"eventVenue":null,"ticketTypes":[]},{"eventID":3,"eventDescription":"Alice Cooper, Poison Concert One Night Only","numberOfTickets":2800,"date":null,"eventVenue":null,"ticketTypes":[]},{"eventID":4,"eventDescription":"Elvis, I Never Left Tour","numberOfTickets":6000,"date":null,"eventVenue":null,"ticketTypes":[]}
]
```

## Yksittäisen tapahtuman tiedot

Näyttää yksittäisen tapahtuman tiedot. Tapahtuman Id/primary key annetaan URL:ssa.

**URL** : `/api/events/:pk`

**URL-PARAMETERS** : `pk=[Long]` jossa pk on tapahtuman eventId tietokannassa. 

**METHOD** : `GET`

### Onnistunut response

**Code** : `200 OK`

**Esimerkki**

```json
{"eventID":1,"eventDescription":"Lady Gaga, Monster Tour","numberOfTickets":3400,"date":null,"eventVenue":null,"ticketTypes":[]}
```

### Virheellinen response

**Ehto** : Jos url-parametrina annettua tapahtumaa ei löydy
**Code** : `404 NOT FOUND`
**Message** : `Cannot find an event with the id {id}`

## Tapahtuman lisäys

Uuden tapahtuman luonti ja lisäys tietokantaan.

**URL** : `/api/events/`

**METHOD** : `POST`

Tapahtuman tiedot json-muodossa. Ei pakollisia kenttiä

Esim:

```json
{
"eventDescription":"Lady Gaga, Monster Tour","numberOfTickets":3400
}
```

## Tapahtuman poisto

Yksittäisen tapahtuman poisto tietokannasta. Tapahtuman Id/primary key annetaan URL:ssa.

**URL** : `/api/events/:pk`

**URL-PARAMETERS** : `pk=[Long]` jossa pk on tapahtuman eventId tietokannassa. 

**METHOD** : `DELETE`