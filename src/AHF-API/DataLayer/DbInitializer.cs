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

                /*Location l1 = new Location(51.229023, 4.404622, "MAS", 300);
                Location l2 = new Location(51.216968, 4.409315, "Rubenshuis", 300);
                Location l3 = new Location(51.222759, 4.397382, "Het Steen", 300);
                l1.Quiz = initialiseQuizMas(context);*/
                Game g1 = new Game();
                g1.Teams.Add(t1);
                g1.Teams.Add(t2);
                g1.Teams.Add(t3);

                /*g1.Locations.Add(l1);
                g1.Locations.Add(l2);
                g1.Locations.Add(l3);*/

                createTestInventoryItems(context);

                context.Inventories.Add(inventoryT1);
                context.Inventories.Add(inventoryT2);
                context.Inventories.Add(inventoryT3);

                context.Teams.Add(t1);
                context.Teams.Add(t2);
                context.Teams.Add(t3);

                /*context.Locations.Add(l1);
                context.Locations.Add(l2);
                context.Locations.Add(l3);*/

                context.Games.Add(g1);

                //context.SaveChanges();
            }
            if (!context.Locations.Any())
            {
                Location l1 = new Location(51.229023, 4.404622, "MAS", 300);
                Location l2 = new Location(51.216968, 4.409315, "Rubenshuis", 300);
                Location l3 = new Location(51.222759, 4.397382, "Het Steen", 300);
                l1.Quiz = initialiseQuizMas(context);

                context.Locations.Add(l1);
                context.Locations.Add(l2);
                context.Locations.Add(l3);

            }

            if (!context.SubstitionPuzzles.Any())
            {
                SubstitionPuzzles sub = new SubstitionPuzzles();
                sub.Key = "MAS";
                sub.ClearText = "museumant";
                sub.Solution = "dead skull";
                context.SubstitionPuzzles.Add(sub);
                context.SaveChanges();
                                
            }
            

            context.SaveChanges();
        }

        private static List<Quizpuzzles> initialiseQuizMas (GameContext context)
        {
            List<Quizpuzzles> qmas = new List<Quizpuzzles>();
            Quizpuzzles q1 = new Quizpuzzles();
            Quizpuzzles q2 =  new Quizpuzzles();
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
    }
}
