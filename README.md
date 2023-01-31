# Kluisenaar
manage locker via discord



Deze repository bestaat uit twee onderdelen.
het ene deel is een Discord bot, te vinden in de map "KluisenaarBot". Het andere deel is De module in de locker(esp32), Te vinden is de src (source) map

Het doel van mijn IOT project is om midelware te maken om mijn locker te kunne beheren met discord.
Het beheren van de locker gaat om de volgende onderdelen:

1. In Discord moet er een bot komen die luistert naar het "/open" commando. als dit command is getypt door iemand die blij de locker mag, moet de locker openen.

2. Waarneer de Locker geopend word zonder dat dit de bedoeling is (Brute force), Moet de locker(esp32) een bericht terug kunnen sturen naar discord. Om aan te geven dat de locker onteautoriseerd geopend is.


de Pipeline werkt als volgt:
Discord(/open command) -> Husarnet(VPN) - > esp32(Open locker);
en
esp32(detecteerd ongeautoriseerde openen van de locker) -> IFTTT(https request naar Discord) -> Discord(Stuurt bericht dat locker geopend is)


## Discord Bot
Voor mijn IOT project heb ik een discord bot gemaakt. Deze bot is geschreven in java en draait op de raspberry pi. 
De Bot is verandwoordelijk voor de communicatie tussen de gebruiker en de locker.
Met een dsicordbot kan je commandos maken. Een van die comandos is "/open" waarneer de bot ziet dat dit getyped is, en het is getyped door een admin. dan zal de bot een verzoek sturen om de kluis te openen.

## De Locker(esp32)
De locker zelf bevat een esp32. een esp32 is een microcontroller met wifi. Deze esp word gebruikt voor twee dingen. 
1. Waarneer hij een verzoek krijgt van de discord bot om de locker te openen zet hij een relay aan en gaat de locker dus open (in de video word dit laten zien met het ledje op de relay). 
2. Waarneer hij ziet dat er via de ldr(light dependent risistor) te veel licht binnen komt stuurt hij een bericht naar de discord server dat de kluis ongeauthoriseerd geopend is.


## Communicatie 
#### http
voor de communicatie heb ik http requests gebruikt. Via http rewuest kan je data sturen op opvragen van of naar een server. 
Het voordeel van http is dat het makkelijk werkt over het internet.
het nadeel is dat http niet bevijligt is.
Om https(secure) te gebruiken moet je certeficaten over een weer sturen om je identiteit te bewijzen. Dit is mij nog niet gelukt
Om deze reden heb ik IFTTT(if this than that) gebruikt om een bericht terug naar de server te sturen. ITFFF staat toe http te ontvangen en maakt er zelf https van.
#### husarnet
De raspberry pi staat bij mij thuis, De locker staat op school. Om zede twee met elkaar te laten communiceren heb ik husarnet gebruikt.
Husarnet is een hele krachtige VPN die gebruikt maakt van de ipv6 Hole punching technick. Dit maakt het mogelijk om zonder port forwarding de esp32 te bereiken vanaf thuis.
Husarnet maakt een virtueel prive netwerk waar de raspberry pi en de esp32 in staan. Doordat ze beide in dit netwerk staan kan je http requests maken alsof je op je locale netwerk zit.
