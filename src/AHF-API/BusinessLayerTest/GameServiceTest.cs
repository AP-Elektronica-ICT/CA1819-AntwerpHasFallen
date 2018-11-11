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

namespace BusinessLayerTest
{
    public class GameServiceTest
    {
        private GameContext gameContext;

        public GameServiceTest()
        {
            InitContext();
        }

        private void InitContext()
        {
            var builder = new DbContextOptionsBuilder<GameContext>().UseInMemoryDatabase();
            var context = new GameContext(builder.Options);
            var games = Enumerable.Range(1, 5).Select(i =>
                 new Game
                 {
                     //Id = i - 1,
                     Teams = Enumerable.Range(1, 3).Select(j =>
                        new Team
                        {
                            //Id = i + j - 2,
                            Name = $"Team{i}{j}",
                            Players = Enumerable.Range(1, 2).Select(k =>
                                new Player {
                                    //Id = i + j + k - 3,
                                    GameId = i,
                                    TeamId = j
                            }).ToList()
                        }).ToList()
                 });
            foreach(Game g in games)
                foreach(Team t in g.Teams)
                {
                    foreach (Player p in t.Players)
                        context.Players.Add(p);
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
            GameService service = new GameService(gameContext);
            bool result = service.isDifferent(array);
            Assert.Equal(result, expected);
        }
    }
}
