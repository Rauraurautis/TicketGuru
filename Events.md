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

## Yksittäisen tapahtuman tietojen haku

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
	"eventTitle":"Kolmas Nainen Live",
	"eventDescription":"Kolmas Nainen Lavalla",
	"numberOfTickets":500,
	"dateOfEvent":"15-12-2022 21:00",
	"eventVenue":{"venueId":1}
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
PAKOLLISENA kaikki perustiedot json-muodossa tai puutteellisiin kenttiin tulee arvoksi **null**. Lipputyyppejä ei tule antaa, ne muokataan muualta.

Esim:

```json
{
	"eventTitle":"Lady Gaga Live",
	"eventDescription":"Lady Gaga, Not-The-Old-Monster Tour",
	"numberOfTickets":4000,
	"dateOfEvent":"19-08-2022 21:00",
	"eventVenue":{"venueId":1}
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
### Virheellinen response

**Ehto** : Jos url-parametrina annettua tapahtumaa ei löydy
**Code** : `404 NOT FOUND`
**Message** : `Cannot find an event with the id {id}`


## Tapahtuman lipputyyppien tietojen haku

Näyttää yksittäisen tapahtuman lipputyyppien tiedot. Tapahtuman Id/primary key annetaan URL:ssa.

**URL** : `/api/events/:pk/tickettypes`

**URL-PARAMETERS** : `pk=[Long]` jossa pk on tapahtuman eventId tietokannassa. 

**METHOD** : `GET`

### Onnistunut response

**Code** : `200 OK`

**Esimerkki**

```json
[
    {
        "ticketTypeId": 1,
        "ticketTypeDescription": "Normaalilippu",
        "price": 50.0,
        "event": {
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
            }
        }
    },
    {
        "ticketTypeId": 2,
        "ticketTypeDescription": "Lastenlippu",
        "price": 20.0,
        "event": {
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
            }
        }
    }
]
```

### Virheellinen response

**Ehto** : Jos url-parametrina annettua tapahtumaa ei löydy
**Code** : `404 NOT FOUND`
**Message** : `Cannot find an event with the id {id}`


## Lipputyypin lisäys tapahtumaan

Uuden lipputyypin luonti tapahtumalle ja lisäys tietokantaan.

**URL** : `/api/events/{id}/tickettypes`

**METHOD** : `POST`

**REQUEST BODY**
Lipputyypin tiedot annetaan json-muodossa(poislukien id, joka autogeneroidaan). Ei pakollisia kenttiä(toistaiseksi).
Tietokentät:
	[String] ticketTypeDescription
	[float] price	**Desimaalierottaja annettava pisteenä**

Esim:

```json
{
	"ticketTypeDescription":"Opiskelijalippu",
	"price":10.50
}
```

### Onnistunut response

**Code** : `200 OK`

**Response body esim** Vastaus palauttaa tallennetun entityn

```json
{
    "ticketTypeId": 4,
    "ticketTypeDescription": "Opiskelijalippu",
    "price": 10.5,
    "event": {
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
        }
    }
}
```
## Lipputyypin poisto

Yksittäisen lipputyypin poisto tietokannasta. Tapahtuman Id/primary key annetaan URL:ssa sekä lipputyypin Id/pk annetaan URL:ssa.

**URL** : `/api/events/:pk/tickettypes/:pk`

**URL-PARAMETERS** : `pk=[Long]` jossa ensimmäinen pk on tapahtuman eventId tietokannassa ja toinen lippytyypin ticketTypeId.

**METHOD** : `DELETE`

### Onnistunut response

**Code** : `200 OK`

**Response body esim**

"message": "Deleted a tickettype with the id {Id}"

## Lipputyypin muokkaus

Tapahtumassa olevan lipputyypin tietojen muokkaus.

**URL** : `/api/events/:pk/tickettypes/:pk`

**URL-PARAMETERS** : `pk=[Long]` jossa ensimmäinen pk on tapahtuman eventId tietokannassa ja toinen lippytyypin ticketTypeId.

**METHOD** : `PUT`

**REQUEST BODY**
PAKOLLISENA kaikki perustiedot json-muodossa tai puutteellisiin kenttiin tulee arvoksi **null**. Lipputyyppejä ei tule antaa, ne muokataan muualta.

Esim:

```json
{
	"ticketTypeDescription":"Muutoslippu",
	"price":5.50
}
```

### Onnistunut response

**Code** : `200 OK`

**Response body esim** Vastaus palauttaa tallennetun entityn

```json
{
    "ticketTypeId": 4,
    "ticketTypeDescription": "Muutoslippu",
    "price": 5.5,
    "event": {
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
        }
    }
}
```
### Virheellinen response

**Ehto** : Jos url-parametrina annettua tapahtumaa ei löydy
**Code** : `404 NOT FOUND`
**Message** : `Cannot find a tickettype with the id {Id}`
