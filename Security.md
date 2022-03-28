# Security

## API:n securityn toteutus

API hyödyntää **JWT** (JSON Web Token)-teknologiaa käyttäjien autentikoimiseen ja autorisoimiseen.

## Järjestelmään kirjautuminen

**URL:** `/login`
**METHOD:** `POST`
**REQUEST BODY:** Järjestelmään kirjautuminen tapahtuu lähettämällä request bodyssä **JSON-muotoinen** pyyntö jossa on käyttäjän tunnus (**username**) sekä käyttäjän salasana (**password**).

**Esimerkki**:

```json
{
    "username": "tunnus",
    "password": "salasana"
}
```

## Onnistunut response

**Code** : `200 OK`

Kirjautumisen onnistuttua käyttäjä saa takaisin **access_tokenin**. API käyttää tätä tokenia käyttäjän tunnistamisessa jokaisessa käyttäjän tekemässä pyynnössä.

**Esimerkki**:
```json
{
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtX3NtaXRoIiwicm9sZXMiOlsiUk9MRV9USUNLRVRJTlNQRUNUT1IiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvZ2luIiwiZXhwIjoxNjQ4NDgyMzYxfQ.S0sRQMarL0Xgv5L2nvBFTbsU88awNTKh20LCj8D3SFs"
}
```

Käyttäjän saatuaan access token tulee se liittää pyyntöjä tekevän käyttäjän Authorization-headeriin. Token on tyyppiä Bearer.

![Tokenin lisäys](assets/images/authimg.png)

Nyt pyyntöjä tekevä käyttäjä pääsee käsiksi hänelle määrätyn roolin perusteella sallittuihin endpointteihin. **Tokenin voimassaoloaika on 30 minuuttia.**

## Epäonnistunut response

**Code** : `400 Bad Request`

Jos kirjautuminen epäonnistuu virheellisen käyttäjänimen tai salasanan vuoksi saa käyttäjä seuraavan virheilmoituksen

```json
{
    "Error": "Wrong password or username"
}
```