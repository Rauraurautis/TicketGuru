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

**Esimerkki**

```json
{
    "timeStamp": "2022-03-15T23:00:59.5869555",
    "httpStatus": "NOT_FOUND",
    "message": "Cannot find an event with the id 5"
}
```

## Tapahtuman lisäys

Uuden tapahtuman luonti ja lisäys tietokantaan.

**URL** : `/api/events/`

**METHOD** : `POST`

**REQUEST BODY**
Tapahtuman tiedot json-muodossa(poislukien id, joka autogeneroidaan). Ainoa pakollinen kenttä on *eventTitle*. *numberOfTickets* täytyy olla 0 tai suurempi.
Tyhjäksi jätetyt kentät tallentuvat null:na.
Tapahtuman tapahtumapaikka annetaan sen id:nä(venueId) muodossa:

`"eventVenue":{"venueId": {id}}`


**Esimerkki**

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

**Code** : `201 CREATED`

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

### Virheellinen response

**Ehto** : Jos json:ssa annettua eventVenue:ta ei löydy
**Code** : `404 NOT FOUND`
**Message** : `Cannot find a venue with the id {id}`

**Esimerkki**

```json
{
    "timeStamp": "2022-03-15T23:00:59.5869555",
    "httpStatus": "NOT_FOUND",
    "message": "Cannot find a venue with the id 5"
}
```

## Tapahtuman poisto

Yksittäisen tapahtuman poisto tietokannasta. Tapahtuman Id/primary key annetaan URL:ssa. Poistaa samalla kaikki tapahtuman tickettypet.

**URL** : `/api/events/:pk`

**URL-PARAMETERS** : `pk=[Long]` jossa pk on tapahtuman eventId tietokannassa. 

**METHOD** : `DELETE`

### Onnistunut response

**Code** : `200 OK`

**Response body esim**

```json
{
    "message": "Deleted an event with the id {Id}"
}
```

### Virheellinen response

**Ehto** : Jos annettua tapahtumaa ei löydy
**Code** : `404 NOT FOUND`
**Message** : `Cannot find an event with the id {id}`

**Esimerkki**

```json
{
    "timeStamp": "2022-03-15T23:00:59.5869555",
    "httpStatus": "NOT_FOUND",
    "message": "Cannot find a venue with the id 5"
}
```

## Tapahtuman muokkaus

Olemassa olevan tapahtuman tietojen muokkaus.

**URL** : `/api/events/:pk`

**URL-PARAMETERS** : `pk=[Long]` jossa pk on tapahtuman eventId tietokannassa. 

**METHOD** : `PUT`

**REQUEST BODY**
PAKOLLISENA kenttänä eventTitle, kuten tapahtuman luonnissakin, MUTTA puuttuvat kentät muuttuvat nulliksi jos tyhjiä. Venuen jäädessä tyhjäksi se ei muutu. Lipputyyppejä ei tule antaa, ne muokataan muualta.

**Esimerkki**

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

**Ehto** : Jos väärä metodi
**Code** : `405 METHOD NOT ALLOWED`
**Message** : `You probably used a method on an url that does not support the method`


## Lipputyypin lisäys tapahtumaan

Uuden lipputyypin luonti tapahtumalle ja lisäys tietokantaan.

**URL** : `/api/events/{id}/tickettypes`

**METHOD** : `POST`

**REQUEST BODY**
Lipputyypin tiedot annetaan json-muodossa(poislukien id, joka autogeneroidaan).
Tietokentät:
	[String] ticketTypeDescription **Pakollinen**
	[float] price	**Desimaalierottaja annettava pisteenä!** **Arvo ei saa olla negatiivinen luku.** **Vapaaehtoinen kenttä, joka defaulttaa 0.0:ksi.**

**Esimerkki**

```json
{
	"ticketTypeDescription":"Opiskelijalippu",
	"price":10.50
}
```

### Onnistunut response

**Code** : `201 Created`

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

### Virheellinen response

**Ehto** : Jos url-parametrina annettua tapahtumaa ei löydy
**Code** : `404 NOT FOUND`
**Message** : `Cannot find an event with the id {id}`

**Ehto** : Jos requestbodysta puuttuu tietoja
**Code** : `400 BAD REQUEST`
**Message** : `{kenttä} must not be blank`

**Ehto** : Jos requestbodyssa price-kenttä on virheellinen
**Code** : `400 BAD REQUEST`
**Message** : `{price} must be greater than or equal to 0`

**Ehto** : Jos requestbody on rikki, esim. ei json
**Code** : `400 BAD REQUEST`
**Message** : `Something went wrong`

## Lipputyypin poisto

Yksittäisen lipputyypin poisto tietokannasta. Tapahtuman Id/primary key annetaan URL:ssa sekä lipputyypin Id/pk annetaan URL:ssa.

**URL** : `/api/events/:pk/tickettypes/:pk`

**URL-PARAMETERS** : `pk=[Long]` jossa ensimmäinen pk on tapahtuman eventId tietokannassa ja toinen lipputyypin ticketTypeId.

**METHOD** : `DELETE`

### Onnistunut response

**Code** : `200 OK`

**Response body esim**

```json
{
    "message": "Deleted a tickettype with the id 6"
}
```

### Virheellinen response

**Ehto** : Jos url-parametrina annettua tapahtumaa ei löydy
**Code** : `404 NOT FOUND`
**Message** : `Cannot find an event with the id {id}`

**Ehto** : Jos url-parametrina annettua lipputyyppiä ei löydy
**Code** : `404 NOT FOUND`
**Message** : `Cannot find a tickettype with the id {id}`

## Lipputyypin muokkaus

Tapahtumassa olevan lipputyypin tietojen muokkaus. Tapahtuman Id/primary key annetaan URL:ssa sekä lipputyypin Id/pk annetaan URL:ssa.

**URL** : `/api/events/:pk/tickettypes/:pk`

**URL-PARAMETERS** : `pk=[Long]` jossa ensimmäinen pk on tapahtuman eventId tietokannassa ja toinen lipputyypin ticketTypeId.

**METHOD** : `PUT`

**REQUEST BODY**
Tietokentät:
	[String] ticketTypeDescription **Pakollinen**
	[float] price	**Pakollinen kenttä TAI se defaulttaa 0.0:ksi!** **Desimaalierottaja annettava pisteenä!** **Arvo ei saa olla negatiivinen luku.** 

**Esimerkki**

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
**Message** : `Cannot find an event with the id {id}`

**Ehto** : Jos url-parametrina annettua lipputyyppiä ei löydy
**Code** : `404 NOT FOUND`
**Message** : `Cannot find a tickettype with the id {id}`

**Ehto** : Jos requestbodysta puuttuu tietoja
**Code** : `400 BAD REQUEST`
**Message** : `{kenttä} must not be blank`

**Ehto** : Jos requestbodyssa price-kenttä on virheellinen
**Code** : `400 BAD REQUEST`
**Message** : `{price} must be greater than or equal to 0`

**Ehto** : Jos requestbody on rikki, esim. ei json
**Code** : `400 BAD REQUEST`
**Message** : `Something went wrong`

## Tapahtumaan ostettujen lippujen haku

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
        "ticketId": 3,
        "ticketCode": "98b0b9ea-4531-44dc-96a0-6d1d46e8edaa",
        "ticketUsed": false,
        "ticketType": {
            "ticketTypeId": 3,
            "ticketTypeDescription": "Lastenlippu",
            "price": 20.0,
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
        },
        "finalPrice": 20.0
    },
    {
        "ticketId": 4,
        "ticketCode": "811ed243-ef7f-46bd-975f-816660477d16",
        "ticketUsed": false,
        "ticketType": {
            "ticketTypeId": 3,
            "ticketTypeDescription": "Lastenlippu",
            "price": 20.0,
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
        },
        "finalPrice": 20.0
    },
    {
        "ticketId": 11,
        "ticketCode": "606e6afb-3915-44e3-b572-274ee34a0f69",
        "ticketUsed": false,
        "ticketType": {
            "ticketTypeId": 3,
            "ticketTypeDescription": "Lastenlippu",
            "price": 20.0,
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
        },
        "finalPrice": 18.0
    },
    {
        "ticketId": 12,
        "ticketCode": "6a2eaeb5-2d2d-4d27-9fc5-31867ae127f9",
        "ticketUsed": false,
        "ticketType": {
            "ticketTypeId": 3,
            "ticketTypeDescription": "Lastenlippu",
            "price": 20.0,
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
        },
        "finalPrice": 20.0
    }
]
```
### Virheellinen response

**Ehto** : Jos url-parametrina annettua tapahtumaa ei löydy
**Code** : `404 NOT FOUND`
**Message** : `Cannot find an event with the id {id}`

## Tapahtumaan ostetun yksittäisen lipun haku

Näyttää yksittäisen tapahtuman yhden lipun tiedot. Tapahtuman Id/primary key annetaan URL:ssa.

**URL** : `/api/events/:pk/tickets/:pk`

**URL-PARAMETERS** : `pk=[Long]` jossa ensimmäinen pk on tapahtuman eventId tietokannassa ja toinen lipputyypin ticketId. 

**METHOD** : `GET`

### Onnistunut response

**Code** : `200 OK`

**Esimerkki**

```json
{
    "ticketId": 2,
    "ticketCode": "e55e5130-6b72-40cc-93d7-fdfe6f9aed83",
    "ticketUsed": false,
    "ticketType": {
        "ticketTypeId": 2,
        "ticketTypeDescription": "Lastenlippu",
        "price": 20.0,
        "event": {
            "eventId": 1,
            "eventTitle": "Lady Gaga Live",
            "eventDescription": "Lady Gaga, Monster Tour 2022",
            "numberOfTickets": 3400,
            "dateOfEvent": "17-04-2022 12:00",
            "eventVenue": {
                "venueId": 2,
                "venueName": "Tampere-talo",
                "venueAddress": "Yliopistonkatu 55, 33100",
                "venueCity": "Tampere"
            }
        }
    },
    "finalPrice": 20.0
}
```

### Virheellinen response

**Ehto** : Jos url-parametrina annettua tapahtumaa ei löydy
**Code** : `404 NOT FOUND`
**Message** : `Cannot find an event with the id {id}`

**Ehto** : Jos url-parametrina annettua lippua ei löydy
**Code** : `404 NOT FOUND`
**Message** : `Cannot find a ticket with the id {id}`


## Tapahtumaan ostetun lipun muuttaminen käytetyksi

Muuttaa aiemmin ostetun lipun ticketUsed-kentän arvoksi True. Default on False.

**URL** : `/api/events/:pk/tickets`

**URL-PARAMETERS** : `pk=[Long]` jossa pk on tapahtuman eventId tietokannassa. 

**METHOD** : `PUT`

**REQUEST BODY**
PAKOLLISENA ostetun lipun uniikki lippukoodi [String]ticketCode, joka on tyyliltään seuraavaa muototyyppiä: "e55e5130-6b72-40cc-93d7-fdfe6f9aed83". [boolean]ticketUsed ei tarvita, eikä sitä voi enää muuttaa takaisin falseksi, jos se on true (väärinkäytöksien estämiseksi).

**Esimerkki**

```json
{
	"ticketCode":"e55e5130-6b72-40cc-93d7-fdfe6f9aed83"
}
```

### Onnistunut response

**Code** : `200 OK`

**Esimerkki**

```json
{
    "ticketId": 3,
    "ticketCode": "e55e5130-6b72-40cc-93d7-fdfe6f9aed83",
    "ticketUsed": true,
    "ticketType": {
        "ticketTypeId": 3,
        "ticketTypeDescription": "Lastenlippu",
        "price": 20.0,
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
    },
    "finalPrice": 20.0
}
```

### Virheellinen response

**Ehto** : Jos url-parametrina annettua tapahtumaa ei löydy
**Code** : `404 NOT FOUND`
**Message** : `Cannot find an event with the id {id}`

**Ehto** : Jos request-parametrina annettua lippua ei löydy
**Code** : `400 BAD REQUEST`
**Message** : `No ticket found with the code {ticketCode}`

**Ehto** : Jos request-parametrina annettu lippu on jo käytetty, eli muutettu muotoon ticketUsed==True
**Code** : `400 BAD REQUEST`
**Message** : `The ticket with the ticketcode {ticketCode} has already been used`

**Ehto** : Jos väärä metodi
**Code** : `405 METHOD NOT ALLOWED`
**Message** : `You probably used a method on an url that does not support the method`

## Yksittäisen tapahtuman lipunmyyntitietojen haku

Näyttää yksittäisen tapahtuman kaikkien ostettujen lippujen tiedot. Tapahtuman Id/primary key annetaan URL:ssa.

**URL** : `/api/events/:pk/salesevents`

**URL-PARAMETERS** : `pk=[Long]` jossa pk on tapahtuman eventId tietokannassa. 

**METHOD** : `GET`

### Onnistunut response

**Code** : `200 OK`

**Esimerkki**

```json
[
    {
        "salesEventId": 2,
        "dateOfSale": "09-03-2022 00:26",
        "tickets": [
            {
                "ticketId": 2,
                "ticketCode": "e55e5130-6b72-40cc-93d7-fdfe6f9aed83",
                "ticketUsed": false,
                "ticketType": {
                    "ticketTypeId": 2,
                    "ticketTypeDescription": "Lastenlippu",
                    "price": 20.0,
                    "event": {
                        "eventId": 1,
                        "eventTitle": "Lady Gaga Live",
                        "eventDescription": "Lady Gaga, Monster Tour 2022",
                        "numberOfTickets": 3400,
                        "dateOfEvent": "17-04-2022 12:00",
                        "eventVenue": {
                            "venueId": 2,
                            "venueName": "Tampere-talo",
                            "venueAddress": "Yliopistonkatu 55, 33100",
                            "venueCity": "Tampere"
                        }
                    }
                },
                "finalPrice": 20.0
            },
            {
                "ticketId": 3,
                "ticketCode": "98b0b9ea-4531-44dc-96a0-6d1d46e8edaa",
                "ticketUsed": true,
                "ticketType": {
                    "ticketTypeId": 3,
                    "ticketTypeDescription": "Lastenlippu",
                    "price": 20.0,
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
                },
                "finalPrice": 20.0
            }
        ]
    },
    {
        "salesEventId": 3,
        "dateOfSale": "09-03-2022 00:42",
        "tickets": [
            {
                "ticketId": 7,
                "ticketCode": "63c9767f-e471-4cef-bf47-bf0786bd1c69",
                "ticketUsed": false,
                "ticketType": {
                    "ticketTypeId": 2,
                    "ticketTypeDescription": "Lastenlippu",
                    "price": 20.0,
                    "event": {
                        "eventId": 1,
                        "eventTitle": "Lady Gaga Live",
                        "eventDescription": "Lady Gaga, Monster Tour 2022",
                        "numberOfTickets": 3400,
                        "dateOfEvent": "17-04-2022 12:00",
                        "eventVenue": {
                            "venueId": 2,
                            "venueName": "Tampere-talo",
                            "venueAddress": "Yliopistonkatu 55, 33100",
                            "venueCity": "Tampere"
                        }
                    }
                },
                "finalPrice": 16.0
            },
            {
                "ticketId": 8,
                "ticketCode": "44dd1683-d479-4241-817f-ef23c4f4541a",
                "ticketUsed": false,
                "ticketType": {
                    "ticketTypeId": 2,
                    "ticketTypeDescription": "Lastenlippu",
                    "price": 20.0,
                    "event": {
                        "eventId": 1,
                        "eventTitle": "Lady Gaga Live",
                        "eventDescription": "Lady Gaga, Monster Tour 2022",
                        "numberOfTickets": 3400,
                        "dateOfEvent": "17-04-2022 12:00",
                        "eventVenue": {
                            "venueId": 2,
                            "venueName": "Tampere-talo",
                            "venueAddress": "Yliopistonkatu 55, 33100",
                            "venueCity": "Tampere"
                        }
                    }
                },
                "finalPrice": 16.0
            }
        ]
    }
]
```
### Virheellinen response

**Ehto** : Jos url-parametrina annettua tapahtumaa ei löydy
**Code** : `404 NOT FOUND`
**Message** : `Cannot find an event with the id {id}`