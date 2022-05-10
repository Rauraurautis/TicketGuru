# API/Events

## Kaikkien tapahtumien listaus

Listaa kaikki tietokannassa olevat tapahtumat.

**URL** : `/api/events`

**METHOD** : `GET`

**AUTHORIZATION** : `ROLE_ADMIN`, `ROLE_SALES`

### Onnistunut response

**Code** : `200 OK`

**Response** :

```json
[
    {
        "eventId": 1,
        "eventTitle": "Lady Gaga Live",
        "eventDescription": "Lady Gaga, Monster Tour 2022",
        "numberOfTickets": 3400,
        "dateOfEvent": "17-04-2022 21:00",
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
        "dateOfEvent": "19-06-2022 12:00",
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

## Tulevien tapahtumien listaus

Listaa kaikki tulevat tapahtumat tietokannasta.

**URL** : `/api/events/upcoming`

**METHOD** : `GET`

**AUTHORIZATION** : `ROLE_ADMIN`, `ROLE_SALES`

### Onnistunut response

**Code** : `200 OK`

**Response** : Sama kuin kaikkien tapahtumien listaus, mutta näyttää vain tulevat tapahtumat

## Menneiden tapahtumien listaus

Listaa kaikki menneet tapahtumat tietokannasta.

**URL** : `/api/events/past`

**METHOD** : `GET`

**AUTHORIZATION** : `ROLE_ADMIN`, `ROLE_SALES`

### Onnistunut response

**Code** : `200 OK`

**Response** : Sama kuin kaikkien tapahtumien listaus, mutta näyttää vain menneet tapahtumat

## Yksittäisen tapahtuman tietojen haku

Näyttää yksittäisen tapahtuman tiedot. Tapahtuman Id/primary key annetaan URL:ssa.

**URL** : `/api/events/{id}`

**URL-PARAMETERS** : `{id}=[Long]` jossa {id} on tapahtuman eventId tietokannassa.

**METHOD** : `GET`

**AUTHORIZATION** : `ROLE_ADMIN`, `ROLE_SALES`

### Onnistunut response

**Code** : `200 OK`

**Response** :

```json
{
    "eventId": 1,
    "eventTitle": "Lady Gaga Live",
    "eventDescription": "Lady Gaga, Monster Tour 2022",
    "numberOfTickets": 3400,
    "dateOfEvent": "17-04-2022 21:00",
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

### Epäonnistunut response

**Ehto** : Jos url-parametrina annettua tapahtumaa ei löydy

**Code** : `404 NOT FOUND`

**Response** :

```json
{
    "timeStamp": "2022-03-15T23:00:59.5869555",
    "httpStatus": "NOT_FOUND",
    "message": "Cannot find an event with the id {id}"
}
```

## Tapahtuman lisäys

Uuden tapahtuman luonti ja lisäys tietokantaan.

**URL** : `/api/events/`

**METHOD** : `POST`

**AUTHORIZATION** : `ROLE_ADMIN`

**REQUEST BODY** :

Tapahtuman tiedot **JSON-muotoinen** Ainoa pakollinen kenttä on **eventTitle**. Kenttä **numberOfTickets** täytyy olla 0 tai suurempi. Tyhjäksi jätetyt kentät tallentuvat null-arvoina odottamaan päivitystä.

Tapahtuman tapahtumapaikka annetaan sen id:nä(venueId) muodossa: `"eventVenue":{"venueId": {id}}`

Tapahtuman päivämäärä tulee antaa muodossa: `"dateOfEvent":"päivä-kuukausi-vuosi tunti:minuutti"`

**Esimerkki** :

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

**Response** :

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

### Epäonnistunut response

**Ehto** : Jos json:ssa annettua eventVenue:ta ei löydy

**Code** : `404 NOT FOUND`

**Response** :

```json
{
    "timeStamp": "2022-03-15T23:00:59.5869555",
    "httpStatus": "NOT_FOUND",
    "message": "Cannot find a venue with the id {id}"
}
```

## Tapahtuman poisto

Yksittäisen tapahtuman poisto tietokannasta. Tapahtuman Id/primary key annetaan URL:ssa. Poistaa samalla tapahtuman lipputyypit.

**Mikäli tapahtumaan on jo myyty lippuja**, sitä ei voi enää poistaa järjestelmästä, jotta kirjanpito ei kärsi.

**URL** : `/api/events/{id}`

**URL-PARAMETERS** : `{id}=[Long]` jossa {id} on tapahtuman eventId tietokannassa.

**METHOD** : `DELETE`

**AUTHORIZATION** : `ROLE_ADMIN`

### Onnistunut response

**Code** : `200 OK`

**Response** :

```json
{
    "message": "Deleted an event with the id {id}"
}
```

### Epäonnistunut response

**Ehto** : Jos annettua tapahtumaa ei löydy

**Code** : `404 NOT FOUND`

**Response** :

```json
{
    "timeStamp": "2022-03-15T23:00:59.5869555",
    "httpStatus": "NOT_FOUND",
    "message": "Cannot find a venue with the id {id}"
}
```

**Ehto** : Jos tapahtumaan on jo myyty lippuja

**Code** : `500 INTERNAL SERVER ERROR`

**Response** : Geneerinen virheviesti

```json
{
    "message": "500 INTERNAL_SERVER_ERROR: Something went wrong"
}
```

## Tapahtuman muokkaus

Olemassa olevan tapahtuman tietojen muokkaus.

**URL** : `/api/events/{id}`

**URL-PARAMETERS** : `{id}=[Long]` jossa {id} on tapahtuman eventId tietokannassa.

**METHOD** : `PUT`

**AUTHORIZATION** : `ROLE_ADMIN`

**REQUEST BODY** :

PAKOLLISENA kenttänä eventTitle, kuten tapahtuman luonnissakin, MUTTA puuttuvat kentät muuttuvat nulliksi jos ovat tyhjiä. Venuen jäädessä tyhjäksi se ei muutu. Lipputyyppejä ei tule antaa, ne muokataan muualta.

**Tapahtuman päivämäärä tulee antaa muodossa:** `"dateOfEvent":"päivä-kuukausi-vuosi tunti:minuutti"`

**Esimerkki** :

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

**Response** :

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

### Epäonnistunut response

**Ehto** : Jos url-parametrina annettua tapahtumaa ei löydy

**Code** : `404 NOT FOUND`

**Message** : `Cannot find an event with the id {id}`

## Tapahtumaan myytävien lipputyyppien tietojen haku

Näyttää yksittäisen tapahtuman lipputyyppien tiedot. Tapahtuman Id/primary key annetaan URL:ssa.

**URL** : `/api/events/{id}/tickettypes`

**URL-PARAMETERS** : `{id}=[Long]` jossa {id} on tapahtuman eventId tietokannassa.

**METHOD** : `GET`

**AUTHORIZATION** : `ROLE_ADMIN`, `ROLE_SALES`

### Onnistunut response

**Code** : `200 OK`

**Response** :

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
            "dateOfEvent": "17-04-2022 21:00",
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
            "dateOfEvent": "17-04-2022 21:00",
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

### Epäonnistunut response

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

**AUTHORIZATION** : `ROLE_ADMIN`

**REQUEST BODY** :

Lipputyypin tiedot annetaan json-muodossa(poislukien id, joka autogeneroidaan).

Tietokentät:

[String] ticketTypeDescription **Pakollinen**

[BigDecimal] price	**Desimaalierottaja annettava pisteenä!** **Arvo ei saa olla negatiivinen luku.** **Vapaaehtoinen kenttä, joka defaulttaa 0.0:ksi.**

**Esimerkki** :

```json
{
	"ticketTypeDescription":"Opiskelijalippu",
	"price":10.50
}
```

### Onnistunut response

**Code** : `201 Created`

**Response** :

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
        "dateOfEvent": 14-03-2024 21:00,
        "eventVenue": {
            "venueId": 4,
            "venueName": "Tavastia",
            "venueAddress": "Urho Kekkosen katu 4, 00100",
            "venueCity": "Helsinki"
        }
    }
}
```

### Epäonnistunut response

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

**URL** : `/api/events/{id}/tickettypes/{id}`

**URL-PARAMETERS** : `{id}=[Long]` jossa ensimmäinen {id} on tapahtuman eventId tietokannassa ja toinen {id} lipputyypin ticketTypeId.

**METHOD** : `DELETE`

**AUTHORIZATION** : `ROLE_ADMIN`

### Onnistunut response

**Code** : `200 OK`

**Response** :

```json
{
    "message": "Deleted a tickettype with the id 6"
}
```

### Epäonnistunut response

**Ehto** : Jos url-parametrina annettua tapahtumaa ei löydy

**Code** : `404 NOT FOUND`

**Message** : `Cannot find an event with the id {id}`


**Ehto** : Jos url-parametrina annettua lipputyyppiä ei löydy

**Code** : `404 NOT FOUND`

**Message** : `Cannot find a tickettype with the id {id}`

## Lipputyypin muokkaus

Tapahtumassa olevan lipputyypin tietojen muokkaus. Tapahtuman Id/primary key annetaan URL:ssa sekä lipputyypin Id/pk annetaan URL:ssa.

**URL** : `/api/events/{id}/tickettypes/{id}`

**URL-PARAMETERS** : `{id}=[Long]` jossa ensimmäinen {id} on tapahtuman eventId tietokannassa ja toinen {id} lipputyypin ticketTypeId.

**METHOD** : `PUT`

**AUTHORIZATION** : `ROLE_ADMIN`

**REQUEST BODY** :

Tietokentät:

[String] ticketTypeDescription **Pakollinen**

[BigDecimal] price	**Pakollinen kenttä TAI se defaulttaa 0.0:ksi!** **Desimaalierottaja annettava pisteenä!** **Arvo ei saa olla negatiivinen luku.** 

**Esimerkki** :

```json
{
	"ticketTypeDescription":"Muutoslippu",
	"price":5.50
}
```

### Onnistunut response

**Code** : `200 OK`

**Response** :

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
        "dateOfEvent": 19-08-2022 21:00,
        "eventVenue": {
            "venueId": 4,
            "venueName": "Tavastia",
            "venueAddress": "Urho Kekkosen katu 4, 00100",
            "venueCity": "Helsinki"
        }
    }
}
```
### Epäonnistunut response

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

## Tapahtumaan ostetun yksittäisen lipun haku

Näyttää yksittäisen tapahtuman yhden lipun tiedot. Lipun ticketcode annetaan url-parametrina. **Parametri pakollinen**.

**URL** : `/api/events/singleticket?ticketcode={ticketcode}`

**URL-PARAMETERS** : `{ticketcode}=[String]` lipun yksilöllinen ticketcode. Mallityyppiä: e45e9930-6b72-41dd-93d7-fdlu6f9aof83

**METHOD** : `GET``

**AUTHORIZATION** : `ROLE_ADMIN`, `ROLE_SALES`, `ROLE_TICKETINSPECTOR`

### Onnistunut response

**Code** : `200 OK`

**Response** :

```json
{
    "ticketId": 2,
    "ticketCode": "e55e5130-6b72-41cc-93d7-fdhe6f9aed83",
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

### Epäonnistunut response

**Ehto** : Jos url-parametrina annettua lippua ei löydy

**Code** : `404 NOT FOUND`

**Message** : `Cannot find a ticket with the id {id}`

## Tapahtumaan ostetun lipun muuttaminen käytetyksi

Muuttaa aiemmin ostetun lipun ticketUsed-kentän arvoksi True. Default on False.

**URL** : `/api/events/tickets`

**METHOD** : `PUT`

**AUTHORIZATION** : `ROLE_ADMIN`, `ROLE_SALES`, `ROLE_TICKETINSPECTOR`

**REQUEST BODY** :

**Pakollisena** ostetun lipun uniikki lippukoodi [String]ticketCode, joka on tyyliltään seuraavaa muototyyppiä: "e55e5130-6b72-40cc-93d7-fdfe6f9aed83".

[boolean]ticketUsed ei tarvita, eikä sitä voi enää muuttaa takaisin falseksi, jos se on true (väärinkäytöksien estämiseksi).

**Esimerkki** :

```json
{
	"ticketCode":"e55e5130-6b72-40cc-93d7-fdfe6f9aed83"
}
```

### Onnistunut response

**Code** : `200 OK`

**Response** :

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
            "dateOfEvent": 19-08-2022 21:00,
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

### Epäonnistunut response

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

**URL** : `/api/events/{id}/salesevents`

**URL-PARAMETERS** : `{id}=[Long]` jossa {id} on tapahtuman eventId tietokannassa. 

**METHOD** : `GET`

**AUTHORIZATION** : `ROLE_ADMIN`, `ROLE_SALES`

### Onnistunut response

**Code** : `200 OK`

**Response** :

```json
[
    {
        "salesEventId": 2,
        "dateOfSale": "09-03-2022 00:26",
        "tickets": [
            {
                "ticketId": 2,
                "ticketCode": "e55e5130-6b72-40cc-93d7-fdfe6f9aed83",
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
                "ticketType": {
                    "ticketTypeId": 3,
                    "ticketTypeDescription": "Lastenlippu",
                    "price": 20.0,
                    "event": {
                        "eventId": 2,
                        "eventTitle": "FrÃ¶belin Palikat Live",
                        "eventDescription": "FrÃ¶belin Palikat, Never Stop The Madness",
                        "numberOfTickets": 250,
                        "dateOfEvent": 19-08-2022 21:00,
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

### Epäonnistunut response

**Ehto** : Jos url-parametrina annettua tapahtumaa ei löydy

**Code** : `404 NOT FOUND`

**Message** : `Cannot find an event with the id {id}`
