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

# Viikko 6 Room Käyttöön otto WeatherAppissä

#### Roomin toiminta
Roomin arkkitehtuuri kostuu seuraavista:
Entity: joka tallentaa haetut asiat ja haetun sään
DAO: Sisältää SQL-kyselyt ja palauttaa Flow-dataa UIlle
Database: on Room tietokannan pääluokka, toimii singleton instanssina
Repository: Yhdistää Roomin ja RetrofitAPIn toiminnan, toimii välimuistina ja rajapintana
ViewModel: Sisältää StateFlown ja Kutsuu Repositorya
UI: Tarkkailee Viewmodelin Flow-dataa ja näyttää säädatan sekä hakuhistorian. Kutsuu viewModelin metodeja.

#### Projektin Rakenne
<img width="298" height="792" alt="image" src="https://github.com/user-attachments/assets/ffe92539-2702-4817-864a-661df750ad17" />

Projekti noudattaa MVVM mallia

#### Datan kulku
1. Käyttähä syöttää kaupungin ja painaa hae
2. UI kutsuu viewModelin SearchWeater funktiota
3. ViewModel kutsuu repositoryn fetchWeatherIfNeeded funktiota.
4. Repository tarkistaa aluksi löytyykö kyseinen data Roomista, jos ei ole roomissa se haetaan APIsta, tallennetaan tulos Roomiin
5. Room lähettää uuden arvon Flow'n kautta
6. UI päivittyy automaattisesti CollectAsState() avulla

#### Välimuistinlogiikka
Kaupungit tallenetaan välimuistiin, jos datan hausta on yli 30 minuuttia se hakee datan uudestaan APIsta. Jos ei ole olemassa olevaa dataa se haetaan APIsta. Muissa tapauksissa kuin näissä käytetään Roomissa olemassa olevaa dataa. Tämän lisäksi jokainen tehty haku tallennetaan hakuhistoriaan.

#### Videolinkki
https://www.youtube.com/watch?v=ON5VCmqOGJE
