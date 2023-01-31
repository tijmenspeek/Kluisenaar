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
esp32(detecteerd ongeauthoriseerde openen van de locker) -> IFTTT(https request naar Discord) -> Discord(Stuurt bericht dat locker geopend is)
