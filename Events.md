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
    {
        "eventId": 1,
        "eventTitle": "Lady Gaga Live",
        "eventDescription": "Lady Gaga, Monster Tour 2022",
        "numberOfTickets": 3400,
        "dateOfEvent": null,
        "eventVenue": {
            "venueId": 2,
            "venueName": "Tampere-talo",
            "venueAddress": "Yliopistonkatu 55, 33100",
            "venueCity": "Tampere"
        },
        "ticketTypes": [
            {
                "ticketTypeId": 1,
                "ticketTypeDescription": "Normaalilippu",
                "price": 50.0
            },
            {
                "ticketTypeId": 2,
                "ticketTypeDescription": "Lastenlippu",
                "price": 20.0
            }
        ]
    },
    {
        "eventId": 2,
        "eventTitle": "FrÃ¶belin Palikat Live",
        "eventDescription": "FrÃ¶belin Palikat, Never Stop The Madness",
        "numberOfTickets": 250,
        "dateOfEvent": null,
        "eventVenue": {
            "venueId": 4,
            "venueName": "Tavastia",
            "venueAddress": "Urho Kekkosen katu 4, 00100",
            "venueCity": "Helsinki"
        },
        "ticketTypes": [
            {
                "ticketTypeId": 3,
                "ticketTypeDescription": "Lastenlippu",
                "price": 20.0
            }
        ]
    }
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
{
    "eventId": 1,
    "eventTitle": "Lady Gaga Live",
    "eventDescription": "Lady Gaga, Monster Tour 2022",
    "numberOfTickets": 3400,
    "dateOfEvent": null,
    "eventVenue": {
        "venueId": 2,
        "venueName": "Tampere-talo",
        "venueAddress": "Yliopistonkatu 55, 33100",
        "venueCity": "Tampere"
    },
    "ticketTypes": [
        {
            "ticketTypeId": 1,
            "ticketTypeDescription": "Normaalilippu",
            "price": 50.0
        },
        {
            "ticketTypeId": 2,
            "ticketTypeDescription": "Lastenlippu",
            "price": 20.0
        }
    ]
}
```



### Virheellinen response

**Ehto** : Jos url-parametrina annettua tapahtumaa ei löydy
**Code** : `404 NOT FOUND`
**Message** : `Cannot find an event with the id {id}`

## Tapahtuman lisäys

Uuden tapahtuman luonti ja lisäys tietokantaan.

**URL** : `/api/events/`

**METHOD** : `POST`

**REQUEST BODY**
Tapahtuman tiedot json-muodossa(poislukien id, joka autogeneroidaan). Ei pakollisia kenttiä(toistaiseksi).
Tapahtuman tapahtumapaikka annetaan sen id:nä(venueId) muodossa:

`"eventVenue":{"venueId": {id}}`

Mikäli id:llä ei löydy tapahtumapaikkaa, eventVenueksi tulee **null**.

Esim:

```json
{
"eventTitle":"Kolmas Nainen Live","eventDescription":"Kolmas Nainen Lavalla","numberOfTickets":500,"dateOfEvent":"15-12-2022 21:00","eventVenue":{"venueId":1}
}
```

### Onnistunut response

**Code** : `200 OK`

**Response body esim** Vastaus palauttaa tallennetun entityn

```json
{
    "eventId": 9,
    "eventTitle": "Kolmas Nainen Live",
    "eventDescription": "Kolmas Nainen Lavalla",
    "numberOfTickets": 500,
    "dateOfEvent": "15-12-2022 21:00",
    "eventVenue": {
        "venueId": 1,
        "venueName": "Sibeliustalo",
        "venueAddress": "Ankkurikatu 7, 15140",
        "venueCity": "Lahti"
    },
    "ticketTypes": null
}
```

## Tapahtuman poisto

Yksittäisen tapahtuman poisto tietokannasta. Tapahtuman Id/primary key annetaan URL:ssa.

**URL** : `/api/events/:pk`

**URL-PARAMETERS** : `pk=[Long]` jossa pk on tapahtuman eventId tietokannassa. 

**METHOD** : `DELETE`

### Onnistunut response

**Code** : `200 OK`

**Response body esim**

"message": "Deleted an event with the id 8"

## Tapahtuman muokkaus

Olemassa olevan tapahtuman tietojen muokkaus.

**URL** : `/api/events/:pk`

**URL-PARAMETERS** : `pk=[Long]` jossa pk on tapahtuman eventId tietokannassa. 

**METHOD** : `PUT`

**REQUEST BODY**
PAKOLLISENA kaikki perustiedot json-muodossa tai puutteellisiin kenttiin tulee arvoksi NULL. Lipputyyppejä ei tarvitse antaa.

Esim:

```json
{
"eventTitle":"Lady Gaga Live","eventDescription":"Lady Gaga, Not-The-Old-Monster Tour","numberOfTickets":4000,"dateOfEvent":"19-08-2022 21:00","eventVenue":{"venueId":1}
}
```

### Onnistunut response

**Code** : `200 OK`

**Response body esim** Vastaus palauttaa tallennetun entityn

```json
{
    "eventId": 1,
    "eventTitle": "Lady Gaga Live",
    "eventDescription": "Lady Gaga, Not-The-Old-Monster Tour",
    "numberOfTickets": 4000,
    "dateOfEvent": "19-08-2022 21:00",
    "eventVenue": {
        "venueId": 1,
        "venueName": "Sibeliustalo",
        "venueAddress": "Ankkurikatu 7, 15140",
        "venueCity": "Lahti"
    },
    "ticketTypes": [
        {
            "ticketTypeId": 1,
            "ticketTypeDescription": "Normaalilippu",
            "price": 50.0
        },
        {
            "ticketTypeId": 2,
            "ticketTypeDescription": "Lastenlippu",
            "price": 20.0
        }
    ]
}
```
