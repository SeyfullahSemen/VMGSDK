**Project omschrijving**
In App Video (sdk / mraid)

Om advertenties in een app te vertonen is een mobile sdk nodig.

Als we een integratie met een app willen hebben zonder afhankelijk te zijn van externe SSPS moeten we een eigen SDK hebben zodat we zelf het beheer hebben van de inventory.

Implentatie van een app is meer werk dan een website, zaak is dus ook dat we de projecten inplannen en op de juiste manier en volgorde opleveren.

**Bericht van Tim**
Hey Seyfullah,

Hartstikke leuk dat je bij ons aan de slag gaat! Zoals je gehoord hebt ben ik 3 weken aan het luieren, maar aangezien ik ervaring heb met mobile development (wel iOS, dus in die termen schrijf ik ook), wilde ik je even mijn gedachten over de SDK mee geven ter inspiratie. Als je zelf een betere oplossing denkt te hebben kan dat! 
Het probleem van de SDK is dat je vanuit de webview (javascript) geen native methodes aan kan spreken en je daar dus creatief voor meot zijn. Vanuit de native code kan je op iOS wel javascript methodes aanroepen, ik weet niet hoe dit in Android zit maar ik verwacht het zelfde.

Wij willen vanuit de advertentie speler kunnen vertellen dat de native view uit moet klappen met een animatie. Om dit vanuit de advertentie speler aan te kunnen geven roepen we een URL aan met een custom URL schema, als event zeg maar, dus in plaats van http://www.google.com/ iets van vmgad://expand. De native webview gaat vervolgens proberen naar die URL te navigeren, alleen wordt dat eerst ondervangen door een stukje code die aan de delegate van de webview hangt, zoals deze methode: https://developer.apple.com/documentation/uikit/uiwebviewdelegate/1617945-webview
In dat stukje code check je of de URL het URL schema er in zit, als dat het geval zeg je tegen de webview dat ie niet naar die URL moet navigeren, en omdat je zie dat er in de URL 'expand' staat, weet je dat de advertentie speler klaar is met laden en dat de native view uitgeplakt moet worden. 
Dit expand event is maar 1 voorbeeld, in de toekomst komen er natuurlijk meer. Je kan ook eens kijk maar eens naar de MRAID specificaties, dat heeft Bert misschien ook al wel genoemd, dat is wat we uiteindelijk moeten bereiken.

**Fasering van het project voor Seyfullah door Bert**
* app1 apk opleveren met daarin een webview met een html5 video die automatisch gaat spelen
* app2 apk opleveren met daarin een webview die dichtgeklapt is en pas openklapt als de html5 video gaat spelen 
* app3 apk opleveren met onze ad-player erin
* app4 apk opleveren met mraid ondersteuning
* app5 apk opleveren met mraid ondersteuning die onze player aanstuurt


**Werkzaamneden vooronderzoek**
initiele bouw van SDK 2x versie 0.1 : 
webview met basic app interactie waar de werking wordt aangetoond versie 0.2 : interne exchange / ad-broker versie 1.0 beta : deployen op git met goeie documentatie testen versie 1.0 : verschillende view typen geïntegreerd en voorbeeld app proj // apk




**Achtergrond informatie Android SDK**

* https://www.iab.com/guidelines/mobile-rich-media-ad-interface-definitions-mraid/

**Voorbeelden van concurrenten**

* https://github.com/teads/TeadsSDK-iOS
* https://github.com/teads/TeadsSDK-android
* https://wiki.appnexus.com/display/sdk/MRAID+and+Mobile+Video+Tutorial
* http://support.adform.com/documentation/build-mobile-mraid-banners/mraid/



