# Documentatie  
Antwerp Has Fallen is een android applicatie die communiceert met een .NET core API. Deze beschikt dan ook over een MySQL database. In het begin hadden we de API en de database op Azure staan maar na wat problemen hiermee zijn we overgeschakelt naar Google Cloud Platform. We hadden eerst geprobeerd het op te lossen door gebruik te maken van IIS en zo de servers lokaal te kunnen draaien voor de app te kunnen testen maar 2 teamleden konden deze niet opstarten dus moesten we naar een ander alternatief zoeken en heeft meneer Peeters ons geholpen met het Google Cloud Platform op te zetten.  
## Google Cloud Platform  
## API  
Voor de android maps API moet er een project aangemaakt worden op het Google Cloud Platform, hier kan je dan de Maps SDK voor Android selecteren, deze activeren en een API-key genereren. Deze API-key moet dan in de AndroidManifest.xml van de android applicatie gestoken worden. daar staat een meta-data tag met een API_KEY in, deze waarde moet dan aangepast worden naar de nieuwe API-key.
## App  
Om de app en het spel op te starten zoals het nu is is er eigenlijk niet veel nodig, je kan gewoon de app opstarten en een game aanmaken. Als je dan in het menu links naar het "team" scherm gaat kan je zo het game-id terug vinden om deze dan door te geven aan de andere teams zodat ze dezelfde game kunnen joinen.  


