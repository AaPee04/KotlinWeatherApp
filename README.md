# Viikko 5 KotlinWeatherApp Apin Avulla

#### Retrofit
Retrofit on HTTP-kirjasto, jonka avulla REST-rajapintoja on helpompi käyttää Android-sovelluksessa. Retrofit määrittelee API-kutsut rajapintana ja hoitaa HTTP-pyyntöjen käsittelyyn.
#### HTTP-pyyntöjen hallinta
HTTP-pyyntöjä määritellään annotaatioilla kuten @GET ja @Query. Retrofit muodstaa URL-osoitteen annetuista parametreista ja lähettää pyynnön API:lle
#### JSON dataluokiksi
API palauttaa vastaukset tiedoista JSON-muodossa, Kotlin muuttaa sen Gsonin avulla Kotlin Data Classiksi. API kutsuu kotlin coroutines mekanismia taustasäikeestä. Tämä estää käyttöliittymää jäätymästä verkko-operaation aikana.
Kun vastaus saadaan coroutine päivittää UI-tilan, jolloin Compose päivittää näkymän.
#### UI-tila
viewModel hallitsee sovelluksen tilaa StateFlow-objektin avulla. Tila voi olla esimerkiksi loading, succes tai error. Compose kuuntelee tilaa ja reagoi automaattisesti näihin muutoksiin. UI-päivittyy ilman mitää erikseen olevaa päivityslogiikkaa.
#### API avaimen tallennus
Api avain on tallennettu LocalProperties ja sitä kutsutaan Gradlessä, BuildConfig tilassa. weatherRepository sitten hakee API avaimen BuildConfigista jossa kutsutaan API avainta.
