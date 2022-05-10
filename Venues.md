# API/Venues

## Kaikkien tapahtumapaikkojen listaus

Listaa kaikki tietokannassa olevat tapahtumapaikat.

**URL** : `/api/venues`

**METHOD** : `GET`

**AUTHORIZATION** : `ROLE_ADMIN`, `ROLE_SALES`

### Onnistunut response

**Code** : `200 OK`

**Response** :

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

**URL** : `/api/venues/{id}`

**URL-PARAMETERS** : `{id}=[Long]` jossa {id} on tapahtumapaikan venueId tietokannassa. 

**METHOD** : `GET`

**AUTHORIZATION** : `ROLE_ADMIN`, `ROLE_SALES`

### Onnistunut response

**Code** : `200 OK`

**Response** :

```json
{
	"venueId":1,
	"venueName":"Sibeliustalo",
	"venueAddress":"Ankkurikatu 7, 15140",
	"venueCity":"Lahti"
}
```

### Epäonnistunut response

**Ehto** : Jos url-parametrina annettua tapahtumaa ei löydy

**Code** : `404 NOT FOUND`

**Message** : `Cannot find a venue with the id {id}`

## Tapahtumapaikan lisäys

Uuden tapahtumapaikan luonti ja lisäys tietokantaan.

**URL** : `/api/venues/`

**METHOD** : `POST`

**AUTHORIZATION** : `ROLE_ADMIN`

**REQUEST BODY** :

Paikan tiedot json-muodossa (poislukien id, joka on autogeneroidaan). Kentät venueName, venueAddress ja venueCity ovat kaikki **pakollisia**.

**Esimerkki** :

```json
{
	"venueName":"Kansalaistori",
	"venueAddress":"Alvar Aallon kuja 1, 00100",
	"venueCity":"Helsinki"
}
```

### Onnistunut response

**Code** : `201 Created`

**Response** :

```json
{
	"venueId":5,
	"venueName":"Kansalaistori",
	"venueAddress":"Alvar Aallon kuja 1, 00100",
	"venueCity":"Helsinki"
}
```

### Epäonnistunut response

**Ehto** : Jos pakollisia kenttiä puuttuu

**Code** : `400 BAD REQUEST`

**Message** : `"{pakollinen kenttä} must not be blank"`

## Tapahtumapaikan poisto

Yksittäisen tapahtumapaikan poisto tietokannasta. Paikan Id/primary key annetaan URL:ssa.

**URL** : `/api/venues/{id}`

**URL-PARAMETERS** : `{id}=[Long]` jossa {id} on paikan venueId tietokannassa. 

**METHOD** : `DELETE`

**AUTHORIZATION** : `ROLE_ADMIN`

### Onnistunut response

**Code** : `200 OK`

**Response** :

```json
{
    "message": "Deleted a venue with the id i{d}"
}
```

### Epäonnistunut response

**Ehto** : Jos url-parametrina annettua tapahtumaa ei löydy

**Code** : `404 NOT FOUND`

**Message** : `Cannot find a venue with the id {id}`

## Tapahtumapaikan muokkaus

Olemassa olevan tapahtumapaikan tietojen muokkaus.

**URL** : `/api/venues/{id}`

**URL-PARAMETERS** : `{id}=[Long]` jossa {id} on tapahtumapaikan eventId tietokannassa. 

**METHOD** : `PUT`

**AUTHORIZATION** : `ROLE_ADMIN`

**REQUEST BODY** :

Tapahtumapaikan tiedot json-muodossa(poislukien id, joka on autogeneroidaan). Kentät venueName, venueAddress ja venueCity ovat kaikki pakollisia.

**Esimerkki** :

```json
{
	"venueName":"Sibeliustalo",
	"venueAddress":"Uusi Ankkurikatu 7, 15140",
	"venueCity":"Suomen Chicago"
}
```

### Onnistunut response

**Code** : `200 OK`

**Response** :

```json
{
	"venueId":1,
	"venueName":"Sibeliustalo",
	"venueAddress":"Uusi Ankkurikatu 7, 15140",
	"venueCity":"Suomen Chicago"
}
```