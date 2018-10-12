# Antwerp Has Fallen!

## Verhaal en beschrijving:
*Door een mislukt experiment in het MLK (centrum voor medische experimenten) is er een schadelijke stof vrijgekomen in Antwerpen, degene die besmet zijn hebben nog maar een paar uur te leven. De laatste groepen die niet besmet zijn moeten dus zo snel mogelijk aan een tegengif geraken of uit de stad geraken voor ze zelf besmet zijn. Ze moeten onderweg naar bepaalde gebieden gaan om de ingrediënten te verzamelen voor een tegengif OF opdrachtjes doen om geld te verdienen daar. Ze mogen wel maar een bepaalde tijd in dat gebied blijven of ze raken zelf besmet! Ze mogen met hun geld wel een medicament voor zichzelf aankopen waardoor ze zelf langer in een gebied kunnen blijven of ze kunnen het andere team saboteren, zodat dit team tijd verliest. Het team dat als eerste alle ingrediënten heeft om een tegengif te maken wint!* <br>
Het spel begint met alle teams op een centrale plaats en van daaruit wordt elk team naar een andere locatie gestuurd waar ze dan opdrachten moeten oplossen. Deze opdrachten zijn vragen over de locatie, het antwoord is dan vaak te vinden op infopanelen of standbeelden e.d. ofwel is er een geheimschrift dat ontcijferd moet worden, vaak in teamverband. De opdracht en ontcijfersleutel worden op verschillende gsm’s getoond zodat niet een iemand bezig is maar dat er samengewerkt moet worden. In ruil voor deze opdrachten krijgen teams geld of ingrediënten voor het tegengif. <br>

## Voorbeeld puzzel
- Locatie: groenplaats
- Opdracht: maak een foto van het opschrift onderaan het standbeeld, zodat het kader tegen de rand van de foto valt.
- Sleutel: filter, gekleurd vlak met uitsparingen dat over de foto gelegd kan worden, zo ziet men de letters die men kan gebruiken om de oplossing te zoeken
- Vraag: hoe werd dit plein ooit genoemd? Je krijgt 1 letter cadeau die niet van het opschrift komt
![Antwoordrooster](/doc/images/Antwoordrooster.jpg)

- Antwoord: Place Bonaparte 
![Voorbeeld puzzel](/doc/images/Voorbeeldpuzzel.jpg)

Sprint 1
- as a user I want to be able to start a game
    - On the start screen in the app, click on the start button => new view => fill in detail about game (amount of players, playtime...) => click ok => data to api => api makes new game and returns the id => in game view
    - Sequence diagram:
    ![Sequence diagram CA18AHF-1](/doc/images/CA18AHF-1.png)
 - as a user I want to be able to join a game
    - On the start screen in the app click on the join button => join view => fill in game id => API adds player to game, returns teams => next screen/popup, choose team => API adds player to team, returns OK => in game view
    - Sequence diagram:
    ![Sequence diagram CA18AHF-2](/doc/images/CA18AHF-2.png)
- as a user I want to be able to receive puzzles/challenges when i arrive on the next location
- as a user I want a puzzle where I need to find things in the location to solve it

## User Stories
 * **Game session**
    * as a user I want to be able to start a game
    * as a user I want to be able to join a game
    * as a user I want to be able to end a game manually
    * as a user I want the game to end as soon as a team completed the cure
    * as a user I want to be able to print/share my teams score after the game ends

* **Location**
    * as a user I want to be able to see a map with the next location on
    * as a user I want to be able to receive puzzles/challenges when i arrive on the next location
    * as a user I want to see a timer how long I can stay in the current location
    * as a user I want to be able to see which ingredients I still have to collect 

* **Rewards/inventory**
    * as a user I want to be able to see the rewards I collected
    * as a user I want to see which ingrediënts I still need to collect
    * as a user I want to receive a reward when I solve a puzzle/challenge
    * as a user I want to receive the next location when I completed a puzzle

* **Shop**
    * as a user I want to spend in game money to decrease the time that the other team can spend in a location
    * as a user I want to spend in game money to give one of the other teams a temporary blackout
    * as a user I want to spend in game money to see the location of the other team(s)
    * as a user I want to spend in game money to steal an ingredient from another team
    * as a user I want to spend in game money to receive tips for a puzzle/challenge
    * as a user I want to spend in game money to spend more time in a location
    * as a user I want to buy the missing ingredients with in game money

* **Education**
    * as a user I want to have to use information about the location in the puzzles/challenges (information to be found on site)
    * as a user I want to see ‘did-you-knows’ on a location 
    * as a user I want to be able to find clues in the ‘did-you-knows’  

* **Puzzles**
    * as a user I want a puzzle where I need to find things in the location to solve it
    * as a user I want a puzzle where I need to solve a multiple choice question within a limited time 
    * as a user I want to fill in a crossword puzzle
    * as a user I want to have to find a code in an (interactive) image to solve the puzzle
    * as a user I want a simon says alike puzzle 
    * as a user I want to be able to solve more than one puzzle for extra rewards (even though I already know my next location)

## Software Architecture

![Software Architecture diagram](/doc/images/SoftwareArchitecture.jpg)
De client is een android app, geschreven in Java. De server zal worden gehost op Azure en bestaat uit een ASP.NET Core MVC Web API met Entity Framework om te communiceren met de SQL database.
