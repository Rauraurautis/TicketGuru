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

Kirjautumisen onnistuttua käyttäjä saa takaisin **access_tokenin** sekä **refresh_tokenin**. API käyttää access_tokenia käyttäjän tunnistamisessa jokaisessa käyttäjän tekemässä pyynnössä ja refresh_tokenia uuden access_tokenin saamisessa.

**Esimerkki**:
```json
{
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtX3NtaXRoIiwicm9sZXMiOlsiUk9MRV9USUNLRVRJTlNQRUNUT1IiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvZ2luIiwiZXhwIjoxNjQ4NDgyMzYxfQ.S0sRQMarL0Xgv5L2nvBFTbsU88awNTKh20LCj8D3SFs",
    "refresh_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2huIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvZ2luIiwiZXhwIjoxNjQ4NjAxMzU5fQ.Bhm_Ilii6Y3raX-prJNaFLUeX4fefcAU1_CQp9AC1qs"
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

**URL:** `/api/token/refresh`
**METHOD:** `GET`

Jotta käyttäjä saa uuden tokenin käyttöön ilman uudelleenkirjautumista järjestelmään pitää aiemmin saatu **refresh_token** liittää edellämainittuun tapaan Authentication-headeriin ja tehdä GET-pyyntö yllämainittuun urliin. Vastauksena tulee uusi access token, joka liitetään uudelleen headeriin Bearer-tyyppisenä.

```json
{
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtX3NtaXRoIiwicm9sZXMiOlsiUk9MRV9USUNLRVRJTlNQRUNUT1IiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvZ2luIiZXhwIhjjoxNjQ4NDgyMzYxfQ.S0sRQMahrL0Xg5L2nvfgBFTbsU88awNTKfh20LCj8D3SgFs",
    "refresh_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2huIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvZ2luIiwiZXhwIjoxNjQ4NjAxMzU5fQ.Bhm_Ilii6Y3raX-prJNaFLUeX4fefcAU1_CQp9AC1qs"
}
```

## Epäonnistunut response

**Code** : `400 Bad Request`

Jos token puuttuu tai kirjautuminen epäonnistuu virheellisen käyttäjänimen tai salasanan vuoksi saa käyttäjä seuraavan virheilmoituksen

```json
{
    "Error": "Missing token or the password / username was false"
}
```