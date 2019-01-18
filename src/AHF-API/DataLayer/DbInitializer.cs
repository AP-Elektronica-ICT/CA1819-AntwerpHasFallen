using DataLayer.Model;
using DataLayer.Model.InventoryModel;
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

                /*g1.Locations.Add(l1);
                g1.Locations.Add(l2);
                g1.Locations.Add(l3);*/
                
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
                createTestInventoryItems(context);

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

                //context.SaveChanges();
            }/*
            if (!context.Locations.Any())
            {
                Location l1 = new Location(51.229023, 4.404622, "MAS", 300);
                Location l2 = new Location(51.216968, 4.409315, "Rubenshuis", 300);
                Location l3 = new Location(51.222759, 4.397382, "Het Steen", 300);
                l1.Quiz = initialiseQuizMas(context);
                l1.subs = initialiseSubMas(context);
                context.Locations.Add(l1);
                context.Locations.Add(l2);
                context.Locations.Add(l3);

            }*/

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

            context.Items.Add(blackout);

            context.ShopItems.Add(shopBlackout);

            context.SaveChanges();
        }
        
    
        private static void createTestInventoryItems(GameContext context)
        {
            Item i1 = new Item()
            {
                Description = "testDescription for a shopItem that has a certain lenght, filled with some random characters. zzçu'tàé ",
                Name = "TestItem1",
                Type = Item.TYPE_ITEM
            };
            Item i2 = new Item()
            {
                Description = "testDescription for a shopItem with a different length",
                Name = "TestItem2",
                Type = Item.TYPE_ITEM
            };
            Item i3 = new Item()
            {
                Description = "testDescription for a shopItem that has a certain lenght, filled with some random characters. zzçu'tàé. \n and added a new line ",
                Name = "TestItem3",
                Type = Item.TYPE_ITEM
            };

            Item i4 = new Item()
            {
                Name = "TestIngredient1",
                Type = Item.TYPE_INGREDIENT
            };

            Item i5 = new Item()
            {
                Name = "TestIngredient2",
                Type = Item.TYPE_INGREDIENT
            };

            Item i6 = new Item()
            {
                Name = "TestIngredient3",
                Type = Item.TYPE_INGREDIENT
            };

            ShopItem s1 = new ShopItem()
            {
                Item = i1,
                Price = 10
            };

            ShopItem s2 = new ShopItem()
            {
                Item = i2,
                Price = 20
            };

            ShopItem s3 = new ShopItem()
            {
                Item = i3,
                Price = 30
            };

            ShopItem s4 = new ShopItem()
            {
                Item = i4,
                Price = 400
            };

            ShopItem s5 = new ShopItem()
            {
                Item = i5,
                Price = 500
            };

            ShopItem s6 = new ShopItem()
            {
                Item = i6,
                Price = 600
            };

            context.Items.Add(i1);
            context.Items.Add(i2);
            context.Items.Add(i3);

            context.Ingredients.Add(i4);
            context.Ingredients.Add(i5);
            context.Ingredients.Add(i6);

            context.ShopItems.Add(s1);
            context.ShopItems.Add(s2);
            context.ShopItems.Add(s3);
            context.ShopItems.Add(s4);
            context.ShopItems.Add(s5);
            context.ShopItems.Add(s6);
        }



        private static List<Anagram> initialiseAnagramMas(GameContext context)
        {
            List<Anagram> anagrammas = new List<Anagram>();

            Anagram anagram = new Anagram();
            anagram.scrambled = "uwrenbalvodalde";
            anagram.sollution = "wandelboulevard";
            anagram.tip = "Ronddom het MAS vind je deze prachtige ... ";

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
            sub.Solution = "dead skull";
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

            q2.Question = "Hoe hoog is het mas juist?";
            q2.Answers = "54 meter,68 meter,65 meter";
            q2.CorrectAnswer = "65 meter";

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
            anagram.scrambled = "vrauzetlkenmsgin";
            anagram.sollution = "kunstverzameling";
            anagram.tip = "In het Rubenshuis vind je een heuze ...";

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

            q2.Question = "Wanneer heeft Rubens het Rubenshuis gekocht?";
            q2.Answers = "1640,1610,1625";
            q2.CorrectAnswer = "1610";

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
            dad.Question = "Geef de chronologische volgorde van deze levensgebeurtenissen van Rubens";
            dad.Answers = "Reis naar Italië,Hofschilder van Albrecht en Isabella,Naar Latijnse school,Meester bij Sint-Lucasgilde";
            dad.CorrectOrder = "Naar Latijnse school,Meester bij Sint-Lucasgilde,Reis naar Italië,Hofschilder van Albrecht en Isabella";
            context.Dadpuzzles.Add(dad);


            dadruben.Add(dad);
            context.SaveChanges();
            return dadruben;

        }

        private static List<Anagram> initialiseAnagramSteen(GameContext context)
        {
            List<Anagram> anagramsteen = new List<Anagram>();

            Anagram anagram = new Anagram();
            anagram.scrambled = "vrauzetlkenmsgin";
            anagram.sollution = "kunstverzameling";
            anagram.tip = "In het Rubenshuis vind je een heuze ...";

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
            q1.Question = "Waarvoor werd het Steen vroeger?";
            q1.Answers = "Verdedigingsburcht,Gevangenis,Museum";
            q1.CorrectAnswer = "Gevangenis";

            q2.Question = "In welk jaar werd het Steen voor deze functie gebruikt?";
            q2.Answers = "1820,1823,1825";
            q2.CorrectAnswer = "1823";

            context.Quizpuzzles.Add(q1);
            context.Quizpuzzles.Add(q2);

            quizruben.Add(q1);
            quizruben.Add(q2);
            context.SaveChanges();

            return quizruben;
        }


    }
}
