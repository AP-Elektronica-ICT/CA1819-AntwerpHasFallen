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

                Game g1 = new Game();
                g1.Teams.Add(t1);
                g1.Teams.Add(t2);
                g1.Teams.Add(t3);

                createTestInventoryItems(context);

                context.Inventories.Add(inventoryT1);
                context.Inventories.Add(inventoryT2);
                context.Inventories.Add(inventoryT3);

                context.Teams.Add(t1);
                context.Teams.Add(t2);
                context.Teams.Add(t3);

                context.Games.Add(g1);

                //context.SaveChanges();
            }
            if (!context.Locations.Any())
            {
                Location l1 = new Location(51.229023, 4.404622, "MAS", 300);
                Location l2 = new Location(51.216968, 4.409315, "Rubenshuis", 300);
                Location l3 = new Location(51.222759, 4.397382, "Het Steen", 300);

                context.Locations.Add(l1);
                context.Locations.Add(l2);
                context.Locations.Add(l3);

            }

            if (!context.Quizpuzzles.Any())
            {
                Quizpuzzles quiz = new Quizpuzzles();


                quiz.Answers = "1008,1420,1052";
                quiz.Question = "Wanneer heeft Antwerpen zijn Stadszegel gekregen";
                quiz.CorrectAnswer = "1008";
                context.Quizpuzzles.Add(quiz);
                context.SaveChanges();
            }

            context.SaveChanges();
        }

        private static void createTestInventoryItems(GameContext context)
        {
            ShopItem s1 = new ShopItem()
            {
                Description = "testDescription for a shopItem that has a certain lenght, filled with some random characters. zzçu'tàé ",
                Name = "TestItem1"
            };
            ShopItem s2 = new ShopItem()
            {
                Description = "testDescription for a shopItem with a different length",
                Name = "TestItem2"
            };
            ShopItem s3 = new ShopItem()
            {
                Description = "testDescription for a shopItem that has a certain lenght, filled with some random characters. zzçu'tàé. \n and added a new line ",
                Name = "TestItem3"
            };

            Ingredient i1 = new Ingredient()
            {
                Name = "TestIngredient1"
            };

            Ingredient i2 = new Ingredient()
            {
                Name = "TestIngredient2"
            };

            Ingredient i3 = new Ingredient()
            {
                Name = "TestIngredient3"
            };

            context.ShopItems.Add(s1);
            context.ShopItems.Add(s2);
            context.ShopItems.Add(s3);

            context.Ingredients.Add(i1);
            context.Ingredients.Add(i2);
            context.Ingredients.Add(i3);

        }
    }
}
