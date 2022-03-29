# API/Venues

## Kaikkien tapahtumapaikkojen listaus

Listaa kaikki tietokannassa olevat tapahtumapaikat.

**AUTHORIZATION** : ADMIN, SALES

**URL** : `/api/venues`

**METHOD** : `GET`

### Onnistunut response

**Code** : `200 OK`

**Esimerkki**

```json
[
	{
	"venueId":1,
	"venueName":"Sibeliustalo",
	"venueAddress":"Ankkurikatu 7, 15140",
	"venueCity":"Lahti"
	},
	{
	"venueId":2,
	"venueName":"Tampere-talo",
	"venueAddress":"Yliopistonkatu 55, 33100",
	"venueCity":"Tampere"
	},
	{
	"venueId":3,
	"venueName":"Nokia Areena","venueAddress":"Sorinkatu 3, 33100",
	"venueCity":"Tampere"
	},
	{
	"venueId":4,
	"venueName":"Tavastia",
	"venueAddress":"Urho Kekkosen katu 4, 00100",
	"venueCity":"Helsinki"
	}
]
```

## Yksittäisen tapahtumapaikan tiedot

Näyttää yksittäisen tapahtumapaikan tiedot. Tapahtumapaikan Id/primary key annetaan URL:ssa.

**URL** : `/api/venues/:pk`

**URL-PARAMETERS** : `pk=[Long]` jossa pk on tapahtumapaikan venueId tietokannassa. 

**METHOD** : `GET`

### Onnistunut response

**Code** : `200 OK`

**Esimerkki**

```json
{
	"venueId":1,
	"venueName":"Sibeliustalo",
	"venueAddress":"Ankkurikatu 7, 15140",
	"venueCity":"Lahti"
}
```

### Virheellinen response

**Ehto** : Jos url-parametrina annettua tapahtumaa ei löydy

**Code** : `404 NOT FOUND`

**Message** : `Cannot find a venue with the id {id}`

## Tapahtumapaikan lisäys

Uuden tapahtumapaikan luonti ja lisäys tietokantaan.

**AUTHORIZATION** : ADMIN

**URL** : `/api/venues/`

**METHOD** : `POST`

**REQUEST BODY**
Paikan tiedot json-muodossa (poislukien id, joka on autogeneroidaan). Kentät venueName, venueAddress ja venueCity ovat kaikki pakollisia.

**Esimerkki**

```json
{
	"venueName":"Kansalaistori",
	"venueAddress":"Alvar Aallon kuja 1, 00100",
	"venueCity":"Helsinki"
}
```

### Onnistunut response

**Code** : `201 Created`

**Response body esim** Vastaus palauttaa tallennetun entityn

```json
{
	"venueId":5,
	"venueName":"Kansalaistori",
	"venueAddress":"Alvar Aallon kuja 1, 00100",
	"venueCity":"Helsinki"
}
```

## Tapahtumapaikan poisto

Yksittäisen tapahtumapaikan poisto tietokannasta. Paikan Id/primary key annetaan URL:ssa.

**AUTHORIZATION** : ADMIN

**URL** : `/api/venues/:pk`

**URL-PARAMETERS** : `pk=[Long]` jossa pk on paikan venueId tietokannassa. 

**METHOD** : `DELETE`

### Onnistunut response

**Code** : `200 OK`

**Response body esim**

```json
{
    "message": "Deleted a tickettype with the id 6"
}
```

## Tapahtumapaikan muokkaus

Olemassa olevan tapahtumapaikan tietojen muokkaus.

**AUTHORIZATION** : ADMIN

**URL** : `/api/venues/:pk`

**URL-PARAMETERS** : `pk=[Long]` jossa pk on tapahtumapaikan eventId tietokannassa. 

**METHOD** : `PUT`

**REQUEST BODY**
Tapahtumapaikan tiedot json-muodossa(poislukien id, joka on autogeneroidaan). Kentät venueName, venueAddress ja venueCity ovat kaikki pakollisia.

**Esimerkki**

```json
{
	"venueName":"Sibeliustalo",
	"venueAddress":"Ankkurikatu 7, 15140",
	"venueCity":"Suomen Chicago"
}
```

### Onnistunut response

**Code** : `200 OK`

**Response body esim** Vastaus palauttaa tallennetun entityn

```json
{
	"venueId":1,
	"venueName":"Sibeliustalo",
	"venueAddress":"Ankkurikatu 7, 15140",
	"venueCity":"Suomen Chicago"
}
```