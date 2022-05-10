# Security

## API:n securityn toteutus

API hyödyntää **JWT** (JSON Web Token)-teknologiaa käyttäjien autentikoimiseen ja autorisoimiseen.

## Järjestelmään kirjautuminen

**URL** : `/login`
**METHOD** : `POST`
**REQUEST BODY** : Järjestelmään kirjautuminen tapahtuu lähettämällä request bodyssä **JSON-muotoinen** pyyntö jossa on käyttäjän tunnus (**username**) sekä käyttäjän salasana (**password**).

**Esimerkki** :

```json
{
    "username": "tunnus",
    "password": "salasana"
}
```

### Onnistunut response

**Code** : `200 OK`

Kirjautumisen onnistuttua käyttäjä saa takaisin **access_tokenin** sekä **refresh_tokenin**. API käyttää access_tokenia käyttäjän tunnistamisessa jokaisessa käyttäjän tekemässä pyynnössä ja refresh_tokenia uuden access_tokenin saamisessa.

**Response** :

```json
{
    "access_token": "eiJ0eXAiOiJKV1QiLCJhbGahgafdzI1NiJ9.eyJzdWIiOiJtX3NtaXRoIidicm9sZXMiOlsiUk9MRV9USUNLRVRJTlNQRUNUT1IiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvZ2luIiwiZXhwIjoxNjQ4NDgyMzYxfQ.S0sRQMarL0Xgv5L2nvBFTbsU88awNTKh20LCj8D3SFs",
    "refresh_token": "eiJ0eXAiOiJKV1QiLCJhbGchfdkJIUzI1NiJ9.eyJzdWIiOiJKb2huIiliaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvZ2luIiwiZXhwIjoxNjQ4NjAxMzU5fQ.Bhm_Ilii6Y3raX-prJNaFLUeX4fefcAU1_CQp9AC1qs"
}
```

Käyttäjän saatuaan access token tulee se liittää pyyntöjä tekevän käyttäjän Authorization-headeriin. Token on tyyppiä Bearer.

![Tokenin lisäys](assets/images/authimg.png)

Nyt pyyntöjä tekevä käyttäjä pääsee käsiksi hänelle määrätyn roolin perusteella sallittuihin endpointteihin. **Access_okenin voimassaoloaika on 30 minuuttia ja refresh_tokenin 1 päivä.**

## Access_tokenin vanheneminen

Access tokenin vanhennuttua käyttäjän yrittäessä tehdä pyyntöä resurssiin hän saa seuraavanlaisen virheilmoituksen

```json
{
    "error_message": "The Token has expired on Wed Mar 30 02:52:47 EEST 2022."
}
```

## Access_tokenin päivittäminen

**URL** : `/api/token/refresh`
**METHOD** : `GET`

Jotta käyttäjä saa uuden tokenin käyttöön ilman uudelleenkirjautumista järjestelmään pitää aiemmin saatu **refresh_token** liittää edellämainittuun tapaan Authentication-headeriin ja tehdä GET-pyyntö yllämainittuun urliin. Vastauksena tulee uusi access token, joka liitetään uudelleen headeriin Bearer-tyyppisenä.

```json
{
    "access_token": "eiJ0eXAiOiJKV1QiLCJhbGcoRlJIUzI1NiJ9.eyJzdWIiOiJtX3NtaXRoIiwicm9sZXMiOlsiUk9MRV9USUNLRVRJTlNQRUNUTT1IiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvZ2luIiZXhwIhjjoxNjQ4NDgyMzYxfQ.S0sRQMahrL0Xg5L2nvfgBFTbsU88awNTKfh20LCj8D3SgFs",
    "refresh_token": "eiJ0eXAiOiJKV1QiLCJhbFokOiJIUzI1NiJ9.eyJzdWIiOiJKb2huIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvH2luIiwiZXhwIjoxNjQ4NjAxMzU5fQ.Bhm_Ilii6Y3raX-prJNaFLUeX4fefcAU1_CQp9AC1qs"
}
```

### Epäonnistunut response

**Ehto** : Jos token puuttuu tai kirjautuminen epäonnistuu virheellisen käyttäjänimen tai salasanan vuoksi saa käyttäjä seuraavan virheilmoituksen
**Code** : `400 Bad Request`
**Response** :

```json
{
    "Error": "Missing token or the password / username was false"
}
```

## Käyttäjien hakeminen

**URL** : `/api/users`
**METHOD** : `GET`
**AUTHORIZATION** : `ROLE_ADMIN`

### Onnistunut response

**Code** : `200 OK`
**Response** :

```json
[
    {
        "id": 1,
        "name": "Joku AdminNimi",
        "username": "JokuA",
		"password": "kryptattu_salasana_tyyppiä_$2a$10$290taumcla983yu5la8c...",
        "roles": [
            {
                "id": 1,
                "name": "ROLE_ADMIN"
            }
        ]
    },
    {
        "id": 2,
        "name": "Joku Myyjänimi",
        "username": "JokuMyyja",
		"password": "kryptattu_salasana_tyyppiä_$2a$10$290taumcla983yu5la8c...",
        "roles": [
            {
                "id": 2,
                "name": "ROLE_SALES"
            }
        ]
    }
]
```

## Yksittäisen käyttäjän hakeminen

**URL** : `/api/users/{username}`
**METHOD** : `POST`
**AUTHORIZATION** : `ROLE_ADMIN`

### Onnistunut response

**Code** : `200 OK`
**Response** :

```json
{
    "id": 1,
    "name": "Joku AdminNimi",
    "username": "JokuA",
	"password": "kryptattu_salasana_tyyppiä_$2a$10$290taumcla983yu5la8c...",
    "roles": [
        {
            "id": 1,
            "name": "ROLE_ADMIN"
        }
    ]
}
```

## Käyttäjän lisääminen

**URL** : `/api/users`
**METHOD:** `POST`
**AUTHORIZATION** : `ROLE_ADMIN`
**REQUEST BODY** : Järjestelmään lisätään käyttäjä ja tälle roolit lähettämällä request bodyssä **JSON-muotoinen** pyyntö jossa on pakollisina kenttinä käyttäjän nimi (**name**), käyttäjän salasana (**password**) sekä tunnus (**username**).

**Esimerkki** :

```json
{
    "name": "Jaakko Uusityyppi",
	"password": "jokusalasana",
    "username": "Jaakko"
}
```

### Onnistunut response

**Code** : `201 OK`
**Response** :

```json
{
    "id": 6,
    "name": "Jaakko Uusityyppi",
    "username": "Jaakko",
	"password": "kryptattu_salasana_tyyppiä_$2a$10$290taumcla983yu5la8c...",
    "roles": []
}
```

### Epäonnistunut response

**Ehto** : Jos salasana puuttuu
**Code** : `403 Bad Request`
**Response** :

```json
{
    "error_message": "Request processing failed; nested exception is java.lang.IllegalArgumentException: rawPassword cannot be null"
}
```

## Yksittäisen käyttäjän poistaminen

**URL** : `/api/users/{id}`
**METHOD** : `DELETE`
**AUTHORIZATION** : `ROLE_ADMIN`

### Onnistunut response

**Code** : `200 OK`
**Response** :

```json
{
    "message": "Deleted a user with the id {id}"
}
```

### Epäonnistunut response

**Ehto** : Jos käyttäjäid:tä {id} ei ole olemassa
**Code** : `404 NOT FOUND`
**Response** :

```json
{
    "timeStamp": "2022-03-10T02:15:30...",
    "httpStatus": "NOT_FOUND",
    "message": "Cannot find a user with the id {id}"
}
```

## Käyttäjäroolin lisääminen järjestelmään

**URL** : `/api/role/`
**METHOD** : `POST`
**AUTHORIZATION** : `ROLE_ADMIN`
**REQUEST BODY** : Järjestelmään lisätään rooli tekemällä **JSON-muotoinen** pyyntö jossa on pakollisena kenttänä roolin nimi (**name**). Alkuperäiset käyttäjäroolit ovat järjestelmässä muodossa ROLE_ADMIN, ROLE_SALES JA ROLE_TICKETINSPECTOR, mutta nimeämistapa ei ole pakottava.

**Esimerkki** :

```json
{
    "name": "ROLE_PROMOTOR"
}
```

### Onnistunut response

**Code** : `201 CREATED`
**Response** :

```json
{
    "id": 5,
    "name": "ROLE_PROMOTOR"
}
```

## Käyttäjäroolin lisääminen käyttäjälle {USER}

**URL** : `/api/role/addtouser`
**METHOD** : `POST`
**AUTHORIZATION** : `ROLE_ADMIN`
**REQUEST BODY** : Käyttäjälle lisätään rooli tekemällä **JSON-muotoinen** pyyntö jossa on pakollisena kenttänä käyttäjätunnus (**username**) sekä käyttäjäroolin nimi (**rolename**).

**Esimerkki** :

```json
{
    "username": "Jaakko",
    "rolename": "ROLE_PROMOTOR"
}
```

### Onnistunut response

**Code** : `200 OK`
**Response** :

```json

```

## Käyttäjäroolin poistaminen käyttäjältä {USER}

**URL** : `/api/role/removefromuser`
**METHOD** : `DELETE`
**AUTHORIZATION** : `ROLE_ADMIN`
**REQUEST BODY** : Käyttäjältä poistetaan rooli tekemällä **JSON-muotoinen** delete-pyyntö jossa on pakollisena kenttänä käyttäjätunnus (**username**) sekä käyttäjäroolin nimi (**rolename**).

**Esimerkki** :

```json
{
    "username": "Jaakko",
    "rolename": "ROLE_PROMOTOR"
}
```

### Onnistunut response

**Code** : `200 OK`
**Response** :

```json
{
    "message": "Deleted role ROLE_PROMOTOR from user Jaakko"
}
```

### Epäonnistunut response

**Ehto** : Jos käyttäjää {username} ei ole olemassa
**Code** : `404 NOT FOUND`
**Response** :

```json
{
    "timeStamp": "2022-03-10T02:15:30...",
    "httpStatus": "NOT_FOUND",
    "message": "User not found in the database"
}
```

**Ehto** : Jos roolia {rolename} ei ole olemassa
**Code** : `404 NOT FOUND`
**Response** :

```json
{
    "timeStamp": "2022-03-10T02:15:30...",
    "httpStatus": "NOT_FOUND",
    "message": "Role not found in the database"
}
```
