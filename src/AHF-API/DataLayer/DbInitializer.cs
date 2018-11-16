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
                Team t1 = new Team("testTeam");

                Team t2 = new Team("otherTeam");

                Team t3 = new Team("newTeam");

                Game g1 = new Game();
                g1.Teams.Add(t1);
                g1.Teams.Add(t2);
                g1.Teams.Add(t3);

                createTestInventoryItems(context);

                context.Teams.Add(t1);
                context.Teams.Add(t2);
                context.Teams.Add(t3);

                context.Games.Add(g1);

                context.SaveChanges();
            }
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
