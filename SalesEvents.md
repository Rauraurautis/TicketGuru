# API/SalesEvents

## Kaikkien myyntitapahtumien listaus

Listaa kaikki tietokannassa olevat myyntitapahtumat.

**URL** : `/api/salesevents`

**METHOD** : `GET`

**AUTHORIZATION** : `ROLE_ADMIN`, `ROLE_SALES`

### Onnistunut response

**Code** : `200 OK`

**Esimerkki**

```json
[
    {
        "salesEventId": 1,
        "dateOfSale": "09-03-2022 00:25",
        "tickets": [
            {
                "ticketId": 1,
                "ticketCode": "a564cf34-2e0b-4863-b2d0-81b99f4cfb90",
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
        ]
    },
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
                "ticketId": 13,
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
    }
]
```

## Yksittäisen myyntitapahtuman haku

Näyttää yksittäisen myyntitapahtuman tiedot. Myyntitapahtuman Id/primary key annetaan URL:ssa.

**URL** : `/api/salesevents/{id}`

**URL-PARAMETERS** : `{id}=[Long]` jossa {id} on myyntitapahtuman eventId tietokannassa. 

**METHOD** : `GET`

**AUTHORIZATION** : `ROLE_ADMIN`, `ROLE_SALES`

### Onnistunut response

**Code** : `200 OK`

**Esimerkki**

```json
{
    "salesEventId": 1,
    "dateOfSale": "09-03-2022 00:25",
    "tickets": [
        {
            "ticketId": 1,
            "ticketCode": "a564cf34-2e0b-4863-b7d0-81b99f4cfb90",
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
    ]
}
```

### Epäonnistunut response

**Ehto** : Jos url-parametrina annettua myyntitapahtumaa ei löydy

**Code** : `404 NOT FOUND`

**Message** : `Cannot find a sales event with the id {id}`
