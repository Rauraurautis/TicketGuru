# API/Sales

## Lippuoston lisäys

Uuden ostetun lipun luonti.

**URL** : `/api/sales/`

**METHOD** : `POST`

**AUTHORIZATION** : `ROLE_ADMIN`, `ROLE_SALES`

**REQUEST BODY** :

Lipunmyyntitapahtuman tiedot json-muodossa **listana** (poislukien id, uniikki lippukoodi sekä lipuntarkistuksen OnkoLippuKäytetty-kenttä, jotka autogeneroidaan).

Käytettävät **peruskentät** ovat:

[long]'ticketTypeId', joka kuuluu valmiiksi tiettyyn tapahtumaan. **Pakollinen**

[int]'nrOfTickets', eli kyseisten myytyjen lipputyyppien määrä. **Pakollinen**, **Pienin sallittu numero on 1**

**Lisäkenttiä** ovat:

[int]'nrOfDiscounted', kyseessä olevien alennuslipputyyppien määrä kokonaisostomäärästä.

[BigDecimal]'discountPercentage', joka on alennusprosentti desimaalina. 0.1 vastaa 10 prosentin hinnanalennusta lipputyypin normaalihinnasta. **Desimaalierottaja annettava pisteenä!**

Nämä kaksi lisäkenttää myyntitapahtuman yhteydessä mahdollistavat kyseessä olevien lipputyyppien hinnanalennukset halutuille määrille ostettuja lippuja.

**Esimerkki ilman hinnanalennuskenttiä**:

```json
[
    {
        "ticketTypeId": 2,
        "nrOfTickets": 1
    },
    {
        "ticketTypeId": 3,
        "nrOfTickets": 4
    }
]
```

**Esimerkki hinnanalennuskenttien kanssa**:

```json
[
    {
        "ticketTypeId": 2,
        "nrOfTickets": 3,
        "nrOfDiscounted": 2,
        "discountPercentage": 0.2
    },
    {
        "ticketTypeId": 3,
        "nrOfTickets": 4,
        "nrOfDiscounted": 2,
        "discountPercentage": 0.1
    }
]
```

### Onnistunut response

**Code** : `201 CREATED`

**Response** :

```json
{
    "salesEventId": 2,
    "dateOfSale": "09-03-2022 00:26",
    "tickets": [
        {
            "ticketId": 2,
            "ticketCode": "e55e5170-6b72-40cc-93f7-fdfe6f9aed83",
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
            "ticketCode": "98b0i9ea-4551-44dc-96a0-6d1d46e8edaa",
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
            "ticketCode": "811edo43-ef7f-46bd-975f-816660g77d16",
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
            "ticketId": 5,
            "ticketCode": "272b8376-0643-44ca-b73c-73360g653425",
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
            "ticketId": 6,
            "ticketCode": "034307f9-aaf3-40d6-adbc-6b1d719a65b2",
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
```

### Virheellinen response

**Ehto** : Jos url-parametrina annettua tapahtumaa ei löydy

**Code** : `404 NOT FOUND`

**Message** : `Cannot find a tickettype with the id {Id}`

**Ehto** : Väärä lippumäärä

**Code** : `400 BAD REQUEST`

**Message** : `{nrOfTickets} must be greater than or equal to 1`
