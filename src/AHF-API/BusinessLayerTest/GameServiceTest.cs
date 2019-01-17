using System;
using Xunit;
using Moq;
using BusinessLayer;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.InMemory;
using Microsoft.Extensions.DependencyInjection;
using DataLayer;
using System.Linq;
using DataLayer.Model;
using DataLayer.Model.InventoryModel;
using System.Collections.Generic;

namespace BusinessLayerTest
{
    public class GameServiceTest
    {
        private static GameContext gameContext;
        private static readonly GameService service;

        static GameServiceTest()
        {
            InitContext();
            service = new GameService(gameContext);
        }

        private static void InitContext()
        {
            var builder = new DbContextOptionsBuilder<GameContext>().UseInMemoryDatabase();
            var context = new GameContext(builder.Options);
            List<Item> items = new List<Item>()
            {
                new Item()
                {
                    Description = "TestDescription 1",
                    Name = "item 1",
                    Type = Item.TYPE_ITEM
                },
                new Item()
                {
                    Description = "TestDescription 2",
                    Name = "item 2",
                    Type = Item.TYPE_ITEM
                },
                new Item()
                {
                    Description = "TestDescription 3",
                    Name = "item 3",
                    Type = Item.TYPE_ITEM
                }
            };
            List<Item> ingredients = new List<Item>()
            {
                new Item()
                {
                    Description = "TestDescription 1",
                    Name = "ingredient 1",
                    Type = Item.TYPE_INGREDIENT
                },
                new Item()
                {
                    Description = "TestDescription 2",
                    Name = "ingredient 2",
                    Type = Item.TYPE_INGREDIENT
                },
                new Item()
                {
                    Description = "TestDescription 3",
                    Name = "ingredient 3",
                    Type = Item.TYPE_INGREDIENT
                }
            };
            var games = Enumerable.Range(1, 5).Select(i =>
                 new Game
                 {
                     Teams = Enumerable.Range(1, 3).Select(j =>
                        new Team
                        {
                            Name = $"Team{i}{j}",
                            Money = 100,
                            Players = Enumerable.Range(1, 2).Select(k =>
                                new Player {
                                    GameId = i,
                                    TeamId = j
                                }).ToList(),
                            Inventory = new Inventory()
                            {
                                Ingredients = Enumerable.Range(1, j).Select(l =>
                                     new InventoryItem()
                                     {
                                         Quantity = l,
                                         Item = ingredients[l - 1]
                                     }).ToList(),
                                Items = Enumerable.Range(1, 3).Select(l =>
                                     new InventoryItem()
                                     {
                                         Quantity = l,
                                         Item = items[l - 1]
                                     }).ToList()
                            }
                        }).ToList()
                 });
            foreach (Item i in items)
                context.Items.Add(i);
            foreach (Item i in ingredients)
                context.Items.Add(i);
            foreach (Game g in games)
                foreach (Team t in g.Teams)
                {
                    foreach (Player p in t.Players)
                        context.Players.Add(p);
                    context.Inventories.Add(t.Inventory);
                    context.Teams.Add(t);
                }
            context.Games.AddRange(games);
            context.SaveChanges();
            gameContext = context;
        }

        [Theory]
        [InlineData("test test", "testtest")]
        [InlineData("i is tt   bla", "iisttbla")]
        [InlineData("nospace", "nospace")]
        [InlineData("       ", "")]
        public void RemoveWhiteSpacesTest(string s, string expected)
        {
            GameService service = new GameService(gameContext);
            string result = service.RemoveWhiteSpaces(s);
            Assert.Equal(result, expected);
        }

        [Theory]
        [InlineData(new string[] { "team 1", "Team 1", "Team 2" }, true)]
        [InlineData(new string[] { "team 1", "team 1", "Team 2" }, false)]
        [InlineData(new string[] { "team 1", "Team 1" }, true)]
        public void IsDifferentTest(string[] array, bool expected)
        {
            bool result = service.isDifferent(array);
            Assert.Equal(result, expected);
        }

        //FAILS WHEN DELETEGAMETEST RAN FIRST!!
        [Theory]
        [InlineData(1)]
        [InlineData(2)]
        [InlineData(3)]
        [InlineData(4)]
        [InlineData(5)]
        public void GetExistingGameByIdTest(int id)
        {
            Game game = service.GetGame(id);
            Assert.Equal(id, game.Id);
        }

        [Theory]
        [InlineData(6)]
        [InlineData(7)]
        [InlineData(8)]
        [InlineData(9)]
        [InlineData(10)]
        public void GetNotExistingGameByIdTest(int id)
        {
            Game game = service.GetGame(id);
            Assert.Null(game);
        }

        [Theory]
        [InlineData(1, true)]
        [InlineData(2, true)]
        [InlineData(3, true)]
        [InlineData(4, true)]
        [InlineData(5, true)]
        [InlineData(6, false)]
        [InlineData(7, false)]
        [InlineData(8, false)]
        [InlineData(9, false)]
        [InlineData(10, false)]
        public void DeleteGameTest(int id, bool expected)
        {
            bool result = service.deleteGame(id);
            Assert.Equal(expected, result);
        }

        [Theory]
        [InlineData(1)]
        [InlineData(2)]
        [InlineData(3)]
        [InlineData(4)]
        [InlineData(5)]
        public void CreateFinishedGameTest(int gameId)
        {
            Game inputGame = gameContext.Games.Include(g => g.Teams).ThenInclude(t => t.Inventory).SingleOrDefault(g => g.Id == gameId);
            FinishedGame result = service.saveGameStats(inputGame, inputGame.Teams);
            Assert.Equal(gameId, result.GameId);
            string expectedLeaderboard = "";
            for(int i = 0; i<3; i++)
                expectedLeaderboard += inputGame.Teams[i].Name + ":" + inputGame.Teams[i].Inventory.Ingredients.Count + "/";
            Assert.Equal(expectedLeaderboard, result.TeamsLeaderboard);
            Assert.Equal(inputGame.Teams[2].Name, result.Winner);
        }
    }
}
