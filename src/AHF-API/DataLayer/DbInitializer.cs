﻿using DataLayer.Model;
using DataLayer.Model.InventoryModel;
using DataLayer.Model.QuestionModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DataLayer
{
    public class DbInitializer
    {
        public static void Initialize(GameContext context)
        {
            context.Database.EnsureCreated();
            
            if (!context.Games.Any())
            {
                Team t1 = new Team("testTeam", 100);
                Inventory inventoryT1 = new Inventory();
                t1.Inventory = inventoryT1;

                Team t2 = new Team("otherTeam", 100);
                Inventory inventoryT2 = new Inventory();
                t2.Inventory = inventoryT2;

                Team t3 = new Team("newTeam", 100);
                Inventory inventoryT3 = new Inventory();
                t3.Inventory = inventoryT3;

                Location l1 = new Location(51.229023, 4.404622, "MAS", 300);
                Location l2 = new Location(51.216968, 4.409315, "Rubenshuis", 300);
                Location l3 = new Location(51.222759, 4.397382, "Het Steen", 300);
                
                Game g1 = new Game();
                g1.Teams.Add(t1);
                g1.Teams.Add(t2);
                g1.Teams.Add(t3);
                
                l1.Quiz = initialiseQuizMas(context);
                l1.subs = initialiseSubMas(context);
                l1.dads = initialiseDadMas(context);
                l1.anagrams = initialiseAnagramMas(context);

                l2.Quiz = initialiseQuizRuben(context);
                l2.subs = initialiseSubRuben(context);
                l2.dads = initialiseDadRuben(context);
                l2.anagrams = initialiseAnagramRuben(context);

                l3.Quiz = initialiseQuizSteen(context);
                l3.subs = initialiseSubSteen(context);
                l3.dads = initialiseDadSteen(context);
                l3.anagrams = initialiseAnagramSteen(context);



                createShopItems(context);
                createIngredients(context);

                context.Inventories.Add(inventoryT1);
                context.Inventories.Add(inventoryT2);
                context.Inventories.Add(inventoryT3);

                context.Teams.Add(t1);
                context.Teams.Add(t2);
                context.Teams.Add(t3);

                context.Locations.Add(l1);
                context.Locations.Add(l2);
                context.Locations.Add(l3);

                context.Games.Add(g1);
            }

            context.SaveChanges();
           
        }

        private static void createShopItems(GameContext context)
        {
            Item blackout = new Item
            {
                Name = "Blackout",
                Type = Item.TYPE_ITEM,
                Description = "Give one of the other teams a blackout for 30 seconds, so that they cannot do anything on the app during that time."
            };

            ShopItem shopBlackout = new ShopItem
            {
                Item = blackout,
                Price = 80
            };

            Item extraTime = new Item
            {
                Name = "Extra Time",
                Type = Item.TYPE_ITEM,
                Description = "Buy yourself some extra time to solve puzzles. Upon use this item gives your team 1 minute extra time."
            };

            ShopItem shopExtraTime = new ShopItem
            {
                Item = extraTime,
                Price = 100
            };

            Item penaltyTime = new Item
            {
                Name = "Penalty Time",
                Type = Item.TYPE_ITEM,
                Description = "Decrease the time a team can spend solving puzzles in a location by 30 seconds. Choose your target on use."
            };

            ShopItem shopPenaltyTime = new ShopItem
            {
                Item = penaltyTime,
                Price = 90
            };

            context.Items.Add(blackout);
            context.Items.Add(penaltyTime);
            context.Items.Add(extraTime);

            context.ShopItems.Add(shopBlackout);
            context.ShopItems.Add(shopPenaltyTime);
            context.ShopItems.Add(shopExtraTime);

            context.SaveChanges();
        }
        
        private static void createIngredients(GameContext context)
        {
            Item sorbitol = new Item
            {
                Name = "sorbitol",
                Type = Item.TYPE_INGREDIENT
            };

            Item citroenzuur = new Item
            {
                Name = "citroenzuur",
                Type = Item.TYPE_INGREDIENT
            };

            Item kaliumsorbaat = new Item
            {
                Name = "kaliumsorbaat",
                Type = Item.TYPE_INGREDIENT
            };

            Item natriumsaccharinaat = new Item
            {
                Name = "natriumsaccharinaat",
                Type = Item.TYPE_INGREDIENT
            };

            Item dextromethorfanhydrobromide = new Item
            {
                Name = "Dextromethorfanhydrobromide",
                Type = Item.TYPE_INGREDIENT
            };

            ShopItem shopSorbitol = new ShopItem
            {
                Item = sorbitol,
                Price = 300
            };

            ShopItem shopCitroenzuur = new ShopItem
            {
                Item = citroenzuur,
                Price = 300
            };

            ShopItem shopKaliumsorbaat = new ShopItem
            {
                Item = kaliumsorbaat,
                Price = 300
            };

            ShopItem shopNatriumsaccharinaat = new ShopItem
            {
                Item = natriumsaccharinaat,
                Price = 300
            };

            ShopItem shopDextromethorfanhydrobromide = new ShopItem
            {
                Item = dextromethorfanhydrobromide,
                Price = 300
            };

            context.Ingredients.Add(sorbitol);
            context.Ingredients.Add(citroenzuur);
            context.Ingredients.Add(kaliumsorbaat);
            context.Ingredients.Add(natriumsaccharinaat);
            context.Ingredients.Add(dextromethorfanhydrobromide);

            context.ShopItems.Add(shopSorbitol);
            context.ShopItems.Add(shopCitroenzuur);
            context.ShopItems.Add(shopKaliumsorbaat);
            context.ShopItems.Add(shopNatriumsaccharinaat);
            context.ShopItems.Add(shopDextromethorfanhydrobromide);
        }
    
        private static List<Anagram> initialiseAnagramMas(GameContext context)
        {
            List<Anagram> anagrammas = new List<Anagram>();

            Anagram anagram = new Anagram();
            anagram.Scrambled = "uwrenbalvodalde";
            anagram.Sollution = "wandelboulevard";
            anagram.Tip = "Rondom het MAS vind je deze prachtige ... ";
            anagram.Difficulty = "medium";

            context.AnagramPuzzles.Add(anagram);
            anagrammas.Add(anagram);
            context.SaveChanges();
            return anagrammas;
        }

        private static List<DAD> initialiseDadMas(GameContext context)
        {
            List<DAD> dadmas = new List<DAD>();

            DAD dad = new DAD();
            dad.Question = "Geef de volgorde waarin deze gebouwen gebouwd zijn van oudste naar nieuwste";
            dad.Answers = "MAS,Centraal station,Boerentoren,Kathedraal";
            dad.CorrectOrder = "Kathedraal,Centraal station,Boerentoren,MAS";
            dad.Difficulty = "medium";
            context.Dadpuzzles.Add(dad);


            dadmas.Add(dad);
            context.SaveChanges();
            return dadmas;

        }
        private static List<SubstitionPuzzles> initialiseSubMas(GameContext context)
        {
            List<SubstitionPuzzles> submas = new List<SubstitionPuzzles>();

            SubstitionPuzzles sub = new SubstitionPuzzles();
            sub.Key = "MAS";
            sub.ClearText = "pespscgld";
            sub.Solution = "deadskull";
            sub.Difficulty = "high";
            context.SubstitionPuzzles.Add(sub);


            submas.Add(sub);
            context.SaveChanges();

            return submas;
        }
        private static List<Quizpuzzles> initialiseQuizMas(GameContext context)
        {
            List<Quizpuzzles> qmas = new List<Quizpuzzles>();
            Quizpuzzles q1 = new Quizpuzzles();
            Quizpuzzles q2 = new Quizpuzzles();
            q1.Question = "Wanneer heeft Antwerpen zijn stadszegel gekregen?";
            q1.Answers = "1008,1052,1485";
            q1.CorrectAnswer = "1008";
            q1.Difficulty = "low";

            q2.Question = "Hoe hoog is het mas juist?";
            q2.Answers = "54 meter,68 meter,65 meter";
            q2.CorrectAnswer = "65 meter";
            q2.Difficulty = "low";

            context.Quizpuzzles.Add(q1);
            context.Quizpuzzles.Add(q2);

            qmas.Add(q1);
            qmas.Add(q2);
            context.SaveChanges();

            return qmas;
        }
        private static List<Anagram> initialiseAnagramRuben(GameContext context)
        {
            List<Anagram> anagramruben = new List<Anagram>();

            Anagram anagram = new Anagram();
            anagram.Scrambled = "vrauzetlkenmsgin";
            anagram.Sollution = "kunstverzameling";
            anagram.Tip = "In het Rubenshuis vind je een heuze ...";
            anagram.Difficulty = "medium";

            context.AnagramPuzzles.Add(anagram);
            anagramruben.Add(anagram);
            context.SaveChanges();
            return anagramruben;
        }

        private static List<DAD> initialiseDadRuben(GameContext context)
        {
            List<DAD> dadruben = new List<DAD>();

            DAD dad = new DAD();
            dad.Question = "Geef de chronologische volgorde van deze levensgebeurtenissen van Rubens";
            dad.Answers = "Reis naar Italië,Hofschilder van Albrecht en Isabella,Naar Latijnse school,Meester bij Sint-Lucasgilde";
            dad.CorrectOrder = "Naar Latijnse school,Meester bij Sint-Lucasgilde,Reis naar Italië,Hofschilder van Albrecht en Isabella";
            dad.Difficulty = "medium";
            context.Dadpuzzles.Add(dad);


            dadruben.Add(dad);
            context.SaveChanges();
            return dadruben;

        }
        private static List<SubstitionPuzzles> initialiseSubRuben(GameContext context)
        {
            List<SubstitionPuzzles> subruben = new List<SubstitionPuzzles>();

            SubstitionPuzzles sub = new SubstitionPuzzles();
            sub.Key = "rubens";
            sub.ClearText = "sbookdbqdaulrdfy ";
            sub.Solution = "herdenkingskapel";
            sub.Difficulty = "high";
            context.SubstitionPuzzles.Add(sub);


            subruben.Add(sub);
            context.SaveChanges();

            return subruben;
        }
        private static List<Quizpuzzles> initialiseQuizRuben(GameContext context)
        {
            List<Quizpuzzles> quizruben = new List<Quizpuzzles>();
            Quizpuzzles q1 = new Quizpuzzles();
            Quizpuzzles q2 = new Quizpuzzles();
            q1.Question = "Wat is de volledige naam van Rubens?";
            q1.Answers = "Peter Paul Rubens,Raoul Peter Rubens,Paul Frank Rubens";
            q1.CorrectAnswer = "Peter Paul Rubens";
            q1.Difficulty = "low";

            q2.Question = "Wanneer heeft Rubens het Rubenshuis gekocht?";
            q2.Answers = "1640,1610,1625";
            q2.CorrectAnswer = "1610";
            q2.Difficulty = "low";

            context.Quizpuzzles.Add(q1);
            context.Quizpuzzles.Add(q2);

            quizruben.Add(q1);
            quizruben.Add(q2);
            context.SaveChanges();

            return quizruben;
        }

        private static List<DAD> initialiseDadSteen(GameContext context)
        {
            List<DAD> dadruben = new List<DAD>();

            DAD dad = new DAD();
            dad.Question = "Geef de functionaliteiten van het Steen in chronologische volgorde";
            dad.Answers = "Woning,Burcht,Museum,Gevangenis";
            dad.CorrectOrder = "Burcht,Woning,Gevangenis,Museum";
            dad.Difficulty = "medium";
            context.Dadpuzzles.Add(dad);


            dadruben.Add(dad);
            context.SaveChanges();
            return dadruben;

        }

        private static List<Anagram> initialiseAnagramSteen(GameContext context)
        {
            List<Anagram> anagramsteen = new List<Anagram>();

            Anagram anagram = new Anagram();
            anagram.Scrambled = "tvslagpssopiaal";
            anagram.Sollution = "visopslagplaats";
            anagram.Tip = "Oude functionaliteit van het Steen";
            anagram.Difficulty = "medium";

            context.AnagramPuzzles.Add(anagram);
            anagramsteen.Add(anagram);
            context.SaveChanges();
            return anagramsteen;
        }
        private static List<SubstitionPuzzles> initialiseSubSteen(GameContext context)
        {
            List<SubstitionPuzzles> subruben = new List<SubstitionPuzzles>();

            SubstitionPuzzles sub = new SubstitionPuzzles();
            sub.Key = "steen";
            sub.ClearText = "zhyxmszivvb ";
            sub.Solution = "houtzagerij";
            sub.Difficulty = "high";
            context.SubstitionPuzzles.Add(sub);


            subruben.Add(sub);
            context.SaveChanges();

            return subruben;
        }
        private static List<Quizpuzzles> initialiseQuizSteen(GameContext context)
        {
            List<Quizpuzzles> quizruben = new List<Quizpuzzles>();
            Quizpuzzles q1 = new Quizpuzzles();
            Quizpuzzles q2 = new Quizpuzzles();
            q1.Question = "Waarvoor werd het Steen vroeger gebruikt?";
            q1.Answers = "Verdedigingsburcht,Gevangenis,Museum";
            q1.CorrectAnswer = "Gevangenis";
            q1.Difficulty = "low";

            q2.Question = "In welk jaar werd het Steen voor deze functie gebruikt?";
            q2.Answers = "1820,1823,1825";
            q2.CorrectAnswer = "1823";
            q2.Difficulty = "low";

            context.Quizpuzzles.Add(q1);
            context.Quizpuzzles.Add(q2);

            quizruben.Add(q1);
            quizruben.Add(q2);
            context.SaveChanges();

            return quizruben;
        }


    }
}
